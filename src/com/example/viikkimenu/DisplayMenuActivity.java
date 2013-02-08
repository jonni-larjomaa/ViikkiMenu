package com.example.viikkimenu;

import android.os.*;
import android.widget.*;
import android.app.*;

public class DisplayMenuActivity extends Activity {

	private String restname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaymenu);
		
		Bundle data = getIntent().getExtras();
		restname = data.getString("restaurant");
		
		TextView rn = (TextView) findViewById(R.id.restaurantname);
		rn.setText(restname);
	}
	
	

}
