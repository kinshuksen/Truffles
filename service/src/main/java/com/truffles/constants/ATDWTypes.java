package com.truffles.constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ATDWTypes implements Serializable{

	private static final long serialVersionUID = -820345688369025369L;
	
	public static final String RESTAURANT = "RESTAURANT";
	
	public static final String ACCOMM = "ACCOMM";
	
	public static final String EVENT = "EVENT";
	
	public static final String TOUR = "TOUR";
	
	public static final String JOURNEY = "JOURNEY";
	
	public static final String INFO = "INFO";
	
	public static final String GENSERVICE = "GENSERVICE";
	
	public static final String TRANSPORT = "TRANSPORT";
	
	public static final String HIRE = "HIRE";

	public static final String DESTINFO = "DESTINFO";
	
	public static final String ATTRACTION = "ATTRACTION";
	
	
	//list of CULTURE classes
	public static final String CLASS_HISTHERITG = "HISTHERITG";	
	public static final String CLASS_GALMUSECOL = "GALMUSECOL";
	public static final String CLASS_LMARKBLD = "LMARKBLD";	
	public static final ArrayList<String> CULTURE_CLASSES = new ArrayList<String>() {{
	    add(CLASS_HISTHERITG);
	    add(CLASS_GALMUSECOL);
	    add(CLASS_LMARKBLD);
	}};
		
	//list of FOOD classes
	public static final String CLASS_FRMFOODPRD = "FRMFOODPRD";	
	public static final String CLASS_DINEATOUT = "DINEATOUT";
	public static final ArrayList<String> FOOD_CLASSES = new ArrayList<String>() {{
	    add(CLASS_FRMFOODPRD);
	    add(CLASS_DINEATOUT);
	}};
	
	//list of WINE classes
	public static final String CLASS_WINVINBREW = "WINVINBREW";	
	public static final ArrayList<String> WINE_CLASSES = new ArrayList<String>() {{
	    add(CLASS_WINVINBREW);
	}};

	//list of NATURE classes
	public static final String CLASS_MINDUSTRY = "MINDUSTRY";	
	public static final String CLASS_NATATTRACT = "NATATTRACT";
	public static final String CLASS_NATPARKRES = "NATPARKRES";
	public static final String CLASS_OBSVPLANET = "OBSVPLANET";
	public static final String CLASS_PKGDNCEM = "PKGDNCEM";
	public static final String CLASS_SCENDRVWLK = "SCENDRVWLK";
	public static final String CLASS_SPORTREC = "ZOOSNCAQU";
	public static final ArrayList<String> NATURE_CLASSES = new ArrayList<String>() {{
	    add(CLASS_MINDUSTRY);
	    add(CLASS_NATATTRACT);
	    add(CLASS_NATPARKRES);
	    add(CLASS_OBSVPLANET);
	    add(CLASS_PKGDNCEM);
	    add(CLASS_SCENDRVWLK);
	    add(CLASS_SPORTREC);
	}};
	
	//list of FUN classes
	public static final String CLASS_AMUSETHEME = "AMUSETHEME";	
	public static final String CLASS_CLASSWRKSP = "CLASSWRKSP";
	public static final String CLASS_ENTERTAIN = "ENTERTAIN";
	public static final String CLASS_MARKET = "MARKET";
	public static final String CLASS_SHOPPING = "SHOPPING";
	public static final String CLASS_SPARETREAT = "SPARETREAT";
	public static final ArrayList<String> FUN_CLASSES = new ArrayList<String>() {{
	    add(CLASS_AMUSETHEME);
	    add(CLASS_CLASSWRKSP);
	    add(CLASS_ENTERTAIN);
	    add(CLASS_MARKET);
	    add(CLASS_SHOPPING);
	    add(CLASS_SPARETREAT);
	}};

	
	
	
}

