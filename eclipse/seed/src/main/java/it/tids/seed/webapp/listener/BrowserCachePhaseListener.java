package it.tids.seed.webapp.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class BrowserCachePhaseListener implements PhaseListener {
	private static final long serialVersionUID = -7847462081197491998L;

	@Override
	public void afterPhase(PhaseEvent event) {
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {		
        HttpServletResponse response = (HttpServletResponse) event.getFacesContext().getExternalContext().getResponse();
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
       
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
        
        response.addHeader("Expires", "Mon, 31 Dec 2000 23:59:59 GMT");

	}

	@Override
	public PhaseId getPhaseId() {		
		return PhaseId.RENDER_RESPONSE;
	}

}
