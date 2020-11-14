package cn.edu.sdjzu.xg.bysj.service;

import cn.edu.sdjzu.xg.bysj.dao.UserDao;
import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;

public final class UserService {
	private UserDao userDao = UserDao.getInstance();
	private static UserService userService = new UserService();
	
	public UserService() {
	}

	public static UserService getInstance(){
		return UserService.userService;
	}

	public Collection<User> getUsers(String condition, Pagination pagination) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		Collection<User> users = userDao.findAll(conn,condition,pagination);
		conn.close();
		return users;
	}
	
	public User getUser(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		User user = userDao.find(id,conn);
		conn.close();
		return user;
	}
	
	public boolean updateUser(User user) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean update = userDao.update(user,conn);
		conn.close();
		return update;
	}
	
	public String addUser(User user) throws SQLException{
		Connection conn = JdbcHelper.getConn();
		String token = userDao.add(user,conn);
		conn.close();
		return token;
	}

	public boolean deleteUser(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean delete =  userDao.delete(id,conn);
		conn.close();
		return delete;
	}

	public Actor login(User userToLogin) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		Actor actor = userDao.login(connection, userToLogin);
		connection.close();
		return actor;
	}
}
