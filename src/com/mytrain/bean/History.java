package com.mytrain.bean;

import android.text.format.Time;

public class History {
	private int hId;
	private String Origin_station;
	private String Terminal_station;
	private Time search_time = new Time();

	public int gethId() {
		return hId;
	}

	public void sethId(int hId) {
		this.hId = hId;
	}

	public String getOrigin_station() {
		return Origin_station;
	}

	public void setOrigin_station(String origin_station) {
		Origin_station = origin_station;
	}

	public String getTerminal_station() {
		return Terminal_station;
	}

	public void setTerminal_station(String terminal_station) {
		Terminal_station = terminal_station;
	}

	public long getSearch_time() {
		return search_time.toMillis(true);
	}

	public void setSearch_time(Time search_time) {
		this.search_time = search_time;
	}
	public void setSearch_time() {
		this.search_time.setToNow();		
	}
	
	public void setSearch_time(long millis) {
		this.search_time.switchTimezone("GTM+8");
		this.search_time.set(millis);
	}
}
