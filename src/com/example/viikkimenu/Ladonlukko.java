package com.example.viikkimenu;

import android.text.format.DateFormat;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ladonlukko extends MenuBuilder{

	String menustr = "";
	String[] days = {"monday","tuesday", "wednesday","thursday","friday"};
	/**
     * Fetch and parse JSON formatted menu string from sodexo jsonfeed.
     * @return String 
     */
    public String fetchMenu(){
        
        menu = "";
        String content;
        String date = DateFormat.format("yyyy/MM/dd", new Date()).toString();
        
        try {

            url = "http://www.sodexo.fi/ruokalistat/output/weekly_json/440/"+date+"/fi";
            Logger.getLogger("ViikkiMenu").log(Level.INFO, url);
            content = getUrlContent(url);            
            menu = ParseMenuStr(content);
            Logger.getLogger("ViikkiMenu").log(Level.INFO, "url contents fetched.");
            
        } catch (IOException ex) {
            Logger.getLogger("ViikkiMenu").log(Level.SEVERE, null, ex);
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
			Logger.getLogger(Ladonlukko.class.getName()).log(Level.SEVERE, null, ex);
		}
        return menustr;
    }
}
