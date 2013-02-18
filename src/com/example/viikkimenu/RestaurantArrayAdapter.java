package com.example.viikkimenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantArrayAdapter extends ArrayAdapter<String> {

	private TypedArray mIcons;
	private String[] mRestaurants;
	private LayoutInflater mInflater;
	private int resourceId;
	
	
	public RestaurantArrayAdapter(Context context, int ViewResourceId, 
								String[] strings, TypedArray icons) {
		
		super(context, ViewResourceId, strings);
		
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRestaurants = strings;
		mIcons = icons;
		resourceId = ViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = mInflater.inflate(resourceId, null);
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.restlogo);
		iv.setImageDrawable(mIcons.getDrawable(position));
		
		TextView tv = (TextView) convertView.findViewById(R.id.srestaurant);
		tv.setText(mRestaurants[position]);
		
		return convertView;
	}
}
