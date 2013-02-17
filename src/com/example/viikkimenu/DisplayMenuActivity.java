package com.example.viikkimenu;

import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.widget.*;
import android.app.*;

public class DisplayMenuActivity extends Activity {

	private String restname;
	private int position;
	private MenuBuilder[] amb = {new Ladonlukko(),
								 new Viikinkartano()};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaymenu);
		
		// get the data send through intent.
		Bundle data = getIntent().getExtras();
		restname = data.getString("restaurant");
		position = data.getInt("position");
		
		// set title for restaurant name
		setTitle(restname);
		
		// start async task to get menu-items. 
		TextView menulist = (TextView) findViewById(R.id.restaurantname);
		menulist.setMovementMethod(new ScrollingMovementMethod());
		setMenu(menulist, position);
	}
	
	private void setMenu(TextView menulist, int pos){
		// setup Progressdialog
		ProgressDialog pd = new ProgressDialog(this);
		
		// setup async call
		MenuAsync ma = new MenuAsync(menulist,pd);
		
		// create object from the restaurant name string.
		// all restaurant names has their reflection class.
		MenuBuilder mb = amb[pos];
		
		ma.execute(mb);
	}
}
