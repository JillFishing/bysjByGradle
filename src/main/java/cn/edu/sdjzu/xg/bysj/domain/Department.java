package cn.edu.sdjzu.xg.bysj.domain;

import java.io.Serializable;
import lombok.*;
@Setter
@Getter
public final class Department implements Comparable<Department>,Serializable{
	private Integer id;
	private String description;
	private String no;
	private String remarks;
	private School school;

	public Department(Integer id, String description, String no,
			String remarks, School school) {
		this(description, no, remarks, school);
		this.id = id;
	}

	public Department(String description, String no,
					  String remarks, School school) {
		this(description, no, remarks);
		this.school = school;
	}

	public Department(String description, String no,
					  String remarks) {
		this.description = description;
		this.no = no;
		this.remarks = remarks;
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
	    
	    retValue = "Department ( "
//	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "description = " + this.description + TAB
	        + "no = " + this.no + TAB
	        + "remarks = " + this.remarks + TAB
	        + "school_json = " + this.school + TAB
	        + " )";
	
	    return retValue;
	}

	@Override
	public int compareTo(Department other) {
		// no为排序依据
		return this.no.compareTo(other.no);
	}
	
	
}
