package cn.edu.sdjzu.xg.bysj.domain.authority;

import lombok.*;

import java.io.Serializable;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resource implements Comparable<Resource>, Serializable {
	private Integer id;
	private String description;
	private String url;
	private String no;
	private String remarks;
	//本资源属于的角色
	private Role role;

	@Override
	public int compareTo(Resource o) {
		// TODO Auto-generated method stub
		return this.id - o.id;
	}
}
