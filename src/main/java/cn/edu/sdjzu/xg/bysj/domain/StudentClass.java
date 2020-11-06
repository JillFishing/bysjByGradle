package cn.edu.sdjzu.xg.bysj.domain;

import util.IdService;

import java.io.Serializable;

public final class StudentClass implements Comparable<StudentClass>,Serializable{
	private Integer id;
	private String description;
	private String no;
	private String remarks;
	private Department department;
	{
		this.id = IdService.getId();
	}
	public StudentClass(Integer id, String description, String no,
                        String remarks, Department department) {
		this(description, no, remarks, department);
		this.id = id;
	}

	public StudentClass(String description, String no,
                        String remarks, Department department) {
		this(description, no, remarks);
		this.department = department;
	}

	public StudentClass(String description, String no,
                        String remarks) {
		this.description = description;
		this.no = no;
		this.remarks = remarks;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}



	@Override
	public int compareTo(StudentClass other) {
		// no为排序依据
		return this.no.compareTo(other.no);
	}
}
