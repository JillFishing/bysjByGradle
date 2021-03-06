package cn.edu.sdjzu.xg.bysj.domain;

import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;

import java.io.Serializable;

public final class Teacher
		extends Actor
		implements Comparable<Teacher>, Serializable{

	private Integer id;
	private String no;
	private String name;
	//属性名为Title，字段名为profTitle
	private ProfTitle profTitle;
	private Degree degree;
	private Department department;

//	private Set<GraduateProject> projects;
//	private Set<Student> students;


	public Teacher(Integer id,
                   String name,
                   String no,
                   ProfTitle title,
                   Degree degree,
                   Department department) {
		this(name, no, title, degree, department);
		this.id = id;

	}

	public Teacher(
            String name, String no) {
		super();
		this.name = name;
		this.no = no;
	}

	public Teacher(
				   String name,
				   String no,
				   ProfTitle title,
				   Degree degree,
				   Department department) {
		this(name, no);
		this.profTitle = title;
		this.degree = degree;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//属性名为Title，字段名为profTitle
	public ProfTitle getTitle() {
		return this.profTitle;
	}
	//属性名为Title，字段名为profTitle
	public void setTitle(ProfTitle title) {
		this.profTitle = title;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

//	public Set<GraduateProject> getProjects() {
//		return projects;
//	}
//
//	public void setProjects(Set<GraduateProject> projects) {
//		this.projects = projects;
//	}
//
//	public Set<Student> getStudents() {
//		return students;
//	}
//
//	public void setStudents(Set<Student> students) {
//		this.students = students;
//	}

	@Override
	public int compareTo(Teacher other) {
		// no为排序依据
		return this.no.compareTo(other.no);
	}


	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "Teacher ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "name = " + this.name + TAB
	        + "title = " + this.profTitle + TAB
	        + "degree = " + this.degree + TAB
	        + "department = " + this.department + TAB
//	        + "projects = " + this.projects + TAB
	        + " )";
	
	    return retValue;
	}

}
