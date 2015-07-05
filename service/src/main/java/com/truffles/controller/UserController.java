package com.truffles.controller;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truffles.dao.AchievementDao;
import com.truffles.dao.UserDao;
import com.truffles.model.User;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;
	
	
	@Autowired
	@Qualifier("userDao")
	private UserDao uDao;

	
	@Autowired
	@Qualifier("achievementDao")
	private AchievementDao achievementDao;

 
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
    @RequestMapping(value = RestURIConstants.REGISTER_USER, method = RequestMethod.GET)
	public @ResponseBody User registerUser(String email, String token, String password, String deviceUUID) {
    	User user = new User();
    	logger.info("Start registerUser.");
        //TODO - Add validation to check nulls. WIll include service layer later
        try {
        	user = uDao.registerUser(email, token, password, deviceUUID);
			achievementDao.insertAchievements(user.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return user;
	}
    
    @RequestMapping(value = RestURIConstants.GET_USER, method = RequestMethod.GET)
	public @ResponseBody boolean login(String email, String token, String password, String deviceUUID) {
    	boolean ackStatus = false;
    	logger.info("Start login.");
        //TODO - Add validation to check nulls. WIll include service layer later
        try {
        	ackStatus = uDao.getUser(email, token, password, deviceUUID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ackStatus;
	}   
    
	
}
