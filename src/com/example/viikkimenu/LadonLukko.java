package com.example.viikkimenu;

import android.text.format.DateFormat;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LadonLukko extends MenuBuilder{
    
    public LadonLukko(String url) {
    	super(url);		
	}

	/**
     * Fetch and parse JSON formatted menu string from sodexo jsonfeed.
     * @return String 
     */
    public String fetchMenu(){
        
        menu = "";
        String content;
        String date = DateFormat.format("yyyy/MM/dd", new Date()).toString();
        
        try {

            url = "http://www.sodexo.fi/ruokalistat/output/daily_json/440/"+date+"/fi";
            content = getUrlContent(url);            
            menu = JsonToMenuStr(content);
            
        } catch (JSONException ex) {
            Logger.getLogger(LadonLukko.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LadonLukko.class.getName()).log(Level.SEVERE, null, ex);
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
    public String JsonToMenuStr(String content) throws JSONException {
        
        JSONObject job = new JSONObject(content);
        JSONArray courses = job.getJSONArray("courses");
        String menustr = "";
        
        for(int i=0; i < courses.length(); i++){
            JSONObject add = courses.getJSONObject(i);
            menustr += add.getString("title_fi")+" "+add.getString("price")+"€ ";
            if(add.has("properties")){
            	menustr += add.getString("properties");
            }
            menustr += "\n";
        }
        return menustr;
    }
}
