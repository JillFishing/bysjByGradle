package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class DegreeDao {
    private static DegreeDao degreeDao = new DegreeDao();
    private DegreeDao() {
    }
    public static DegreeDao getInstance() {
        return degreeDao;
    }

    public Collection<Degree> findAll(Connection conn, String condition, Pagination pagination) throws SQLException {
        //创建集合类对象，用来保存并排序所有获得的department对象
        Collection<Degree> degrees = new HashSet<>();
        int totalNum = degreeDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from degree ");
        //将可能的条件附加到主句后
        if (condition != null){
            String clause = Condition.toWhereClause(condition);
            select.append(clause);
        }
        if (pagination != null){
            select.append(pagination.toLimitClause(totalNum)+ " ");
        }
        //在连接上创建语句盒子对象
        System.out.println(select.toString());
        PreparedStatement statement = conn.prepareStatement(select.toString());
        //执行SQL语句
        ResultSet results = statement.executeQuery();
        //遍历resultSet，并将结果写入对象存进集合内
        while (results.next()){
            int id = results.getInt("id");
            String description = results.getString("description");
            String no = results.getString("no");
            String remarks = results.getString("remarks");
            Degree degree = new Degree(id,description,no,remarks);
            degrees.add(degree);
        }
        //关闭资源
        JdbcHelper.close(results,statement,null);
        //返回获得的集合对象
        return degrees;
    }


    public Degree find(Integer id,Connection conn) throws SQLException {
        Degree desiredDegree = null;
        //创建SQL语句
        String search = "SELECT * from degree where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        ResultSet results = statement.executeQuery(search);
        results.next();
        try(statement;results){
            //将获取的对象参数写入预先创建的对象
            desiredDegree = new Degree(
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4)
            );
            return desiredDegree;
        }
    }

    public boolean update(Degree degree,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String update = "update degree set description = ?,no= ?,remarks= ? where id = " + degree.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, degree.getDescription());
            statement.setString(2, degree.getNo());
            statement.setString(3, degree.getRemarks());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(Degree degree,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into degree(description,no,remarks) values (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        System.out.println(degree);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, degree.getDescription());
            statement.setString(2, degree.getNo());
            statement.setString(3, degree.getRemarks());
            statement.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean delete(Integer id,Connection conn) throws SQLException {
        //创建删除语句
        String delete = "delete from degree where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM degree";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}

