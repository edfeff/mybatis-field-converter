package com.wpp.fc.core;

import com.wpp.fc.common.Converter;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class SpiConverterRegistry extends BaseConverterRegistry {

  public SpiConverterRegistry() {
    init();
  }

  private void init() {
    ServiceLoader<Converter> serviceLoader = ServiceLoader.load(Converter.class);
    Iterator<Converter> it = serviceLoader.iterator();
    while (it.hasNext()) {
      Converter converter = it.next();
      add(converter);
    }
  }

}
