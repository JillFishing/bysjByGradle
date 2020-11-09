package cn.edu.sdjzu.xg.bysj.domain;

import lombok.Getter;
import lombok.Setter;
import util.IdService;

import java.io.Serializable;
import java.util.Set;
@Setter
@Getter
public final class Teacher implements Comparable<Teacher>,Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String no;
	private String name;
	//属性名为Title，字段名为profTitle
	private ProfTitle profTitle;
	private Degree degree;
	private Department department;
	private Set<GraduateProject> projects;
	private Set<Student> students;

	{
		this.id = IdService.getId();
	}

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

	public Teacher(Integer id) {
		this.id = id;
	}

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
	        + "projects = " + this.projects + TAB
	        + " )";
	
	    return retValue;
	}

}
