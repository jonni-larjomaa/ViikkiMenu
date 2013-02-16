package com.example.viikkimenu;

import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.widget.*;
import android.app.*;

public class DisplayMenuActivity extends Activity {

	private String restname;
	private MenuBuilder[] amb = {new Ladonlukko()};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaymenu);
		
		// get the data send through intent.
		Bundle data = getIntent().getExtras();
		restname = data.getString("restaurant");
		
		// set title for restaurant name
		setTitle(restname);
		
		// start async task to get menu-items. 
		TextView menulist = (TextView) findViewById(R.id.restaurantname);
		menulist.setMovementMethod(new ScrollingMovementMethod());
		setMenu(menulist, restname);
	}
	
	private void setMenu(TextView menulist, String r){
		// setup Progressdialog
		ProgressDialog pd = new ProgressDialog(this);
		
		// setup async call
		MenuAsync ma = new MenuAsync(menulist,pd);
		
		// create object from the restaurant name string.
		// all restaurant names has their reflection class.
		MenuBuilder mb = amb[0];
		
		ma.execute(mb);
	}
}
