package com.mytrain.testcase;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;  

import com.mytrain.utils.DatabaseHelper;

public class TestDatabase extends AndroidTestCase {
	public void testDatabase(){
		DatabaseHelper dbHelper = new DatabaseHelper(this.getContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
	}
}
