package com.truffles.model;

import java.io.Serializable;

public class Achievement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5774678679855073162L;
	
	private int progress;
	private String poi_type;
	private int target;
	
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public String getPoi_type() {
		return poi_type;
	}
	public void setPoi_type(String poi_type) {
		this.poi_type = poi_type;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	

}
