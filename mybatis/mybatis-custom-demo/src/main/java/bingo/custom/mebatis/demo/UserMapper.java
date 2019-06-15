package bingo.custom.mebatis.demo;

import java.util.List;

public interface UserMapper {
    User selectByPrimaryKey(Integer userid);

    List<User> selectAll();
}