package com.example.emarket;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends CustomWindow {
	
	Button LihatSemuaToko, Keranjang, CaraBeli, TentangKami, Keluar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		this.title.setText("Home");
		this.icon.setImageResource(R.drawable.home);
		
		LihatSemuaToko=(Button)findViewById(R.id.LihatSemuaToko);
		Keranjang=(Button)findViewById(R.id.Keranjang);
		TentangKami=(Button)findViewById(R.id.TentangKami);
		Keluar=(Button)findViewById(R.id.Keluar);
		
		LihatSemuaToko.setOnClickListener(new Button.OnClickListener() {
	       	@Override
			public void onClick(View v) {
	       		LihatSemuaToko();
	       	}
	    });
		Keranjang.setOnClickListener(new Button.OnClickListener() {
	       	@Override
			public void onClick(View v) {
	       		Keranjang();
	       	}
	    });

		TentangKami.setOnClickListener(new Button.OnClickListener() {
	       	@Override
			public void onClick(View v) {
	       		TentangKami();
	       	}
	    });
		Keluar.setOnClickListener(new Button.OnClickListener() {
	       	@Override
			public void onClick(View v) {
	       		//android.os.Process.killProcess(android.os.Process.myPid());
	            finish();
	       	}
	    });
	}

	
	public void LihatSemuaToko() {
		Intent i=new Intent(this,LihatSemuaToko.class);
		startActivity(i);
	}
	public void Keranjang() {
		Intent i=new Intent(this,Keranjang.class);
		startActivity(i);
	}
	public void CaraBeli() {
		Intent i=new Intent(this,CaraBeli.class);
		startActivity(i);
	}
	public void TentangKami() {
		Intent i=new Intent(this,TentangKami.class);
		startActivity(i);
	}

}

