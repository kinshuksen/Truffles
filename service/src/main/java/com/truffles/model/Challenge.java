package com.truffles.model;

import java.io.Serializable;

public class Challenge implements Serializable{

	private static final long serialVersionUID = -2777274733382821585L;
	
	private int id;
	private String description;
	private String poi_type;
	private int target;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPoi_type() {
		return poi_type;
	}
	public void setPoi_type_id(String poi_type) {
		this.poi_type = poi_type;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	
}
