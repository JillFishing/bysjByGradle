package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.DegreeDao;
import cn.edu.sdjzu.xg.bysj.domain.Degree;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class DegreeService {
    private static DegreeDao degreeDao = DegreeDao.getInstance();
    private static DegreeService degreeService =new DegreeService();
    private DegreeService(){}

    public static DegreeService getInstance(){
        return degreeService;
    }

    public Collection<Degree> findAll(String condition, Pagination pagination) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        Collection<Degree> degrees =degreeDao.findAll(conn, condition, pagination);
        conn.close();
        return degrees;
    }


    public Degree find(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        Degree degree = degreeDao.find(id,conn);
        conn.close();
        return degree;
    }

    public boolean update(Degree degree) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean update = degreeDao.update(degree,conn);
        conn.close();
        return update;
    }

    public boolean add(Degree degree) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean add = degreeDao.add(degree,conn);
        conn.close();
        return add;
    }

    public boolean delete(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean delete = degreeDao.delete(id,conn);
        conn.close();
        return delete;
    }

}

