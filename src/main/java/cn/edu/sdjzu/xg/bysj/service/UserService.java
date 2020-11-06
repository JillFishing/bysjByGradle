package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.TeacherDao;
import cn.edu.sdjzu.xg.bysj.dao.UserDao;
import cn.edu.sdjzu.xg.bysj.domain.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import exception.UsernameDuplicateException;
import util.JdbcHelper;

import java.sql.*;

public final class UserService {
	private UserDao userDao = UserDao.getInstance();
	private static UserService userService = new UserService();
	
	public UserService() {
	}

	public static UserService getInstance(){
		return UserService.userService;
	}

	public String getUsers() throws SQLException {
		return userDao.findAll();
	}
	
	public User getUser(Integer id) throws SQLException {
		return userDao.find(id);
	}
	
	public boolean updateUser(User user,Connection conn) throws SQLException {
		return userDao.update(user,conn);
	}
	
	public int addUser(User user,Connection conn) throws SQLException{
			return userDao.add(user,conn);
	}

	public boolean deleteUser(Integer id) throws SQLException {
		return userDao.delete(id);
	}
	
	
	/*public User login(String username, String password) throws SQLException {
		Collection<User> users = this.getUsers();
		User desiredUser = null;
		for(User user:users){
			if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
				desiredUser = user;
			}
		}
		return desiredUser;
	}	*/
}
