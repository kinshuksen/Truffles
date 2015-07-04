package com.truffles.model;

import java.io.Serializable;

public class POI implements Serializable {
	
	private static final long serialVersionUID = 7649030387627573638L;
	
	private String poiId;
	private String poiType;
	private String name;
	private String description;
	private String latitude;
	private String longitude;
	private String sourceAPI;
	private String distance;

	public POI(){
		
	}
	public POI(String poiId, String poiType, String name,
			String description, String latitude, String longitude, String sourceAPI, String distance) {
		super();
		this.poiId = poiId;
		this.poiType = poiType;
		this.name = name;
		this.description = description;
		this.latitude = latitude;		
		this.longitude = longitude;
		this.sourceAPI = sourceAPI;
		this.distance = distance;
	}
	
	
	public String getPoiId() {
		return poiId;
	}
	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}
	public String getPoiType() {
		return poiType;
	}
	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getSourceAPI() {
		return sourceAPI;
	}


	public void setSourceAPI(String sourceAPI) {
		this.sourceAPI = sourceAPI;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	

}
