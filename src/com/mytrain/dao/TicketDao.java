package com.mytrain.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mytrain.bean.Ticket;
import com.mytrain.bean.Train;
import com.mytrain.utils.DatabaseHelper;

public class TicketDao {
	private DatabaseHelper dbHelper;

	public TicketDao(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public long addTicket(Ticket ticket) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("username", ticket.getUserName());
		values.put("trainType", ticket.getTrainType());
		values.put("carriage_num", ticket.getCarriage_num());
		values.put("seat_num", ticket.getSeat_num());
		values.put("buy_time", (ticket.getBuy_time().toMillis(true) / 1000));
		long count = db.insert("ticket", null, values);
		return count;
	}

	public List<Ticket> selectTicket(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit) {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.query("ticket", new String[] { "*" }, selection,
				selectionArgs, groupBy, having, orderBy);
		// Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			Ticket ticket = new Ticket();
			ticket.setTicketId(c.getInt(c.getColumnIndex("ticketId")));
			ticket.setUserName(c.getString(c.getColumnIndex("username")));
			ticket.setTrainType(c.getString(c.getColumnIndex("trainType")));
			ticket.setCarriage_num(c.getInt(c.getColumnIndex("carriage_num")));
			ticket.setSeat_num(c.getInt(c.getColumnIndex("seat_num")));
			ticket.setBuy_time(((long) c.getInt(c.getColumnIndex("buy_time"))) * 1000);
			tickets.add(ticket);
		}
		c.close();
		return tickets;
	}

	public void deleteTicket(Ticket ticket) {
		String id = String.valueOf(ticket.getTicketId());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("ticket", "ticketId = ?", new String[] { id });
	}

	public void deleteTicket(String ticketId) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("ticket", "ticketId = ?", new String[] { ticketId });
	}
	
	public Cursor queryTheCursor() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM ticket", null);
		return c;
	}
}
