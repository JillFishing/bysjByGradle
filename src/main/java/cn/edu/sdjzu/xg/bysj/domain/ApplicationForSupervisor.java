package cn.edu.sdjzu.xg.bysj.domain;

import java.io.Serializable;
import java.util.Collection;
import lombok.*;
/**
 * 表示学生提交的选择导师的志愿。
 */
@Setter
@Getter
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

    @Override
    public int compareTo(ApplicationForSupervisor
                                 applicationForSupervisor) {
        return this.id - applicationForSupervisor.id;
    }
}
