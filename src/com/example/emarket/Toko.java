package com.example.emarket;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Toko extends CustomWindow {
	ListView ListView01;
	String[] nama ;
    String id[] ;
    String harga[] ;
    String keterangan[] ;
    String gambar[] ;
    String stok[] ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.toko);
		this.title.setText("Product");
		this.icon.setImageResource(R.drawable.product);
		
		tampilproduk();
	}
	
	private void tampilproduk() {
		// TODO Auto-generated method stub
		String data;
		String idtoko = getIntent().getStringExtra("id_toko");
		String namatoko = getIntent().getStringExtra("nama_toko");
		try {
            DefaultHttpClient client = new DefaultHttpClient();
            //HttpGet request = new HttpGet("http://192.168.155.1/emarket/jsonproduk.php?id=" +idtoko);
            HttpGet request = new HttpGet("http://erwinrizki.comxa.com/emarket/jsonproduk.php?id=" +idtoko);
            HttpResponse response = client.execute(request);
            HttpEntity entity=response.getEntity();
            data=EntityUtils.toString(entity);
            Log.e("STRING", data);
            
            try {
		       JSONObject object=new JSONObject(data);
		       JSONArray Jarray = object.getJSONArray("data_produk");
		       
		       id = new String[Jarray.length()];
		       nama = new String[Jarray.length()];
		       harga = new String[Jarray.length()];
		       keterangan = new String[Jarray.length()];
		       gambar = new String[Jarray.length()];
		       stok = new String[Jarray.length()];
		       
		       for(int i=0;i<Jarray.length(); i++)
		       {
		    	   JSONObject obj = Jarray.getJSONObject(i);
		    	   id[i] = obj.getString("id_produk");
		    	   nama[i] = obj.getString("nama_produk");
		    	   harga[i] = obj.getString("harga_produk");
		    	   keterangan[i] = obj.getString("keterangan_produk");
		    	   gambar[i] = obj.getString("gambar_produk");
		    	   stok[i] = obj.getString("stok");
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
	    		final String selection3 = harga[arg2];
	    		final String selection4 = keterangan[arg2];
	    		final String selection5 = gambar[arg2];
	    		final String selection6 = stok[arg2];
	    		String aaa = getIntent().getStringExtra("id_toko");
	    		
	    		Intent aa = new Intent(getApplicationContext(), Perproduk.class);
				aa.putExtra("id_produk", selection);
				aa.putExtra("nama_produk", selection2);
				aa.putExtra("harga_produk", selection3);
				aa.putExtra("keterangan_produk", selection4);
				aa.putExtra("gambar_produk", selection5);
				System.out.println("Gambar Produk: " +selection5);
				aa.putExtra("stok", selection6);
				aa.putExtra("id_toko",aaa);
				startActivity(aa);
				
			}
	    });
	}
}


