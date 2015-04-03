package it.tids.seed.webapp.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

@FacesComponent(value="ResetSessionScopedBeanComponent")
public class ResetSessionScopedBeanComponent extends UIOutput{
	
	public void encodeBegin(FacesContext context) throws IOException {
		String method = context.getExternalContext().getRequestParameterMap().get("method");
		Object beanName = getAttributes().get("beanName");
		if( "reset".equals(method) && beanName!=null){		
			context.getExternalContext().getSessionMap().remove(beanName.toString());
			String requestPath = context.getExternalContext().getRequestServletPath();
			String contextPath = context.getExternalContext().getRequestContextPath();
			context.getExternalContext().redirect(contextPath+requestPath);
		}		
	}
}