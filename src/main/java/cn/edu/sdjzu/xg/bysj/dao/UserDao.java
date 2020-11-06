package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import util.JdbcHelper;
import java.sql.*;


public final class UserDao {
    private static UserDao userDao = new UserDao();
    private UserDao() {
    }
    public static UserDao getInstance() {
        return userDao;
    }


    public String findAll() throws SQLException {
        Connection conn = JdbcHelper.getConn();
        String search = "SELECT * from user_odd";
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
        String user_json = array.toString();
        JdbcHelper.close(results,statement,conn);
        return user_json;
    }

    public User find(Integer id) throws SQLException {
        User desiredUser = null;
        String search = "select * from user_odd where id=" + id;
        Connection conn = JdbcHelper.getConn();
        PreparedStatement statement = conn.prepareStatement(search);
        try(statement;conn){
            ResultSet results=statement.executeQuery();
            results.next();
            desiredUser = new User(results.getString(2),
                                    results.getString(3),
                                    null,
                                    null
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
        return desiredUser;
    }

    public int add(User user, Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Insert into user_odd(username,password)Values(?,?)",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,user.getUsername());
        statement.setString(2,user.getPassword());
        int affected = statement.executeUpdate();
        int id_user = 0;
        ResultSet results = statement.getGeneratedKeys();
        if (results.next()){
            id_user = results.getInt(1);
        }
        return id_user;
    }
    public boolean update(User user,Connection conn) throws SQLException{
        //使用预编译创建SQL语句
        String update = "update user_odd set username= ?,password= ? ,teacher_id= ? where id = " + user.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getTeacher().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(Integer id) throws SQLException{
        Connection conn = JdbcHelper.getConn();
        //创建删除语句
        String delete = "delete from user_odd where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }
}
