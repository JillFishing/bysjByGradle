package cn.edu.sdjzu.xg.bysj.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class User  implements Comparable<User>,Serializable{
	private Integer id;
	private String username;
	private String password;
	private LocalDateTime loginTime;
	private Teacher teacher;
	
	public User(){}

	public User(String username, String password, LocalDateTime loginTime,
				Teacher teacher) {
		super();
		this.username = username;
		this.password = password;
		this.loginTime = loginTime;
		this.teacher = teacher;
	}

	public User(Integer id, String username, String password, LocalDateTime loginTime,
				Teacher teacher) {
		this(username, password, loginTime, teacher);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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
	        + "teacher = " + this.teacher + TAB
	        + " )";
	
	    return retValue;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}

}
