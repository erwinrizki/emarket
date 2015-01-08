package com.example.emarket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Keranjang extends CustomWindow {
	String[] daftar,daftar2,daftar3;
	ListView ListView1;
	protected Cursor cursor;
	SQLHelper dbHelper;
	public static Keranjang ma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keranjang);
		
		this.title.setText("Shopping Cart");
		this.icon.setImageResource(R.drawable.cart);
		
		ma = this;
        
        dbHelper = new SQLHelper(this);

		RefreshList();
	}
	
	public void RefreshList()
    {
    	SQLiteDatabase db = dbHelper.getReadableDatabase();

		cursor = db.rawQuery("SELECT * FROM keranjang ORDER BY id ASC",null);

		daftar = new String[cursor.getCount()];
		daftar2 = new String[cursor.getCount()];
		daftar3 = new String[cursor.getCount()];
		cursor.moveToFirst();
		for (int cc=0; cc < cursor.getCount(); cc++)
		{
			cursor.moveToPosition(cc);
			daftar[cc] = cursor.getString(0).toString();//idkeranjang
			daftar2[cc] = cursor.getString(1).toString();//id barang
			daftar3[cc] = cursor.getString(2).toString();//nama barang
		}
		
    	ListView1 = (ListView)findViewById(R.id.ListView1);
        //ListView1.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar3));
        ListView1.setAdapter(new ArrayAdapter(this, R.layout.list_white_text, R.id.list_content, daftar3));
        ListView1.setSelected(true);
        ListView1.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				final String selection = daftar[arg2]; 
		    	final CharSequence[] dialogitem = {"Bayar", "Hapus"};
		    	AlertDialog.Builder builder = new AlertDialog.Builder(Keranjang.this);
		        builder.setTitle(daftar3[arg2]);
		        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						switch(item){
						case 0 :
							Intent i = new Intent(getApplicationContext(), Bayarproduk.class);
							i.putExtra("idkeranjang", selection);
							i.putExtra("idbarang", daftar2[arg2]);
					    	startActivity(i);
							break;
						case 1 :
							SQLiteDatabase db = dbHelper.getWritableDatabase();
							db.execSQL("delete from keranjang where id = '"+selection+"'");
					        RefreshList();
							break;
						}
					}
				});
		        builder.create().show();
			}});
        ((ArrayAdapter)ListView1.getAdapter()).notifyDataSetInvalidated();
    }
}
