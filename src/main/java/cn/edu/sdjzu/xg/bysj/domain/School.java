package cn.edu.sdjzu.xg.bysj.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public final class School implements Comparable<School>,Serializable{
	private Integer id;//对应着数据库表中的非业务主键 object id
	private String no;
	private String description;
	private String remarks;

	public School(Integer id, String description, String no, String remarks) {
		this(description, no, remarks);
		this.id = id;
	}

	public School(String description, String no, String remarks) {
		super();
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
	    
	    retValue = "School ( "
	        + "id = " + this.id + TAB
	        + "description = " + this.description + TAB
	        + "no = " + this.no + TAB
	        + "remarks = " + this.remarks + TAB
	        + " )";
	
	    return retValue;
	}

	@Override
	public int compareTo(School other) {
		// no为排序依据
		return this.no.compareTo(other.no);
	}
}
