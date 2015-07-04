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
	
	   @RequestMapping(value = RestURIConstants.BAG_TRUFFLE, method = RequestMethod.GET)
	   	public @ResponseBody Integer bagTruffle(String poiId, String latitude, String longitude, String name, int userId, String poiType) throws Exception {
	    	System.out.println("Starting checkIn()");
	    	return truffleDao.bagTruffle(poiId, latitude, longitude, name, userId, poiType);
	   	}
      
	
	
	
}
