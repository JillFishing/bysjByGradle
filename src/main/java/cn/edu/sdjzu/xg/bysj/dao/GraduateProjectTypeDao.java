package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临

import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectType;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import util.JdbcHelper;
import java.sql.*;

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

    public String finaAll() throws SQLException {
        Connection conn = JdbcHelper.getConn();
        String search = "SELECT * from GraduateProjectType";
        //在连接上创建语句盒子对象
        PreparedStatement statement = conn.prepareStatement(search);
        //执行SQL语句
        ResultSet results = statement.executeQuery();
        ResultSetMetaData resultSetMetaData = results.getMetaData();
        int column = resultSetMetaData.getColumnCount();
        JSONArray array = new JSONArray();
        while (results.next()){
            JSONObject object = new JSONObject();
            for (int i = 1;i<=column;i++){
                object.put(resultSetMetaData.getColumnLabel(i),results.getObject(i));
            }
            array.add(object);
        }
        String gPT_json = array.toString();
        JdbcHelper.close(results,statement,conn);
        return gPT_json;
    }

    public GraduateProjectType find(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
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
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4)
            );
            return graduateProjectType;
        }
    }

    public boolean update(GraduateProjectType graduateProjectType) throws SQLException {
        Connection conn = JdbcHelper.getConn();
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

    public boolean add(GraduateProjectType graduateProjectType) throws SQLException {
        Connection conn = JdbcHelper.getConn();
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

    public boolean delete(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
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
}
