package com.example.nativemovieondemand;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;


public class JSONParser {

	Context context;
  // constructor
	
  public JSONParser() {
	
  }
  
  public JSONParser(Context context) {
	  this.context = context;
  }
  
  public JSONObject getJSONFromUrl(String url) {
		 return  this.getJSONFromUrl(url, null, null);
	  }
	  
  public JSONObject getJSONFromUrl(String url,String userid,String password) {
    try {
    	ProgressDialog progressDialog = null;
    	if(this.context!=null)
    	{
    		 progressDialog= ProgressDialog.show( this.context, "Progress Bar", "Please wait...", true);
    		 	
    	}
    	JsonParserTask task = new JsonParserTask(progressDialog);
		return task.execute(url,userid,password).get();
	} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	}
	return null;
  }
}

