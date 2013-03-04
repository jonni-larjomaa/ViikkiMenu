package com.example.menus;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

public class Evira extends MenuBuilder {

	
	public Evira(Context context) {
		super(context);
		url = "http://www.amica.fi/evira";
	}

	@Override
	public String fetchMenu() {
		
		String filename = Evira.class.getSimpleName()+"_"+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		
		try{
			if((menu = readCacheContents(filename)).length() > 1){
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

	@Override
	public String ParseMenuStr(String content) {
		
		content = content.replaceAll("(?i)<br[^>]*>", " br2n ").replaceAll("(?i)<strong>", " br3n ");
		Document doc = Jsoup.parse(content);
		Elements els = doc.select("div.ContentArea > p");
		
		String menustr = "";
		
		for(Element el : els){
			
			menuLog.log(Level.INFO,"element text: "+el.text()+"has nodename: "+el.nodeName());
			menustr += el.text().replaceAll("br2n", "\n").replaceAll("br3n", "\n\n");
		}
		return menustr;
	}

}
