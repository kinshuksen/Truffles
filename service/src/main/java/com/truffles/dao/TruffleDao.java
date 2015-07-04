package com.truffles.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TruffleDao {
	
	@Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;
	
	public Integer bagTruffle(String poiId, String latitude, String longitude, String name, int userId, String poiType) throws Exception {
		Integer newId = null;
		String query = "INSERT INTO [truffle] values (?,?,?,?,?,?,?) ";
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
        try {
			pstmt = dataSource.getConnection().prepareStatement(query);
			int i=1;
			pstmt.setString(i++, poiId);
			pstmt.setString(i++, name);
			pstmt.setString(i++, latitude);
			pstmt.setString(i++, longitude);
			pstmt.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(i++, userId);
			pstmt.setString(i++, poiType);			
			pstmt.executeUpdate();
			
			String returnQuery = "SELECT IDENT_CURRENT('[truffle]')";
			pstmt1 = dataSource.getConnection().prepareStatement(returnQuery);
			rs = pstmt1.executeQuery();
			while(rs.next()){
				newId = rs.getInt(1);
				System.out.println(newId);
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
        return newId;
        
    }
}
