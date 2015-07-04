package com.truffles.model;

import java.io.Serializable;

public class Challenge implements Serializable{

	private static final long serialVersionUID = -2777274733382821585L;
	
	private int id;
	private String description;
	private String poi_type_id;
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
	public String getPoi_type_id() {
		return poi_type_id;
	}
	public void setPoi_type_id(String poi_type_id) {
		this.poi_type_id = poi_type_id;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	
}
