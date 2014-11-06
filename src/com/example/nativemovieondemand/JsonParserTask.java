package com.example.nativemovieondemand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


public class JsonParserTask extends AsyncTask<String, Void , JSONObject>{
	  static InputStream is = null;
	  static JSONObject jObj = null;
	  static String json = "";
	  
    ProgressDialog progressDialog;
    //declare other objects as per your need
    
    public JsonParserTask()
    {
    	
    }
    
    public JsonParserTask(ProgressDialog progressDialog)
    {
    	this.progressDialog = progressDialog;
    }
    
	    
	@Override
	protected JSONObject doInBackground(String... params) {
		// Making HTTP request
	    try {
	      // defaultHttpClient
	      DefaultHttpClient httpClient = new DefaultHttpClient();
	      HttpGet httpPost = new HttpGet(params[0]);
	      if(params[0].contains("login"))
	      {
	      String input = params[1]+":"+params[2];  
		  String credential = StringUtils.newStringUtf8(Base64.encodeBase64(input.getBytes()));
		  httpPost.addHeader("Authorization", "basic "+credential);
	      }
	      HttpResponse httpResponse = httpClient.execute(httpPost);
	      HttpEntity httpEntity = httpResponse.getEntity();
	      is = httpEntity.getContent();
	    } catch (IllegalStateException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
        BufferedReader reader;
		try {
			 reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			 StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		          sb.append(line + "n");
		        }
		        is.close();
		        json = sb.toString();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	       
	    	  
	      // try parse the string to a JSON object
	      try {
	      	System.out.println(json);
	        jObj = new JSONObject(json);
	      } catch (JSONException e) {
	        Log.e("JSON Parser", "Error parsing data " + e.toString());
	      }
	      // return JSON String
	      return jObj;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		if(progressDialog!=null)
		progressDialog.dismiss();
	}
}
