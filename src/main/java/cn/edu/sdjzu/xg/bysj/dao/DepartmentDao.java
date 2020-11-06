package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.Department;

import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class DepartmentDao {
    private static DepartmentDao departmentDao = new DepartmentDao();

    private DepartmentDao() {
    }

    public static DepartmentDao getInstance() {
        return departmentDao;
    }

    public Collection<Department> findAll(Connection conn, String condition, Pagination pagination) throws SQLException {
        //创建集合类对象，用来保存并排序所有获得的department对象
        Collection<Department> depts = new HashSet<>();
        int totalNum = SchoolDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from department ");
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
            int school_id = results.getInt("managementSchool");
            Department department = new Department(id,description,no,remarks, SchoolService.getInstance().find(school_id));
            depts.add(department);
        }
        //关闭资源
        JdbcHelper.close(results,statement,null);
        //返回获得的集合对象
        return depts;
    }

    public Department find(Integer id,Connection conn) throws SQLException {
        Department desiredDepartment = null;
        //创建SQL语句
        String search = "SELECT * from department where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        ResultSet results = statement.executeQuery(search);
        results.next();
        try(statement;results){
            //将获取的对象参数写入预先创建的对象
            desiredDepartment = new Department(
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4),
                    SchoolService.getInstance().find(results.getInt(5))
            );
            return desiredDepartment;
        }
    }

    public boolean update(Department department,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String update = "update department set description = ?,no= ?,remarks= ?,managementSchool= ? where id = " + department.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, department.getDescription());
            statement.setString(2, department.getNo());
            statement.setString(3, department.getRemarks());
            statement.setInt(4, department.getSchool().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(Department department,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into department(description,no,remarks,managementSchool) values (?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        System.out.println(department);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, department.getDescription());
            statement.setString(2, department.getNo());
            statement.setString(3, department.getRemarks());
            statement.setInt(4, department.getSchool().getId());
            statement.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean delete(Integer id,Connection conn) throws SQLException {
        //创建删除语句
        String delete = "delete from department where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }
    public Integer countAll(School school,Connection conn) throws SQLException{
        int id = school.getId();
        //创建SQL语句
        String search = "SELECT count(id) as cnt_by_school from department where id = " + id;
        //在连接上创建语句盒子对象
        PreparedStatement statement = conn.prepareStatement(search);
        //执行SQL语句
        ResultSet results = statement.executeQuery();
        results.next();
        //获取学院下专业数量
        int count = results.getInt(1);
        //关闭资源
        JdbcHelper.close(results,statement,conn);
        //返回数据
        return count;
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM department";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}

