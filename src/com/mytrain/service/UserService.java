package com.mytrain.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mytrain.bean.User;
import com.mytrain.dao.UserDao;


public class UserService {
	private Context context;
	private UserDao userdao;
	public UserService(Context context){
		this.context=context;
		userdao = new UserDao(context);
	}
	
 
	public boolean checkUser(String username,String userpwd){
		//实例化user对象，然后向user对象放入用户名和密码
		User user= new User();
		user.setUsername(username);
		user.setUserpwd(userpwd);
		//实例化userdao对象，调用findbyuser方法
		userdao=new UserDao(context);
		Cursor cu=userdao.findByUser(user);
		if(cu.moveToNext()){
			Log.i("TAG", "*****"+user.getUsername());
			return true;
		}
		cu.close();
		return false;
	}
	public boolean checkUsername(String username){
		User user = new User();
		user.setUsername(username);
		userdao = new UserDao(context);
		Cursor cu = userdao.findByUsername(user);
		if(cu.moveToNext()){
			return true;
		}
		cu.close();
		return false;
	}
	public boolean changePwd(User user,String newpassword){
		Cursor cursor = userdao.findByUser(user);
		if(cursor.moveToFirst()){
			userdao.updatePassword(user, newpassword);
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}
	
	public User lookForInfo(User user){
		Cursor cursor = userdao.findByUser(user);
		if(cursor.moveToFirst()){
			user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
			user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			user.setCardID(cursor.getString(cursor.getColumnIndex("cardId")));
			user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			user.setMoney(Integer.valueOf(cursor.getString(cursor.getColumnIndex("money"))));
		}
		cursor.close();
		return user;	
	}
	
	public boolean UpdateUser(User user){
		
		int i = userdao.updateUser(user);
		if(i>0)
			return true;
		else
			return false;	
	}
	
	public void addMoney(User user,int money){	
		userdao.addMoney(user, money);
	}
	
	public boolean subMoney(User user,int money){
		return userdao.subMoney(user, money);
	}

}
