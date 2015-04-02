package it.tids.seed.webapp.view;

import it.tids.seed.Constants;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;

@ManagedBean
public class LoginView extends BaseView {
	private static final long serialVersionUID = 365248799921582233L;

	@PostConstruct
	public void initLogin(){
		getSession().setAttribute(Constants.REMOTE_IP_EVENT_KEY, getRequest().getRemoteAddr());
	}
	
	public String login() {		
		
		String outcome = null;
		try {		    
			HttpServletRequest request = getRequest();
			HttpServletResponse response = getResponse();			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/j_security_check");
			dispatcher.forward(request, response);
			
			getFacesContext().responseComplete();
			
		} catch (Exception e) {
			LogFactory.getLog(getClass()).error("Eccezione generatata durante l'autenticazione di Spring-Security", e);
			outcome = "error";
		}
		
		return outcome;
		
	}
}