package cn.edu.sdjzu.xg.bysj.service;
//201902104050 姜瑞临

import cn.edu.sdjzu.xg.bysj.dao.TeacherDao;
import cn.edu.sdjzu.xg.bysj.dao.UserDao;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import exception.UsernameDuplicateException;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class TeacherService {
	private static TeacherDao teacherDao= TeacherDao.getInstance();
	private static TeacherService teacherService=new TeacherService();

	private TeacherService(){}
	public static TeacherService getInstance(){
		return teacherService;
	}
	
	public Collection<Teacher> findAll(Pagination pagination, String condition) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		Collection<Teacher> teachers = teacherDao.findAll(pagination,condition,conn);
		conn.close();
		return teachers;
	}
	
	public Teacher find(Integer id) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		Teacher find = teacherDao.find(id,conn);
		conn.close();
		return find;
	}
	
	public boolean update(Teacher teacher) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean update = teacherDao.update(teacher,conn);
		conn.close();
		return update;
	}
	//增加user
	public int add(Teacher teacher) throws SQLException,UsernameDuplicateException{
		Connection conn = JdbcHelper.getConn();
		//关闭自动提交，事务开始
		conn.setAutoCommit(false);
		int teacherId = 0;
		try{
			teacherId = teacherDao.add(teacher,conn);
			teacher.setId(teacherId);
			User user = new User(teacher.getNo(),teacher.getNo(),null, teacher);
			String token = UserDao.getInstance().add(user,conn);
			JWTVerifier build = JWT.require(Algorithm.HMAC256("114514")).build();
			DecodedJWT verify = build.verify(token);
			int userId = verify.getClaim("id").asInt();
			user.setId(userId);
			teacherDao.update(teacher,conn);
			user.setActor(teacher);
			UserDao.getInstance().update(user,conn);

		}catch (SQLException e){
			e.printStackTrace();
			//回滚事务中所有操作
			conn.rollback();
		}
		finally {
			//重启自动提交，事务结束
			conn.setAutoCommit(true);
			conn.close();
		}
		return teacherId;
	}

	public boolean delete(Teacher teacher) throws SQLException {
		Connection conn = JdbcHelper.getConn();
		boolean delete = teacherDao.delete(teacher.getId(),conn);
		conn.close();
		return delete;
	}
}
