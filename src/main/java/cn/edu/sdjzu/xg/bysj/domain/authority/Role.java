package cn.edu.sdjzu.xg.bysj.domain.authority;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class Role implements Comparable<Role>, Serializable {
	private Integer id;
	private String description;
	private String no;
	//本对象对应的所有的资源。表中没有对应的字段，需要倒查。
	private Collection<Resource> resources;

	public Role(Integer id, String description, String no) {
		this.id = id;
		this.description = description;
		this.no = no;
	}

	@Override
	public int compareTo(Role o) {
		return this.id - o.id;
	}
}
