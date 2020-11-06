package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.School;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class SchoolDao {
    public static SchoolDao schoolDao = new SchoolDao();

    private SchoolDao(){
    }
    public static SchoolDao getInstance() {
        return schoolDao;
    }

    //查找全部条目
    public Collection<School> findAll(Connection conn,String condition,Pagination pagination) throws SQLException {
        //创建集合类对象，用来保存并排序所有获得的School对象
        Collection<School> schools = new TreeSet<School>();
        int totalNum = SchoolDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from school ");
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
                int id = results.getInt("id");
                String description = results.getString("description");
                String no = results.getString("no");
                String remarks = results.getString("remarks");
                School school = new School(id,description,no,remarks);
                schools.add(school);
        }
        JdbcHelper.close(results,statement,null);
        //返回获得的集合对象
        return schools;
    }
    //查找单条的方法
    public School find(Integer id,Connection conn) throws SQLException {
        //创建SQL语句
        String search = "SELECT * from school where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        try(statement){
            //执行SQL语句
            ResultSet results = statement.executeQuery(search);
            results.next();
            //将获取的对象参数写入预先创建的对象
            School school = new School(results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4)
            );
            JdbcHelper.close(results,null,null);
            return school;
        }

    }
    //更新方法
    public boolean update(School school,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String update = "update School set description = ?,no= ?,remarks= ? where id = " + school.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, school.getDescription());
            statement.setString(2, school.getNo());
            statement.setString(3, school.getRemarks());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    //添加方法
    public boolean add(School school,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into School(description,no,remarks) values (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, school.getDescription());
            statement.setString(2, school.getNo());
            statement.setString(3, school.getRemarks());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    //删除方法
    public boolean delete(Integer id,Connection conn) throws SQLException {
        //创建删除语句
        String delete = "delete from school where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        statement.close();

        return (i>0);
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM school";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}
