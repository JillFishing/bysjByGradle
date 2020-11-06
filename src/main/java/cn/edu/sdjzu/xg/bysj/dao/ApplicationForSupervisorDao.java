package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.ApplicationForSupervisorEntry;
import cn.edu.sdjzu.xg.bysj.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplicationForSupervisorDao {
    private static ApplicationForSupervisorDao applicationForSupervisorDao = new ApplicationForSupervisorDao();
    private ApplicationForSupervisorDao() {
    }
    public static ApplicationForSupervisorDao getInstance() {
        return applicationForSupervisorDao;
    }

    public boolean add(ApplicationForSupervisorEntry[] applicationForSupervisorEntries,
                       String selfIntro, Student student, Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into applicationForSupervisorEntry(" +
                "teacher1ID, teacher2ID, teacher3ID, teacher4ID, teacher5ID, teacher6ID,selfIntro,student_id) " +
                "values (?,?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            statement.setInt(1, applicationForSupervisorEntries[0].getTeacher().getId());
            statement.setInt(2, applicationForSupervisorEntries[1].getTeacher().getId());
            statement.setInt(3, applicationForSupervisorEntries[2].getTeacher().getId());
            statement.setInt(4, applicationForSupervisorEntries[3].getTeacher().getId());
            statement.setInt(5, applicationForSupervisorEntries[4].getTeacher().getId());
            statement.setInt(6, applicationForSupervisorEntries[5].getTeacher().getId());
            statement.setString(7, selfIntro);
            statement.setInt(8, student.getId());
            statement.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }
}