package it.tids.seed.webapp.view;

import it.tids.seed.model.User;
import it.tids.seed.service.ServiceProvider;
import it.tids.seed.service.SessionManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ActiveUsersView extends BaseView implements Serializable {
	private static final long serialVersionUID = -2725378005612769815L;

	private List<User> listUsers;
	private int rowsCount = 0;
	private int scrollerIndex = 1;
	
	private int anonymousCount = 0;
	private int trustedCount = 0;
		
	public ActiveUsersView() {
		SessionManager sessionManager = ServiceProvider.getSessionManager();
		List<User> applicationUsers = sessionManager.getLoggedUsersList();
		setListUsers((applicationUsers != null) ? new ArrayList<User>(applicationUsers) : new ArrayList<User>());
		
		setAnonymousCount(sessionManager.getAnonymousSessions().intValue());
		setTrustedCount(sessionManager.getAuthenticatedSessions().intValue());
		setRowsCount(listUsers.size());
	}
	
	public List<User> getListUsers() {
		return listUsers;
	}

	private void setListUsers(List<User> listUsers) {
		this.listUsers = listUsers;
	}

	private void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setScrollerIndex(int scrollerIndex) {
		this.scrollerIndex = scrollerIndex;
	}

	public int getScrollerIndex() {
		return scrollerIndex;
	}

	public int getAnonymousCount() {
		return anonymousCount;
	}

	private void setAnonymousCount(int anonymousCount) {
		this.anonymousCount = anonymousCount;
	}


	public int getTrustedCount() {
		return trustedCount;
	}


	public void setTrustedCount(int trustedCount) {
		this.trustedCount = trustedCount;
	}
}
