package com.mytrain.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mytrain.bean.Train;
import com.mytrain.utils.DatabaseHelper;

public class TrainDao {
	private DatabaseHelper dbHelper;

	public TrainDao(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public long addTrain(Train train) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("trainType", train.getTrainType());
		values.put("origin_station", train.getOrigin_station());
		values.put("terminal_station", train.getTerminal_station());
		values.put("start_time", (train.getStart_time().toMillis(true) / 1000));
		values.put("reach_time", (train.getReach_time().toMillis(true) / 1000));
		values.put("ticket_price", train.getTicket_price());
		values.put("seat_total_num", train.getSeat_total_num());
		values.put("seat_remain_num", train.getSeat_remain_num());
		values.put("state", train.getState());
		long count = db.insert("train", null, values);
		return count;
	}

	public List<Train> selectTrain(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit) {
		ArrayList<Train> trains = new ArrayList<Train>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db
				.query("train",
						new String[] { "*" },
						selection, selectionArgs, groupBy, having, orderBy);
		//Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			Train train = new Train();
			train.setTrainID(c.getInt(c.getColumnIndex("trainId")));
			train.setTrainType(c.getString(c.getColumnIndex("trainType")));
			train.setOrigin_station(c.getString(c
					.getColumnIndex("origin_station")));
			train.setTerminal_station(c.getString(c
					.getColumnIndex("terminal_station")));
			train.setStart_time(((long) c.getInt(c.getColumnIndex("start_time"))) * 1000);
			train.setReach_time(((long) c.getInt(c.getColumnIndex("reach_time"))) * 1000);
			train.setTicket_price(c.getInt(c.getColumnIndex("ticket_price")));
			train.setSeat_total_num(c.getInt(c
					.getColumnIndex("seat_total_num")));
			train.setSeat_remain_num(c.getInt(c
					.getColumnIndex("seat_remain_num")));
			trains.add(train);
		}
		c.close();
		return trains;
	}

	public void updateTrain(Train train) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("trainType", train.getTrainType());
		values.put("origin_station", train.getOrigin_station());
		values.put("terminal_station", train.getTerminal_station());
		values.put("start_time", (train.getStart_time().toMillis(true) / 1000));
		values.put("reach_time", (train.getReach_time().toMillis(true) / 1000));
		values.put("ticket_price", train.getTicket_price());
		values.put("seat_total_num", train.getSeat_total_num());
		values.put("seat_remain_num", train.getSeat_remain_num());
		values.put("state", train.getState());
		db.update("train", values, "trainId = ?",
				new String[] { String.valueOf(train.getTrainID()) });
	}

	public void deleteTrain(String trainType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("train", "trainType = ?", new String[] { trainType });
	}
	
	public Cursor queryTheCursor() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM train", null);
		return c;
	}

}
