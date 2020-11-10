package cn.edu.sdjzu.xg.bysj.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public final class StudentClass implements Comparable<StudentClass>,Serializable{
	private Integer id;
	private String description;
	private String no;
	private String remarks;
	private Department department;

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

	@Override
	public int compareTo(StudentClass other) {
		// no为排序依据
		return this.no.compareTo(other.no);
	}
}
