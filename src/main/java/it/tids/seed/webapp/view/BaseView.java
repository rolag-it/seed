package it.tids.seed.webapp.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;

import it.tids.seed.Constants;
import it.tids.seed.util.ParseUtil;

@ManagedBean
public class BaseView implements Serializable {
	private static final long serialVersionUID = 7018999032731211646L;
	
	protected ResourceBundle getBundle() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return ResourceBundle.getBundle(getBundleName(), getRequest().getLocale(), classLoader);
	}
	
	protected String getText(String key) {
		String message;

		try {
			message = getBundle().getString(key);
		} catch (java.util.MissingResourceException mre) {
			LogFactory.getLog(getClass()).warn("Missing key for '" + key + "'");
			return "???" + key + "???";
		}

		return message;
	}
	
	protected void addInfoMessage(String componentId, String msgKey, Object...args) {
		String value = getText(msgKey, args);
		getFacesContext().addMessage(componentId,
				new FacesMessage(FacesMessage.SEVERITY_INFO, value, value));

	}

	protected void addErrorMessage(String componentId, String msgKey, Object...args) {
		String value = getText(msgKey, args);
		getFacesContext().addMessage(componentId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, value, value));

	}

	protected String getText(String key, Object arg) {
		if (arg == null) {
			return getText(key);
		}
		try {
			MessageFormat form = new MessageFormat(getBundle().getString(key));
			if (arg instanceof String) {
				return form.format(new Object[] { arg });
			} else if (arg instanceof Object[]) {
				return form.format(arg);
			} else {
				LogFactory.getLog(getClass()).error("arg '" + arg + "' not String or Object[]");
	
				return "";
			}
		} catch (java.util.MissingResourceException mre) {
			LogFactory.getLog(getClass()).warn("Missing key for '" + key + "'");
			return "???" + key + "???";
		}
	}	
	
	
	protected final String redirectOn(String outcome){
		return String.format("%s?faces-redirect=true", outcome);
	}	
	
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	protected final void addFlashParam(String key, Object value){
		getFacesContext().getExternalContext().getFlash().put(key, value);
	}
	
	protected final Object retrieveFlashParam(String key){
		return getFacesContext().getExternalContext().getFlash().get(key);		
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
	}
	
	protected HttpSession getSession() {
		return (HttpSession) getFacesContext().getExternalContext().getSession(false);
	}
	
	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
	}

	protected ServletContext getServletContext() {
		return (ServletContext) getFacesContext().getExternalContext().getContext();
	}

	protected void sendNotFoundError() {
		try {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
			getFacesContext().responseComplete();
		} catch (Exception e) {
			LogFactory.getLog(getClass()).error("Errore reindirizzamento su 404", e);
		}
	}

	protected void sendForbiddenError() {
		try {
			getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
			getFacesContext().responseComplete();
		} catch (Exception e) {
			LogFactory.getLog(getClass()).error("Errore reindirizzamento su 403", e);
		}
	}

	public String getStackTrace() {
		Throwable currentException = (Throwable) getFacesContext()
				.getExternalContext().getRequestMap()
				.get("javax.servlet.error.exception");

		String stacktrace = " [Dettagli errore non disponibili] ";

		if (currentException != null) {			
			if(currentException instanceof ViewExpiredException){
				try {
					getFacesContext().getExternalContext().redirect("/seed/timeout.xhtml");
					getFacesContext().responseComplete();
				} catch (IOException e) {				
				} 				
			} 	else {
				StringWriter writer = new StringWriter();
				PrintWriter printWriter = new PrintWriter(writer);

				currentException.printStackTrace(printWriter);
				stacktrace = writer.toString();
			}			
		}

		return stacktrace;
	}

	public String getBundleName() {
		return Constants.BUNDLE_KEY;
	}	
	
	public String getDefaultTemplate() {
		return Constants.DEFAULT_TEMPLATE;
	}
	
	public TimeZone getTimeZone() {
		return TimeZone.getTimeZone(Constants.DEFAULT_TIMEZONE_ID);

	}	
	
	public void validateNotEmpty(FacesContext context, UIComponent component, Object value) throws ValidatorException {		
		if (ParseUtil.isBlank(value)){			
			String message = getText("javax.faces.component.UIInput.REQUIRED");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		}
	}
}
