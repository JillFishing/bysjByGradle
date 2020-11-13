package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.Student;
import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import cn.edu.sdjzu.xg.bysj.service.StudentService;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import lombok.Cleanup;
import util.Condition;
import util.Pagination;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class ActorDao {
    private static ActorDao actorDao = new ActorDao();
    private static Collection<Actor> actors;

    private ActorDao() {
    }

    public static ActorDao getInstance() {
        return actorDao;
    }

    public Collection<Actor> findAll() throws SQLException {
        return ActorDao.actors;
    }

    public Collection<Actor> findAll(Connection connection, String conditionList, Pagination pagination) throws SQLException {
        //创建Teacher类型集合类对象，HashSet不保证结果中元素的顺序
        Collection<Actor> desiredTeachers = new HashSet<Actor>();

        //创建查询教师的主语句
        StringBuilder sql_select = new StringBuilder("SELECT * FROM actor ");
        //如果有条件，则生成条件子句，并附加到主语句后面
        if (conditionList != null) {
            String whereClause = Condition.toWhereClause(conditionList);
            sql_select.append(whereClause);
        }
        //获取Teacher中的元组总数
        int totalNum = ActorDao.getInstance().count(connection);
        if (pagination != null) {
            String paginationClause = pagination.toLimitClause(totalNum);
            sql_select.append(paginationClause);
        }
        //获得查询教师的语句对象
        @Cleanup PreparedStatement pstmt_select =
                connection.prepareStatement(sql_select.toString());
        //获得教师对象的结果集
        @Cleanup ResultSet resultSet_select = pstmt_select.executeQuery();
        //遍历结果集，根据每个元素创建对象，并添加到集合类对象desiredTeachers中
        while (resultSet_select.next()) {
            int id = resultSet_select.getInt("id");
            String name = resultSet_select.getString("name");
            String no = resultSet_select.getString("no");
            Actor desiredTeacher = new Student(name, no);
            desiredTeacher.setId(id);
            desiredTeachers.add(desiredTeacher);
        }
        return desiredTeachers;
    }

    public Actor find(Integer id, Connection conn) throws SQLException {
        //先后在Sudent和Teacher中查找相应的id
        Actor desiredActor = null;
        //查询id对应的Student对象
        desiredActor = StudentDao.getInstance().find(id, conn);
        if (desiredActor != null) {
            return desiredActor;
        }
        desiredActor = TeacherDao.getInstance().find(id, conn);
        if(desiredActor != null){
            return desiredActor;
        }
        //查询id对应的Teacher对象
        return desiredActor;
        //在各子类对象中均未查询到
    }

    public boolean update(Actor actor) throws SQLException {
        actors.remove(actor);
        return actors.add(actor);
    }

    /**
     * 由于Teacher扩展了Actor，前者对象id取后者的id的值。
     * Actor表只有id一个字段，该字段自动生成。
     * 用于事务。调用本方法的Service，在调用本方法后，还会调用UserDao.add方法
     * 两次对Dao的调用要保证是同一个Connection对象，才能将它们设置在同一事务中
     *
     * @param connection 来自Service的Connection对象
     * @return 成功增加：新对象的id，失败：0
     * @throws SQLException
     */
    public int add(Connection connection) throws SQLException {
        //根据连接对象准备语句对象，如果SQL语句为多行，注意语句不同部分之间要有空格
        @Cleanup PreparedStatement preparedStatement_actor =
                connection.prepareStatement(
                        "INSERT INTO actor " +
                                " ()" +
                                " VALUES ()", Statement.RETURN_GENERATED_KEYS);//要求返回主键集合
        //执行预编译语句，用其返回值、影响的行数为赋值affectedRowNum
        int affectedRowNum = preparedStatement_actor.executeUpdate();
        int idOfAddedActor = 0;
        ResultSet resultSet = preparedStatement_actor.getGeneratedKeys();//获得生成的主键集合
        if (resultSet.next()) {
            idOfAddedActor = resultSet.getInt(1);//读取第一个主键
        }
        //新增元组的id
        return idOfAddedActor;

    }

    public boolean delete(Integer id, Connection conn) throws SQLException {
        //创建删除语句
        String delete = "delete from Actor where id = " + id;
        //在连接上创建语句盒子对象
        Statement statement = conn.createStatement();
        //执行SQL语句
        int i = statement.executeUpdate(delete);
        //关闭连接
        statement.close();
        return (i>0);
    }

    public int count(Connection connection) throws SQLException {
        String sql_count = "SELECT COUNT(id) as cnt FROM actor";
        PreparedStatement pstmt_count = connection.prepareStatement(sql_count);
        int counter = 0;
        ResultSet resultSet_count = pstmt_count.executeQuery();
        if (resultSet_count.next()) {
            counter = resultSet_count.getInt("cnt");
        }return counter;
    }

}
