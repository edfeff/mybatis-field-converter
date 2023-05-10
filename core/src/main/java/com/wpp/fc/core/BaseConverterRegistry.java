package com.wpp.fc.core;

import com.wpp.fc.common.Converter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public abstract class BaseConverterRegistry implements ConverterRegistry {

  protected Map<Class<? extends Converter>, Converter> converterMap = new HashMap<>();

  @Override
  public void add(Converter converter) {
    converterMap.put(converter.getClass(), converter);
  }

  @Override
  public Converter get(Class<? extends Converter> clazz) {
    return converterMap.get(clazz);
  }

  @Override
  public void remove(Class<? extends Converter> clazz) {
    converterMap.remove(clazz);
  }
}
