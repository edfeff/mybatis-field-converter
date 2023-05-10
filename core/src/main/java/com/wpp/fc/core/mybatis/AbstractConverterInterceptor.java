package com.wpp.fc.core.mybatis;

import java.util.Properties;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public abstract class AbstractConverterInterceptor implements Interceptor {

  protected final Log log = LogFactory.getLog(getClass());

  protected Properties properties;

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public Properties getProperties() {
    return properties;
  }

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    Object result = internalIntercept(invocation);

    return result;
  }

  protected abstract Object internalIntercept(Invocation invocation) throws Throwable;
}
