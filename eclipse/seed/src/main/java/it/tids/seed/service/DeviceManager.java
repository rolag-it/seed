package it.tids.seed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import it.tids.seed.Constants;
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

}
