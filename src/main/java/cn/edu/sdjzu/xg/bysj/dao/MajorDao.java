package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.Major;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import util.JdbcHelper;
import java.sql.*;


public final class MajorDao {
    private static MajorDao majorDao = new MajorDao();

    private MajorDao() {
    }

    public static MajorDao getInstance() {
        return majorDao;
    }

    /*public static void main(String[] args) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        MajorDao dao = new MajorDao();
        //测试各个方法
        String majors = dao.findAll();
        display(majors);
        //创建测试用对象
        Department department = DepartmentService.getInstance().find(2);
        Major major_obj = new Major(111,"测试","020106","",department);
        boolean major_add = dao.add(major_obj);
        display(major_add);
        Major major = dao.find(11);
        display(major);
        major_obj.setDescription("测试改");
        boolean major_update = dao.update(major_obj);
        display(major_update);
        boolean major_delete = dao.delete(11);
        display(major_delete);
        conn.close();
    }*/

    private static void display(Object majors) {
            System.out.println(majors);
    }

    public String findAll() throws SQLException {
        Connection conn = JdbcHelper.getConn();
        String search = "SELECT * from major";
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
        String major_json = array.toString();
        JdbcHelper.close(results,statement,conn);
        return major_json;
    }

    public Major find(Integer id) throws SQLException{
        Major desiredMajor = null;
        Connection conn = JdbcHelper.getConn();
        //创建SQL语句
        String search = "SELECT * from major where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        ResultSet results = statement.executeQuery(search);
        results.next();
        try(statement;conn;results){
            //将获取的对象参数写入预先创建的对象
            desiredMajor = new Major(
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4),
                    DepartmentService.getInstance().find(results.getInt("department"))
            );
        return desiredMajor;
        }
    }

    public boolean update(Major major) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        //使用预编译创建SQL语句
        String update = "update major set description = ?,no= ?,remarks= ?,department= ? where id = " + major.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement;conn) {
            System.out.println(major.getDescription());
            statement.setString(1, major.getDescription());
            statement.setString(2, major.getNo());
            statement.setString(3, major.getRemarks());
            statement.setInt(4, major.getDepartment().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean add(Major major) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        //使用预编译创建SQL语句
        String create = "insert into major(description,no,remarks,department) values (?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        //执行SQL语句并返回结果和关闭连接
        try (statement;conn) {
            statement.setString(1, major.getDescription());
            statement.setString(2, major.getNo());
            statement.setString(3, major.getRemarks());
            statement.setInt(4, DepartmentService.getInstance().find(2).getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        //创建删除语句
        String delete = "delete from major where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }
}
