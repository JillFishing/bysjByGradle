package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.authority.Resource;
import cn.edu.sdjzu.xg.bysj.domain.authority.Role;
import cn.edu.sdjzu.xg.bysj.service.RoleService;
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

public final class ResourceDao {
    private static ResourceDao resourceDao = null;

    //将唯一构造器私有，阻止本类外生成本类的对象
    private ResourceDao() {
    }

    //返回本类唯一的对象
    public static ResourceDao getInstance() {
        // 静态变量resourceDao为空时，才创建ResourceDao对象。
        // 第二次调用本方法resourceDao就不空了，不再创建对象。
        if (resourceDao == null) {
            resourceDao = new ResourceDao();
        }
        return resourceDao;
    }

    /**
     * 返回id对应的Resource对象
     * @param id 对象对应的id
     * @return Resource对象
     * @throws SQLException
     */
    public Resource find(Connection connection, Integer id) throws SQLException {
        //创建Resource类型的引用，暂不创建对象
        Resource desiredResource = null;
        String sql_select_id = "SELECT * FROM resource WHERE id = ?";
        PreparedStatement pstmt_select_id = connection.prepareStatement(sql_select_id);
        pstmt_select_id.setInt(1, id);
        ResultSet resultSet_select_id = pstmt_select_id.executeQuery();
        //如果表中存在id对应的记录，则获得表中的字段值，并创建对象
        if (resultSet_select_id.next()) {
            String description = resultSet_select_id.getString("description");
            String no = resultSet_select_id.getString("no");
            String url = resultSet_select_id.getString("url");
            String remarks = resultSet_select_id.getString("remarks");
            int roleId = resultSet_select_id.getInt("role_id");
            Role role = RoleService.getInstance().find(roleId);
            desiredResource = new Resource(id, description, url, no, remarks, role);
        }
        //关闭三个资源
        JdbcHelper.close(pstmt_select_id, resultSet_select_id);
        return desiredResource;
    }

    public Collection<Resource> findAll(Connection connection, Integer roleId)
            throws SQLException {
        Collection<Resource> desiredResources = new TreeSet<Resource>();
        String sql_select_role_id = "SELECT * FROM resource WHERE role_id = ?";
        PreparedStatement pstmt_select = connection.prepareStatement(sql_select_role_id);
        pstmt_select.setInt(1, roleId);
        ResultSet resultSet_select = pstmt_select.executeQuery();
        //如果表中存在id对应的记录，则获得表中的字段值，并创建对象
        while (resultSet_select.next()) {
            int id = resultSet_select.getInt("id");
            String description = resultSet_select.getString("description");
            String no = resultSet_select.getString("no");
            String url = resultSet_select.getString("url");
            String remarks = resultSet_select.getString("remarks");
            //会造成循环调用
//            Role role = RoleService.getInstance().find(roleId);
            Resource desiredResource = new Resource(id, description, url, no, remarks, null);
            desiredResources.add(desiredResource);
        }
        JdbcHelper.close(pstmt_select, resultSet_select);
        return desiredResources;
    }

    /**
     * 返回某页上的Resource对象
     *
     * @param pagination 分页对象(页号, 每页面对象数)
     * @return Resource对象集合
     * @throws SQLException
     */
    public Collection<Resource> findAll(Connection connection, Pagination pagination, String conditionList)
            throws SQLException {
        //创建集合类对象，用来保存所有记录代表的Resource对象
        //TreeSet按compare(Object o)方法指定的顺序排序
        Collection<Resource> desiredResources = new TreeSet<Resource>();

        //创建查询教师的主语句
        StringBuilder sql_select = new StringBuilder("SELECT * FROM Resource ");
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
        ResultSet resultSet_select = pstmt_select_all.executeQuery();
        //遍历resultSet
        while (resultSet_select.next()) {
            //获得resource表中当前记录的id字段的值
            int id = resultSet_select.getInt("id");
            String description = resultSet_select.getString("description");
            String no = resultSet_select.getString("no");
            String url = resultSet_select.getString("url");
            String remarks = resultSet_select.getString("remarks");
            int roleId = resultSet_select.getInt("role_id");
            Role role = RoleService.getInstance().find(roleId);
            //以当前记录的各个字段值为参数，创建Resource对象
            Resource desiredResource = new Resource(id, description, url, no, remarks, role);
            //将当前记录代表的Resource对象添加到desiredResources中
            desiredResources.add(desiredResource);
        }
        JdbcHelper.close(pstmt_select_all, resultSet_select);
        return desiredResources;
    }

    public boolean update(Connection connection, Resource resource)
            throws SQLException {
        //准备预编译的语句
        String sql_update = "UPDATE resource SET description = ?, url = ?, " +
                "no = ? WHERE id = ?";
        PreparedStatement pstmt_update =
                connection.prepareStatement(sql_update);
        pstmt_update.setString(1, resource.getDescription());
        pstmt_update.setString(2, resource.getUrl());
        pstmt_update.setString(3, resource.getNo());
        pstmt_update.setInt(4, resource.getId());
        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_update.executeUpdate();
        JdbcHelper.close(pstmt_update);
        return rowAffected > 0;
    }

    public boolean add(Connection connection, Resource resource) throws SQLException {
        //获取数据库连接
//        Connection connection = JdbcHelper.getConn();
        //准备预编译的语句
        String sql_insert = "INSERT INTO resource (description, url, no, role_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt_update =
                connection.prepareStatement(sql_insert);

        pstmt_update.setString(1, resource.getDescription());
        pstmt_update.setString(2, resource.getUrl());
        pstmt_update.setString(3, resource.getNo());
        pstmt_update.setInt(4, resource.getRole().getId());

        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_update.executeUpdate();

        JdbcHelper.close(pstmt_update, connection);
        return rowAffected > 0;
    }

    public boolean delete(Connection connection, Integer id) throws SQLException {
        //准备预编译的语句
        String sql_delete_id = "DELETE FROM resource WHERE id = ?";
        PreparedStatement pstmt_delete =
                connection.prepareStatement(sql_delete_id);

        pstmt_delete.setInt(1, id);

        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_delete.executeUpdate();

        JdbcHelper.close(pstmt_delete);
        return rowAffected > 0;
    }
    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM resource";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }
}
