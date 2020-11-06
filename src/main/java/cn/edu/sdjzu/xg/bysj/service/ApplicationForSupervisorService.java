package cn.edu.sdjzu.xg.bysj.service;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.dao.ApplicationForSupervisorDao;
import cn.edu.sdjzu.xg.bysj.domain.ApplicationForSupervisorEntry;
import cn.edu.sdjzu.xg.bysj.domain.Student;
import util.JdbcHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class ApplicationForSupervisorService {
    private static ApplicationForSupervisorDao applicationForSupervisorDao = ApplicationForSupervisorDao.getInstance();
    private static ApplicationForSupervisorService applicationForSupervisorService =new ApplicationForSupervisorService();
    private ApplicationForSupervisorService(){}

    public static ApplicationForSupervisorService getInstance(){
        return applicationForSupervisorService;
    }
    public boolean add(ApplicationForSupervisorEntry[] applicationForSupervisorEntries,
                       String selfIntro, Student student) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean add = applicationForSupervisorDao.add(applicationForSupervisorEntries,selfIntro,student,conn);
        conn.close();
        return add;
    }
}
