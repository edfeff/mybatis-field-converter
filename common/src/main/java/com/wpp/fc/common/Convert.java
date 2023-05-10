package com.wpp.fc.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wpp
 * @desc <pre>字段转换器</pre>
 * @see
 * @since 2023/5/10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Convert {

  /**
   * 转换器实现类 默认不进行转换 优先执行
   */
  Class<? extends Converter> value() default NoOpConverter.class;

//  /**
//   * 多个转换器，在value之后按照顺序执行
//   */
//  Class<? extends Converter> values() default NoOpConverter.class;

  /**
   * 参数
   */
  String param() default "";
}
