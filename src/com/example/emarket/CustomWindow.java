package com.example.emarket;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.TextureView;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomWindow extends Activity {
	protected TextView title;
	protected ImageView icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_custom_window);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_custom_window);
		
		title = (TextView)findViewById(R.id.title);
		icon = (ImageView)findViewById(R.id.icon);
	}
}
