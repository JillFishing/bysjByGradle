package cn.edu.sdjzu.xg.bysj.service;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.dao.GraduateProjectCategoryDao;
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectCategory;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectCategoryService {
	private GraduateProjectCategoryDao graduateProjectCategoryDao = GraduateProjectCategoryDao.getInstance();
	//本类的一个对象引用，保存自身对象
	private static GraduateProjectCategoryService graduateProjectCategoryService =  new GraduateProjectCategoryService();
	//私有的构造方法，防止其它类创建它的对象
	private GraduateProjectCategoryService(){}
	//静态方法，返回本类的惟一对象
	public synchronized static GraduateProjectCategoryService getInstance() {
		return graduateProjectCategoryService;
	}

	public Collection<GraduateProjectCategory> findAll(String condition, Pagination pagination) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		Collection<GraduateProjectCategory> graduateProjectCategories =graduateProjectCategoryDao.findAll(conn, condition, pagination);
		conn.close();
		return graduateProjectCategories;
	}

	public GraduateProjectCategory find(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		GraduateProjectCategory degree = graduateProjectCategoryDao.find(id,conn);
		conn.close();
		return degree;
	}

	public boolean update(GraduateProjectCategory graduateProjectCategory) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean update = graduateProjectCategoryDao.update(graduateProjectCategory,conn);
		conn.close();
		return update;
	}

	public boolean add(GraduateProjectCategory graduateProjectCategory) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean add = graduateProjectCategoryDao.add(graduateProjectCategory,conn);
		conn.close();
		return add;
	}

	public boolean delete(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean delete = graduateProjectCategoryDao.delete(id,conn);
		conn.close();
		return delete;
	}

}
