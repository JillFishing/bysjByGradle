package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.ActorDao;
import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import lombok.Cleanup;
import util.JdbcHelper;

import java.sql.Connection;
import java.sql.SQLException;

public final class ActorService {
    private static ActorDao actorDao = ActorDao.getInstance();
    private static ActorService actorService = new ActorService();


    public static ActorService getInstance() {
        return actorService;
    }

    //  不会查找一个Actor集合
    //	不会增加一个Actor
    //	不会修改一个Actor
    //	不会删除一个Actor


    public Actor find(Integer id) throws SQLException {
        @Cleanup Connection connection = JdbcHelper.getConn();
        Actor actor = actorDao.find(id, connection);
        return actor;
    }

}
