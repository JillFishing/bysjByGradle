package cn.edu.sdjzu.xg.bysj.service;
//201902104050 姜瑞临

import cn.edu.sdjzu.xg.bysj.dao.DepartmentDao;
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;
import util.JdbcHelper;
import util.Pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class DepartmentService {
    private static DepartmentDao departmentDao= DepartmentDao.getInstance();
    private static DepartmentService departmentService=new DepartmentService();

    private DepartmentService(){}

    public static DepartmentService getInstance(){
        return departmentService;
    }

    public Collection<Department> findAll(String condition,Pagination pagination) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        Collection<Department> departments = departmentDao.findAll(conn,condition,pagination);
        JdbcHelper.close(null,conn);
        return departments;
    }

    public Department find(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        Department department =  departmentDao.find(id,conn);
        conn.close();
        return department;
    }

    public boolean update(Department department) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean updated = departmentDao.update(department,conn);
        conn.close();
        return updated;
    }

    public boolean add(Department department) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean add = departmentDao.add(department,conn);
        conn.close();
        return add;
    }
    public Integer countAll(School  school) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        Integer num = departmentDao.countAll(school,conn);
        conn.close();
        return num;
    }
    public boolean delete(Integer id) throws SQLException {
        Connection conn = JdbcHelper.getConn();
        boolean beDeled = departmentDao.delete(id,conn);
        conn.close();
        return beDeled;
    }

}

