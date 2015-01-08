package com.example.emarket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.R.string;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Perproduk extends CustomWindow {
	
	EditText namap, kategorip, keteranganp, hargap, stokp;
	TextView atas;
	Button masukkeranjang;
	ImageView gambar;
	String a, b;
	SQLHelper dbHelper;
	int stok;
	ImageLoader imageLoader = new ImageLoader(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.perproduk);
	    dbHelper = new SQLHelper(this);
	    
	    this.title.setText("Detail");
	    this.icon.setImageResource(R.drawable.shop2);
	    
	    atas = (TextView) findViewById(R.id.atas);
	    keteranganp = (EditText) findViewById(R.id.keteranganp);
	    hargap = (EditText) findViewById(R.id.hargap);
	    stokp = (EditText) findViewById(R.id.stokp);
	    masukkeranjang = (Button) findViewById(R.id.masukkeranjang);
	    gambar = (ImageView)findViewById(R.id.gambarp);
	    
	    a = getIntent().getStringExtra("id_produk");
	    b = getIntent().getStringExtra("nama_produk");
	    String c = getIntent().getStringExtra("harga_produk");
	    String e = getIntent().getStringExtra("keterangan_produk");
	    String f = getIntent().getStringExtra("gambar_produk");
	    String g = getIntent().getStringExtra("stok");
	    final String h = getIntent().getStringExtra("id_toko");
	    stok = Integer.parseInt(g);
	    String f1 = "http://erwinrizki.comxa.com/emarket/gambarproduk/" +f;
	    
	    System.out.println("Isi f1 adalah: " +f1);
	    
	    atas.setText("Produk\t: " + b);
	    keteranganp.setText(e);
	    hargap.setText(c);
	    stokp.setText(g);
	    imageLoader.DisplayImage(f1, gambar);
	    
	    masukkeranjang.setOnClickListener(new Button.OnClickListener() {  
            
          public void onClick(View v) {  
        	  if(stok == 0) {
        		  Toast.makeText(getBaseContext(), "Maaf Stok Barang Kosong, Data Tidak Bisa Dimasukkan ke Keranjang", Toast.LENGTH_LONG).show();
        	  } else {
        		  SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        		  Cursor c = db1.rawQuery("SELECT * FROM keranjang WHERE idproduk = " +a, null);
        		  c.moveToFirst();
        		  
        		  if(c.getCount() == 0) {
		        	  SQLiteDatabase db = dbHelper.getWritableDatabase();
		        	  db.execSQL("insert into keranjang(idproduk,namaproduk) " + " values('"+ a +"','"+ b +"')");
		              
		              Toast.makeText(getBaseContext(), "Data Berhasil Ditambahkan ke Keranjang", Toast.LENGTH_LONG).show();
		              Toast.makeText(getApplicationContext(), "Silahkan Lanjutkan Transaksi Anda di Menu Keranjang", Toast.LENGTH_LONG).show();
        		  } else {
        			  Toast.makeText(getBaseContext(), "Data Sudah Ada Dalam Keranjang", Toast.LENGTH_LONG).show();
        		  }
        	  }
              //mlebukeranjang();
        	  /*
				try {
					String id = URLEncoder.encode(a, "utf-8");
					url += "?id=" +id;
					System.out.println("Url= " +url);
					getRequest(url);
				} catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			  
        	  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      	      StrictMode.setThreadPolicy(policy);
      	      
        	  try {
        		  String url = "http://192.168.155.1/emarket/jsontambahkeranjang.php";
        		  String id = URLEncoder.encode(a, "utf-8");
        		  url += "?id=" +id;
        		  DefaultHttpClient client = new DefaultHttpClient();
                  HttpGet request = new HttpGet(url);
                  HttpResponse response = client.execute(request);
                  Toast.makeText(getBaseContext(), "Data Berhasil Ditambahkan ke Keranjang", Toast.LENGTH_LONG).show();
        	  } catch (ClientProtocolException e) {
                  Log.d("HTTPCLIENT", e.getLocalizedMessage());
              	//Toast.makeText(this,"Gagal Client Protocol", Toast.LENGTH_SHORT).show();
              } catch (IOException e) {
                  Log.d("HTTPCLIENT", e.getLocalizedMessage());
              	//Toast.makeText(this,"Gagal IO ", Toast.LENGTH_SHORT).show();
              }	
        	  Toast.makeText(getApplicationContext(), "Silahkan Lanjutkan Transaksi Anda di Menu Keranjang", Toast.LENGTH_LONG).show(); 
          	  */
          }  
      });  
			
	}
	
	/*
	public void getRequest(String Url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(Url);
		System.out.println("Url = " +Url);
		
		try {
			HttpResponse response = client.execute(request);
			Toast.makeText(this, "Tambah Data" + request(response) + " ", 
					Toast.LENGTH_SHORT).show();
		} catch(Exception ex) {
			//ex.printStackTrace();
			Toast.makeText(this, "Tambah Data Gagal!", Toast.LENGTH_SHORT).show();
			System.out.println("Gagal karena: " +ex.getMessage());
		}
	}
	
	public static String request(HttpResponse response) {
		String result = "";
		
		try {
			InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
		} catch(Exception ex) {
			result = "Error";
		}
		
		return result;
	}
	*/
}






