package com.wpp.fc.common;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class NoOpConverter implements Converter<Object> {

  @Override
  public Object read(Object value, String params) {
    return value;
  }

  @Override
  public Object write(Object value, String params) {
    return value;
  }
}
