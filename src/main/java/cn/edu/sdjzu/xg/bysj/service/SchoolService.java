package cn.edu.sdjzu.xg.bysj.service;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.dao.SchoolDao;
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;
import exception.BysjException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class SchoolService {
	private static SchoolDao schoolDao= SchoolDao.getInstance();
	private static SchoolService schoolService=new SchoolService();

	
	public static SchoolService getInstance(){
		return schoolService;
	}

	public Collection<School> findAll(String condition,Pagination pagination) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		Collection<School> schools = schoolDao.findAll(conn,condition,pagination);
		conn.close();
		return schools;
	}
	
	public School find(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		School school =  schoolDao.find(id,conn);
		conn.close();
		return school;
	}

	public boolean update(School school) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean updated = schoolDao.update(school,conn);
		conn.close();
		return updated;
	}
	
	public boolean add(School school) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean add = schoolDao.add(school,conn);
		conn.close();
		return add;
	}

	public boolean delete(Integer id) throws SQLException{
		int count = DepartmentService.getInstance().countAll(find(id));
		if (count == 0){
			Connection conn = JdbcHelper.getConn();
			boolean beDeled = schoolDao.delete(id,conn);
			conn.close();
			return beDeled;
		}else {
			Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
			logger.error(new BysjException(find(id).getDescription() + "仍然有下属系，不能删除"));
			return false;
		}
	}
}
