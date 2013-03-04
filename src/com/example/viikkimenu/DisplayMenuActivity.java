package com.example.viikkimenu;

import java.util.ArrayList;

import com.example.menus.Evira;
import com.example.menus.Ladonlukko;
import com.example.menus.MenuBuilder;
import com.example.menus.Tahka;
import com.example.menus.Viikinkartano;

import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.widget.*;
import android.app.*;

public class DisplayMenuActivity extends Activity {

	private String restname;
	private int position;
	private ArrayList<MenuBuilder> amb = new ArrayList<MenuBuilder>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set the layout to be used.
		setContentView(R.layout.displaymenu);
		
		// add menu creator objects to list.
		amb.add(new Ladonlukko(this));
		amb.add(new Viikinkartano(this));
		amb.add(new Tahka(this));
		amb.add(new Evira(this));
		
		// get the data send through intent.
		// restaurant name and the index int.
		Bundle data = getIntent().getExtras();
		restname = data.getString("restaurant");
		position = data.getInt("position");
		
		// set title for restaurant name
		setTitle(restname);
		
		// set menulist textView and have scrollingmovement enabled. 
		TextView menulist = (TextView) findViewById(R.id.restaurantname);
		menulist.setMovementMethod(new ScrollingMovementMethod());
		
		// setup Progressdialog
		ProgressDialog pd = new ProgressDialog(this);
				
		// setup async call
		MenuAsync ma = new MenuAsync(menulist,pd);
		
		// create object from the restaurant name string.
		// all restaurant names has their reflection class.
		MenuBuilder mb = amb.get(position);
		
		// execute async task
		ma.execute(mb);
	}
}
