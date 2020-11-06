package cn.edu.sdjzu.xg.bysj.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 * 表示学生提交的选择导师的志愿。
 */

public final class ApplicationForSupervisor implements
        Comparable<ApplicationForSupervisor>, Serializable {
    //志愿集合
    private Collection<ApplicationForSupervisorEntry> applicationForSupervisorEntries;
    private Integer id;
    //自我介绍
    private String selfIntroduction;
    //填写志愿的学生
    private Student student;
    //年份
    private int year;

    public ApplicationForSupervisor() {
    }

    public ApplicationForSupervisor(Collection<ApplicationForSupervisorEntry> applicationForSupervisorEntries, Integer id, String selfIntroduction, Student student, int year) {
        this.applicationForSupervisorEntries = applicationForSupervisorEntries;
        this.id = id;
        this.selfIntroduction = selfIntroduction;
        this.student = student;
        this.year = year;
    }

    public Collection<ApplicationForSupervisorEntry> getApplicationForSupervisorEntries() {
        return applicationForSupervisorEntries;
    }

    public void setApplicationForSupervisorEntries(Collection<ApplicationForSupervisorEntry> applicationForSupervisorEntries) {
        this.applicationForSupervisorEntries = applicationForSupervisorEntries;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    @Override
    public int compareTo(ApplicationForSupervisor
                                 applicationForSupervisor) {
        return this.id - applicationForSupervisor.id;
    }
}
