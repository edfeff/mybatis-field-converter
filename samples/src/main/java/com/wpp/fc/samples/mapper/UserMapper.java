package com.wpp.fc.samples.mapper;

import com.wpp.fc.samples.po.User;
import java.util.List;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public interface UserMapper {

  List<User> list();

  int save(User user);

}
