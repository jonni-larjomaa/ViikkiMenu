package com.example.viikkimenu;



import com.example.viikkimenu.menus.MenuBuilder;

import android.app.*;
import android.os.*;
import android.widget.*;

public class MenuAsync extends AsyncTask<MenuBuilder, Void, String> {

	private TextView tv;
	private ProgressDialog pd;
	private boolean revalidate;
	
	public MenuAsync(TextView t, ProgressDialog d, boolean re){
		tv = t;
		pd = d;
		revalidate = re;
	}
	
	
	@Override
	protected void onPreExecute() {
		pd.setTitle("Getting Menu");
		pd.setMessage("Loading...");
		pd.show();
	}


	@Override
	protected String doInBackground(MenuBuilder... mb) {
		return mb[0].fetchMenu(revalidate);
	}

	@Override
	protected void onPostExecute(String result) {
		tv.setText(result);
		pd.dismiss();
	}
}
