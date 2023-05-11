package com.wpp.fc.core.mybatis;

import com.wpp.fc.common.Convert;
import com.wpp.fc.common.Converter;
import com.wpp.fc.core.ConverterRegistry;
import com.wpp.fc.core.SpiConverterRegistry;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.invoker.Invoker;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public abstract class BaseConverterFieldHandler {

  protected static final ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
  protected static final Set<Class> notSupportConvertSet = Collections.synchronizedSet(new HashSet<>(16));
  protected static final Map<Class<?>, ConvertFields> classConvertFieldsMap = new ConcurrentHashMap<>(16);
  protected static final ConverterRegistry converterRegistry = new SpiConverterRegistry();


  protected Object convert(Object result) throws Throwable {
    if (result == null) {
      return null;
    }
    Class<?> clazz = result.getClass();
    if (clazz.isPrimitive()) {
      return result;
    }
    if (clazz.isArray()) {
      Object[] arr = (Object[]) result;
      for (int i = 0; i < arr.length; i++) {
        convert(arr[i]);
      }
    }
    if (Iterable.class.isAssignableFrom(clazz)) {
      Iterable iterable = (Iterable) result;
      Iterator it = iterable.iterator();
      while (it.hasNext()) {
        Object next = it.next();
        convert(next);
      }
    }

    result = doConvert(result, clazz);

    return result;
  }

  private Object doConvert(Object result, Class<?> clazz) throws Throwable {
    if (notSupportConvertSet.contains(clazz)) {
      return result;
    }
    ConvertFields cfs = getConvertFields(clazz);
    if (cfs == null) {
      notSupportConvertSet.add(clazz);
      return result;
    }
    return doConvert(result, clazz, cfs);
  }

  private Object doConvert(Object result, Class<?> clazz, ConvertFields cfs) throws Throwable {
    for (ConvertField cf : cfs.convertFields) {
      filedConvert(result, clazz, cf.field, cf.convert);
    }
    return result;
  }

  protected void filedConvert(Object result, Class<?> clazz, Field field, Convert convert) throws Throwable {
    Class<? extends Converter> keyClazz = convert.value();
    Converter converter = converterRegistry.get(keyClazz);
    if (converter == null) {
      return;
    }
    Reflector reflector = reflectorFactory.findForClass(clazz);
    Object source = null;

    if (reflector.hasGetter(field.getName())) {
      Invoker getInvoker = reflector.getGetInvoker(field.getName());
      source = getInvoker.invoke(result, null);
    } else {
      if (!field.isAccessible()) {
        field.setAccessible(true);
      }
      source = field.get(result);
    }

    Object dest = invokeConvert(converter, source, convert);

    if (dest != source) {
      if (reflector.hasSetter(field.getName())) {
        Invoker setInvoker = reflector.getSetInvoker(field.getName());
        setInvoker.invoke(result, new Object[]{dest});
      } else {
        field.set(result, dest);
      }
    }
  }

  public abstract Object invokeConvert(Converter converter, Object source, Convert convert);


  private ConvertFields getConvertFields(Class<?> clazz) {
    ConvertFields convertFields = classConvertFieldsMap.get(clazz);

    if (convertFields != null) {
      return convertFields;
    }

    convertFields = new ConvertFields();
    for (Field field : clazz.getDeclaredFields()) {
      if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
        continue;
      }
      Convert convert = field.getAnnotation(Convert.class);
      if (convert == null) {
        continue;
      }
      convertFields.convertFields.add(new ConvertField(field, convert));
    }

    if (!convertFields.convertFields.isEmpty()) {
      classConvertFieldsMap.put(clazz, convertFields);
      return convertFields;
    }

    return null;
  }

  static class ConvertField {

    Field field;
    Convert convert;

    public ConvertField(Field field, Convert convert) {
      this.field = field;
      this.convert = convert;
    }
  }

  static class ConvertFields {

    List<ConvertField> convertFields = new ArrayList<>();
  }


}
