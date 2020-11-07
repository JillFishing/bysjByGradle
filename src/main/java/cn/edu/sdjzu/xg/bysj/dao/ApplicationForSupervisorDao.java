package cn.edu.sdjzu.xg.bysj.dao;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.ApplicationForSupervisorEntry;
import cn.edu.sdjzu.xg.bysj.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class ApplicationForSupervisorDao {
    private static ApplicationForSupervisorDao applicationForSupervisorDao = new ApplicationForSupervisorDao();
    private ApplicationForSupervisorDao() {
    }
    public static ApplicationForSupervisorDao getInstance() {
        return applicationForSupervisorDao;
    }

    public boolean add(List<ApplicationForSupervisorEntry> applicationForSupervisorEntries,
                       String selfIntro, Student student, Connection conn) throws SQLException {
        //使用预编译创建SQL语句
        String create = "insert into applicationForSupervisor(" +
                "teacher1ID, teacher2ID, teacher3ID, teacher4ID, teacher5ID, teacher6ID,selfIntro,student_id) " +
                "values (?,?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(create);
        //执行SQL语句并返回结果和关闭连接
        try (statement) {
            for(int i = 1;i<=6;i++){
                System.out.println(applicationForSupervisorEntries.get(i-1).getTeacher().getId());
                statement.setInt(i, applicationForSupervisorEntries.get(i-1).getTeacher().getId());
            }
            System.out.println(selfIntro);
            statement.setString(7, selfIntro);
            System.out.println(student.getId());
            statement.setInt(8, student.getId());
            statement.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }
}