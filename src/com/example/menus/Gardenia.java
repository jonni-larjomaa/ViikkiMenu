package com.example.menus;

import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

public class Gardenia extends MenuBuilder {

	public Gardenia(Context context) {
		super(context);
		
		cache = this.getClass().getSimpleName()+"_"+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		url = "http://www.gardenia-helsinki.fi/lounaslista.htm";
	}

	@Override
	public String ParseMenuStr(String content) {
		
		content = content.replaceAll("(?i)<br[^>]*>", " br2n ");
		Document doc = Jsoup.parse(content);
		Elements els = doc.select("blockquote > p[align=left]");
		String test = "";
		
		for(Element el : els){
			test += el.text().replaceAll("br2n", "\n")+"\n\n";
		}
		
		return test;
	}

}
