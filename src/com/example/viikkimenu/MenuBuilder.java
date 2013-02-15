package com.example.viikkimenu;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

abstract public class MenuBuilder {
    
    protected String url,menu,content;

    public MenuBuilder(String url){
        this.url = url;
    }

    protected String getUrlContent(String url) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, Integer.valueOf(10000));
        client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, Integer.valueOf(10000));

        content = client.execute(get, new BasicResponseHandler());

        return content;
    }

    abstract public String fetchMenu();
}
