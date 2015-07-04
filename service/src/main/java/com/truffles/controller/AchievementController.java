package com.truffles.controller;

import java.sql.SQLException;
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
import com.truffles.model.Achievement;


/**
 * Handles requests for the Employee service.
 */
@Controller
public class AchievementController {
	
	private static final Logger logger = LoggerFactory.getLogger(AchievementController.class);
	
	
	@Autowired
	@Qualifier("achievementDao")
	private AchievementDao achievementDao;
	
    
   @RequestMapping(value = RestURIConstants.GET_ACHIEVEMENTS, method = RequestMethod.GET)
  	public @ResponseBody List<Achievement> getAchivements(int userId) throws SQLException {
   	System.out.println("Starting getAchivements()");
   	return achievementDao.getProgress(userId);
  	}
	
	
	
}
