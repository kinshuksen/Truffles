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

public class AchievementDao {
	
	@Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;
	
	public List<Achievement> getProgress(int userId) throws SQLException {
		String query = "SELECT count(*) AS progress,"
				+ "truffle.poi_type,"
				+ " challenge.[target]"
				+ "FROM truffle, challenge "
				+ "where user_id = ? "
				+ "GROUP BY truffle.poi_type, challenge.[target]";
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		List<Achievement> ach = new ArrayList<Achievement>();
        try {
			pstmt = dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Achievement ac = new Achievement();
				ac.setProgress(rs.getInt("progress"));
				ac.setPoi_type(rs.getString("poi_type"));
				ac.setTarget(rs.getInt("target"));
				ach.add(ac);
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
        return ach;
        
    }
}
