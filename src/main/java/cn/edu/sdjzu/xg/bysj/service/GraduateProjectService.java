package cn.edu.sdjzu.xg.bysj.service;

import cn.edu.sdjzu.xg.bysj.dao.GraduateProjectDao;
import cn.edu.sdjzu.xg.bysj.domain.GraduateProject;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectService {
	private static GraduateProjectDao graduateProjectDao = GraduateProjectDao.getInstance();
	private static GraduateProjectService graduateProjectService = new GraduateProjectService();
	private GraduateProjectService(){}

	public static GraduateProjectService getInstance(){
		return graduateProjectService;
	}

	//获取所有课题
	public Collection<GraduateProject> findAll(String condition, Pagination pagination)throws SQLException{
		Connection conn = JdbcHelper.getConn();
		Collection<GraduateProject> gps = graduateProjectDao.findAll(conn, condition, pagination);
		conn.close();
		return gps;
	}
	//获得id对应的课题
	public GraduateProject find(Integer id)throws SQLException {
		Connection conn = JdbcHelper.getConn();
		GraduateProject gp = graduateProjectDao.find(id,conn);
		conn.close();
		return gp;
	}

	//增加一个课题
	public boolean add(GraduateProject project)throws SQLException{
		Connection conn = JdbcHelper.getConn();
		boolean added = graduateProjectDao.add(project,conn);
		conn.close();
		return added;
	}
	//更新一个课题
	public boolean update(GraduateProject project)throws SQLException{
		Connection conn = JdbcHelper.getConn();
		boolean update = graduateProjectDao.update(project,conn);
		conn.close();
		return update;
	}
	//删除一个课题
	public boolean delete(int id)throws SQLException{
		Connection conn = JdbcHelper.getConn();
		boolean delete = graduateProjectDao.delete(id,conn);
		conn.close();
		return delete;
	}
}
