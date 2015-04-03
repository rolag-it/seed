package it.tids.seed.security;

import it.tids.seed.model.User;
import it.tids.seed.service.UserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class AppUserService implements UserDetailsService {
	
	@Autowired
	private UserManager userManager;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		User user = userManager.getUserByUsername(username);		
		if (user == null) throw new UsernameNotFoundException("User "+username+" not found");		
		return new AppUser(user);
	}

}
