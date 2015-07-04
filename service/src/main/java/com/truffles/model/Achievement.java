package com.truffles.model;

import java.io.Serializable;

public class Achievement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5774678679855073162L;
	
	private int progress;
	private String poi_type_id;
	private int target;
	
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
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
