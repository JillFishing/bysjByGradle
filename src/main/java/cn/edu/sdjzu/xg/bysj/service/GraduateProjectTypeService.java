package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.GraduateProjectTypeDao;
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectType;

import java.sql.SQLException;

public final class GraduateProjectTypeService {
	private static GraduateProjectTypeService graduateProjectTypeService = new GraduateProjectTypeService();
	private GraduateProjectTypeDao graduateProjectTypeDao = GraduateProjectTypeDao.getInstance();
	
	private GraduateProjectTypeService(){}
	
	public static GraduateProjectTypeService getInstance(){
		return graduateProjectTypeService;
	}

	public String findAll() throws SQLException {
		return graduateProjectTypeDao.finaAll();
	}

	public GraduateProjectType find(Integer id) throws SQLException {
		return graduateProjectTypeDao.find(id);
	}
	
	public boolean update(GraduateProjectType graduateProjectType) throws SQLException {
		return graduateProjectTypeDao.update(graduateProjectType);
	}
	
	public boolean add(GraduateProjectType graduateProjectType) throws SQLException {
		return graduateProjectTypeDao.add(graduateProjectType);
	}
	
	public boolean delete(Integer id) throws SQLException {
		return graduateProjectTypeDao.delete(id);
	}
}
