package com.mytrain.bean;

import android.text.format.Time;

public class Ticket {
	
	public Ticket() {
		super();
		buy_time = new Time();
	}
	public Ticket(int ticketId, String userName, String trainType,
			int carriage_num, int seat_num) {
		super();
		this.ticketId = ticketId;
		this.userName = userName;
		this.trainType = trainType;
		this.carriage_num = carriage_num;
		this.seat_num = seat_num;
		buy_time = new Time();
	}

	private int ticketId;
	private String userName;
	private String trainType;
	private int carriage_num;
	private int seat_num;
	private Time buy_time;
	
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", userName=" + userName
				+ ", trainType=" + trainType + ", carriage_num=" + carriage_num
				+ ", seat_num=" + seat_num + "]";
	}
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public int getCarriage_num() {
		return carriage_num;
	}
	public void setCarriage_num(int carriage_num) {
		this.carriage_num = carriage_num;
	}
	public void setCarriage_num(int total_seat, int remain_seat){
		carriage_num = (total_seat-remain_seat)/100+1;
	} 
	public int getSeat_num() {
		return seat_num;
	}
	public void setSeat_num(int seat_num) {
		this.seat_num = seat_num;
	}
	public void setSeat_num(int total_seat, int remain_seat){
		this.seat_num = (total_seat-remain_seat)%100+1;
	}
	public Time getBuy_time() {
		return buy_time;
	}

	public String getBuy_time(Boolean date) {
		if (date) {
			String format1 = buy_time.format("%m-%d %H:%M");
			return format1;
		} else {
			String format2 = buy_time.format("%H:%M");
			return format2;
		}
	}

	public void setBuy_time(){
		this.buy_time.setToNow();
	}
	public void setBuy_time(int year, int month, int day, int hour, int minute) {
		this.buy_time.switchTimezone("GTM+8");
		this.buy_time.set(0, minute, hour, day, month-1, year);
	}

	public void setBuy_time(long millis) {
		this.buy_time.switchTimezone("GTM+8");
		buy_time.set(millis);
	}
}
