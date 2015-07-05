package com.truffles.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.truffles.model.Beacon;
import com.truffles.model.Challenge;

public class BeaconDao {
	
	@Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;
	
	
	public List<Beacon> getBeacons() throws SQLException{
		//JDBC Code - Start
		String query = "select * from Beacon";
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		List<Beacon> beacons = new ArrayList<Beacon>();
		
        try {
			pstmt = dataSource.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Beacon beacon = new Beacon();
				beacon.setId((rs.getInt("id")));
				beacon.setName(rs.getString("name"));
				beacon.setLatitude(rs.getString("latitude"));
				beacon.setLongitude(rs.getString("longitude"));
				beacon.setUUID(rs.getString("UUID"));
				beacon.setContent(rs.getString("content"));
				beacon.setRadius(rs.getInt("radius"));
				beacons.add(beacon);
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
		return beacons;

	}
	
 
}
