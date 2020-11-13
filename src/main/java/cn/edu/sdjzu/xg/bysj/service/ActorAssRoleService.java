package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.ActorAssRoleDao;
import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import cn.edu.sdjzu.xg.bysj.domain.authority.ActorAssRole;
import util.JdbcHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class ActorAssRoleService {
    private static final ActorAssRoleDao actorAssRoleDao = ActorAssRoleDao.getInstance();
    private static final ActorAssRoleService actorAssRoleService = new ActorAssRoleService();

    private ActorAssRoleService() {
    }

    public static ActorAssRoleService getInstance() {
        return actorAssRoleService;
    }

    //获得某Actor对象关联的所有ActorAssRole对象
    public Collection<ActorAssRole> findAll(Actor actor) throws SQLException {
        //获取数据库连接
        Connection connection = JdbcHelper.getConn();
        Collection<ActorAssRole> actorAssRoles = actorAssRoleDao.findAll(connection, actor);
        JdbcHelper.close(connection);
        return actorAssRoles;
    }

    public Collection<ActorAssRole> findAll() throws SQLException {
        //获取数据库连接
        Connection connection = JdbcHelper.getConn();
        Collection<ActorAssRole> actorAssRoles = actorAssRoleDao.findAll(connection);
        //释放连接
        JdbcHelper.close(connection);
        return actorAssRoles;
    }

    public ActorAssRole find(Integer id) throws SQLException {
        //获取数据库连接
        Connection connection = JdbcHelper.getConn();
        ActorAssRole actorAssRole = actorAssRoleDao.find(connection, id);
        //释放连接
        JdbcHelper.close(connection);
        return actorAssRole;

    }

    public boolean update(ActorAssRole actorAssRole) throws SQLException {
        //获取数据库连接
        Connection connection = JdbcHelper.getConn();
        boolean updated = actorAssRoleDao.update(connection, actorAssRole);
        //释放连接
        JdbcHelper.close(connection);
        return updated;
    }

    public boolean add(ActorAssRole actorAssRole) throws SQLException {
        //获取数据库连接
        Connection connection = JdbcHelper.getConn();
        boolean added = actorAssRoleDao.add(connection, actorAssRole);
        //释放连接
        JdbcHelper.close(connection);
        return added;
    }

    public boolean delete(Integer id) throws SQLException {
        boolean deleted = false;
        Connection connection = JdbcHelper.getConn();
        deleted = actorAssRoleDao.delete(connection, id);
        return deleted;
    }
}

