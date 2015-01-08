package com.example.emarket;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class LihatSemuaToko extends CustomWindow {

	ListView ListView01;
	String[] nama ;
    String id[] ;
    String alamat[] ;
    String notel[] ;
    String pemilik[] ;
    
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lihatsemuatoko);
		
		this.title.setText("All Store");
		this.icon.setImageResource(R.drawable.shop1);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		
		tampiltoko();
	}

	private void tampiltoko() {
		// TODO Auto-generated method stub
		String data;
		try {
            DefaultHttpClient client = new DefaultHttpClient();
            //HttpGet request = new HttpGet("http://192.168.155.1/emarket/jsontoko.php");
            HttpGet request = new HttpGet("http://erwinrizki.comxa.com/emarket/jsontoko.php");
            HttpResponse response = client.execute(request);
            HttpEntity entity=response.getEntity();
            data=EntityUtils.toString(entity);
            Log.e("STRING", data);
            
            try {
		       JSONObject object=new JSONObject(data);
		       JSONArray Jarray = object.getJSONArray("data_toko");
		       
		       id = new String[Jarray.length()];
		       nama = new String[Jarray.length()];
		       		       
		       for(int i=0;i<Jarray.length(); i++)
		       {
		    	   JSONObject obj = Jarray.getJSONObject(i);
		    	   id[i] = obj.getString("id_toko");
		    	   nama[i] = obj.getString("nama_toko");		    	   
		       }    
       
		      } catch (JSONException e) {
		       // TODO Auto-generated catch block
		    	  Toast.makeText(this,"Gagal JSON: " +e.getMessage(), Toast.LENGTH_SHORT).show();
		      }
          
        } catch (ClientProtocolException e) {
            //Log.d("HTTPCLIENT", e.getLocalizedMessage());
        	Toast.makeText(this,"Gagal Client Protocol", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Log.d("HTTPCLIENT", e.getLocalizedMessage());
        	Toast.makeText(this,"Gagal IO ", Toast.LENGTH_SHORT).show();
        }	
		
		
		ListView01 = (ListView)findViewById(R.id.ListView01);
		//ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, nama));
		ListView01.setAdapter(new ArrayAdapter(this, R.layout.list_white_text, R.id.list_content, nama));
		ListView01.setOnItemClickListener(new OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    		final String selection = id[arg2];
	    		final String selection2 = nama[arg2];
	    		Intent aa = new Intent(getApplicationContext(), Toko.class);
				aa.putExtra("id_toko", selection);
				aa.putExtra("nama_toko", selection2);
				startActivity(aa);
			}
	    });
	}
}	

