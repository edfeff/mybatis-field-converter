package com.wpp.fc.common;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public interface ConverterFactory<T> {

  Converter<T> create(Class<? extends Converter<T>> clazz);
}
