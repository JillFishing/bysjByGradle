package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import cn.edu.sdjzu.xg.bysj.domain.authority.ActorAssRole;
import cn.edu.sdjzu.xg.bysj.domain.authority.Role;
import cn.edu.sdjzu.xg.bysj.service.ActorService;
import cn.edu.sdjzu.xg.bysj.service.RoleService;
import util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

public final class ActorAssRoleDao {

    private static ActorAssRoleDao actorAssRoleDao = new ActorAssRoleDao();

    private ActorAssRoleDao() {
    }

    public static ActorAssRoleDao getInstance() {
        if (actorAssRoleDao == null) {
            actorAssRoleDao = new ActorAssRoleDao();
        }
        return actorAssRoleDao;
    }

    public static void main(String[] args) throws SQLException {

    }

    public boolean add(Connection connection, ActorAssRole actorAssRole) throws SQLException {
        //准备预编译的语句
        String sql_insert = "INSERT INTO actorAssRole " +
                "( actor_id, role_id) VALUES (?, ?)";
        PreparedStatement pstmt_update =
                connection.prepareStatement(sql_insert);

        pstmt_update.setInt(1, actorAssRole.getActor().getId());
        pstmt_update.setInt(2, actorAssRole.getRole().getId());

        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_update.executeUpdate();

        JdbcHelper.close(pstmt_update);
        return rowAffected > 0;
    }

    public ActorAssRole find(Connection connection, Integer id) throws SQLException {
        //创建ActorAssRole类型的引用，暂不创建对象
        ActorAssRole desiredActorAssRole = null;

        String sql_select_id = "SELECT * FROM actorAssRole WHERE id = ?";
        PreparedStatement pstmt_select_id =
                connection.prepareStatement(sql_select_id);
        pstmt_select_id.setInt(1, id);
        ResultSet resultSet_select_id = pstmt_select_id.executeQuery();
        //如果表中存在id对应的记录，则获得表中的字段值，并创建对象
        if (resultSet_select_id.next()) {
            int actor_id = resultSet_select_id.getInt("actor_id");
            int roleId = resultSet_select_id.getInt("role_id");
            //获得当前ActorAssRole关联的Actor
            Actor actor = ActorService.getInstance().find(actor_id);
            //获得当前ActorAssRole关联的Role
            Role role = RoleService.getInstance().find(roleId);
            desiredActorAssRole = new ActorAssRole(id, actor, role);
        }
        //关闭三个资源
        JdbcHelper.close(pstmt_select_id, resultSet_select_id);
        return desiredActorAssRole;
    }

    /**
     * 查找该Actor对象所拥有的ActorAssRole对象
     * 用于权限管理中的角色-菜单展示
     *
     * @param connection
     * @param actor
     * @return
     * @throws SQLException
     */
    public Collection<ActorAssRole> findAll(Connection connection, Actor actor) throws SQLException {
        //创建集合类对象，用来保存所有记录代表的ActorAssRole对象
        //TreeSet按compare(Object o)方法指定的顺序排序
        Collection<ActorAssRole> desiredActorAssRoles = new TreeSet<ActorAssRole>();

        //准备预编译的语句
        String sql_select_all = "SELECT * FROM ActorAssRole WHERE actor_id = ?";
        PreparedStatement pstmt_select_all =
                connection.prepareStatement(sql_select_all);
        pstmt_select_all.setInt(1, actor.getId());
        //执行预编译语句，结果集保存在resultSet中
        ResultSet resultSet_select_all = pstmt_select_all.executeQuery();
        //遍历resultSet
        while (resultSet_select_all.next()) {
            int id = resultSet_select_all.getInt("id");
            //获得actorAssRole表中当前记录的id字段的值
            int actor_id = resultSet_select_all.getInt("actor_id");
            int roleId = resultSet_select_all.getInt("role_id");
            //获得当前ActorAssRole关联的Role
            Role role = RoleService.getInstance().find(roleId);
            ActorAssRole desiredActorAssRole =
                    new ActorAssRole(id, actor, role);
            //将当前记录代表的ActorAssRole对象添加到desiredActorAssRoles中
            desiredActorAssRoles.add(desiredActorAssRole);
        }
        JdbcHelper.close(pstmt_select_all, resultSet_select_all);
        return desiredActorAssRoles;
    }

    public Collection<ActorAssRole> findAll(Connection connection) throws SQLException {
        //创建集合类对象，用来保存所有记录代表的ActorAssRole对象
        //TreeSet按compare(Object o)方法指定的顺序排序
        Collection<ActorAssRole> desiredActorAssRoles = new TreeSet<ActorAssRole>();
        //准备预编译的语句
        String sql_select_all = "SELECT * FROM ActorAssRole";
        PreparedStatement pstmt_select_all =
                connection.prepareStatement(sql_select_all);
        //执行预编译语句，结果集保存在resultSet中
        ResultSet resultSet_select_all = pstmt_select_all.executeQuery();
        //遍历resultSet
        while (resultSet_select_all.next()) {
            int id = resultSet_select_all.getInt("id");
            //获得actorAssRole表中当前记录的id字段的值
            int actor_id = resultSet_select_all.getInt("actor_id");
            int roleId = resultSet_select_all.getInt("role_id");
            //获得当前ActorAssRole关联的Actor
            Actor actor = ActorService.getInstance().find(actor_id);
            //获得当前ActorAssRole关联的Role
            Role role = RoleService.getInstance().find(roleId);
            ActorAssRole desiredActorAssRole =
                    new ActorAssRole(id, actor, role);
            //将当前记录代表的ActorAssRole对象添加到desiredActorAssRoles中
            desiredActorAssRoles.add(desiredActorAssRole);
        }
        JdbcHelper.close(pstmt_select_all, resultSet_select_all);
        return desiredActorAssRoles;
    }

    public boolean update(Connection connection, ActorAssRole actorAssRole) throws SQLException {
        //准备预编译的语句
        String sql_update =
                "UPDATE actorAssRole SET actor_id = ?, role_id = ? " +
                        "WHERE id = ?";
        PreparedStatement pstmt_update =
                connection.prepareStatement(sql_update);

        pstmt_update.setInt(1, actorAssRole.getActor().getId());
        pstmt_update.setInt(2, actorAssRole.getRole().getId());
        pstmt_update.setInt(3, actorAssRole.getId());


        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_update.executeUpdate();

        JdbcHelper.close(pstmt_update);
        return rowAffected > 0;
    }

    public boolean delete(Connection connection, Integer id) throws SQLException {
        //准备预编译的语句
        String sql_delete_id = "DELETE FROM actorAssRole WHERE id = ?";
        PreparedStatement pstmt_delete =
                connection.prepareStatement(sql_delete_id);
        pstmt_delete.setInt(1, id);
        //执行预编译语句，影响的行数保存在rowAffected中
        int rowAffected = pstmt_delete.executeUpdate();

        JdbcHelper.close(pstmt_delete);
        return rowAffected > 0;
    }
}

