package com.wpp.fc.core.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
@Intercepts({
  @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
  @Signature(type = ResultSetHandler.class, method = "handleCursorResultSets", args = {Statement.class}),
  @Signature(type = ResultSetHandler.class, method = "handleOutputParameters", args = {CallableStatement.class}),

  @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
  @Signature(type = ParameterHandler.class, method = "getParameterObject", args = {})

})
public class DefaultConverterInterceptor extends AbstractConverterInterceptor {

  private ReadHandler readHandler;
  private WriteHandler writeHandler;

  @Override
  public void setProperties(Properties properties) {
    super.setProperties(properties);
    init();
  }

  protected void init() {
    //todo 改为可配置
    this.readHandler = new DefaultReadHandler();
    this.writeHandler = new DefaultWriteHandler();
  }

  @Override
  protected Object internalIntercept(Invocation invocation) throws Throwable {
    if (invocation == null) {
      return null;
    }

    if (invocation.getTarget() instanceof ParameterHandler) {
      return handleParameterHandler(invocation);
    } else if (invocation.getTarget() instanceof ResultSetHandler) {
      return handleResultSetHandler(invocation);
    }

    return invocation.proceed();
  }

  protected Object handleResultSetHandler(Invocation invocation)
    throws Throwable {
    return readHandler.handleResultSetHandler(invocation);
  }

  private Object handleParameterHandler(Invocation invocation)
    throws Throwable {
    return writeHandler.handleParameterHandler(invocation);
  }

}
