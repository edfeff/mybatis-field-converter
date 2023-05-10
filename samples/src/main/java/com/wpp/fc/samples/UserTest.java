package com.wpp.fc.samples;

import com.wpp.fc.samples.mapper.UserMapper;
import com.wpp.fc.samples.po.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author wpp
 * @desc <pre></pre>
 * @see
 * @since 2023/5/10
 */
public class UserTest {

  public static void main(String[] args) throws IOException {
    String resource = "com/wpp/fc/samples/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    SqlSession sqlSession = sqlSessionFactory.openSession(true);

    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User user = createUser();
    mapper.save(user);

    List<User> list = mapper.list();
    for (User u : list) {
      System.out.println(u);
    }

    sqlSession.close();
  }

  private static User createUser() {
    User user = new User();
    user.setName("test");
    user.setAge(90);
    user.setPhone("123678901");
    user.setPassword("testtest");
    user.setAddress("shanghai");
    user.setB64("testtesttest123ab");
    return user;
  }

}
