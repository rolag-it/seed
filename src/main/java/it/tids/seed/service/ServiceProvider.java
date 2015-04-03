package it.tids.seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Fornisce un manager ad un layer richiedente fuori dal context di Spring. 
 *
 */
public final class ServiceProvider {
	
	@Autowired
	private UserManager userManager;	
	
	@Autowired
	SessionManager sessionManager;
	
	private static ServiceProvider instance;
	
	protected ServiceProvider(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	/**
	 * Metodo di aderenza al pattern sigleton 
	 * restituisce l'unica instanza del {@link ServiceProvider}
	 * in modo thread-safe con soluzione "Double-Checked Locking"
	 * 
	 */
	private static synchronized ServiceProvider getInstance() {		
		if (instance == null) {           
			synchronized (ServiceProvider.class){
                if (instance == null) {
                        instance = new ServiceProvider();
                }
            }
		}		
		return instance;
	}	
	
	/**
	 * Restituisce il manager {@link UserManager}
	 * 
	 */
	public static UserManager getUserManager() {		
		return getInstance().userManager;
	}	

	/**
	 * Restituisce il manager {@link SessionManager}
	 * 
	 */
	public static SessionManager getSessionManager() {
		return getInstance().sessionManager;
	}		
}