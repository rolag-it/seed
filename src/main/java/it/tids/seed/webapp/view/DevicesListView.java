package it.tids.seed.webapp.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import it.tids.seed.model.Device;
import it.tids.seed.model.Performance;
import it.tids.seed.service.DeviceManager;

@ManagedBean
@ViewScoped
public class DevicesListView extends BaseView {
	private static final long serialVersionUID = -1185314227515508566L;
    	
	private List<Device> devices;
	private int rows = 0; 
	private int index = 1;
			
	@ManagedProperty(value="#{deviceManager}")
	transient private DeviceManager deviceManager;
		
	public void setDeviceManager(DeviceManager deviceManager){
		this.deviceManager = deviceManager;
	}
	
	public List<Device> getDevices() {
		return devices;
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
		devices =  deviceManager.getAll();
		rows = devices.size();
		
		for (Device device: devices){
			List<Performance> performances = deviceManager.getDailyPerformances(device.getId());
			device.getPerformances().addAll(performances);
		}
	}
	
	public Device getCenterDevice() {
		if (devices!=null && !devices.isEmpty()) {
			return devices.get(0);
		} else {
			getFacesContext().responseComplete();
			redirectOn("empty");
			return null;
		}
	}
	
	
	
}