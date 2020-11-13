package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.authority.Resource;
import cn.edu.sdjzu.xg.bysj.domain.authority.Role;
import cn.edu.sdjzu.xg.bysj.service.ResourceService;
import util.Condition;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public final class RoleDao {
    private static RoleDao roleDao = null;

    //将唯一构造器私有，阻止本类外生成本类的对象
    private RoleDao() {
    }

    //返回本类唯一的对象
    public static RoleDao getInstance() {
        // 静态变量roleDao为空时，才创建RoleDao对象。
        // 第二次调用本方法roleDao就不空了，不再创建对象。
        if (roleDao == null) {
            roleDao = new RoleDao();
        }
        return roleDao;
    }

    /**
     * 返回id对应的Role对象
     *
     * @param id 对象对应的id
     * @return Role对象
     * @throws SQLException
     */
    public Role find(Connection connection, Integer id) throws SQLException {
        //创建Role类型的引用，暂不创建对象
        Role desiredRole = null;
        String sql_select_id = "SELECT * FROM role WHERE id = ?";
        PreparedStatement pstmt_select_id = connection.prepareStatement(sql_select_id);
        pstmt_select_id.setInt(1, id);
        ResultSet resultSet_select_id = pstmt_select_id.executeQuery();
        //如果表中存在id对应的记录，则获得表中的字段值，并创建对象
        if (resultSet_select_id.next()) {
            String description = resultSet_select_id.getString("description");
            String no = resultSet_select_id.getString("no");
            String remarks = resultSet_select_id.getString("remarks");
            //为集合字段resources赋值：本对象关联的所有Resource对象
            Collection<Resource> resources = ResourceService.getInstance().findAll(id);
            desiredRole = new Role(id, description, no, resources);
        }
        //关闭三个资源
        JdbcHelper.close(pstmt_select_id, resultSet_select_id);
        return desiredRole;
    }

    /**
     * 返回某页上的Role对象
     *
     * @param pagination 分页对象(页号, 每页面对象数)
     * @return Role对象集合
     * @throws SQLException
     */
    public Collection<Role> findAll(Connection connection, Pagination pagination, String conditionList)
            throws SQLException {
        //创建集合类对象，用来保存所有记录代表的Role对象
        //TreeSet按compare(Object o)方法指定的顺序排序
        Collection<Role> desiredRoles = new TreeSet<Role>();

        //创建查询教师的主语句
        StringBuilder sql_select = new StringBuilder("SELECT * FROM Role ");
        //如果有条件，则生成条件子句，并附加到主语句后面
        if (conditionList != null) {
            String whereClause = Condition.toWhereClause(conditionList);
            sql_select.append(whereClause);
        }
        //获取Teacher中的元组总数
        int totalNum = count(connection);
        if (pagination != null) {
            String paginationClause = pagination.toLimitClause(totalNum);
            sql_select.append(paginationClause);
        }

        PreparedStatement pstmt_select_all =
                connection.prepareStatement(sql_select.toString());
        //执行预编译语句，结果集保存在resultSet中
        ResultSet resultSet = pstmt_select_all.executeQuery();
        //遍历resultSet
        while (resultSet.next()) {
            //获得role表中当前记录的id字段的值
            int id = resultSet.getInt("id");
            String description = resultSet.getString("description");
            String no = resultSet.getString("no");
            String remarks = resultSet.getString("remarks");
            //为集合字段resources赋值：本对象关联的所有Resource对象
            Collection<Resource> resources = ResourceService.getInstance().findAll(id);
            //以当前记录的各个字段值为参数，创建Role对象
            Role desiredRole = new Role(id, description, no);
            //将当前记录代表的Role对象添加到desiredRoles中
            desiredRoles.add(desiredRole);
        }
        JdbcHelper.close(pstmt_select_all, resultSet);
        return desiredRoles;
    }

    public boolean update(Connection connection, Role role) throws SQLException {

        //准备预编译的语句
        String sql_update = "UPDATE role SET description = ?, no = ? WHERE id = ?";
        PreparedStatement pstmt_update =
                connection.prepareStatement(sql_update);

        pstmt_update.setString(1, role.getDescription());
        pstmt_update.setString(2, role.getNo());
        pstmt_update.setInt(3, role.getId());

        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_update.executeUpdate();

        JdbcHelper.close(pstmt_update);
        return rowAffected > 0;
    }

    public boolean add(Connection connection, Role role) throws SQLException {
        //获取数据库连接
//        Connection connection = JdbcHelper.getConn();
        //准备预编译的语句
        String sql_insert = "INSERT INTO role (description, no) VALUES (?, ?)";
        PreparedStatement pstmt_update =
                connection.prepareStatement(sql_insert);

        pstmt_update.setString(1, role.getDescription());
        pstmt_update.setString(2, role.getNo());


        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_update.executeUpdate();

        JdbcHelper.close(pstmt_update, connection);
        return rowAffected > 0;
    }

    public boolean delete(Connection connection, Integer id) throws SQLException {
        //准备预编译的语句
        String sql_delete_id = "DELETE FROM role WHERE id = ?";
        PreparedStatement pstmt_delete =
                connection.prepareStatement(sql_delete_id);

        pstmt_delete.setInt(1, id);

        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_delete.executeUpdate();

        JdbcHelper.close(pstmt_delete);
        return rowAffected > 0;
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM role";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}
