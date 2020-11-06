package cn.edu.sdjzu.xg.bysj.dao;/*
package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.*;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectStatusService;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public final class GraduateProjectDao {
    static Set<GraduateProject> projects;
    private static GraduateProjectDao graduateProjectDao = new GraduateProjectDao();

    static {

        try{



            GraduateProjectStatus applied = GraduateProjectStatusService.getInstance().find(1);

            GraduateProjectCategory design = GraduateProjectCategoryDao.getInstance().find(1);
            GraduateProjectCategory thesis = GraduateProjectCategoryDao.getInstance().find(2);

            GraduateProjectType real = GraduateProjectTypeDao.getInstance().find(1);
            GraduateProjectType virtual = GraduateProjectTypeDao.getInstance().find(2);

            GraduateProject project1 = new GraduateProject("虚拟现实中碰撞处理的研究",
                    thesis, virtual, applied, st);
            GraduateProject project2 = new GraduateProject("某高校监考管理系统的设计与实现",
                    design, real, applied, st);
            GraduateProject project3 = new GraduateProject("网上教学平台的设计与实现",
                    design, real, applied, lx);
            GraduateProject project4 = new GraduateProject("基于分形技术的防伪研究",
                    thesis, real, applied, wf);

            projects = new TreeSet<GraduateProject>();
            projects.add(project1);
            projects.add(project2);
            projects.add(project3);
            projects.add(project4);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private GraduateProjectDao() {
    }

    public static GraduateProjectDao getInstance()throws SQLException {
        return graduateProjectDao;
    }


    public Collection<GraduateProject> findAll()throws SQLException {
        return projects;
    }

    public void addGraduateProject(GraduateProject project)throws SQLException {
        projects.add(project);
    }

    public void update(GraduateProject project)throws SQLException {
        projects.remove(project);
        this.addGraduateProject(project);
    }

    public GraduateProject find(Integer id)throws SQLException {
        GraduateProject desiredGraduateProject = null;
        for (GraduateProject graduateProject : projects) {
            if (id.equals(graduateProject.getId())) {
                desiredGraduateProject = graduateProject;
                break;
            }
        }
        return desiredGraduateProject;

    }

    public void delete(int id)throws SQLException {
        Iterator<GraduateProject> it = projects.iterator();
        while (it.hasNext()) {
            GraduateProject g = it.next();
            if (g.getId() == id) {
                it.remove();
            }
        }
    }
}
*/
