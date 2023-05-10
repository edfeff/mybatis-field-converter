package com.wpp.fc.core.mybatis;

import com.wpp.fc.common.Convert;
import com.wpp.fc.common.Converter;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Invocation;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class DefaultWriteHandler extends BaseConverterFieldHandler implements WriteHandler {

  private Method getParameterObject;

  public DefaultWriteHandler() {
    init();
  }

  private void init() {
    try {
      getParameterObject = ParameterHandler.class.getMethod("getParameterObject");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Object handleParameterHandler(Invocation invocation)
    throws Throwable {

    if (getParameterObject == null) {
      return invocation.proceed();
    }

    Method method = invocation.getMethod();
    switch (method.getName()) {
      case "setParameters":
        return handleSetParameters(invocation);
      case "getParameterObject":
        return handleGetParameterObject(invocation);
    }
    return invocation.proceed();
  }


  private Object handleSetParameters(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();
    if (args == null || args.length == 0 || args[0] == null || !(args[0] instanceof PreparedStatement)) {
      return invocation.proceed();
    }
    Object target = invocation.getTarget();
    Object parameterObject = getParameterObject.invoke(target);
    convert(parameterObject);
    return invocation.proceed();
  }

  private Object handleGetParameterObject(Invocation invocation) throws Throwable {
    //todo 可能存在多次调用的问题，需要进行仅处理一次的判定
    Object target = invocation.getTarget();
    return convert(target);
  }

  @Override
  public Object invokeConvert(Converter converter, Object source, Convert convert) {
    return converter.write(source, convert.param());
  }
}
