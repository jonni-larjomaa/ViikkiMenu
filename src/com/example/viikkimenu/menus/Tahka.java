package com.example.viikkimenu.menus;

import java.util.Calendar;
import java.util.logging.Level;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

public class Tahka extends MenuBuilder {

	
	public Tahka(Context context) {
		super(context);
		
		cache = this.getClass().getSimpleName()+"_"+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		url = "http://www.amica.fi/tahka";
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
