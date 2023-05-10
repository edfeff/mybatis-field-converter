package com.wpp.fc.core.mybatis;

import com.wpp.fc.common.Convert;
import com.wpp.fc.common.Converter;
import java.lang.reflect.Method;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Invocation;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class DefaultReadHandler extends BaseConverterFieldHandler implements ReadHandler {

  public Object handleResultSetHandler(Invocation invocation) throws Throwable {
    Method method = invocation.getMethod();
    switch (method.getName()) {
      case "handleResultSets":
        return handleResultSets(invocation);
      case "handleCursorResultSets":
        return handleCursorResultSets(invocation);
      case "handleOutputParameters":
        return handleOutputParameters(invocation);
      default:
        return invocation.proceed();
    }
  }

  private Object handleResultSets(Invocation invocation) throws Throwable {
    Object result = invocation.proceed();
    result = convert(result);
    return result;
  }

  @Override
  public Object invokeConvert(Converter converter, Object source, Convert convert) {
    return converter.read(source, convert.param());
  }


  private Object handleCursorResultSets(Invocation invocation) {
    ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
    //TODO
    return null;
  }

  private Object handleOutputParameters(Invocation invocation) {
    ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
    //TODO
    return null;
  }


}
