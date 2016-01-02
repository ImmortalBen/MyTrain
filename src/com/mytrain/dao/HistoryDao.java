package com.mytrain.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mytrain.bean.History;
import com.mytrain.utils.DatabaseHelper;

public class HistoryDao {
	private DatabaseHelper dbHelper;

	public HistoryDao(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public long addHistory(History history) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("origin_station", history.getOrigin_station());
		values.put("terminal_station", history.getTerminal_station());
		values.put("search_time", (history.getSearch_time() / 1000));
		long count = db.insert("history", null, values);
		return count;
	}

	public List<History> selectHistory(String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		ArrayList<History> historylist = new ArrayList<History>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.query("history", new String[] { "*" }, selection,
				selectionArgs, groupBy, having, orderBy);
		// Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			History history = new History();
			history.sethId(c.getInt(c.getColumnIndex("hId")));
			history.setOrigin_station(c.getString(c
					.getColumnIndex("origin_station")));
			history.setTerminal_station(c.getString(c
					.getColumnIndex("terminal_station")));
			history.setSearch_time(((long) c.getInt(c
					.getColumnIndex("search_time"))) * 1000);
			historylist.add(history);
		}
		c.close();
		return historylist;
	}
	
	public void deleteHistory() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("history", null, new String[] {});
	}
}
