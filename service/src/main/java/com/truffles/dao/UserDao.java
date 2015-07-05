package com.truffles.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.truffles.model.User;

public class UserDao {
	
	@Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;
	
	public User registerUser(String email, String token, String password, String deviceUUID) throws SQLException {
		User newId = new User();
		String query = "INSERT INTO [user] values (?,?,?,?)";
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
        //JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        try {
			pstmt = dataSource.getConnection().prepareStatement(query);
			int i=1;
			pstmt.setString(i++, email);
			pstmt.setString(i++, token);
			pstmt.setString(i++, password);
			pstmt.setString(i++, deviceUUID);
			pstmt.executeUpdate();
			
			String returnQuery = "SELECT IDENT_CURRENT('[user]')";
			pstmt1 = dataSource.getConnection().prepareStatement(returnQuery);
			rs = pstmt1.executeQuery();
			while(rs.next()){
				newId.setId(rs.getInt(1));
				newId.setEmail(email);
				newId.setToken(token);
				newId.setDeviceUUID(deviceUUID);
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
	public boolean getUser(String email, String token, String password, String deviceUUID) throws SQLException{
		//JDBC Code - Start
		String query = "select * from [User] where email = ? AND password = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean usr = false;

		try {
			pstmt = dataSource.getConnection().prepareStatement(query);

			pstmt.setString(1, email);
			pstmt.setString(1, password);
			rs = pstmt.executeQuery();

			while(rs.next()){
				usr = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(pstmt != null)
				pstmt.close();
			if(rs != null)
				rs.close();
		}
		return usr;

	}
}
