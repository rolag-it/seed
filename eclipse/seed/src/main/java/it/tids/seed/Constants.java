package it.tids.seed;


/**
 * Constant values used throughout the application.
 * 
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class Constants {
    //~ Static fields/initializers =============================================
	
	private Constants(){}	
	
	public static final String WEBAPP_CONTEXT = "/seed";
	
	public static final String REMOTE_IP_EVENT_KEY = "REMOTE_IP";
	
	/**
	 * Path dei template utilizzati da JSF
	 * 
	 */
	public static final String DEFAULT_TEMPLATE = "/layout/template.xhtml";	
	
	public static final String DEFAULT_TIMEZONE_ID = "Europe/Rome";
   
	/**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";
    
    public static final String CONFIG = "appConfig";
        
    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";    
    
    /**
     * Dimensione della sigola pagina dei datatable
     * 
     */
    public static final int MAX_PAGE_SIZE = 32;   
        
}