package com.wpp.fc.samples.po;

import com.wpp.fc.common.Convert;
import com.wpp.fc.core.converter.AesConverter;
import com.wpp.fc.core.converter.Base64Converter;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class User {

  private long id;

  private String name;

  private int age;

  private String phone;

  private String address;

  @Convert(value = AesConverter.class, param = "${props:aes.key:123456}")
  private String password;

  @Convert(Base64Converter.class)
  private String b64;

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", age=" + age +
      ", phone='" + phone + '\'' +
      ", address='" + address + '\'' +
      ", password='" + password + '\'' +
      ", b64='" + b64 + '\'' +
      '}';
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getB64() {
    return b64;
  }

  public void setB64(String b64) {
    this.b64 = b64;
  }
}
