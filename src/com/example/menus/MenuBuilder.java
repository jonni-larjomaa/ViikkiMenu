package com.example.menus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.*;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

abstract public class MenuBuilder {
    
    protected String url,menu,content,cache;
    protected Context ctx;
    protected static Logger menuLog = Logger.getLogger("ViikkiMenu");
    
    
    public MenuBuilder(Context context){
    	ctx = context;
    }
    
    /**
     * fetch the content form designated url.
     * 
     * @param url
     * @return String contents of the response as string.
     * @throws IOException
     */
    protected String getContent(String url) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, Integer.valueOf(10000));
        client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, Integer.valueOf(10000));

        content = client.execute(get, new BasicResponseHandler());
        
        menuLog.log(Level.INFO, content);
        return content;
    }
  
    /**
     * Fetch and parse JSON formatted menu string from sodexo jsonfeed.
     * @return String 
     */
    public String fetchMenu(boolean revalidate){
        
        menu = "";
        
        
        try{
			if((menu = readCacheContents(cache)).length() > 1 && !revalidate){
				menuLog.log(Level.INFO,"read menu from cache: "+menu);
			}
			else{	
				menu = ParseMenuStr(getContent(url));
				writeCacheContents(cache, menu);
				menuLog.log(Level.INFO, "read menu from internet");
			}
		}
		catch (IOException e){
			menuLog.log(Level.INFO, e.toString());
		}
        return menu;
    }
    
    /**
     * read the cached contents of menu.
     * 
     * @param filename
     * @return 
     */
    protected String readCacheContents(String filename){
		
    	String line = "";
    	StringBuilder contents = new StringBuilder();
    	File cacheFile = new File(ctx.getCacheDir(), filename);
    	
    	if(cacheFile.exists()){
    		menuLog.log(Level.INFO,"reading from cachefile: "+cacheFile.getPath());
    		
    		try {
    			
				BufferedReader fl = new BufferedReader(
									new InputStreamReader(
									new FileInputStream(cacheFile)));
				while((line = fl.readLine()) != null){
					contents.append(line+"\n");
				}
				fl.close();
				
			} catch (IOException e) {
				menuLog.log(Level.SEVERE,"Cache file could not be read",e);
			}
    	}
    	return contents.toString();
    }
    
    /**
     * write the cache contents to given file.
     * 
     * @param filename
     * @param contents
     */
    protected void writeCacheContents(String filename, String contents){
    	
    	File cacheFile = new File(ctx.getCacheDir(), filename);
    	
    	menuLog.log(Level.INFO, "writing to file:"+cacheFile.getPath());
    	
    	try {
    		if(!cacheFile.exists()){
    			cacheFile.createNewFile();
    		}
    		
    		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(cacheFile));
    		
    		osw.write(contents);
    		osw.flush();
    		osw.close();
    			
    		menuLog.log(Level.INFO, "created cache with content:"+contents);
    		
		} catch (IOException ex) {
			menuLog.log(Level.SEVERE,"Something went wrong during cache writing",ex);
		}
    }
    
    abstract public String ParseMenuStr(String content);
}
