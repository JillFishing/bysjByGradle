package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectCategory;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class GraduateProjectCategoryDao {
    //本类的一个对象引用，保存自身对象
    private static GraduateProjectCategoryDao graduateProjectCategoryDao = null;

    //私有的构造方法，防止其它类创建它的对象
    private GraduateProjectCategoryDao() {
    }

    //静态方法，返回本类的惟一对象
    public synchronized static GraduateProjectCategoryDao getInstance() {
        return graduateProjectCategoryDao == null ? new GraduateProjectCategoryDao()
                : graduateProjectCategoryDao;
    }

    public Collection<GraduateProjectCategory> findAll(Connection conn, String condition, Pagination pagination) throws SQLException {
        //创建集合类对象，用来保存并排序所有获得的department对象
        Collection<GraduateProjectCategory> graduateProjectCategories = new HashSet<>();
        int totalNum = graduateProjectCategoryDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from graduateProjectCategory ");
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
            GraduateProjectCategory graduateProjectCategory = new GraduateProjectCategory(id,description,no,remarks);
            graduateProjectCategories.add(graduateProjectCategory);
        }
        //关闭资源
        JdbcHelper.close(results,statement,null);
        //返回获得的集合对象
        return graduateProjectCategories;
    }

    public GraduateProjectCategory find(Integer id,Connection conn) throws SQLException {
        GraduateProjectCategory graduateProjectCategory = null;
        //创建SQL语句
        String search = "SELECT * from GraduateProjectCategory where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        ResultSet results = statement.executeQuery(search);
        results.next();
        try(statement;results){
            //将获取的对象参数写入预先创建的对象
            graduateProjectCategory = new GraduateProjectCategory(
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4)
            );
            return graduateProjectCategory;
        }
    }

    public boolean update(GraduateProjectCategory graduateProjectCategory,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String update = "update graduateProjectCategory set description = ?,no= ?,remarks= ? where id = " + graduateProjectCategory.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, graduateProjectCategory.getDescription());
            statement.setString(2, graduateProjectCategory.getNo());
            statement.setString(3, graduateProjectCategory.getRemarks());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(GraduateProjectCategory graduateProjectCategory,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into graduateProjectCategory(description,no,remarks) values (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        System.out.println(graduateProjectCategory);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, graduateProjectCategory.getDescription());
            statement.setString(2, graduateProjectCategory.getNo());
            statement.setString(3, graduateProjectCategory.getRemarks());
            statement.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean delete(Integer id,Connection conn) throws SQLException {
        //创建删除语句
        String delete = "delete from graduateProjectCategory where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }

    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM graduateProjectCategory";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}
