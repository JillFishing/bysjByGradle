package cn.edu.sdjzu.xg.bysj.service;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.dao.MajorDao;
import cn.edu.sdjzu.xg.bysj.domain.Major;
import java.sql.SQLException;

public class MajorService {
    private static MajorDao majorDao= MajorDao.getInstance();
    private static MajorService majorService=new MajorService();
    private MajorService(){}

    public static MajorService getInstance(){
        return majorService;
    }

    public String findAll() throws SQLException {
        return majorDao.findAll();
    }
    public Major find(Integer id) throws SQLException {
        return majorDao.find(id);
    }

    public boolean update(Major major) throws SQLException {
        return majorDao.update(major);
    }

    public boolean add(Major major) throws SQLException {
        return majorDao.add(major);
    }

    public boolean delete(Integer id) throws SQLException {
        return majorDao.delete(id);
    }
}
