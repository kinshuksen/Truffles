package com.truffles.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.truffles.constants.POITypes;
import com.truffles.model.POI;

public class POIDao {

	private String url;
	private String atdw;
	private String monuments;
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAtdw() {
		return atdw;
	}

	public void setAtdw(String atdw) {
		this.atdw = atdw;
	}

	public String getMonuments() {
		return monuments;
	}

	public void setMonuments(String monuments) {
		this.monuments = monuments;
	}

	public List<POI> getPOIs(String latitude, String longitude) {

		List<POI> poiList = null;

		try {

			//getMonumentPOIs(latitude, longitude);
			
			poiList = getATDWPOIs(latitude, longitude);

		

		} catch (Exception e) {
			e.printStackTrace();
		}

		return poiList;

	}

	public POI getPOIbyId(String email, String token, String password,
			String deviceUUID, DataSource dataSource) throws SQLException {
		POI poiList = null;
		RestTemplate restTemplate = new RestTemplate();
		String url = "";
		poiList = restTemplate.getForObject(url, POI.class);
		// TODO : Add logic to consume the webservice
		return poiList;

	}

	String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public List<POI> getATDWPOIs(String latitude, String longitude) {

		List<POI> poiList = new ArrayList<POI>();
		RestTemplate restTemplate = new RestTemplate();
		//String url = this.url + "products?key=" +this.key + "&latlong=" + latitude+","+ longitude+ "&dist=" + distance + "&size=20&out=xml&mv=ENGLISH" ;
		//String sUrl = "http://govhack.atdw.com.au/productsearchservice.svc/products?key=928546742361&latlong=-34.922853,138.601914&dist=10&size=20&out=json";
		//String sUrl = "http://intecggmirror.azurewebsites.net/api/GovHackApi/GetATDWPlaceMarkers?lat=-34.922853&lon=138.601914";
		String atdwUrl = this.url + this.atdw + "?lat=" + latitude + "&lon="+ longitude;
		System.out.println(atdwUrl);
		try{
			String jsonResponse = restTemplate.getForObject(atdwUrl, String.class);
			System.out.println(jsonResponse);
			JSONObject jObject  = new JSONObject(jsonResponse); 
			JSONObject data = jObject.getJSONObject("ResponseObject");
			JSONArray jArray = data.getJSONArray("products");
			System.out.println(jArray.length());
			for(int i=0; i< jArray.length(); i++){
				POI poi = new POI();
				JSONObject atdw = jArray.getJSONObject(i);
				poi.setPoiId(atdw.getString("productId"));
				poi.setDescription(atdw.getString("productDescription"));
				poi.setName(atdw.getString("productName"));
				String[] latlong = atdw.getString("nearestLocation").split(",");
				poi.setLatitude(latlong[0]);
				poi.setLongitude(latlong[1]);
				poi.setPoiType(POITypes.INFO);
				poi.setSourceAPI("ATDW");
				poi.setDistance(atdw.getString("distanceToLocation"));
				poiList.add(poi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return poiList ;
	}

	public List<POI> getMonumentPOIs(String latitude, String longitude) {

		List<POI> poiList = null;

		try {
			RestTemplate restTemplate = new RestTemplate();

			String sUrl = "http://intecggmirror.azurewebsites.net/api/GovHackApi/GetPlaceMarkers?lat=-34.922853&lon=138.601914";

			System.out.println(sUrl);

			String res = restTemplate.getForObject(sUrl, String.class);

			// PrintWriter out = new
			// PrintWriter("C:/Users/jeffrey1m/Desktop/mon.txt");
			// out.println(res);

			// res = res.replace("\\s*\\n\\s*", "");
			res = res.trim();

			// System.out.println(res.length());
			// System.out.println(res);

			JSONObject jObject = new JSONObject(res);
			JSONArray places = jObject.getJSONArray("ResponseObject");

			System.out.println(places.length());

			for (int i = 0; i < places.length(); i++) {

				JSONObject place = places.getJSONObject(i);

				String id = place.getString("PlaceMarkerId");
				String name = place.getString("Name");
				String description = place.getString("Description");
				String category = place.getString("CategoryDesc");
				String lat = place.getString("Latitude");
				String long1 = place.getString("Longitude");
				// String dist = place.getString("Distance");

				// POI poi = new POI(id, POITypes.CULTURE, name, description, );

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return poiList;

	}

}
