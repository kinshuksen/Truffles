package com.truffles.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	
	public void updateAchievements(int userId, String poiType) throws SQLException{
		HashMap<String,Integer> challengeMap = new HashMap<String,Integer>();
			challengeMap.put("FOOD",2); // This will cover challenges 2&6
			challengeMap.put("CULTURE",3); //This will cover challenges 3,4,5
			challengeMap.put("WINE",7);
			challengeMap.put("FIRST",1);// This will cover challenges 7
		Integer challengeId = (Integer) challengeMap.get(poiType);
		if(challengeId!=null){
			int cId = challengeId.intValue();
			System.out.println("challenge id "+cId + "poiType = " + poiType);
			if(cId == 1 || cId == 2 || cId == 3 || cId ==7){
				String query = "UPDATE [achievment] set [current] = [current] + 1 "
						+ "where user_id = ? and challenge_id ";
				if(cId == 2)
					query += " in (?,?)";
				else if(cId == 3)
					query += " in (?,?,?)";
				else if(cId == 7 || cId == 1)
					query += " = ?";
				
				String qry = "UPDATE a "
						+ "set a.complete = 1 "
						+ "FROM achievment a "
						+ "where a.[current] >= (select [target] from challenge c where id = a.challenge_id)";
				
				String frstQry = "select count(*) from [truffle] as count where user_id = "+ userId;
				PreparedStatement pstmt1 = null;
				PreparedStatement pstmt2 = null;
				PreparedStatement pstmt3 = null;
				ResultSet rs = null;
				try {
					int count=0;
					System.out.println("Dodgy code for achievments starts");
					pstmt2 = dataSource.getConnection().prepareStatement(frstQry);
					rs = pstmt2.executeQuery();
					while(rs.next()){
						count = rs.getInt(1);
					}
					System.out.println("Is first check in??" + (count == 1));
					
					pstmt1 = dataSource.getConnection().prepareStatement(query);
					pstmt1.setInt(1, userId);
					if(cId == 2){
						pstmt1.setInt(2, cId);
						pstmt1.setInt(3, 6);
					}else if(cId == 3){
						pstmt1.setInt(2, cId);
						pstmt1.setInt(3, 4);
						pstmt1.setInt(4, 5);
					}else if(cId == 7) {
						pstmt1.setInt(2, cId);
					}else if(count == 1){
						pstmt1.setInt(2, count);
					}
					pstmt1.executeUpdate();
					System.out.println("Updated achievments...");
					pstmt3 = dataSource.getConnection().prepareStatement(qry);
					System.out.println("Updating the status of individual achievments");
					pstmt3.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(pstmt1 != null)
						pstmt1.close();
					if(pstmt2 != null)
						pstmt2.close();
					if(pstmt3 != null)
						pstmt3.close();
					
				}

			}
		}
	}

}
