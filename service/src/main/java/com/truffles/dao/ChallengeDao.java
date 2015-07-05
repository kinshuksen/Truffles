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

public class ChallengeDao {
	
	@Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;
	
	public List<Challenge> getChallenges() throws SQLException {
		String query = "SELECT * from challenge" ;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		List<Challenge> chal = new ArrayList<Challenge>();
        try {
			pstmt = dataSource.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Challenge ch = new Challenge();
				ch.setId((rs.getInt("id")));
				ch.setDescription(rs.getString("description"));
				//ch.setPoi_type_id(rs.getString("poi_type"));
				ch.setTarget(rs.getInt("target"));
				chal.add(ch);
			}
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				pstmt.close();
			if(pstmt1 != null)
				pstmt1.close();
			if(rs != null)
				rs.close();
			
		}
        return chal;
        
    }
}
