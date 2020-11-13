package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import cn.edu.sdjzu.xg.bysj.service.ActorService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;


public final class UserDao {
    private static UserDao userDao = new UserDao();
    private UserDao() {
    }
    public static UserDao getInstance() {
        return userDao;
    }


    public Collection<User> findAll(Connection conn, String condition, Pagination pagination) throws SQLException {
        Collection<User> desireUsers = new TreeSet<User>();
        int totalNum = count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from user ");
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
            String username = results.getString("username");
            String password = results.getString("password");
            Actor actor = ActorDao.getInstance().find(results.getInt("actor_id"),conn);
            User user = new User(id,username,password,actor);
            desireUsers.add(user);
        }
        JdbcHelper.close(results,statement,conn);
        return desireUsers;
    }

    public User find(Integer id,Connection conn) throws SQLException {
        User desiredUser = null;
        String search = "select * from user where id=" + id;
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
        PreparedStatement statement = conn.prepareStatement("Insert into user(username,password,actor_id)Values(?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,user.getUsername());
        statement.setString(2,user.getPassword());
        statement.setInt(3,user.getActor().getId());
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
        String update = "update user set username= ?,password= ? ,teacher_id= ? where id = " + user.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getActor().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(Integer id,Connection conn) throws SQLException{
        //创建删除语句
        String delete = "delete from user where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }
    public Actor login(Connection connection, User user) throws SQLException {
        //创建Teacher类型的引用，暂不创建对象
        Actor currentActor = null;
        String sql_login = "SELECT * FROM user WHERE username =? and password=?";
        PreparedStatement pstmt_login =
                connection.prepareStatement(sql_login);
        pstmt_login.setString(1, user.getUsername());
        pstmt_login.setString(2, user.getPassword());
        ResultSet resultSet_login = pstmt_login.executeQuery();
        //如果表中存在id对应的记录，则获得表中的actor_id，并获得对应的Actor对象
        if (resultSet_login.next()) {
            int actor_id = resultSet_login.getInt("actor_id");
            currentActor = ActorService.getInstance().find(actor_id);
        }
        return currentActor;
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM user";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}
