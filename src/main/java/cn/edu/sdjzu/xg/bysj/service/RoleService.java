package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.RoleDao;
import cn.edu.sdjzu.xg.bysj.domain.authority.Role;
import exception.BysjException;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;

public final class RoleService {
    private static final RoleDao roleDao = RoleDao.getInstance();
    private static final RoleService roleService = new RoleService();


    public static RoleService getInstance() {
        return roleService;
    }

    public static void main(String[] args) throws Exception {

//		for(int i = 3; i < 24; i++) {
//			Role civilRole = new Role(""+i, ""+i, "");
//			RoleService.getInstance().add(civilRole);
//		}
//		RoleService.getInstance().add(civilRole);

//		Role mgtRole = RoleService.getInstance().find(5);
//		RoleService.getInstance().delete(mgtRole);
	}

	public Collection<Role> findAll(Pagination pagination, String conditionList) throws SQLException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		Collection<Role> roles = roleDao.findAll(connection, pagination, conditionList);
		//释放连接
		JdbcHelper.close(connection);
		return roles;
	}

	public Role find(Integer id) throws SQLException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		Role role = roleDao.find(connection, id);
		//释放连接
		JdbcHelper.close(connection);
		return role;
	}

	public boolean update(Role role) throws SQLException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		boolean updated = roleDao.update(connection, role);
		//释放连接
		JdbcHelper.close(connection);
		return updated;
	}

	public boolean add(Role role) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		boolean added = roleDao.add(connection, role);
		JdbcHelper.close(connection);
		return added;
	}

	public boolean delete(Integer id) throws SQLException, BysjException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		boolean deleted = false;
		try {
			deleted = roleDao.delete(connection, id);
			return deleted;
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new BysjException("某员工或学生正在使用当前角色");
		} finally {
			//释放连接
			JdbcHelper.close(connection);
		}

	}

}
