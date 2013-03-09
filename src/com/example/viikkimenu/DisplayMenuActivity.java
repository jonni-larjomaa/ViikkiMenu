package com.example.viikkimenu;

import java.util.ArrayList;

import com.example.viikkimenu.menus.*;


import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.view.*;
import android.widget.*;
import android.app.*;

public class DisplayMenuActivity extends Activity {

	private String restname;
	private int position;
	private ArrayList<MenuBuilder> amb = new ArrayList<MenuBuilder>();
	private TextView menulist;
	private ProgressDialog pd;
	private MenuBuilder mb; 
	
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
		amb.add(new Gardenia(this));
		
		// get the data send through intent.
		// restaurant name and the index int.
		Bundle data = getIntent().getExtras();
		restname = data.getString("restaurant");
		position = data.getInt("position");
		
		// set title for restaurant name
		setTitle(restname);
		
		menulist = (TextView) findViewById(R.id.restaurantname);
		menulist.setMovementMethod(new ScrollingMovementMethod());
		
		pd = new ProgressDialog(this);
				
		// setup async call
		MenuAsync ma = new MenuAsync(menulist,pd);
		
		mb = amb.get(position);
		
		// execute async task
		ma.execute(mb);
	}
	
	/**
	 * OnClick implementation for data refresh on menu view.
	 * @param v
	 */
	public void refresh(View v){
		RevalidateASync ras = new RevalidateASync(menulist, pd);
		mb = amb.get(position);
		ras.execute(mb);
	}
}
