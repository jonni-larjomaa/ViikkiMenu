package com.example.menus;

import android.content.Context;
import android.text.format.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ladonlukko extends MenuBuilder{

	// days and localized finnish names for them.
	String[][] days = {{"monday","tuesday","wednesday","thursday","friday"},
					   {"maanantai","tiistai","keskiviikko","torstai","perjantai"}};
	
	
	public Ladonlukko(Context context) {
		super(context);
		
		String date = DateFormat.format("yyyy/MM/dd", new Date()).toString();
		
		cache = this.getClass().getSimpleName()+"_"+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);		
		url = "http://www.sodexo.fi/ruokalistat/output/weekly_json/440/"+date+"/fi";
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

			for(int j=0;j < days[0].length; j++){
				
				JSONArray courses = job.getJSONArray(days[0][j]);
				menustr += days[1][j]+":\n";
				
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
