package com.wpp.fc.common;

import java.util.Properties;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public interface Converter<T> {

  /**
   * 数据库读取出的值进行转换
   */
  T read(T value, String params);

  /**
   * 写入数据库之前进行转换
   */
  T write(T value, String params);

  /**
   * 初始化
   */
  default void init(Properties properties) {}
}
