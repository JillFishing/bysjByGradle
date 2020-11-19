package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.*;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectCategoryService;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectStatusService;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectTypeService;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class GraduateProjectDao {
    private static GraduateProjectDao graduateProjectDao = new GraduateProjectDao();
    private GraduateProjectDao() {
    }
    public static GraduateProjectDao getInstance(){
        return graduateProjectDao;
    }

    public Collection<GraduateProject> findAll(Connection conn, String condition, Pagination pagination)throws SQLException {
        //创建集合类对象，用来保存并排序所有获得的department对象
        Collection<GraduateProject> graduateProjects = new HashSet<>();
        int totalNum = GraduateProjectDao.getInstance().count(conn);
        //创建查询的主句
        StringBuilder select = new StringBuilder("SELECT * from graduateProject ");
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
            Integer id = results.getInt("id");
            String title = results.getString("title");
            GraduateProjectCategory graduateProjectCategory =
                    GraduateProjectCategoryService.getInstance().find(results.getInt("gpc_id"));
            GraduateProjectType graduateProjectType =
                    GraduateProjectTypeService.getInstance().find(results.getInt("gpt_id"));
            GraduateProjectStatus graduateProjectStatus =
                    GraduateProjectStatusService.getInstance().find(results.getInt("gps_id"));
            Teacher teacher = TeacherService.getInstance().find(results.getInt("teacher_id"));
            GraduateProject gp = new GraduateProject(id,title,
                    graduateProjectCategory,graduateProjectType,graduateProjectStatus,teacher);
            graduateProjects.add(gp);
        }
        //关闭资源
        JdbcHelper.close(results,statement,null);
        //返回获得的集合对象
        return graduateProjects;
    }
    public GraduateProject find(Integer id,Connection conn)throws SQLException {
        GraduateProject desiredGraduateProject = null;
        //创建SQL语句
        String search = "SELECT * from graduateProject where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        ResultSet results = statement.executeQuery(search);
        results.next();
        try(statement;results){
            //将获取的对象参数写入预先创建的对象
            desiredGraduateProject = new GraduateProject(
                    results.getInt("id"),
                    results.getString("title"),
                    GraduateProjectCategoryDao.getInstance().find(results.getInt("gpc_id"),conn),
                    GraduateProjectTypeDao.getInstance().find(results.getInt("gpt_id"),conn),
                    GraduateProjectStatusDao.getInstance().find( results.getInt("gps_id"),conn),
                    TeacherDao.getInstance().find(results.getInt("teacher_id"),conn)
                    );
            return desiredGraduateProject;
        }
    }
    public boolean add(GraduateProject project,Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into graduateProject() values (?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, project.getTitle());
            statement.setInt(2, project.getGraduateProjectCategory().getId());
            statement.setInt(3, project.getGraduateProjectType().getId());
            statement.setInt(4, project.getGraduateProjectStatus().getId());
            statement.setInt(5, project.getTeacher().getId());
            statement.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }
    public boolean update(GraduateProject project,Connection conn)throws SQLException {
        //使用预编译创建SQL语句
        String update = "update graduateProject set title = ?,gpc_id = ?,gpt_id = ?,gps_id = ?,teacher_id = ? where id = " + project.getId();
        PreparedStatement statement = conn.prepareStatement(update);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setString(1, project.getTitle());
            statement.setInt(2, project.getGraduateProjectCategory().getId());
            statement.setInt(3, project.getGraduateProjectType().getId());
            statement.setInt(4, project.getGraduateProjectStatus().getId());
            statement.setInt(5, project.getTeacher().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer id,Connection conn)throws SQLException {
    //创建删除语句
        String delete = "delete from graduateProject where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        JdbcHelper.close(statement,conn);
        return (i>0);
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM graduateProject";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}

