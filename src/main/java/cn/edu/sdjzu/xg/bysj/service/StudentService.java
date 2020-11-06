package cn.edu.sdjzu.xg.bysj.service;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.dao.StudentDao;
import cn.edu.sdjzu.xg.bysj.domain.Student;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
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
        Collection<Student> students =studentDao.findAll(conn, condition, pagination);
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

    public boolean add(Student student) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean add = studentDao.add(student,conn);
        conn.close();
        return add;
    }

    public boolean delete(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean delete = studentDao.delete(id,conn);
        conn.close();
        return delete;
    }
}
