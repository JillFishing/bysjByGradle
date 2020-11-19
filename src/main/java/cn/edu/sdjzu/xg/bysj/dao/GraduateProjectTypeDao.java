package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectType;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class GraduateProjectTypeDao {
    private static GraduateProjectTypeDao graduateProjectTypeDao = new GraduateProjectTypeDao();
    private GraduateProjectTypeDao() {
    }

    public static GraduateProjectTypeDao getInstance() {
        if (graduateProjectTypeDao == null){
            graduateProjectTypeDao = new GraduateProjectTypeDao();
        }
        return graduateProjectTypeDao;
    }
    public Collection<GraduateProjectType> finaAll(String condition, Pagination pagination,Connection conn) throws SQLException {
        //创建集合类对象，用来保存并排序所有获得的department对象
        Collection<GraduateProjectType> graduateProjectTypes = new HashSet<>();
        int totalNum = GraduateProjectTypeDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from graduateProjectType ");
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
            int id = results.getInt("id");
            String description = results.getString("description");
            String no = results.getString("no");
            String remarks = results.getString("remarks");
            GraduateProjectType gpt = new GraduateProjectType(id,description,no,remarks);
            graduateProjectTypes.add(gpt);
        }
        //关闭资源
        JdbcHelper.close(results,statement,null);
        //返回获得的集合对象
        return graduateProjectTypes;
    }

    public GraduateProjectType find(Integer id,Connection conn) throws SQLException {
        //创建SQL语句
        String search = "SELECT * from graduateprojecttype where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        try(statement;conn){
            //执行SQL语句
            ResultSet results = statement.executeQuery(search);
            results.next();
            //将获取的对象参数写入预先创建的对象
            GraduateProjectType graduateProjectType = new GraduateProjectType(
                    results.getInt("id"),
                    results.getString("description"),
                    results.getString("no"),
                    results.getString("remarks")
            );
            return graduateProjectType;
        }
    }

    public boolean update(GraduateProjectType graduateProjectType,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String update = "update graduateProjectType set description = ?,no= ?,remarks= ? where id = " + graduateProjectType.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement;conn) {
            statement.setString(1, graduateProjectType.getDescription());
            statement.setString(2, graduateProjectType.getNo());
            statement.setString(3, graduateProjectType.getRemarks());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean add(GraduateProjectType graduateProjectType,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into graduateProjectType(description,no,remarks) values (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        //执行SQL语句并返回结果和关闭连接
        try (statement;conn) {
            statement.setString(1, graduateProjectType.getDescription());
            statement.setString(2, graduateProjectType.getNo());
            statement.setString(3, graduateProjectType.getRemarks());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(Integer id,Connection conn) throws SQLException {
        //创建删除语句
        String delete = "delete from graduateProjectType where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM graduateProjectType";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}
