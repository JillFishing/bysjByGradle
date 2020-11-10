package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.Student;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public class StudentDao {
    public static StudentDao studentDao = new StudentDao();
    private StudentDao(){
    }
    public static StudentDao getInstance() {
        return studentDao;
    }
    //查找全部条目
    public Collection<Student> findAll(Connection conn, String condition, Pagination pagination) throws SQLException {
        //创建集合类对象，用来保存并排序所有获得的School对象
        Collection<Student> schools = new TreeSet<Student>();
        int totalNum = SchoolDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from student ");
        //将可能的条件附加到主句后
        if (condition != null){
            String clause = Condition.toWhereClause(condition);
            select.append(clause);
        }
        if (pagination != null){
            select.append(pagination.toLimitClause(totalNum)+ " ");
        }//在连接上创建语句盒子对象
        PreparedStatement statement = conn.prepareStatement(select.toString());
        //执行SQL语句
        ResultSet results = statement.executeQuery();
        //遍历resultSet，并将结果写入对象存进集合内
        while (results.next()){
            String name = results.getString("name");
            String no = results.getString("no");
            String remarks = results.getString("remarks");
            Teacher supervisor  = TeacherService.getInstance().find(results.getInt("teacher_id"));
            Student student = new Student(name,no,remarks,supervisor);
            schools.add(student);
        }
        JdbcHelper.close(results,statement,null);
        //返回获得的集合对象
        return schools;
    }
    public Collection<Student> findAll(Connection conn, Teacher teacher, Pagination pagination) throws SQLException {
        //创建集合类对象，用来保存并排序所有获得的School对象
        Collection<Student> schools = new TreeSet<Student>();
        int totalNum = SchoolDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from student ");
        //将可能的条件附加到主句后
        if (teacher != null){
            select.append(" where teacher_id= "+teacher.getId()+" ");
        }
        if (pagination != null){
            select.append(pagination.toLimitClause(totalNum)+ " ");
        }
        System.out.println(select.toString());
        //在连接上创建语句盒子对象
        PreparedStatement statement = conn.prepareStatement(select.toString());
        //执行SQL语句
        ResultSet results = statement.executeQuery();
        //遍历resultSet，并将结果写入对象存进集合内
        while (results.next()){
            String description = results.getString("name");
            String no = results.getString("no");
            String remarks = results.getString("remarks");
            /*Integer studentClass_id = results.getInt("studentClass_id");*/
            Teacher supervisor  = TeacherService.getInstance().find(results.getInt("teacher_id"));
            Student student = new Student(description,no,remarks,supervisor);
            schools.add(student);
        }
        JdbcHelper.close(results,statement,null);
        //返回获得的集合对象
        return schools;
    }
    //查找单条的方法
    public Student find(Integer id,Connection conn) throws SQLException {
        //创建SQL语句
        String search = "SELECT * from student where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        try(statement){
            //执行SQL语句
            ResultSet results = statement.executeQuery(search);
            results.next();
            //将获取的对象参数写入预先创建的对象
            Student student = new Student(results.getString(2),
                    results.getString(3),
                    results.getString(4),
                    TeacherService.getInstance().find(results.getInt(5))
            );
            JdbcHelper.close(results,null,null);
            return student;
        }

    }
    //更新方法
    public boolean update(Student student,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String update = "update student set id= ? name = ?,no= ?,teacher_id= ? where no = " + student.getNo();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getNo());
            statement.setInt(4, student.getSupervisor().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    //添加方法
    public boolean add(Student student,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into student(id,name,no,teacher_id) values (?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getNo());
            statement.setInt(4, student.getSupervisor().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    //删除方法
    public boolean delete(Integer id,Connection conn) throws SQLException {
        //创建删除语句
        String delete = "delete from student where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        statement.close();

        return (i>0);
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM student";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}
