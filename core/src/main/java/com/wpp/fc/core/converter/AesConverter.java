package com.wpp.fc.core.converter;

import com.wpp.fc.core.ParamParser;
import com.wpp.fc.core.SimpleParamParser;
import com.wpp.fc.core.util.AesUtil;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class AesConverter extends StringConverter {

  private ParamParser paramParser = new SimpleParamParser();


  @Override
  public String read(String value, String params) {
    String key = paramParser.eval(params);
    if (key == null || key.length() == 0) {
      return value;
    }
    return AesUtil.decrypt(key, value);
  }

  @Override
  public String write(String value, String params) {
    String key = paramParser.eval(params);
    if (key == null || key.length() == 0) {
      return value;
    }
    return AesUtil.encrypt(key, value);
  }
}
