package it.tids.seed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import it.tids.seed.Constants;
import it.tids.seed.model.Performance;
import it.tids.seed.model.Device;

@Service
public class DeviceManager {

	@Autowired
	private HibernateTemplate hibernateTemplate;
		
	@Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
	@SuppressWarnings("unchecked")
	public List<Device> getAll() {
		
		return (List<Device>) hibernateTemplate.find("from Device");
	}
	
	@SuppressWarnings("unchecked")	
	public List<Performance> getDailyPerformances(){			
		return (List<Performance>) hibernateTemplate.find("from Performance");
	}
	
	@SuppressWarnings("unchecked")	
	public List<Performance> getDailyPerformances(Integer id){
		String query = "from Performance p";
		if (id!=null) {
			query+=" where p.device.id = "+id;
		}
		query+=" order by p.device.id, p.day";
		return (List<Performance>) hibernateTemplate.find(query);
	}

}
