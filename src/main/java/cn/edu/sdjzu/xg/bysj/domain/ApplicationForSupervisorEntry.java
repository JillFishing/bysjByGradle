package cn.edu.sdjzu.xg.bysj.domain;

import lombok.*;

import java.io.Serializable;

/**
  * 表示一个目标导师和对应的志愿顺序
 */
@Setter
@Getter
public class ApplicationForSupervisorEntry implements Comparable<ApplicationForSupervisorEntry>, Serializable {
    public ApplicationForSupervisorEntry(Teacher teacher, int priority) {
        this.teacher = teacher;
        this.priority = priority;
    }

    //本类不需要 id
    //目标导师
    private Teacher teacher;
    //志愿顺序
    private int priority;
    private ApplicationForSupervisor applicationForSupervisor;

    public ApplicationForSupervisorEntry() {
    }

    public ApplicationForSupervisorEntry(Teacher teacher, int priority, ApplicationForSupervisor applicationForSupervisor) {
        this.teacher = teacher;
        this.priority = priority;
        this.applicationForSupervisor = applicationForSupervisor;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public int getPriority() {
        return priority;
    }

    public ApplicationForSupervisor getApplicationForSupervisor() {
        return applicationForSupervisor;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setApplicationForSupervisor(ApplicationForSupervisor applicationForSupervisor) {
        this.applicationForSupervisor = applicationForSupervisor;
    }
    @Override
    public int compareTo(ApplicationForSupervisorEntry applicationForSupervisorEntry) {
        return this.priority - applicationForSupervisorEntry.priority;
    }
}
