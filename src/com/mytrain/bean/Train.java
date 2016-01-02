package com.mytrain.bean;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.text.format.Time;

public class Train {
	public Train() {
		super();
		this.start_time = new Time();
		this.reach_time = new Time();
	}

	public Train(int trainID, String trainType, String origin_station,
			String terminal_station, int ticket_price, 
			int seat_total_num, int seat_remain_num, int state) {
		super();
		this.trainID = trainID;
		this.trainType = trainType;
		this.origin_station = origin_station;
		this.terminal_station = terminal_station;
		this.start_time = new Time();
		this.reach_time = new Time();
		this.ticket_price = ticket_price;
		this.seat_total_num = seat_total_num;
		this.seat_remain_num = seat_remain_num;
		this.state = state;
	}

	private int trainID;
	private String trainType; // 列车类型（G：高速动车、C：城际动车、K：快速动车等）
	private String origin_station;// 始发站
	private String terminal_station;// 终点站
	private Time start_time; // 发车时间
	private Time reach_time; // 到达时间
	private int ticket_price; // 票价
	private int seat_total_num;
	private int seat_remain_num;
	private int state;

	@Override
	public String toString() {
		return "Train [trainID=" + trainID + ", trainType=" + trainType
				+ ", origin_station=" + origin_station + ", terminal_station="
				+ terminal_station + ", start_time=" + start_time.toString()
				+ ", reach_time=" + reach_time.toString() + ", ticket_price="
				+ ticket_price + ", seat_total_num=" + seat_total_num
				+ ", seat_remain_num=" + seat_remain_num + ", state=" + state
				+ "]";
	}

	public int getTrainID() {
		return trainID;
	}

	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}

	public String getTrainType() {
		return trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}

	public String getOrigin_station() {
		return origin_station;
	}

	public void setOrigin_station(String origin_station) {
		this.origin_station = origin_station;
	}

	public String getTerminal_station() {
		return terminal_station;
	}

	public void setTerminal_station(String terminal_station) {
		this.terminal_station = terminal_station;
	}

	public Time getStart_time() {
		return start_time;
	}

	public String getStart_time(Boolean date) {
		if (date) {
			String format1 = start_time.format("%Y-%m-%d");
			return format1;
		} else {
			String format2 = start_time.format("%H:%M");
			return format2;
		}
	}

	public void setStart_time(int year, int month, int day, int hour, int minute) {
		this.start_time.switchTimezone("GTM+8");
		this.start_time.set(0, minute, hour, day, month-1, year);
		this.start_time.set(this.start_time.toMillis(true));
	}

	public void setStart_time(long millis) {
		this.start_time.switchTimezone("GTM+8");
		start_time.set(millis);
	}

	public Time getReach_time() {
		return reach_time;
	}

	public String getReach_time(Boolean date) {
		if (date) {
			String format1 = reach_time.format("%Y-%m-%d");
			return format1;
		} else {
			String format2 = reach_time.format("%H:%M");
			return format2;
		}
	}

	public void setReach_time(int year, int month, int day, int hour, int minute) {
		this.start_time.switchTimezone("GTM+8");
		this.reach_time.set(0, minute, hour, day, month-1, year);
		this.reach_time.set(this.reach_time.toMillis(true));
	}

	public void setReach_time(long millis) {
		this.start_time.switchTimezone("GTM+8");
		this.reach_time.set(millis);
	}

	public int getTicket_price() {
		return ticket_price;
	}

	public void setTicket_price(int ticket_price) {
		this.ticket_price = ticket_price;
	}

	public int getSeat_total_num() {
		return seat_total_num;
	}

	public void setSeat_total_num(int seat_total_num) {
		this.seat_total_num = seat_total_num;
	}

	public int getSeat_remain_num() {
		return seat_remain_num;
	}

	public void setSeat_remain_num(int seat_remain_num) {
		this.seat_remain_num = seat_remain_num;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
