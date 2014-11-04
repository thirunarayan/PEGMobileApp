package com.example.nativemovieondemand;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MovieDetailsActivity extends ActionBarActivity {

	String URL = "http://pegdemo.mybluemix.net/PEGServer/MovieList.lib/MovieDtl/movieDetail";
	TextView tvDtlTitle,tvDtlDesc,tvDtlDur,tvDtlRelDt,tvDtlDescText,tvDtlRate;
	String titleName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_details);
		intialize();
		Bundle movieBundle = getIntent().getExtras();
		titleName = movieBundle.getString("title_name");
		getMovieInformation(movieBundle.getString("title_id"));
	}
	
	private void intialize() {
		Log.i("Thiru","in initialze");
		tvDtlTitle =  (TextView) findViewById(R.id.tvDtlTitle);
		tvDtlDesc =  (TextView) findViewById(R.id.tvDtlDesc);
		tvDtlDur =  (TextView) findViewById(R.id.tvDtlDur);
		tvDtlRelDt =  (TextView) findViewById(R.id.tvDtlRelDt);
		tvDtlDescText =  (TextView) findViewById(R.id.tvDtlDescText);
		tvDtlRate =  (TextView) findViewById(R.id.tvDtlRate);
		
	}
	
	private void getMovieInformation(String titleId){
		String url = URL+"?title_id="+titleId;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = parser.getJSONFromUrl(url);
		System.out.println(jsonObject);
		try {
			createTextViews(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createTextViews(JSONObject movieDetails) throws JSONException{
		
		String desc = movieDetails.get("dscr").toString();
		String duration = movieDetails.get("film_minutes").toString();
		String releaseDt = movieDetails.get("release_date").toString();
		String descTxt = movieDetails.get("dscr_text").toString();
		String ratings = movieDetails.get("rating_code").toString();
		
		tvDtlTitle.setText(titleName);
		tvDtlDesc.setText(desc);
		tvDtlDur.setText(duration);
		tvDtlRelDt.setText(releaseDt);
		tvDtlDescText.setText(descTxt);
		tvDtlRate.setText(ratings);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie_details, menu);
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
