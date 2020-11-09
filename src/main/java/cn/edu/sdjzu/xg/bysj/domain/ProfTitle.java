package cn.edu.sdjzu.xg.bysj.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public final class ProfTitle  implements Comparable<ProfTitle>,Serializable{
	private Integer id;
	private String description;
	private String no;
	private String remarks;
	public ProfTitle(Integer id, String description, String no, String remarks) {
		super();
		this.id = id;
		this.description = description;
		this.no = no;
		this.remarks = remarks;
	}

	@Override
	public int compareTo(ProfTitle other) {
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
	    
	    retValue = "Title ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "description = " + this.description + TAB
	        + "no = " + this.no + TAB
	        + "remarks = " + this.remarks + TAB
	        + " )";
	
	    return retValue;
	}
	
	
}
