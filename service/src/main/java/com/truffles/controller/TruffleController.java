package com.truffles.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truffles.dao.TruffleDao;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class TruffleController {
	
	private static final Logger logger = LoggerFactory.getLogger(TruffleController.class);
	
	@Autowired
	@Qualifier("truffleDao")
	private TruffleDao truffleDao;
	
	   @RequestMapping(value = RestURIConstants.CHECK_IN, method = RequestMethod.GET)
	   	public @ResponseBody Integer checkIn(String poiId, String latitude, String longitude, String name, int beaconId, int userId, int poiTypeId) throws SQLException {
	    	System.out.println("Starting checkIn()");
	    	return truffleDao.checkIn(poiId, latitude, longitude, name, beaconId, userId, poiTypeId);
	   	}
      
	
	
	
}
