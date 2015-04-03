package it.tids.seed.service;

import it.tids.seed.model.Device;
import it.tids.seed.model.Measure;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeasureManager {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	public List<Measure> getAll(){
		return (List<Measure>) hibernateTemplate.find("from Measure");
	}	
	
	@Transactional
	public void addMeasure(int deviceId, long unixTimestamp, int value) {
		
		Device relatedDevice = hibernateTemplate.get(Device.class, deviceId);
		Date date = new Date(unixTimestamp*1000);
		
		save(relatedDevice,date,value);	
		
	}
	
	@Transactional
	public void addMeasure(Device relatedDevice, Date date, int value) {				
		save(relatedDevice,date,value);	
		
	}
	
	private void save(Device device, Date date, int value) {
		Measure m = new Measure();
		m.setDevice(device);
		m.setDate(date);
		m.setValue(value);
		
		hibernateTemplate.persist(m);
	}	

}
