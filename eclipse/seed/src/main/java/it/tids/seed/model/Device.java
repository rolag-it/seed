package it.tids.seed.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the machine database table.
 * 
 */
@Entity
@Table(name = "devices")
public class Device implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "device_id", insertable = false, updatable = false)
	private Integer id;

	@Column(name = "device_code")
	private String code;

	@Column(name = "device_lat")
	private Float latitude;

	@Column(name = "device_lon")
	private Float longitude;
	
	@Column(name = "device_loadfactor")
	private Float loadFactor;
	
	@Transient
	private final transient List<Performance> performances;	
	
	public Device() {
		performances = new LinkedList<>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLoadFactor() {
		return this.loadFactor;
	}

	public void setLoadFactor(Float loadFactor) {
		this.loadFactor = loadFactor;
	}

	public Float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	public List<Performance> getPerformances() {
		return performances;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((loadFactor == null) ? 0 : loadFactor.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
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
		Device other = (Device) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (loadFactor == null) {
			if (other.loadFactor != null)
				return false;
		} else if (!loadFactor.equals(other.loadFactor))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Machine [id=" + id + ", code=" + code + ", latitude="
				+ latitude + ", loadFactor=" + loadFactor + ", longitude="
				+ longitude + "]";
	}

}