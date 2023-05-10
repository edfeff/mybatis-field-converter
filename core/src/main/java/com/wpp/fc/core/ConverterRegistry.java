package com.wpp.fc.core;

import com.wpp.fc.common.Converter;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public interface ConverterRegistry {

  void add(Converter converter);

  Converter get(Class<? extends Converter> clazz);

  void remove(Class<? extends Converter> clazz);

}
