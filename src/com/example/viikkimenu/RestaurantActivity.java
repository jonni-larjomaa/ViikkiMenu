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
        
        // create adapter
        ArrayAdapter<String> restsAA = new ArrayAdapter<String>(this, 
        														R.layout.restaurantlistitem,
        														R.id.srestaurant,
        														rests);
        // set listadapter for restaurants.
        this.setListAdapter(restsAA);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, DisplayMenuActivity.class);
		i.putExtra("restaurant", rests[position]);
		startActivity(i);
	}
    
    
}
