package it.tids.seed.webapp.component;


import it.tids.seed.model.User;
import it.tids.seed.util.SecurityUtil;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent(value="UserCheckInComponent")
public class UserCheckInComponent extends UIOutput{

	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();			
		
		User currentUser = SecurityUtil.getUser();
		if (currentUser != null) {
			/*
			 * Controllo cambio password
			 */
			if (currentUser.isOneTimePassword()){		
				context.getExternalContext().redirect("/seed/changepwd.html");
			
				context.responseComplete();
			} 		
		
			StringBuilder outputText = new StringBuilder();
			outputText.append("<ul style=\"list-style: none; margin: 0; padding: 5px; line-height: 200%\">");
			outputText.append("<li><span>Utente:&#160;");
			outputText.append(currentUser.getFullName());
			outputText.append("</span></li>");
			outputText.append("<li><span>");
			outputText.append(currentUser.getRolesDescription());
			outputText.append("</span></li>");
		    writer.write(outputText.toString());		
		}
	}
}