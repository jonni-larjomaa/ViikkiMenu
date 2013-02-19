package com.example.viikkimenu;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.format.DateFormat;

public class Viikinkartano extends MenuBuilder {

	String[] days = {"monday","tuesday", "wednesday","thursday","friday"};
	
	public Viikinkartano(Context context) {
		super(context);
		
		String date = DateFormat.format("yyyy/MM/dd", new Date()).toString();
		url = "http://www.sodexo.fi/ruokalistat/output/weekly_json/494/"+date+"/fi";
	}
	
	/**
     * Fetch and parse JSON formatted menu string from sodexo jsonfeed.
     * @return String 
     */
    public String fetchMenu(){
        
        menu = "";
        String filename = "Viikinkartano_"+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        
        try {

            if(hasCache(filename)){
            	menu = readCacheContents(filename);
            	menuLog.log(Level.INFO,"read menu from cache");
            }
            else{
            	menu = ParseMenuStr(getContent(url));
            	writeCacheContents(filename, menu);
                menuLog.log(Level.INFO, "read menu from internet");
            }
        } catch (IOException ex) {
            menuLog.log(Level.SEVERE, null, ex);
        }
        
        return menu;
    }
    
    /**
     * Parses the daily json string to a defined form of single CS.
     * 
     * @param content
     * @return String
     * @throws JSONException
     */
    public String ParseMenuStr(String content) {
        
    	String menustr = "";
    	
		try {
			JSONObject job = new JSONObject(content);
			job = job.getJSONObject("menus");

			// loop trhough days
			for(int j=0;j < days.length; j++){
				
				JSONArray courses = job.getJSONArray(days[j]);
				menustr += days[j]+":\n";
				
				//loop through menu items.
				for(int i=0; i < courses.length(); i++){
				    JSONObject add = courses.getJSONObject(i);
				    if(add.has("title_fi")){
				    	menustr += add.getString("title_fi");
				    }
				    if(add.has("price")){
				    	menustr += add.getString("price")+"€ ";
				    }
				    if(add.has("properties")){
				    	menustr += add.getString("properties");
				    }
				    menustr += "\n";
				}
				menustr += "\n";
			}
		} catch (JSONException ex) {
			Logger.getLogger("ViikkiMenu").log(Level.SEVERE, null, ex);
		}
		
		// return formed menustring.
        return menustr;
    }
}
