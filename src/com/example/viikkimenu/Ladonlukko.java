package com.example.viikkimenu;

import android.content.Context;
import android.text.format.DateFormat;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ladonlukko extends MenuBuilder{

	String[] days = {"monday","tuesday", "wednesday","thursday","friday"};
	
	public Ladonlukko(Context context) {
		super(context);
		
		String date = DateFormat.format("yyyy/MM/dd", new Date()).toString();
		this.url = "http://www.sodexo.fi/ruokalistat/output/weekly_json/440/"+date+"/fi";
	}
	
	/**
     * Fetch and parse JSON formatted menu string from sodexo jsonfeed.
     * @return String 
     */
    public String fetchMenu(){
        
        menu = "";        
        String filename = "Ladonlukko_"+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        
        try{
			if((menu = readCacheContents(filename)).length() > 1 ){
				menuLog.log(Level.INFO,"read menu from cache: "+menu);
			}
			else{	
				menu = ParseMenuStr(getContent(url));
				writeCacheContents(filename, menu);
				menuLog.log(Level.INFO, "read menu from internet");
			}
		}
		catch (IOException e){
			menuLog.log(Level.INFO, e.toString());
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

			for(int j=0;j < days.length; j++){
				
				JSONArray courses = job.getJSONArray(days[j]);
				menustr += days[j]+":\n";
				
				for(int i=0; i < courses.length(); i++){
				    JSONObject add = courses.getJSONObject(i);
				    menustr += add.getString("title_fi")+" "+add.getString("price")+"€ ";
				    if(add.has("properties")){
				    	menustr += add.getString("properties");
				    }
				    menustr += "\n";
				}
				menustr += "\n";
			}
		} catch (JSONException ex) {
			menuLog.log(Level.SEVERE, null, ex);
		}
        return menustr;
    }
}
