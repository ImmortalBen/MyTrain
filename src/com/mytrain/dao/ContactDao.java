package com.mytrain.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mytrain.bean.Contact;
import com.mytrain.utils.DatabaseHelper;

public class ContactDao {
	private DatabaseHelper dbHelper;

	public ContactDao(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public long addContact(Contact contact) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("uId", contact.getUserId());
		values.put("name", contact.getName());
		values.put("cardId", (contact.getCardId()));
		long count = db.insert("contact", null, values);
		return count;
	}

	public List<Contact> selectContact(String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		ArrayList<Contact> contactlist = new ArrayList<Contact>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.query("contact", new String[] { "*" }, selection,
				selectionArgs, groupBy, having, orderBy);
		// Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			Contact contact = new Contact();
			contact.setContactId(c.getInt(c.getColumnIndex("cId")));
			contact.setUserId(c.getInt(c
					.getColumnIndex("uId")));
			contact.setName(c.getString(c
					.getColumnIndex("name")));
			contact.setCardId(c.getString(c
					.getColumnIndex("cardId")));
			contactlist.add(contact);
		}
		c.close();
		return contactlist;
	}
}
