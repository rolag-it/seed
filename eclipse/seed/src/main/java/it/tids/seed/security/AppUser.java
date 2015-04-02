package it.tids.seed.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import it.tids.seed.model.Role;
import it.tids.seed.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUser implements UserDetails {
	private static final long serialVersionUID = -2090350593982613109L;

	private User user;
	
	public AppUser() {	
	}
	
	public AppUser(User user) {	
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<AppAuthority> authorities = new HashSet<AppAuthority>();
		if (user!=null){
			for (Role role : user.getRoles()) {
				authorities.add(new AppAuthority(role));
			}
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}	

	@Override
	public boolean isCredentialsNonExpired() {		
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {		
		return isEnabled();
	}

	@Override
	public boolean isEnabled() {
		return user!=null && user.isEnabled();
	}

	@Override
	public String getPassword() {	
		return user!=null ? user.getPassword() : "anonymous";
	}

	@Override
	public String getUsername() {	
		return user!=null ? user.getUsername() : "anonymous";
	}

}
