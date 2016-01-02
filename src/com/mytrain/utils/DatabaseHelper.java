package com.mytrain.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASENAME = "My12306.DB";
	private static final int VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASENAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table user(uId integer primary key autoincrement,"
				+ "username varchar(50) not null,"
				+ "userpwd varchar(50) not null,"
				+ "email varchar(50) not null,"
				+ "cardId varchar(50) not null," + "money integer not null,"
				+ "phone varchar(50) not null," + "state integer)";
		db.execSQL(sql);
		String sql2 = "create table train(trainId integer primary key autoincrement,"
				+ "trainType varchar(50) not null,"
				+ "origin_station varchar(50) not null,"
				+ "terminal_station varchar(50) not null,"
				+ "start_time integer not null,"
				+ "reach_time integer not null,"
				+ "ticket_price integer not null,"
				+ "seat_total_num integer not null,"
				+ "seat_remain_num integer not null," + "state integer)";
		db.execSQL(sql2);
		String sql3 = "create table ticket(ticketId integer primary key autoincrement,"
				+ "username varchar(50) not null,"
				+ "trainType varchar(50) not null,"
				+ "carriage_num integer not  null,"
				+ "seat_num integer not null," + "buy_time integer not null)";
		db.execSQL(sql3);

		String sql4 = "create table history(hId integer primary key autoincrement,"
				+ "origin_station varchar(50) not null,"
				+ "terminal_station varchar(50) not null,"
				+ "search_time integer not null)";
		db.execSQL(sql4);

		String sql5 = "create table contact(cId integer primary key autoincrement,"
				+ "uId integer not null,"
				+ "name varchar(50) not null,"
				+ "cardId varchar(50) not null)";
		db.execSQL(sql5);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		String sql = "drop table if exists user";
		db.execSQL(sql);
		this.onCreate(db);
	}

}
