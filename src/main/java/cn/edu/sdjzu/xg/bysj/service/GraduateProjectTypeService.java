package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.GraduateProjectTypeDao;
import cn.edu.sdjzu.xg.bysj.domain.GraduateProject;
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectType;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectTypeService {
	private static GraduateProjectTypeService graduateProjectTypeService = new GraduateProjectTypeService();
	private GraduateProjectTypeDao graduateProjectTypeDao = GraduateProjectTypeDao.getInstance();
	private GraduateProjectTypeService(){}
	
	public static GraduateProjectTypeService getInstance(){
		return graduateProjectTypeService;
	}

	public Collection<GraduateProjectType> findAll(String condition, Pagination pagination) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		Collection<GraduateProjectType> gpts =graduateProjectTypeDao.finaAll(condition,pagination,conn);
		conn.close();
		return gpts;
	}

	public GraduateProjectType find(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		GraduateProjectType gpt = graduateProjectTypeDao.find(id,conn);
		conn.close();
		return gpt;
	}
	
	public boolean update(GraduateProjectType graduateProjectType) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean update = graduateProjectTypeDao.update(graduateProjectType,conn);
		conn.close();
		return update;
	}
	
	public boolean add(GraduateProjectType graduateProjectType) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean added =  graduateProjectTypeDao.add(graduateProjectType,conn);
		conn.close();
		return added;
	}
	
	public boolean delete(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean delete = graduateProjectTypeDao.delete(id,conn);
		conn.close();
		return delete;
	}
}
