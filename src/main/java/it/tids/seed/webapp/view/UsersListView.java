package it.tids.seed.webapp.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import it.tids.seed.model.User;
import it.tids.seed.service.UserManager;

@ManagedBean
@ViewScoped
public class UsersListView extends BaseView {
	private static final long serialVersionUID = -1185314227515508566L;
    	
	private List<User> listUsers;
	private int rows = 0; 
	private int index = 1;
			
	@ManagedProperty(value="#{userManager}")
	transient private UserManager userManager;
	
	public void setUserManager(UserManager userManager){
		this.userManager = userManager;
	}
	
	public List<User> getListUsers() {
		return listUsers;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}	
	
	@PostConstruct
	public void showList(){		
		listUsers =  userManager.getUsersList();
		rows = listUsers.size();
	}
	
}