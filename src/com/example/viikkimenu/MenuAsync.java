package com.example.viikkimenu;



import android.app.*;
import android.os.*;
import android.widget.*;

public class MenuAsync extends AsyncTask<MenuBuilder, Void, String> {

	TextView tv;
	ProgressDialog pd;
	
	public MenuAsync(TextView t, ProgressDialog d){
		tv = t;
		pd = d;
	}
	
	
	@Override
	protected void onPreExecute() {
		pd.setTitle("Getting Menu");
		pd.setMessage("Loading...");
		pd.show();
	}


	@Override
	protected String doInBackground(MenuBuilder... mb) {
		return mb[0].fetchMenu();
	}

	@Override
	protected void onPostExecute(String result) {
		tv.setText(result);
		pd.dismiss();
	}
	
	

}
