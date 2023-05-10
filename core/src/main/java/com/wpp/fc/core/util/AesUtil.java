package com.wpp.fc.core.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class AesUtil {

  private static final String KEY_ALGORITHM = "AES";
  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

  public static String encrypt(String aesKey, String content) {
    return encrypt(aesKey, content, null);
  }

  public static String encrypt(String aesKey, String content, String defaultStr) {
    try {
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
      byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
      cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(aesKey));
      byte[] result = cipher.doFinal(byteContent);
      return new String(Base64.getEncoder().encode(result));
    } catch (Exception ex) {
      Logger.getLogger(AesUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    return defaultStr;
  }

  public static String decrypt(String aesKey, String content) {
    return decrypt(aesKey, content, null);
  }

  public static String decrypt(String aesKey, String content, String defaultStr) {
    try {
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, getSecretKey(aesKey));
      byte[] result = cipher.doFinal(Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8)));
      return new String(result, StandardCharsets.UTF_8);
    } catch (Exception ex) {
      Logger.getLogger(AesUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    return defaultStr;
  }

  private static Key getSecretKey(String key) throws NoSuchAlgorithmException {
    KeyGenerator kg = null;
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    random.setSeed(key.getBytes());
    try {
      kg = KeyGenerator.getInstance(KEY_ALGORITHM);
      kg.init(256, random);
      SecretKey secretKey = kg.generateKey();
      return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(AesUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public static void main(String[] args) {
    System.out.println(AesUtil.encrypt("123456", "123456"));
    System.out.println(AesUtil.decrypt("123456", "4OyfmosI7xKY3WGrofnqyA=="));
  }
}
