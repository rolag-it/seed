package it.tids.seed.webapp.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

import it.tids.seed.model.Role;
import it.tids.seed.model.User;
import it.tids.seed.service.ServiceProvider;
import it.tids.seed.service.UserManager;

@ManagedBean
@ViewScoped
public class UserFormView extends BaseView {
	private static final long serialVersionUID = -3791170727771053834L;
	protected static final String PARAM_USER_ID = "usrname";
		
	private User user=null;
	private List<String> userRoles;
	private Map<String, String> availableRoles;	
		
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<String> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}
	
	public Map<String, String> getAvailableRoles() {
		return availableRoles;
	}	
			
	@PostConstruct
	public void initForm(){				
		availableRoles = new LinkedHashMap<String, String>();
		userRoles = new ArrayList<String>();
		
		UserManager userManager = ServiceProvider.getUserManager();			
		List<Role> roles = userManager.getRoleList();		
		for (Role role : roles) {
			availableRoles.put(role.getDescription(), role.getName());
		}
		
		String username = (String) retrieveFlashParam(PARAM_USER_ID);	
		if (username!=null){
			user = ServiceProvider.getUserManager().getUserByUsername(username);
			if (user!=null){
				for (Role role : user.getRoles()){
					userRoles.add(role.getName());
				}			
			}
		} else {
			user = new User();
			user.setEnabled(true);
		}		
	}
			
	public String save(){
		String outcome = "usersList";
		
		try {
			UserManager userManager = ServiceProvider.getUserManager();
			user.getRoles().clear();
			for (String roleName : userRoles){
				user.getRoles().add(userManager.getRole(roleName));
			}
			
			userManager.saveUser(user);
			getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
			addInfoMessage(null, "success.saving");			
		
		} catch (DataIntegrityViolationException usrEx) {
			addErrorMessage(null, "errors.existing.user", user.getUsername());
			outcome = null;
		} catch (Exception e) {
			LogFactory.getLog(getClass()).error(e.getMessage());
			addErrorMessage(null, "errors.saving");	
			outcome = null;
		}
		
		return outcome;
		
	}
	
	public String resetPassword(){
		String outcome = redirectOn("usersList");		
			try {
				UserManager userManager = ServiceProvider.getUserManager();
				userManager.resetPassword(user.getId());
				
				getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
				addInfoMessage(null, "user.reset", user.getUsername());
			} catch (Exception e) {
				LogFactory.getLog(getClass()).error(e.getMessage());
				addErrorMessage(null, "errors.saving");	
				outcome = null;
			}		
		return outcome;
		
	}
	
	public String cancel(){
		return redirectOn("usersList");
	}
}