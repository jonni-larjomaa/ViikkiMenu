package com.example.viikkimenu;

import android.os.*;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.*;

public class RestaurantActivity extends ListActivity {

	
	private String[] rests;
	private TypedArray logos;
	
	@SuppressLint("Recycle")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        
        // get the restaurants and names.
        rests = this.getResources().getStringArray(R.array.restaurants);
        logos = this.getResources().obtainTypedArray(R.array.restlogos);
        
        
        // create adapter
        RestaurantArrayAdapter restsAA = 
        		new RestaurantArrayAdapter(this, 
        								   R.layout.restaurantlistitem,
        								   rests,
        								   logos);
        
        // set list adapter for restaurants.
        setListAdapter(restsAA);
    }
	
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// when destroyed clean up
		logos.recycle();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, DisplayMenuActivity.class);
		i.putExtra("restaurant", rests[position]);
		i.putExtra("position", position);
		startActivity(i);
	}    
    
}
