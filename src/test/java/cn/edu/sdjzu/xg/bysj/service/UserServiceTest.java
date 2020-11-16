package cn.edu.sdjzu.xg.bysj.service;

import cn.edu.sdjzu.xg.bysj.domain.User;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Test
    public void login() throws SQLException {
        User user = new User("ssss","ssss",null,null);
        UserService.getInstance().login(user);
    }
}