package com.truffles.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truffles.dao.AchievementDao;
import com.truffles.dao.TruffleDao;
import com.truffles.model.Truffle;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class TruffleController {
	
	private static final Logger logger = LoggerFactory.getLogger(TruffleController.class);
	
	@Autowired
	@Qualifier("truffleDao")
	private TruffleDao truffleDao;
	
	
	@Autowired
	@Qualifier("achievementDao")
	private AchievementDao achievementDao;
	
	
	   @RequestMapping(value = RestURIConstants.BAG_TRUFFLE, method = RequestMethod.GET)
	   	public @ResponseBody Integer bagTruffle(String poiId, String latitude, String longitude, String name, int userId, String poiType) throws Exception {
	    	System.out.println("Starting checkIn()");
	    	Integer trufffle = truffleDao.bagTruffle(poiId, latitude, longitude, name, userId, poiType);
	    	achievementDao.updateAchievements(userId, poiType);
	    	return trufffle;
	   	}
	   
	   @RequestMapping(value = RestURIConstants.GET_TRUFFLES, method = RequestMethod.GET)
	   	public @ResponseBody List<Truffle> getTruffles(int userId) throws Exception {
	    	System.out.println("Starting checkIn()");
	    	return truffleDao.getTruffles(userId);
	   	}
      
	
	
	
}
