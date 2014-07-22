package com.otto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Service {

//	@PrimaryKey
//	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	private Key key;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private Long securityCode;
	
	@Persistent
	private Long routeId;
	
	@Persistent
	private String routeName;
	
	@Persistent
	private Date start;

	@Persistent
	private Date end;
	
	@Persistent
	private List<String> track;
	
	@Persistent
	private List<String> messages;
	
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Long getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(Long securityCode) {
		this.securityCode = securityCode;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getTrack() {
		return track;
	}

	public void setTrack(List<String> track) {
		this.track = track;
	}
	
	public void addTrack(String track) {
		if(this.track == null) {
			this.track = new ArrayList<String>();
		}
		
		this.track.add(track);
	}
	
	public void addMessage(String message) {
		if(this.getMessages() == null) {
			this.setMessages(new ArrayList<String>());
		}
		
		this.getMessages().add(message);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}