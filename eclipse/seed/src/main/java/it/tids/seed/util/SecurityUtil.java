package it.tids.seed.util;

import it.tids.seed.model.User;
import it.tids.seed.security.AppUser;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
   
	private SecurityUtil(){}
	
	public static User getUser(){    	
    	AppUser authenticadedUser;     	
    	try {
    		authenticadedUser =	(AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {		
			authenticadedUser =	null;
		}   	
    	return authenticadedUser!=null ? authenticadedUser.getUser() : null;
    }  
	 
	public static boolean isUserInRole(String roleName){
		User usr = getUser();
    	return usr!=null && usr.getRolesName().contains(roleName);
	}
	
}