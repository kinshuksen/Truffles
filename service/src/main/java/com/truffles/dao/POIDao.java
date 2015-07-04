package com.truffles.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.truffles.constants.ATDWTypes;
import com.truffles.constants.POITypes;
import com.truffles.constants.SourceAPIs;
import com.truffles.controller.TruffleController;
import com.truffles.model.POI;

public class POIDao {

	private static final Logger logger = LoggerFactory.getLogger(POIDao.class);
	
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

			poiList = getATDWPOIs(latitude, longitude);

			poiList.addAll(getMonumentPOIs(latitude, longitude));

			// sort this list by distance
			if (poiList.size() > 0) {
				Collections.sort(poiList, new Comparator<POI>() {
					@Override
					public int compare(final POI object1,
							final POI object2) {
						return object1.getDistance().compareTo(object2.getDistance());
					}
				});
			}
						

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("size of POI list - " + poiList.size());

		return poiList.subList(0, 9);

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

		Date start = new Date();
		
		List<POI> poiList = new ArrayList<POI>();
		RestTemplate restTemplate = new RestTemplate();
		// String url = this.url + "products?key=" +this.key + "&latlong=" +
		// latitude+","+ longitude+ "&dist=" + distance +
		// "&size=20&out=xml&mv=ENGLISH" ;
		// String sUrl =
		// "http://govhack.atdw.com.au/productsearchservice.svc/products?key=928546742361&latlong=-34.922853,138.601914&dist=10&size=20&out=json";
		// String sUrl =
		// "http://intecggmirror.azurewebsites.net/api/GovHackApi/GetATDWPlaceMarkers?lat=-34.922853&lon=138.601914";
		String atdwUrl = this.url + this.atdw + "?lat=" + latitude + "&lon="
				+ longitude;
		
		try {
			String jsonResponse = restTemplate.getForObject(atdwUrl,
					String.class);
			//System.out.println(jsonResponse);
			JSONObject jObject = new JSONObject(jsonResponse);
			JSONObject data = jObject.getJSONObject("ResponseObject");
			JSONArray jArray = data.getJSONArray("products");
			//System.out.println(jArray.length());
			for (int i = 0; i < jArray.length(); i++) {
				POI poi = new POI();
				JSONObject atdw = jArray.getJSONObject(i);

				poi.setPoiId(atdw.getString("productId"));
				poi.setDescription(atdw.getString("productDescription"));
				poi.setName(atdw.getString("productName"));
				String[] latlong = atdw.getString("nearestLocation").split(",");
				poi.setLatitude(latlong[0]);
				poi.setLongitude(latlong[1]);

				poi.setSourceAPI(SourceAPIs.ATDW);
				poi.setDistance(atdw.getString("distanceToLocation"));
				
				String type = getPOITypeBySourceAPIType(poi,
						atdw.getString("productCategoryId"));

								
				if (type != null) {
					poi.setPoiType(type);
					poiList.add(poi);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date end = new Date();
		
		System.out.println("---adtw taken = " + end.compareTo(start));
		
		return poiList;
	}

	public List<POI> getMonumentPOIs(String latitude, String longitude) {


		Date start = new Date();
		
		List<POI> poiList = new ArrayList<POI>();

		try {
			RestTemplate restTemplate = new RestTemplate();

			String sUrl = "http://intecggmirror.azurewebsites.net/api/GovHackApi/GetPlaceMarkers?lat="
					+ latitude + "&lon=" + longitude;

			String res = restTemplate.getForObject(sUrl, String.class);

			res = res.trim();

			// System.out.println(res.length());
			// System.out.println(res);

			JSONObject jObject = new JSONObject(res);
			JSONArray places = jObject.getJSONArray("ResponseObject");

			for (int i = 0; i < places.length(); i++) {

				JSONObject place = places.getJSONObject(i);

				String id = place.getString("PlaceMarkerId");
				String name = place.getString("Name");
				String description = place.getString("Description");
				String lat = place.getString("Latitude");
				String long1 = place.getString("Longitude");
				String dist = place.getString("Distance");

				POI poi = new POI(id, null, name, description, lat, long1,
						SourceAPIs.MONUMENTS, dist);

				String type = getPOITypeBySourceAPIType(poi,
						place.getString("CategoryDesc"));

				if (!StringUtils.isEmpty(type)) {
					poi.setPoiType(type);
					poiList.add(poi);
				}

				poiList.add(poi);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Date end = new Date();
				
		return poiList;

	}

	/**
	 * Returns a value from POITypes based on the existing type from the source
	 * api and our business rules.
	 * 
	 * @param poi
	 * @param SourceAPIType
	 * @return
	 */
	public String getPOITypeBySourceAPIType(POI poi, String sourceAPIType) {

		String type = "";

		// all data from last year's api goes into culture as they are
		// monumenets, heritage buildings etc.
		if (poi.getSourceAPI().equals(SourceAPIs.MONUMENTS)) {

			if (sourceAPIType.equals("Plaque")) {
				return "";
			}
			return POITypes.CULTURE;
		} else if (poi.getSourceAPI().equals(SourceAPIs.ATDW)) {

			if (sourceAPIType.equals(ATDWTypes.ACCOMM)) {
				return POITypes.ACCOM;
			} else if (sourceAPIType.equals(ATDWTypes.RESTAURANT)) {
				return POITypes.FOOD;
			} else if (sourceAPIType.equals(ATDWTypes.EVENT)
					|| sourceAPIType.equals(ATDWTypes.TOUR)
					|| sourceAPIType.equals(ATDWTypes.JOURNEY)) {
				return POITypes.FUN;
			} else if (sourceAPIType.equals(ATDWTypes.INFO)
					|| sourceAPIType.equals(ATDWTypes.GENSERVICE)
					|| sourceAPIType.equals(ATDWTypes.TRANSPORT)
					|| sourceAPIType.equals(ATDWTypes.HIRE)
					|| sourceAPIType.equals(ATDWTypes.DESTINFO)) {
				return POITypes.INFO;
			} else if (sourceAPIType.equals(ATDWTypes.ATTRACTION)) {

				
				try {

					RestTemplate restTemplate = new RestTemplate();

					String sUrl = "http://intecggmirror.azurewebsites.net/api/GovHackApi/GetATDWProductById?productId="
							+ poi.getPoiId();

					String res = restTemplate.getForObject(sUrl, String.class);

					res = res.trim();

					JSONObject jObject = new JSONObject(res);

					JSONObject data = jObject.getJSONObject("ResponseObject");

					JSONArray array = data
							.getJSONArray("verticalClassifications");

									
					int foodCount = 0;
					int wineCount = 0;
					int funCount = 0;
					int natureCount = 0;
					int cultureCount = 0;

					for (int i = 0; i < array.length(); i++) {

						JSONObject classi = array.getJSONObject(i);

						String typeId = classi.getString("productTypeId");

						if (ATDWTypes.FUN_CLASSES.contains(typeId)) {
							funCount++;
						}
						if (ATDWTypes.NATURE_CLASSES.contains(typeId)) {
							natureCount++;
						}
						if (ATDWTypes.CULTURE_CLASSES.contains(typeId)) {
							cultureCount++;
						}
						if (ATDWTypes.FOOD_CLASSES.contains(typeId)) {
							foodCount++;
						}
						if (ATDWTypes.WINE_CLASSES.contains(typeId)) {
							wineCount++;
						}

					}

					int maxCount = Math.max(
							funCount,
							Math.max(
									natureCount,
									Math.max(cultureCount,
											Math.max(foodCount, wineCount))));
					
					if (wineCount > 0) {
						return POITypes.WINE;
					} else if (funCount >= maxCount) {
						return POITypes.FUN;
					} else if (natureCount >= maxCount) {
						return POITypes.NATURE;
					} else if (foodCount >= maxCount) {
						return POITypes.FOOD;
					} else if (cultureCount >= maxCount) {
						return POITypes.CULTURE;
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

		System.out.println(new Date() + " - end this mess");
		return type;

	}
}