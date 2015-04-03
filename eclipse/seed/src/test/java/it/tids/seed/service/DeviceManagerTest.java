package it.tids.seed.service;

import it.tids.seed.model.Device;
import it.tids.seed.model.Performance;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-test.xml"})
public class DeviceManagerTest extends BaseManagerTestCase{
	
	@Autowired
	private DeviceManager machineManager;
	
	@Test
	public void testGetAll(){
		createSecureContext("user");
		List<Device> machines = machineManager.getAll();		
		assertFalse(machines.isEmpty());
		destroySecureContext();
	}
	
	@Test
	public void testGetPerformances(){
		List<Performance> performances = machineManager.getDailyPerformances();
		assertFalse(performances.isEmpty());
		
		createSecureContext("user");
		Device device = machineManager.getAll().get(0);		
		List<Performance> devicePerformances = machineManager.getDailyPerformances(device.getId());
		for (Performance performance: devicePerformances) {
			assertTrue(performance.getDevice().equals(device));
		}
		destroySecureContext();
	}
	
	@Test
	public void testSicurezzaMetodi(){
		try {
			machineManager.getAll();
			fail();
		} catch (Exception e) {			
			assertTrue(e instanceof AuthenticationCredentialsNotFoundException);	
		}
	}

}
