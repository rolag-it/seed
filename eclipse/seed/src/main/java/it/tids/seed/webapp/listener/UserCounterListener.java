package it.tids.seed.webapp.listener;

import it.tids.seed.Constants;
import it.tids.seed.security.AppUser;
import it.tids.seed.service.ServiceProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;


public class UserCounterListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
	protected final Log log = LogFactory.getLog(getClass());
    public static final String EVENT_KEY = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
      
	@Override
    public void contextInitialized(ServletContextEvent event) {
		ServiceProvider.getSessionManager().clear();
    }

	@Override
    public void contextDestroyed(ServletContextEvent event) {
		ServiceProvider.getSessionManager().clear();
    }   
    
	@Override
    public void attributeAdded(HttpSessionBindingEvent event) {
    	
		if (event.getName().equals(EVENT_KEY)) {        	
            SecurityContext securityContext = (SecurityContext) event.getValue();	            
            if (securityContext.getAuthentication().getPrincipal() instanceof AppUser) {
            	AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();                
            	ServiceProvider.getSessionManager().registerLogon(event.getSession().getId(), appUser.getUser());
            } 
       	}
    	
    	if (event.getName().equals(Constants.REMOTE_IP_EVENT_KEY)) {   		
    		String remoteIp = event.getSession().getAttribute(Constants.REMOTE_IP_EVENT_KEY).toString();        	
        	ServiceProvider.getSessionManager().registerClientIP(event.getSession().getId(), remoteIp.trim());
       	}
    }    

	@Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
		
    }

	@Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
		
    }

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		ServiceProvider.getSessionManager().register(event.getSession().getId(), null, null);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		ServiceProvider.getSessionManager().unregister(event.getSession().getId());
	}   
}