package com.wpp.fc.core;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class SimpleParamParser implements ParamParser {

  public final String PATTERN_PREFIX = "${";
  public final String PATTERN_SUFFIX = "}";

  @Override
  public String eval(String param) {
    if (param == null || param.length() == 0) {
      return param;
    }
    if (param.startsWith(PATTERN_PREFIX) && param.endsWith(PATTERN_SUFFIX)) {
      return evalExpression(param);
    }
    return param;
  }

  private String evalExpression(String param) {
    String expression = param.substring(PATTERN_PREFIX.length(), param.length() - PATTERN_SUFFIX.length());
    String[] exs = expression.split(":");
    switch (exs[0]) {
      case "env":
        String value = System.getenv(exs[1]);
        if (value == null && exs.length == 3) {
          return exs[2];
        }
        return value;
      case "props":
        value = System.getProperty(exs[1]);
        if (value == null && exs.length == 3) {
          return exs[2];
        }
        return value;
    }
    return expression;
  }

}
