package com.mytrain.test;

import com.mytrain.bean.History;
import com.mytrain.dao.HistoryDao;
import com.mytrain.utils.DatabaseHelper;

import android.test.AndroidTestCase;

public class historycase extends AndroidTestCase {
	public void testCreate() {
		DatabaseHelper dbhelper = new DatabaseHelper(this.getContext());
		dbhelper.getWritableDatabase();
	}

	public void testSave() {
		HistoryDao hdao = new HistoryDao(this.getContext());
		History history = new History();
		history.setOrigin_station("广州南");
		history.setTerminal_station("珠海");
		history.setSearch_time();
		hdao.addHistory(history);
	}
}