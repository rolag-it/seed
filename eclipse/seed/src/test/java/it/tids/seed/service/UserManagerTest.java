package it.tids.seed.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.tids.seed.model.User;
import it.tids.seed.service.UserManager;
import it.tids.seed.util.SecurityUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-test.xml"})
public class UserManagerTest extends BaseManagerTestCase {
	
	@Autowired
	UserManager userManager;
		
	@Test
	public void testSaveUser() {		
		createSecureContext("admin");
		
		User user = new User();
		user.setFirstName("Test");
		user.setLastName("Test");
		user.setUsername("Test");
		user.setPassword("Test");
						
		assertNull(user.getId());		
		
		hibernateTemplate.clear();
		
		userManager.saveUser(user);
	    assertNotNull(user.getId());
        assertTrue(user.isOneTimePassword());
        
        destroySecureContext();
    }
		
	@Test	
	public void testUsernameCostraint(){
		createSecureContext("admin");
		
		User user = new User();		
		user.setFirstName("Test");
		user.setLastName("Test");
		user.setUsername("admin");			
		
		assertNull(user.getId());
		
		try {
			userManager.saveUser(user);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof DataIntegrityViolationException);
			assertTrue(e.getMessage().contains("username"));
		}
		
		destroySecureContext();
	}
	
	@Test
	public void testGetUserAndResetPassword(){
		createSecureContext("admin");
		
		Long id = new Long(1);
		
		User user = userManager.getUser(id);
		
		assertEquals(id, user.getId());		
		assertFalse(user.isOneTimePassword());
		
		userManager.resetPassword(id);
		user = userManager.getUser(id);
		assertTrue(user.isOneTimePassword());
		
		destroySecureContext();
	}
	
	@Test
	public void testSicurezzaMetodi(){
		try {
			userManager.getUsersList();
			fail();
		} catch (Exception e) {			
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		try {
			userManager.getUser(null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		try {
			userManager.getUserByUsername(null);

		} catch (Exception e) {
			fail();	
		}
		
		try {
			userManager.saveUser(null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		try {
			userManager.findUser(null,null,null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		try {
			userManager.getRole(0L);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		try {
			userManager.getRole("Test");
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		try {
			userManager.getRoleList();
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		try {
			userManager.resetPassword(null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		try {
			userManager.changePassword(null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
		
		createSecureContext("user");
		
		try {
			userManager.getUser(null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);	
		}
		
		try {
			userManager.saveUser(null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);	
		}
		
		try {
			userManager.resetPassword(SecurityUtil.getUser().getId());
			
		} catch (Exception e) {
			fail();				
		}
		
		try {
			userManager.changePassword(SecurityUtil.getUser());			
		} catch (Exception e) {
			fail();				
		}
		destroySecureContext();
	}

}
