package it.tids.seed.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.tids.seed.Constants;
import it.tids.seed.model.Role;
import it.tids.seed.model.User;
import it.tids.seed.security.AppUser;
import it.tids.seed.util.SecurityUtil;


/**
 * Definisce i metodi di business-logic per la 
 * gestione dell'entit&#224; di business
 * "Utente applicativo"
 *
 */
@Service
public class UserManager  {   

	@Autowired
	private HibernateTemplate hibernateTemplate;	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    /**
     * Recupera i dati persistenti di un utente applicativo specificato per identificativo univoco
     *
     * @param userId identificativo univoco dell'utente da recuperare
     * @return  <ul>
     * 			<li> @{link User} entit&#224; persistente corrispondente all'id specificato </li>
     * 			<li> <code>null</code> se nessuna entit&#224; persistente corrisponde all'id specificato </li>
     */
	@Secured(Constants.ROLE_ADMIN)
	public User getUser(Long userId) {		
		return hibernateTemplate.get(User.class, userId);
    }

	/**
     * Recupera i dati persistenti di un utente applicativo specificato per username univoco
     *
     * @param username username univoco dell'utente da recuperare
     * @return  {link User} entit&#224; persistente corrispondente allo username specificato
     * @throws  {link UsernameNotFoundException} se nessuna entit&#224; persistente corrisponde allo username specificato 			
     */
	@SuppressWarnings("unchecked")		
    public User getUserByUsername(String username)  {
		List<User> listUser = (List<User>) hibernateTemplate.find("from User u where u.username = ?",username);
		return listUser.isEmpty()? null : listUser.get(0);
    }

    /**
     * Recupera i dati persistenti di tutti gli utenti applicativi
     * 
     */
    @Secured(Constants.ROLE_ADMIN)
    @SuppressWarnings("unchecked")		
    public List<User> getUsersList() {
        return (List<User>) hibernateTemplate.find("from User");
    }
    
    /**
     * Recupera i dati persistenti di un ruolo applicativo specificato per identificativo univoco
     *
     * @param userId identificativo univoco del ruolo da recuperare
     * @return  <ul>
     * 			<li> @{link Role} entit&#224; persistente corrispondente all'id specificato </li>
     * 			<li> <code>null</code> se nessuna entit&#224; persistente corrisponde all'id specificato </li>
     */    
    @Secured(Constants.ROLE_ADMIN)
    public Role getRole(Long id){
    	return hibernateTemplate.get(Role.class, id);
    }
    
    /**
     * Recupera i dati persistenti di un ruolo applicativo specificato per nome univoco
     *
     * @param userId identificativo univoco del ruolo da recuperare
     * @return  <ul>
     * 			<li> @{link Role} entit&#224; persistente corrispondente al nome specificato </li>
     * 			<li> <code>null</code> se nessuna entit&#224; persistente corrisponde al nome specificato </li>
     */
    @SuppressWarnings("unchecked")
	@Secured(Constants.ROLE_ADMIN)
    public Role getRole(String name){
		List<Role> roles = (List<Role>) hibernateTemplate.find("from Role where name=?", name);
        return (roles.isEmpty()) ? null : (Role) roles.get(0);
    }
    
    /**
     * Recupera i dati persistenti di tutti i ruoli applicativi
     * 
     */
    @Secured(Constants.ROLE_ADMIN)
    @SuppressWarnings("unchecked")		
    public List<Role> getRoleList() {
        return (List<Role>) hibernateTemplate.find("from Role");
    }

    /**
     * Rende persistenti i dati per l'utente specificato; 
     * se i dati corrispondono ad un nuovo utente la password verr&#224; impostata uguale allo username specifiato.
     *
     *
     * @throws @{link UserExistsException} se i dati che si stanno salvando corrispondono di un nuovo utente con username gi&#224; utilizzato.
     * 
     */
    @Secured(Constants.ROLE_ADMIN)
	@Transactional
    public void saveUser(User user) {
     
       if (user.isNewEntity()){    	       	   
    	   user.setVersion(0);    	   	
    	   user.setUsername(user.getUsername().toLowerCase());	      	  
	       user.setPassword(passwordEncoder.encodePassword(user.getUsername(),null));
	       user.setOneTimePassword(true);       
       }     
       
       hibernateTemplate.saveOrUpdate(user);	    	
      
	}   
    
    /**
     * Reimposta la password per l'utente specificato come il suo username.
     * 
     */    
    @Secured({Constants.ROLE_ADMIN,Constants.ROLE_USER})
    @Transactional
	public User resetPassword(Long userId){
		User user = getUser(userId);
		 user.setPassword(passwordEncoder.encodePassword(user.getUsername(),null));
		 user.setOneTimePassword(true);
		 hibernateTemplate.saveOrUpdate(user);	
		 securityCheck(user);
		 return user;
	}
    
    /**
     * Reimposta la password per l'utente specificato con quella impostata nell'oggetto passato.
     * Il salvataggio della password avviene in modaalit&#224; criptata.
     * 
     */    
    @Secured({Constants.ROLE_ADMIN,Constants.ROLE_USER})
    @Transactional
	public User changePassword(User user){		
		user.setOneTimePassword(false);
		user.setPasswordTime(new Timestamp(System.currentTimeMillis()));
		user.setPassword(passwordEncoder.encodePassword(user.getPassword(),null));
		hibernateTemplate.saveOrUpdate(user);	
		securityCheck(user);
		return user;
	}
    
    /**
     * Recupera la lista degli utenti applicativi che corrispondono ai parametri specificati. 
     *
     */
    @Secured(Constants.ROLE_ADMIN)
    @SuppressWarnings({ "unchecked", "rawtypes" })		
    public List<User> findUser(String nomeUtente, String cognome, String nome){
		List queryParams = new ArrayList();
    	
    	StringBuilder queryString = new StringBuilder("from User u where id>0");						
		if(nomeUtente!=null && nomeUtente!=""){			
			queryString.append(" and u.username like ? ");
			queryParams.add("%"+nomeUtente+"%");
		}				
							
		if(cognome!=null && cognome!=""){			
			queryString.append(" and u.lastName like ? ");
			queryParams.add("%"+cognome+"%");
		}	
		
		if(nome!=null && nome!=""){		
			queryString.append(" and u.firstName like ? ");
			queryParams.add("%"+nome+"%");
		}

		queryString.append(" order by u.username");
		return (List<User>) hibernateTemplate.find(queryString.toString(),queryParams.toArray()); 
    }
    
    private void securityCheck(User modifiedUser){
		User loggedUser = SecurityUtil.getUser();		
		boolean loggedUserChanged = loggedUser.getId().equals(modifiedUser.getId());		
		
		if (loggedUserChanged) {
			AppUser appUser = new AppUser(modifiedUser);
			Authentication authentication = new UsernamePasswordAuthenticationToken(appUser, appUser.getPassword(), appUser.getAuthorities());			
			SecurityContextHolder.getContext().setAuthentication(authentication);			
		}
	}   
    
}
