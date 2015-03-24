package com.example.providertest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;

public class MainActivity extends Activity implements OnClickListener {

	private String newId;
	public static final String bookUriPre = "content://com.example.databasetest."
			+ "provider/book";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button addData = (Button) findViewById(R.id.add_data);
		addData.setOnClickListener(this);
		Button updateData = (Button) findViewById(R.id.update_data);
		updateData.setOnClickListener(this);
		Button deleteData = (Button) findViewById(R.id.delete_data);
		deleteData.setOnClickListener(this);
		Button queryData = (Button) findViewById(R.id.query_data);
		queryData.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Uri uri;// = null;
		ContentValues values;
		switch (v.getId()) {
		case R.id.add_data:
			uri = Uri.parse(bookUriPre);
			values = new ContentValues();
			values.put("name", "A Clash of Kings");
			values.put("author", "Geory Martin");
			values.put("pages", 1040);
			values.put("price", 55.95);
			Uri newUri = getContentResolver().insert(uri, values);
			newId = newUri.getPathSegments().get(1);
			break;
		case R.id.update_data:
			uri = Uri.parse(bookUriPre + "/" + newId);
			values = new ContentValues();
			values.put("name", "A Storm of Swords");
			values.put("pages", 1216);
			values.put("price", 2.95);
			getContentResolver().update(uri, values, null, null);
			break;
		case R.id.delete_data:
			uri = Uri.parse(bookUriPre + "/" + newId);
			getContentResolver().delete(uri,null, null);
			break;
		case R.id.query_data:
			uri = Uri.parse(bookUriPre);
			Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String name = cursor.getString(cursor.getColumnIndex("name"));
					Log.e("MainActivity", id + "," + name);
				}
				cursor.close();
			}
			break;
		default:
		}
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
