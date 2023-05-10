package com.wpp.fc.core.mybatis;

import org.apache.ibatis.plugin.Invocation;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public interface WriteHandler {

  Object handleParameterHandler(Invocation invocation)
    throws Throwable;
}
