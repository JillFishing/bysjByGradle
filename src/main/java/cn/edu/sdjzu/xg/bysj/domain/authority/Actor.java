package cn.edu.sdjzu.xg.bysj.domain.authority;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
@Getter
@Setter
public abstract class Actor {	
	private Collection<ActorAssRole> actorAssRoles;
	public Integer id;
	public String name;
	public String no;

}
