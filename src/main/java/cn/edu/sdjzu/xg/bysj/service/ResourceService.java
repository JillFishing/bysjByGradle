package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.ResourceDao;
import cn.edu.sdjzu.xg.bysj.domain.authority.Resource;
import exception.BysjException;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;

public final class ResourceService {
	private static final ResourceDao resourceDao = ResourceDao.getInstance();
	private static final ResourceService resourceService = new ResourceService();


	public static ResourceService getInstance() {
		return resourceService;
	}

	public Collection<Resource> findAll(Integer roleId) throws SQLException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		Collection<Resource> resources = resourceDao.findAll(connection, roleId);
		//释放连接
		JdbcHelper.close(connection);
		return resources;
	}

	public Collection<Resource> findAll(Pagination pagination, String conditionList) throws SQLException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		Collection<Resource> resources = resourceDao.findAll(connection, pagination, conditionList);
		//释放连接
		JdbcHelper.close(connection);
		return resources;

	}

	public Resource find(Integer id) throws SQLException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		Resource resource = resourceDao.find(connection, id);
		//释放连接
		JdbcHelper.close(connection);
		return resource;
	}

	public boolean update(Resource resource) throws SQLException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		boolean updated = resourceDao.update(connection, resource);
		//释放连接
		JdbcHelper.close(connection);
		return updated;
	}

	public boolean add(Resource resource) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		boolean added = resourceDao.add(connection, resource);
		JdbcHelper.close(connection);
		return added;
	}

	public boolean delete(Integer id) throws SQLException, BysjException {
		//获取数据库连接
		Connection connection = JdbcHelper.getConn();
		boolean deleted = false;
		try {
			deleted = resourceDao.delete(connection, id);
			return deleted;
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new BysjException("某角色正在使用当前资源");
		} finally {
			//释放连接
			JdbcHelper.close(connection);
		}

	}
}
