package cn.edu.sdjzu.xg.bysj.domain;

import java.io.Serializable;
import lombok.*;
@Setter
@Getter
public final class GraduateProjectType implements Comparable<GraduateProjectType>,Serializable  {
	private Integer id;
	private String description;
	private String no;
	private String remarks;

	public GraduateProjectType() {
	}

	public GraduateProjectType(Integer id, String description, String no, String remarks) {
		super();
		this.id = id;
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
	    
	    retValue = "GraduateProjectType ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "description = " + this.description + TAB
	        + "no = " + this.no + TAB
	        + "remarks = " + this.remarks + TAB
	        + " )";
	
	    return retValue;
	}



	@Override
	public int compareTo(GraduateProjectType other) {
		// no为排序依据
		return this.no.compareTo(other.no);
	}
	
	
	
}
