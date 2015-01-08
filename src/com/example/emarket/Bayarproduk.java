package com.example.emarket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Bayarproduk extends CustomWindow {
	String[] lokasi = {"Semarang","Jawa Tengah","Jawa Barat","Jawa Timur & Bali","Sumatra","Kalimantan","Sulawesi","Papua"};
	Spinner spin;
	EditText gambarbp, hargabp, stokbp, jumlahbeli, totalbayar, alamat, notransfer, tujuan;
	ImageView gambar;
	TextView atas;
	Button cektotalbayar,bayar;
	String a,b,c,d,e,f,g,h,i,j,k,pilihlokasi,gbr;
	String a1,b1,c1,d1;
	SQLHelper dbHelper;
	int tot;
	ImageLoader imageLoader = new ImageLoader(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bayarproduk);
		
		this.title.setText("Order");
		this.icon.setImageResource(R.drawable.bayar);
		
		Spinner spin = (Spinner)findViewById(R.id.spinner1);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,lokasi);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				pilihlokasi = arg0.getItemAtPosition(arg2).toString();
				//Toast.makeText(getBaseContext(), pilihlokasi, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    dbHelper = new SQLHelper(this); 
	    atas = (TextView) findViewById(R.id.atasbp);
	    hargabp = (EditText) findViewById(R.id.hargabp);
	    stokbp = (EditText) findViewById(R.id.stokbp);
	    jumlahbeli = (EditText) findViewById(R.id.jumlahbeli);
	    cektotalbayar = (Button) findViewById(R.id.cektotalbayar);
	    totalbayar = (EditText) findViewById(R.id.totalbayar);
	    alamat = (EditText) findViewById(R.id.alamat);
	    notransfer = (EditText) findViewById(R.id.notransfer);
	    bayar = (Button) findViewById(R.id.bayar);
	    gambar = (ImageView)findViewById(R.id.gambarbp);
	    tujuan = (EditText)findViewById(R.id.tujuan);
	    
	    tampildataproduk();
	    cektotal();
	    bayarr();
	}

	private void tampildataproduk() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    String data;
		a = getIntent().getStringExtra("idkeranjang");
		b = getIntent().getStringExtra("idbarang");
		
		try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://erwinrizki.comxa.com/emarket/jsondetailproduk.php?id=" +b);
            HttpResponse response = client.execute(request);
            HttpEntity entity=response.getEntity();
            data=EntityUtils.toString(entity);
            Log.e("STRING", data);
		   
            try {
            	JSONObject object=new JSONObject(data);
		       	JSONArray Jarray = object.getJSONArray("data_produk");
		       
		       	JSONObject obj = Jarray.getJSONObject(0);	   
		       	c = obj.getString("nama_produk");
	    	   	d = obj.getString("harga_produk");
	    	   	e = obj.getString("stok"); 
	    	   	gbr = obj.getString("gambar_produk");
	    	   	a1 = obj.getString("nama_bank");
	    	   	b1 = obj.getString("nama_pemilik");
	    	   	c1 = obj.getString("nomer_rekening");
	    	   	d1 = a1+ "-" +c1+ " a/n " +b1;
	    	   	String gbr1 = "http://erwinrizki.comxa.com/emarket/gambarproduk/" +gbr;
	    	   	
	    	   	atas.setText("Produk\t: " + c);
	    	   	hargabp.setText(d);
	    	    stokbp.setText(e);
	    	    jumlahbeli.setText("1");
	    	    tujuan.setText(d1);
	    	    imageLoader.DisplayImage(gbr1, gambar);
	    	    
            	} catch (JSONException e) {
		    	  // TODO Auto-generated catch block
            		System.out.println("Error JSON: " +e.getMessage());
            		Toast.makeText(this,"Gagal JSON: " +e.getMessage(), Toast.LENGTH_SHORT).show();
		      	}
		} catch (ClientProtocolException e) {
			//Log.d("HTTPCLIENT", e.getLocalizedMessage());
			Toast.makeText(this,"Gagal Client Protocol", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			//Log.d("HTTPCLIENT", e.getLocalizedMessage());
			Toast.makeText(this,"Gagal IO ", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void cektotal() {
		cektotalbayar.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) { 
				if(Integer.parseInt(stokbp.getText().toString()) == 0){
					Toast.makeText(getBaseContext(), "Maaf Stok Barang Kosong", Toast.LENGTH_SHORT).show();
					Toast.makeText(getBaseContext(), "Cek Beberapa Hari Lagi Untuk Melihat Ketersediaan Barang", Toast.LENGTH_LONG).show();
					totalbayar.setText("Total Bayar\t: Rp. 0");
				}
				else if(Integer.parseInt(jumlahbeli.getText().toString()) > Integer.parseInt(stokbp.getText().toString())){
					Toast.makeText(getBaseContext(), "Stok Barang Tidak Cukup Untuk Memenuhi Jumlah Produk Yang Akan Anda Beli", Toast.LENGTH_LONG).show();
					totalbayar.setText("Total Bayar\t: Rp. 0");
				}
				else if(Integer.parseInt(jumlahbeli.getText().toString()) > 0){
					int a = 0;
					if(pilihlokasi =="Semarang"){
						a = 10000;
					}else if (pilihlokasi =="Jawa Tengah"){
						a = 20000;
					}else if (pilihlokasi =="Jawa Barat"){
						a = 30000;
					}else if (pilihlokasi =="Jawa Timur & Bali"){
						a = 35000;
					}else if (pilihlokasi =="Sumatra"){
						a = 60000;
					}else if (pilihlokasi =="kalimantan"){
						a = 70000;
					}else if (pilihlokasi =="Sulawesi"){
						a = 80000;
					}else{
						a = 100000;
					}
					int b = Integer.parseInt(jumlahbeli.getText().toString());
					int c = Integer.parseInt(hargabp.getText().toString());
					tot = (b * c) + a;
					totalbayar.setText(String.valueOf(tot));
				}
				else{
					Toast.makeText(getBaseContext(), "Transaksi Gagal. Terjadi Kesalahan Dalam Proses", Toast.LENGTH_LONG).show();
					totalbayar.setText("Total Bayar\t: Rp. 0");
				}
			}
		});
	}
	
	private void bayarr() {
		bayar.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) { 
				f=jumlahbeli.getText().toString();
				g = String.valueOf(tot);
				h=alamat.getText().toString();
				i=notransfer.getText().toString();
				
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	      	    StrictMode.setThreadPolicy(policy);
	      	      
	        	try {
	        		String url = "http://erwinrizki.comxa.com/emarket/jsontambahkeranjang.php";
	        		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
	        		nameValuePairs.add(new BasicNameValuePair("id", b));
					nameValuePairs.add(new BasicNameValuePair("harga", d));
					nameValuePairs.add(new BasicNameValuePair("jumlahbeli", jumlahbeli.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("lokasipembeli", pilihlokasi));
					nameValuePairs.add(new BasicNameValuePair("jumlahbayar", g));
					nameValuePairs.add(new BasicNameValuePair("alamat", alamat.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("notransfer", notransfer.getText().toString()));
	        		
	        		DefaultHttpClient client = new DefaultHttpClient();
	        		HttpPost request = new HttpPost(url);
			    	request.setEntity(new UrlEncodedFormEntity(nameValuePairs));	
					HttpResponse response = client.execute(request);
					
					//Toast.makeText(getBaseContext(), "Transaksi Berhasil", Toast.LENGTH_LONG).show();
					
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					db.execSQL("delete from keranjang where id = '"+a+"'");
					
					Toast.makeText(getBaseContext(), "Transaksi Berhasil", Toast.LENGTH_LONG).show();
					keawal();
	        	  } catch (ClientProtocolException e) {
	        		  Log.d("HTTPCLIENT", e.getLocalizedMessage());
	        		  Toast.makeText(getBaseContext(), "Gagal Client Protocol", Toast.LENGTH_LONG).show(); 
	              } catch (IOException e) {
	                  Log.d("HTTPCLIENT", e.getLocalizedMessage());
	                  Toast.makeText(getBaseContext(), "Gagal IO ", Toast.LENGTH_LONG).show(); 
	              }
			}
		});
	}
	
	public void keawal() {
		//android.os.Process.killProcess(android.os.Process.myPid());
		Intent i=new Intent(this,MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}

}
