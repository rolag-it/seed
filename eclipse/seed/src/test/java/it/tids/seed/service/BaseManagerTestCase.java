package it.tids.seed.service;

import it.tids.seed.security.AppUserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Classe base per i test Service 
 *
 */
public abstract class BaseManagerTestCase  {
    protected final Log log = LogFactory.getLog(getClass());
    
    @Autowired
    private AppUserService userService;
    
    /**
     * Metodo che crea un security context di Spring per consentire test sulla
     * sicurezza dei metodi
     */
    protected void createSecureContext(final String username) {
    	destroySecureContext();
    	
    	UserDetails appUser = userService.loadUserByUsername(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(appUser, appUser.getPassword(), appUser.getAuthorities());			
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
    }

    protected void destroySecureContext() {
    	SecurityContextHolder.clearContext();
    }
}