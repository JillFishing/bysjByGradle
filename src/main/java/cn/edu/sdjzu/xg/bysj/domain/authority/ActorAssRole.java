package cn.edu.sdjzu.xg.bysj.domain.authority;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActorAssRole implements Comparable<ActorAssRole>, Serializable {
	
	private Integer id;
	private Actor actor;
	private Role role;

	@Override
	public int compareTo(ActorAssRole o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}
	
	
}
