package com.truffles.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truffles.constants.POITypes;
import com.truffles.constants.SourceAPIs;
import com.truffles.dao.POIDao;
import com.truffles.model.POI;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class POIController {
	
	private static final Logger logger = LoggerFactory.getLogger(POIController.class);
	
	private static POITypes poiTypes = new POITypes();
	
	@Autowired
	@Qualifier("POIDao")
	private POIDao poiDao;
	
	      
    @RequestMapping(value = RestURIConstants.GET_POI, method = RequestMethod.GET)
   	public @ResponseBody List<POI> getPOI(String latitude, String longitude) throws JSONException {
    	System.out.println("Starting getPOI()");
    	return poiDao.getPOIs(latitude, longitude);
   	}
    
    
    @RequestMapping(value = RestURIConstants.GET_POI_TEST, method = RequestMethod.GET)
   	public @ResponseBody POI[] getPOITest(String latitude, String longitude) {
    	System.out.println("Starting getPOITest()");
    	List<POI> pois = new ArrayList<POI>();
    	
    	//PointsOfInterestDao poiDao = new PointsOfInterestDao();
		//poi = poiDao.getPOI(latitude, longitude,  dist);
    	
    	POI accomPoint = new POI("9061683$a7ca6a6a-63af-45e8-a2d6-f2e1d7dfd00b", poiTypes.ACCOM, 
    			"Blue Galah Backpackers Hostel",
    			"This family owned and operated international backpackers hostel is located in Adelaide’s city centre. "
    			+ "Close to international food court, all urban buses and trains, Rundle Mall, bus station and only a few "
    			+ "steps from many of Adelaide's main tourist attractions. Facilities include dormitories from three to 10 "
    			+ "beds (singles, twin, shared rooms), luggage storage, pick up by arrangement, air conditioning, wide screen "
    			+ "televisions, cable television, DVDs and free movie hire, large balconies alfresco and enclosed, designated "
    			+ "outdoor smoking area, friendly staff, travel desk, tour sales, helpful local advice, modern kitchen, "
    			+ "coin operated laundry, licenced Bar open to 3am, Internet access, email, website chat, printing and faxing,"
    			+ " international public telephones, convenience store/delicatessen, pool table, quiet/relaxed atmosphere. "
    			+ "Group or student bookings welcome. Open 24 hours a day.", "-34.923622000","138.600199000", SourceAPIs.ATDW, "5");
    	
    	POI culturePoint = new POI("9001506$d7358032-771d-4c6e-8548-a6620c5c59d1", poiTypes.CULTURE, 
    			"Art Gallery Of South Australia",
    			"The Art Gallery of South Australia holds one of Australia's finest collections of Australian and International"
    			+ " art. The gallery's permanent display of Australian art is outstanding, enabling you to track the development"
    			+ " of Australian art from the colonial period to the present day. Its collection of 19th century colonial art"
    			+ " contains fine examples of early oil painting, watercolours, sculpture, silver and furniture. The gallery "
    			+ "also has a comprehensive collection of Aboriginal desert dot paintings from Central Australia, dating from "
    			+ "the beginning of the painting movement in the early 1970s. Visitors can also trace the history of European "
    			+ "art from the 15th to 20th century, particularly the development of landscape and portrait painting.The highlight "
    			+ "of the gallery's Asian collection is its holdings of South East Asian ceramics that is the finest museum collection"
    			+ " of such material in the world. Free lunchtime talks at 12.45pm are available on most Tuesdays."
    			,  "-34.920950587","138.603987693", SourceAPIs.ATDW, "2");

    	
    	POI naturePoint = new POI("9194722$d3678c0c-b464-475c-9564-5922bf403343", poiTypes.NATURE, 
    			"Natural Heritage Interpretive Trail",
    			"The Natural Heritage Interpretive Trail explores West Terrace Cemetery's hidden landscape of native and exotic "
    			+ "vegetation. The trail will introduce you to a diverse selection of trees, shrubs, climbers and ground covers, "
    			+ "their unique uses, the fauna they support and the symbolism attached to them. "
    			+ "The cemetery is home to more than 20 rare and endangered flora, from the obvious to the almost invisible. This "
    			+ "remnant native vegetation offers a snapshot of the plant communities once common in Adelaide. There are also many"
    			+ " exotic species, which reflect cemetery planting practices and fashions over time."
    			+ "There are 10 sites to visit on this one kilometre self-guided loop-walk. Each point of interest is marked with an "
    			+ "information panel.A map and green way-finding markers will help you navigate your way through the cemetery. A copy "
    			+ "of the map can be found at stop one on the trail, located just inside the main entrance to the cemetery. Allow 60"
    			+ " minutes to complete this trail."
    			+ "Alternatively, you can book a guided tour of West Terrace Cemetery and let our knowledgeable guides bring to life the"
    			+ " captivating and long forgotten stories of some of the State's and Nation's most notable figures and early pioneers."   			
    			,  "-34.933687697","138.587830066", SourceAPIs.ATDW, "3");
    	
    	POI winePoint = new POI("9151841$298b5cf6-884c-4662-a7f1-3cd5e34df1b9", poiTypes.WINE, 
    			"Aramis Vineyards",
    			"Visit Aramis Cellar Door and put some glamour into your wine world and enjoy a sophisticated cellar door experience without"
    			+ " leaving the city."
    			+ "Lose yourself in the hedonistic pleasures of some of South Australia's most iconic wines in the exclusive Adelaide "
    			+ "Boutique and discover why Aramis Vineyards truly are the modern classics of the McLaren Vale region."   			
    			,  "-34.931976000","138.573208000", SourceAPIs.ATDW, "4");
    	
    	POI funPoint = new POI("9214242$0d12d3c1-6013-4608-8829-a86a8c5c9ebd", poiTypes.FUN, 
    			"Flight Experience Adelaide",
    			"At Flight Experience Adelaide we open the cockpit to the world and make it real!"
    			+ "Under the guidance of your instructor, you will experience the next best thing to flying a real commercial jet airliner."
    			+ "Choose from a wide variety of airports, weather conditions and options. You take control and taxi, take off, fly and land"
    			+ " the aircraft yourself!"
    			+ "Our replica Boeing 737 Simulator features full cockpit layout, instruments and systems and high definition 180 degree "
    			+ "panoramic visuals!"
    			+ "Want a unique gift for a special occasion? A Flight Experience is the perfect gift for someone special - it's sure to "
    			+ "please and will never be forgotten!"
    			+ "Have a look on our website for all flight package prices, or telephone us and speak with our friendly staff!"   			
    			,  "-34.908787900","138.596282200", SourceAPIs.ATDW, "10");
    	
    	
    	POI foodPoint = new POI("9225921$463f4aed-30c8-4724-bf16-9dde48a20ceb", poiTypes.FOOD, 
    			"Madame Hanoi",
    			"An absolutely beautiful restaurant. Formerly part of the Adelaide Railway Station, lofty ceilings allow for mezzanine balcony"
    			+ " dining, and towering, tit-illating (you'll see) wall mural. Madame Hanoi is grand, adorned with Vietnamese trinkets, "
    			+ "well-styled, and she doesn't come cheap. Treat yourself to (very) petite share plates of hot pepper crayfish roll, crispy"
    			+ " pork belly in lettuce cups, and soft shell crab bao – it's steam bun blackened with squid ink."
    			+ "Madame Hanoi only takes bookings for 8 or more."  			
    			,  "-34.921430000","138.601166000", SourceAPIs.ATDW, "3");
    	
       	POI infoPoint = new POI("9208579$f0859c0a-22a8-4e92-9005-ae873b5ccca0", poiTypes.INFO, 
    			"Adelaide Visitor Information Centre",
    			"The Adelaide Visitor Information Centre is located in James Place just off Rundle Mall in the heart of Adelaide's shopping "
    			+ "precinct offering assistance to local, interstate and overseas visitors.The centre is staffed by Visitor Information Service "
    			+ "volunteers who can assist with directions, brochures, maps, event information and holiday suggestions that will make your visit"
    			+ " to Adelaide and South Australia an enjoyable one."
    			+ "Free wheelchair and pusher hire is available.First Steps in Adelaide, a free orientation walk of the City, departs from the "
    			+ "Adelaide Visitor Information Centre at 9.30am Monday to Friday (excluding Public Holidays).The centre is open Monday to Friday"
    			+ " 9am to 5pm, Saturday and Sunday 10am to 4pm and Public Holidays 11am to 3pm."  			
    			,  "-34.923518417","138.600609834", SourceAPIs.ATDW, "5");
       	    	 
       	pois.add(accomPoint);
       	pois.add(naturePoint);
       	pois.add(funPoint);
       	pois.add(infoPoint);
       	pois.add(foodPoint);
       	pois.add(winePoint);
       	pois.add(culturePoint);              	

    	return pois.toArray(new POI[pois.size()]);
    	
   	}    
    
	
	
	
}
