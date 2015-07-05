package com.truffles.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.truffles.model.Achievement;
import com.truffles.model.Challenge;

public class AchievementDao {
	
	@Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;
	
	
	@Autowired
	@Qualifier("challengeDao")
	private ChallengeDao challengeDao;
	
	public void insertAchievements(int userId) throws SQLException{
		List<Challenge> chList = challengeDao.getChallenges();
		System.out.println("Adding challenges into Achievement for user" + userId);
		String query = "INSERT INTO [achievment] values (?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			for(int i=0; i< chList.size();i++){
				pstmt = dataSource.getConnection().prepareStatement(query);
				Challenge ch = chList.get(i);
				pstmt.setInt(1, ch.getId());
				pstmt.setInt(2, 0);
				pstmt.setBoolean(3, false);
				pstmt.setInt(4, userId);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public List<Achievement> getAchievements(int userId){
		List<Achievement> ach = new ArrayList<Achievement>();
		String query = "select a.[id], a.[challenge_id], a.[current], a.[complete], a.[user_id], "
				+ "c.[name], c.[description], c.[target] "
				+ "from [achievment] a RIGHT JOIN [challenge] c "
				+ "on a.challenge_id = c.id "
				+ "where a.user_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Achievement ac = new Achievement();
				ac.setId(rs.getInt("id"));
				ac.setChallenge_id(rs.getInt("challenge_id"));
				ac.setCurrent_number(rs.getInt("current"));
				ac.setComplete(rs.getBoolean("complete"));
				ac.setUser_id(rs.getInt("user_id"));
				ac.setName(rs.getString("name"));
				ac.setDescription(rs.getString("description"));
				ac.setTarget(rs.getInt("target"));
				ach.add(ac);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ach;
	}
	public void updateAchievements(){
		String query = "UPDATE [achievment] set ";
	}
        
}
