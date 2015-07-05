package com.truffles.model;

import java.io.Serializable;

public class Achievement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5774678679855073162L;
	
	private int id;
	private int challenge_id;
	private int current_number;
	private int percent_complete;
	private boolean complete;
	private int user_id;
	
	/*Fields to mix up challenge data */
	private String name;
	private String description;
	private int target;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChallenge_id() {
		return challenge_id;
	}
	public void setChallenge_id(int challenge_id) {
		this.challenge_id = challenge_id;
	}
	public int getCurrent_number() {
		return current_number;
	}
	public void setCurrent_number(int current_number) {
		this.current_number = current_number;
	}
	public int getPercent_complete() {
		return percent_complete;
	}
	public void setPercent_complete(int percent_complete) {
		this.percent_complete = percent_complete;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	

}
