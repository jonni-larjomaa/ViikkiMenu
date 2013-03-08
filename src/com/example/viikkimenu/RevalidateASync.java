package com.example.viikkimenu;



import com.example.menus.MenuBuilder;

import android.app.*;
import android.os.*;
import android.widget.*;

public class RevalidateASync extends AsyncTask<MenuBuilder, Void, String> {

	TextView tv;
	ProgressDialog pd;
	
	public RevalidateASync(TextView t, ProgressDialog d){
		tv = t;
		pd = d;
	}
	
	
	@Override
	protected void onPreExecute() {
		pd.setTitle("Refreshing");
		pd.setMessage("Please Wait ...");
		pd.show();
	}


	@Override
	protected String doInBackground(MenuBuilder... mb) {
		return mb[0].fetchMenu(true);
	}

	@Override
	protected void onPostExecute(String result) {
		tv.setText(result);
		pd.dismiss();
	}
}
