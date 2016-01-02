package com.mytrain.bean;

import android.text.format.Time;

public class Order {
	private int ticketId;
	private String trainType;
	private String start_station;// 始发站
	private String terminal_station;// 终点站
	private String start_time; // 发车时间
	private String start_date;
	private int ticket_price; // 票价
	private int time;
	
	
	public int getTime() {
		return time;
	}
	public void setTime(int l) {
		this.time = l;
	}
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public String getStart_station() {
		return start_station;
	}
	public void setStart_station(String start_station) {
		this.start_station = start_station;
	}
	public String getTerminal_station() {
		return terminal_station;
	}
	public void setTerminal_station(String terminal_station) {
		this.terminal_station = terminal_station;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public int getTicket_price() {
		return ticket_price;
	}
	public void setTicket_price(int ticket_price) {
		this.ticket_price = ticket_price;
	}
	
}
