package com.mytrain.test;

import com.mytrain.dao.UserDao;
import com.mytrain.utils.DatabaseHelper;
import com.mytrain.bean.User;

import android.test.AndroidTestCase;


public class UserServiceCase extends AndroidTestCase{
	public void testCreate() {
		DatabaseHelper dbhelper = new DatabaseHelper(this.getContext());
		dbhelper.getWritableDatabase();
	}

	public void testSave() {
		UserDao us = new UserDao(this.getContext());
		User user = new User();
		user.setEmail("123@321.com");
		user.setUsername("111");
		user.setUserpwd("111");
		user.setCardID("1234");
		user.setPhone("4321");
		user.setMoney(1000);
		user.setState(1);
		long count = us.addUser(user);
		System.out.print("aaaaaa" + count);
	}
}
