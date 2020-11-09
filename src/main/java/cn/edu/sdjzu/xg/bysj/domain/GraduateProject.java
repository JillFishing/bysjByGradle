package cn.edu.sdjzu.xg.bysj.domain;

import util.IdService;

import java.io.Serializable;
import lombok.*;
@Setter
@Getter
public final class GraduateProject implements Comparable<GraduateProject>,Serializable{
	private Integer id;
	private String title;
	private GraduateProjectCategory graduateProjectCategory;
	private GraduateProjectType graduateProjectType;
	private GraduateProjectStatus graduateProjectStatus;
	private Teacher teacher;
	{
		this.id = IdService.getId();
	}

	public GraduateProject() {
		super();
	}




	public GraduateProject(int id, String title,
                           GraduateProjectCategory graduateProjectCategory, GraduateProjectType graduateProjectType,
                           GraduateProjectStatus graduateProjectStatus, Teacher teacher) {
		this(title, graduateProjectCategory, graduateProjectType,graduateProjectStatus,teacher);
		this.id = id;
	}


	public GraduateProject(String title,
						   GraduateProjectCategory graduateProjectCategory,
						   GraduateProjectType graduateProjectType,
						   GraduateProjectStatus graduateProjectStatus,
						   Teacher teacher) {
		super();
		this.title = title;
		this.graduateProjectCategory = graduateProjectCategory;
		this.graduateProjectType = graduateProjectType;
		this.graduateProjectStatus = graduateProjectStatus;
		this.teacher = teacher;
	}

	@Override
	public int compareTo(GraduateProject o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GraduateProject other = (GraduateProject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	    
	    retValue = "GraduateProject ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "title = " + this.title + TAB
	        + "projectCategrory = " + this.graduateProjectCategory + TAB
	        + "graduateProjectType = " + this.graduateProjectType + TAB
	        + "teacher = " + this.teacher + TAB
	        + " )";
	
	    return retValue;
	}

}
