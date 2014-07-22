package com.otto.admin.shared;

import java.io.Serializable;

public class ServiceDTO implements Serializable {

	private static final long serialVersionUID = -9075428894785314457L;
	
	private String name;
	private double lat;
	private double lng;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
}
