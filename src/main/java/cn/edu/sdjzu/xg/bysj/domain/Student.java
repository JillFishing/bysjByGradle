package cn.edu.sdjzu.xg.bysj.domain;

import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import lombok.Getter;
import lombok.Setter;
import util.IdService;

import java.io.Serializable;
@Setter
@Getter
public final class Student extends Actor implements Comparable<Student>,Serializable{
	private Integer id;
	private String name;
	private String no;
	private String remarks;
	private StudentClass studentClass;
	//导师
	private Teacher supervisor;
	{
		this.id = IdService.getId();
	}

	public Student(Integer id, String name, String no, String remarks, Teacher supervisor) {
		super();
		this.id = id;
		this.name = name;
		this.no = no;
		this.remarks = remarks;
		this.supervisor = supervisor;
	}

	public Student(String name, String no, String remarks, StudentClass studentClass, Teacher supervisor) {
		this(name, no, remarks, studentClass);
		this.supervisor = supervisor;
	}

	public Student(Integer id, String name, String no,
				   String remarks, StudentClass studentClass) {
		this(name, no, remarks, studentClass);
		this.id = id;
	}

	public Student(String name, String no,
				   String remarks, StudentClass studentClass) {
		this(name, no, remarks);
		this.studentClass = studentClass;
	}

	public Student(String name, String no,
				   String remarks) {
		this.name = name;
		this.no = no;
		this.remarks = remarks;
	}

	public Student(String name, String no, String remarks, Teacher teacher) {
		this(name, no, remarks);
		this.supervisor = teacher;
	}

	public Student(String name, String no) {
		this.name = name;
		this.no = no;
	}

	public Student(String name, String no,Teacher supervisor) {
		this(name, no);
		this.supervisor = supervisor;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", no='" + no + '\'' +
				", remarks='" + remarks + '\'' +
				", studentClass=" + studentClass +
				", supervisor=" + supervisor +
				'}';
	}

	@Override
	public int compareTo(Student other) {
		// no为排序依据
		return this.no.compareTo(other.no);
	}
}
