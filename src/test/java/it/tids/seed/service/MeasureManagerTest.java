package it.tids.seed.service;

import it.tids.seed.model.Device;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-test.xml"})
public class MeasureManagerTest extends BaseManagerTestCase {
	
	@Autowired
	private MeasureManager measureManager;
	
	@Autowired
	private DeviceManager deviceManager;
	
	@Test
	public void testInserimentoMisure(){
		createSecureContext("user");
		List<Device> devices = deviceManager.getAll();
		
		int initialMeasures = measureManager.getAll().size();
		
		
		long startTime = 1425250200000l; //1 marzo 2015
		long oneDay = 1000*60*60*24;
		Random random = new Random(startTime);
		for (Device device:devices) {
						
			int measureValue = 10240 + random.nextInt(4096);
			
			for (int i=0;i<31;i++){
				long measureTime = startTime+(oneDay*i);
				measureValue+=random.nextInt(320)+64;
				measureManager.addMeasure(device, new Date(measureTime), measureValue);
			}
			
		}
		
		int insertedMeasures = 31*devices.size();
		int finalMeasures = measureManager.getAll().size();
		
		assertEquals(finalMeasures-initialMeasures, insertedMeasures);
		destroySecureContext();
	}

}
