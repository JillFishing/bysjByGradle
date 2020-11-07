package cn.edu.sdjzu.xg.bysj.domain;

import util.IdService;

import java.io.Serializable;

public final class Student implements Comparable<Student>,Serializable{
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

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public StudentClass getStudentClass() {
		return studentClass;
	}

	public Teacher getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Teacher supervisor) {
		this.supervisor = supervisor;
	}

	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
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
