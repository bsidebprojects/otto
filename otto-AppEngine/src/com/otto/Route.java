package com.otto;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Route {

//	@PrimaryKey
//	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	private Key key;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String name;
	
	@Persistent
	private String group;
	
	@Persistent
	private boolean periodic;
	
	@Persistent
	private Long frecuency;
	
	@Persistent
	private List<String> stops;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean isPeriodic() {
		return periodic;
	}

	public void setPeriodic(boolean periodic) {
		this.periodic = periodic;
	}

	public Long getFrecuency() {
		return frecuency;
	}

	public void setFrecuency(Long frecuency) {
		this.frecuency = frecuency;
	}

	public List<String> getStops() {
		return stops;
	}

	public void setStops(List<String> stops) {
		this.stops = stops;
	}
	
	public void addStop(String stop) {
		if(this.stops == null) {
			this.stops = new ArrayList<String>();
		}
		
		this.stops.add(stop);
	}
}
