package com.wpp.fc.core.converter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author wpp
 * @desc <pre>
 *   将普通字符串按照Base64编码后存储，从DB中读取时解码成普通字符串
 *   --
 *   注意事项：使用者需要注意数据库字符串的长度，使用Base64编码后字符串比原始字符串更长
 * </pre>
 * @see
 * @since 2023/5/10
 */
public class Base64Converter extends StringConverter {

  public Base64Converter() {

  }

  @Override
  public String read(String value, String param) {
    return new String(Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8)));
  }

  @Override
  public String write(String value, String param) {
    return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }

}
