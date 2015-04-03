package it.tids.seed.model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="daily_perfomances")
public class Performance {

	@Id
	@Column(name="performance_id", insertable=false, updatable=false, unique=true, nullable = false)
	private String id;
	
	@Column(name="performance_day", insertable=false, updatable=false, nullable = false)
	private Timestamp day;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "device_id")
	private Device device;
	
	@Column(name = "performance_value", insertable=false, updatable=false, nullable = false)
	private int value;
	
	
	Performance() {
		
	}

	public String getId() {
		return id;
	}


	public Timestamp getDay() {
		return day;
	}
	
	public String getDayLabel(){
		Date date = new Date(day.getTime());		
		DateFormat formatter = new  SimpleDateFormat("dd-MM-YYYY");
		return formatter.format(date);
	}


	public Device getDevice() {
		return device;
	}


	public int getValue() {
		return value;
	}
	
	@Transient
	public float getVolume() {
		return value*device.getLoadFactor();
	}


	void setId(String id) {
		this.id = id;
	}


	void setDay(Timestamp day) {
		this.day = day;
	}


	void setDevice(Device device) {
		this.device = device;
	}

	void setValue(int value) {
		this.value = value;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + value;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Performance other = (Performance) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (value != other.value)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Performance [id=" + id + ", day=" + day + ", device="
				+ device + ", value=" + value + "]";
	}	
	

}
