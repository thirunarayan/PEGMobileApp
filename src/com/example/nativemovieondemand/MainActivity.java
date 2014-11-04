package com.example.nativemovieondemand;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	String URL = "http://pegdemo.mybluemix.net/PEGServer/MovieList.lib/MovieSrch/movieSearch"; 
	EditText edTitlSrch;
	Button goBtn;
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
		goBtn = (Button) findViewById(R.id.btnSearch);
		listView = (ListView) findViewById(R.id.listTitls);
		goBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				search();
			}
		});
	}

	private void search()
	{
		String text = edTitlSrch.getText().toString();
		String url = URL+"?title="+text+"&dir_first_name="+"&dir_last_name="+"&titleId=";
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = parser.getJSONFromUrl(url);
		System.out.println(jsonObject);
		try {
			makeListView(parser.getTitlesDetail(jsonObject));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void makeListView(final TitlesDetail t)
	{
		   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		              android.R.layout.simple_list_item_1, android.R.id.text1, t.titleNames);
		    
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
                      
                    // Show Alert 
                    /*Toast.makeText(getApplicationContext(),
                      "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                      .show();*/
                   
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
