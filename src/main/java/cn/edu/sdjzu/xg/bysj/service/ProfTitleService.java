package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.ProfTitleDao;
import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class ProfTitleService {
	private static ProfTitleDao profTitleDao= ProfTitleDao.getInstance();
	private static ProfTitleService profTitleService=new ProfTitleService();
	private ProfTitleService(){}

	public static ProfTitleService getInstance(){
		return profTitleService;
	}

	public Collection<ProfTitle> findAll(String condition, Pagination pagination) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		Collection<ProfTitle> profTitles= profTitleDao.findAll(pagination,condition,conn);
		conn.close();
		return profTitles;
	}

	public ProfTitle find(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		ProfTitle profTitle = profTitleDao.find(id,conn);
		conn.close();
		return profTitle;
	}

	public boolean update(ProfTitle profTitle) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean update = profTitleDao.update(profTitle,conn);
		conn.close();
		return update;
	}

	public boolean add(ProfTitle profTitle) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean add = profTitleDao.add(profTitle,conn);
		conn.close();
		return add;
	}

	public boolean delete(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean beDeled = profTitleDao.delete(id,conn);
		conn.close();
		return beDeled;
	}

}

