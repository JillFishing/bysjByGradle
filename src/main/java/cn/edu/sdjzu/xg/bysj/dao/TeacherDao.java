package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import exception.UsernameDuplicateException;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;


public final class TeacherDao {
    private static TeacherDao teacherDao = new TeacherDao();
    private TeacherDao() {
    }
    public static TeacherDao getInstance() {
        return teacherDao;
    }

    public Collection<Teacher> findAll(Pagination pagination, String condition, Connection conn) throws SQLException{
        //创建集合类对象，用来保存并排序所有获得的teacher对象
        Collection<Teacher> teachers = new TreeSet<Teacher>();
        int totalNum = teacherDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from teacher ");
        //将可能的条件附加到主句后
        if (condition != null){
            String clause = Condition.toWhereClause(condition);
            select.append(clause);
        }
        if (pagination != null){
            select.append(pagination.toLimitClause(totalNum)+ " ");
        }
        //在连接上创建语句盒子对象
        PreparedStatement statement = conn.prepareStatement(select.toString());
        //执行SQL语句
        ResultSet results = statement.executeQuery();
        //遍历resultSet，并将结果写入对象存进集合内
        while (results.next()){
            String name = results.getString("name");
            String no = results.getString("no");
            Teacher teacher = new Teacher(name,no);
            teachers.add(teacher);
        }
        JdbcHelper.close(results,statement,conn);
        //返回获得的集合对象
        return teachers;
    }

    public Teacher find(Integer id, Connection conn) throws SQLException {
        Teacher desiredTeacher = null;
        String search = "select * from teacher where id=" + id;
        PreparedStatement statement = conn.prepareStatement(search);
        try(statement;conn){
            ResultSet results=statement.executeQuery();
            results.next();
            desiredTeacher = new Teacher(
                    results.getString(2),
                    results.getString(3)
                    );
            desiredTeacher.setId(results.getInt(1));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return desiredTeacher;
    }

    public boolean update(Teacher teacher, Connection conn) throws SQLException{
        //使用预编译创建SQL语句
        String update = "update teacher set name = ?,no = ? where id = " + teacher.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, teacher.getName());
            statement.setString(2, teacher.getNo());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public int add(Teacher teacher, Connection conn) throws SQLException{
        //准备语句对象
        String add = "Insert into teacher(name,no) values(?,?)";
        PreparedStatement statement = conn.prepareStatement(add, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,teacher.getName());
        statement.setString(2,teacher.getNo());
        //执行语句
        int affect = statement.executeUpdate();
        int id_teacher=0;
        //获得生成的主键集合
        ResultSet results = statement.getGeneratedKeys();
        if(results.next()){
            //读取第一个主键
            id_teacher = results.getInt(1);
        }
        return id_teacher;
    }

    public boolean delete(Integer id,Connection conn) throws SQLException{
        //创建删除语句
        String delete = "delete from teacher where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM teacher";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}
