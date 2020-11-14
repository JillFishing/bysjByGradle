package cn.edu.sdjzu.xg.bysj.service;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.dao.ActorDao;
import cn.edu.sdjzu.xg.bysj.dao.StudentDao;
import cn.edu.sdjzu.xg.bysj.dao.UserDao;
import cn.edu.sdjzu.xg.bysj.domain.Student;
import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

public class StudentService {
    private static StudentDao studentDao = StudentDao.getInstance();
    private static StudentService studentService =new StudentService();
    private StudentService(){}

    public static StudentService getInstance(){
        return studentService;
    }

    public Collection<Student> findAll(String condition, Pagination pagination) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        Collection<Student> students =studentDao.findAll(conn,condition, pagination);
        conn.close();
        return students;
    }
    public Collection<Student> findAll(Integer teacher, Pagination pagination) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        Collection<Student> students =studentDao.findAll(conn,TeacherService.getInstance().find(teacher), pagination);
        System.out.println(students);
        conn.close();
        return students;
    }

    public Student find(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        Student student = studentDao.find(id,conn);
        conn.close();
        return student;
    }

    public boolean update(Student student) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean update = studentDao.update(student,conn);
        conn.close();
        return update;
    }

    public String add(Student student) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        String token = null;
        //关闭自动提交，事务开始
        conn.setAutoCommit(false);
        int studentId;
        try{
            studentId = ActorDao.getInstance().add(conn);
            student.setId(studentId);
            User user = new User(studentId,student.getNo(),student.getNo(),null, student);
            token = UserDao.getInstance().add(user,conn);
            studentDao.add(student,conn);
            conn.commit();
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
        conn.close();
        return token;
    }

    public boolean delete(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean delete = studentDao.delete(id,conn);
        conn.close();
        return delete;
    }
}
