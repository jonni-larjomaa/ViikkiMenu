package com.example.viikkimenu;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.View;
import android.widget.*;

public class RestaurantActivity extends ListActivity {

	
	private String[] rests;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        
        // get the restaurants and names.
        rests = this.getResources().getStringArray(R.array.restaurants);
        setRestaurantListView(rests);
        
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, DisplayMenuActivity.class);
		i.putExtra("restaurant", rests[position]);
		i.putExtra("position", position);
		startActivity(i);
	}
	
	// convinience method for setting up list view for activity.
	private void setRestaurantListView(String[] r){
		
		// create adapter
        ArrayAdapter<String> restsAA = new ArrayAdapter<String>(this, 
        														R.layout.restaurantlistitem,
        														R.id.srestaurant,
        														r);
        // set list adapter for restaurants.
        this.setListAdapter(restsAA);
	}
    
    
}
