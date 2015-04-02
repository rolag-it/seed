package it.tids.seed.webapp.view;

import javax.faces.bean.ManagedBean;

import org.apache.commons.logging.LogFactory;
import org.richfaces.function.RichFunction;

import it.tids.seed.model.User;
import it.tids.seed.service.ServiceProvider;
import it.tids.seed.service.UserManager;
import it.tids.seed.util.SecurityUtil;

@ManagedBean
public class ChangepwdView extends BaseView {
	private static final long serialVersionUID = 972319310612744218L;	
			
	private String nuovaPassword;
	private String confermaNuovaPassword;
	
	public String getNuovaPassword() {
		return nuovaPassword;
	}
	
	public void setNuovaPassword(String nuovaPassword) {
		this.nuovaPassword = nuovaPassword;
	}
	
	public String getConfermaNuovaPassword() {
		return confermaNuovaPassword;
	}
	
	public void setConfermaNuovaPassword(String confermaNuovaPassword) {
		this.confermaNuovaPassword = confermaNuovaPassword;
	}

	public ChangepwdView(){
	}	
	
	public String changePassword() {
		
		String outcome = null;
		
		if (!nuovaPassword.equals(confermaNuovaPassword)) {		
			addErrorMessage(RichFunction.clientId("changePassword"), "errors.pwd.diff");								
		} else
		if (nuovaPassword.contains("\u0020")){
			addErrorMessage(RichFunction.clientId("changePassword"), "errors.pwd.space");	
		} else {
			UserManager userManager = ServiceProvider.getUserManager();
			User user = SecurityUtil.getUser();
			user.setPassword(nuovaPassword);			
			try {				
				userManager.changePassword(user);				
				outcome = redirectOn("home");
			} catch (IllegalArgumentException illegalArgumentException) {				
				addErrorMessage(RichFunction.clientId("changePassword"), "errors.managed", illegalArgumentException.getMessage());				
			} catch (Exception exception) {
				LogFactory.getLog(getClass()).error(exception.getMessage());
				addErrorMessage(RichFunction.clientId("changePassword"), "errors.saving");				
			}						
		}
		return outcome;
	}
}