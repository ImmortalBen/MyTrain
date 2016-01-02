package com.mytrain.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mytrain.bean.Ticket;
import com.mytrain.bean.User;
import com.mytrain.utils.DatabaseHelper;


public class UserDao {
	private DatabaseHelper dbHelper;

	public UserDao(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public long addUser(User user) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("email", user.getEmail());
		values.put("username", user.getUsername());
		values.put("userpwd", user.getUserpwd());
		values.put("cardID", user.getCardID());
		values.put("phone", user.getPhone());
		values.put("money", user.getMoney());
		values.put("state", user.getState());
		long count = db.insert("user", null, values);
		return count;
	}

	public Cursor findByUser(User user) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "Select * from user where username=? and userpwd=?";
		Cursor cursor = db.rawQuery(sql, new String[] { user.getUsername(),
				user.getUserpwd() });
		return cursor;
	}
	public Cursor findByUsername(User user) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "Select * from user where username=?";
		Cursor cursor = db.rawQuery(sql, new String[]{user.getUsername()});
		return cursor;
	}
	public void updatePassword(User user,String newpassword){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userpwd", newpassword);
		db.update("user",values,"username=?", new String[]{user.getUsername()}); 
		db.close();
	}
	
	public User selectUser(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.query("user", new String[] { "*" }, selection,
				selectionArgs, groupBy, having, orderBy);
		// Cursor c = queryTheCursor();
		User user = new User();
		while (c.moveToNext()) {
			user.setUserID(c.getInt(c.getColumnIndex("uId")));
			user.setUsername(c.getString(c.getColumnIndex("username")));
			user.setUserpwd(c.getString(c.getColumnIndex("userpwd")));
			user.setCardID(c.getString(c.getColumnIndex("cardId")));
			user.setEmail(c.getString(c.getColumnIndex("email")));
			user.setPhone(c.getString(c.getColumnIndex("phone")));
			user.setMoney(c.getInt(c.getColumnIndex("money")));
			user.setState(c.getInt(c.getColumnIndex("state")));
		}
		c.close();
		return user;
	}
	
	public int updateUser(User user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("username", user.getUsername());
		values.put("userpwd", user.getUserpwd());
		values.put("cardId", user.getCardID());
		values.put("money", user.getMoney());
		values.put("phone", user.getPhone());
		values.put("email", user.getEmail());
		return db.update("user", values, "username=?", new String[]{user.getUsername()});		
	}
	
	public void addMoney(User user,int money){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = findByUser(user);
		if(cursor.moveToFirst()){
			int money1 = cursor.getInt(cursor.getColumnIndex("money"));
			int money2 = money + money1;
			ContentValues values = new ContentValues();
			values.put("money", money2);
			db.update("user",values,"username=?", new String[]{user.getUsername()}); 
		}	
		cursor.close();
	}
	
	public boolean subMoney(User user,int money){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = findByUser(user);
		if(cursor.moveToFirst()){
			int money1 = cursor.getInt(cursor.getColumnIndex("money"));
			int money2 = money1 - money;
			if(money2 > 0){
				ContentValues values = new ContentValues();
				values.put("money", money2);
				db.update("user",values,"username=?", new String[]{user.getUsername()});
				return true;
			}
		}	
		cursor.close();
		return false;
	}
}

