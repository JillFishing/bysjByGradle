package cn.edu.sdjzu.xg.bysj.domain;

import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class User implements Comparable<User>,Serializable{
	private Integer id;
	private String username;
	private String password;
	private LocalDateTime loginTime;
	private Actor actor;
	
	public User(){}

	public User(String username, String password, LocalDateTime loginTime,
				Actor teacher) {
		super();
		this.username = username;
		this.password = password;
		this.loginTime = loginTime;
		this.actor = teacher;
	}

	public User(Integer id, String username, String password, LocalDateTime loginTime,
				Actor teacher) {
		this(username, password, loginTime, teacher);
		this.id = id;
	}

	public User(int id, String username, String password, Actor actor) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.actor = actor;
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
	    
	    retValue = "Login ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "username = " + this.username + TAB
	        + "password = " + this.password + TAB
	        + "loginTime = " + this.loginTime + TAB
	        + "teacher = " + this.actor + TAB
	        + " )";
	
	    return retValue;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}

}
