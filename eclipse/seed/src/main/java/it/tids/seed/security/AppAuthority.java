package it.tids.seed.security;

import it.tids.seed.model.Role;

import org.springframework.security.core.GrantedAuthority;

public class AppAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 483331076454990885L;

	private Role role;	
	
	public AppAuthority(){		
	}
	
	public AppAuthority(Role role){
		this.role = role;
	}			
	
	public Role getRole() {
		return role;
	}
	
	@Override
	public String getAuthority() {		
		return role!=null ? role.getName() : "ROLE_ANONYMOUS";
	}	
		
}