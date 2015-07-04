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

import com.truffles.dao.ChallengeDao;
import com.truffles.model.Achievement;
import com.truffles.model.Challenge;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class ChallengeController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);
	
	
	@Autowired
	@Qualifier("challengeDao")
	private ChallengeDao challengeDao;

	 @RequestMapping(value = RestURIConstants.GET_CHALLENGES, method = RequestMethod.GET)
	  	public @ResponseBody List<Challenge> getChallenges() throws SQLException {
	   	System.out.println("Starting getChallenges()");
	   	return challengeDao.getChallenges();
	  	}
	
	
}
