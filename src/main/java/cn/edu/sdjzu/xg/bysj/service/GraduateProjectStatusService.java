package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.GraduateProjectStatusDao;
import cn.edu.sdjzu.xg.bysj.domain.GraduateProject;
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectStatus;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectStatusService {
	private static GraduateProjectStatusDao graduateProjectStatusDao= GraduateProjectStatusDao.getInstance();
	private static GraduateProjectStatusService graduateProjectStatusService=new GraduateProjectStatusService();

	public static GraduateProjectStatusService getInstance(){
		return graduateProjectStatusService;
	}

	//获取所有课题
	public Collection<GraduateProjectStatus> findAll(String condition, Pagination pagination)throws SQLException{
		Connection conn = JdbcHelper.getConn();
		Collection<GraduateProjectStatus> gps = graduateProjectStatusDao.findAll(conn, condition, pagination);
		conn.close();
		return gps;
	}
	//获得id对应的课题
	public GraduateProjectStatus find(Integer id)throws SQLException {
		Connection conn = JdbcHelper.getConn();
		GraduateProjectStatus gp = graduateProjectStatusDao.find(id,conn);
		conn.close();
		return gp;
	}

	//增加一个课题
	public boolean add(GraduateProjectStatus project)throws SQLException{
		Connection conn = JdbcHelper.getConn();
		boolean added = graduateProjectStatusDao.add(project,conn);
		conn.close();
		return added;
	}
	//更新一个课题
	public boolean update(GraduateProjectStatus project)throws SQLException{
		Connection conn = JdbcHelper.getConn();
		boolean update = graduateProjectStatusDao.update(project,conn);
		conn.close();
		return update;
	}
	//删除一个课题
	public boolean delete(int id)throws SQLException{
		Connection conn = JdbcHelper.getConn();
		boolean delete = graduateProjectStatusDao.delete(id,conn);
		conn.close();
		return delete;
	}
}
