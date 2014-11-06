package com.example.nativemovieondemand;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity {

	EditText etUrl;
	Button btSet;
	SharedPreferences sp;
	public static final String URLPREF = "pref_url";
	public static final String PACKAGE_NAME = "com.example.nativemovieondemand.";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		etUrl = (EditText) findViewById(R.id.etUrl);
		btSet = (Button) findViewById(R.id.btSet);
		btSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sp = getApplicationContext().getSharedPreferences(URLPREF, 0);
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("url", etUrl.getText().toString());
				if(editor.commit())
				{
					Toast.makeText(getApplicationContext(), "URL Updated Successfully", Toast.LENGTH_SHORT).show();
					navigateToMovieSearch();
				}else {
					Toast.makeText(getApplicationContext(), "Failed to update URL", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}

	public void navigateToMovieSearch(){
		Bundle bundle = getIntent().getExtras();
		String parentClassName = PACKAGE_NAME + bundle.get("PARENT_CLASS_NAME").toString();
		Class parentClass = null;
		try {
			parentClass = Class.forName(parentClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Intent intent = new Intent(getApplicationContext(), parentClass);
		startActivity(intent);
	}
}
