package com.example.nativemovieondemand;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	private String DEFAULT_PORT = "http://pegdemo.mybluemix.net/"; 
	private String MOVIE_SEARCH_URL="PEGServer/MovieList.lib/MovieSrch/movieSearch";
	EditText edTitlSrch;
	ListView listView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intialize();
	}

	
	private void intialize() {
		Log.i("Thiru","in initialze");
		edTitlSrch =  (EditText) findViewById(R.id.etTitlSrch);
		listView = (ListView) findViewById(R.id.listTitls);
		edTitlSrch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				search(s);
			}
		});
	}
	
	private void search(Editable edTitlSrch)
	{
		String text = edTitlSrch.toString();
		search(text);
	}
	
	private void search(String text)
	{
		String url = getURL()+"?title="+text+"&dir_first_name="+"&dir_last_name="+"&titleId=";
		JSONParser parser = new JSONParser(this);
		JSONObject jsonObject = parser.getJSONFromUrl(url);
		System.out.println(jsonObject);
		try {
			TitlesDetail t = this.getTitlesDetail(jsonObject);
			makeListView(t);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void makeListView(final TitlesDetail t)
	{
		   CustomAdaptor adapter = new CustomAdaptor(this, t.titleNames,t.imageIds);
		    
            // Assign adapter to ListView
            listView.setAdapter(adapter); 
            
            // ListView Item Click Listener
            listView.setOnItemClickListener(new OnItemClickListener() {
 
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view,
                     int position, long id) {
                    
                   // ListView Clicked item index
                   int itemPosition     = position;
                   
                   // ListView Clicked item value
                   String  itemValue    = (String) listView.getItemAtPosition(position);
                      
                   Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                   intent.putExtra("title_name", itemValue );
                   intent.putExtra("title_id", getTitleId(itemPosition, t.titleIds));
                   startActivity(intent);
                  }
             }); 
	}
	
	protected String getTitleId(int itemPosition, List<String> titleIds) {
		return titleIds.get(itemPosition);
	}
	
  private String getURL()
  {
		String url = null;
		SharedPreferences sp = getSharedPreferences("pref_url", 0);
		String sharedUrl = sp.getString("url",null);
		if(sharedUrl != null)
		return  url = sharedUrl+MOVIE_SEARCH_URL;
		else
		return  url = DEFAULT_PORT+MOVIE_SEARCH_URL; 
   }
	
  public TitlesDetail getTitlesDetail(JSONObject object) throws JSONException
  {
	  TitlesDetail t = new TitlesDetail();
	  JSONArray jsonTitleNamesArray = object.getJSONArray("name");
	  JSONArray jsonTitleIdArray = object.getJSONArray("title_id");
	  t.titleIds = getListFromJsonArray(jsonTitleIdArray);
	  t.titleNames = getListFromJsonArray(jsonTitleNamesArray);
	  
	  t.imageIds = new ArrayList<Integer>();
		for(int i=0;i<t.titleIds.size();i++)
		{
			t.imageIds.add(R.drawable.movieul);
		}
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this,SettingActivity.class);
			intent.putExtra("PARENT_CLASS_NAME", "MainActivity");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
