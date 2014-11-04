package com.example.nativemovieondemand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
public class JSONParser {
  static InputStream is = null;
  static JSONObject jObj = null;
  static String json = "";
  // constructor
  public JSONParser() {
  }
  
  public JSONObject getJSONFromUrl(String url) {
	 return  this.getJSONFromUrl(url, null, null);
  }
  
  public JSONObject getJSONFromUrl(String url,String userid,String password) {
    // Making HTTP request
    try {
      // defaultHttpClient
      DefaultHttpClient httpClient = new DefaultHttpClient();
      HttpGet httpPost = new HttpGet(url);
      if(url.contains("login"))
      {
      String input = userid+":"+password;  
	  String credential = StringUtils.newStringUtf8(Base64.encodeBase64(input.getBytes()));
	  httpPost.addHeader("Authorization", "basic "+credential);
      }
      HttpResponse httpResponse = httpClient.execute(httpPost);
      HttpEntity httpEntity = httpResponse.getEntity();
      is = httpEntity.getContent();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          is, "iso-8859-1"), 8);
      StringBuilder sb = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        sb.append(line + "n");
      }
      is.close();
      json = sb.toString();
    } catch (Exception e) {
      Log.e("Buffer Error", "Error converting result " + e.toString());
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
  
  public TitlesDetail getTitlesDetail(JSONObject object) throws JSONException
  {
	  Iterator<String> iterator = object.keys();
	  TitlesDetail t = new TitlesDetail();
	  String key;
	  while(iterator.hasNext())
	  {
		 key = iterator.next();
		 if(!key.equals("movieSearch")){
		 JSONArray jsonValue = object.getJSONArray(key);
		 if(key.equals("name")||key.equals("title_id"))
		 {
			 createTitlesDetail(key,jsonValue,t);
		 }
		 System.out.println("json value"+jsonValue.toString());} 
	  }
	  return t;
  }
  
private TitlesDetail createTitlesDetail(String key,JSONArray jsArray,TitlesDetail t) throws JSONException {
	
	if(key.equals("title_id"))
		t.titleIds = getListFromJsonArray(jsArray);
	else
		t.titleNames = getListFromJsonArray(jsArray);
	
	return t;
}

private List<String> getListFromJsonArray(JSONArray jsArray) throws JSONException {
	List<String> listdata = new ArrayList<String>();
	if (jsArray != null) { 
		   for (int i=0;i<jsArray.length();i++){ 
			   listdata.add(jsArray.get(i).toString());
		   } 
		} 
	return listdata;
}
}

