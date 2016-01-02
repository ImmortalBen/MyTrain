package com.mytrain.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mytrain.R;
import com.mytrain.bean.Order;
import com.mytrain.bean.Ticket;
import com.mytrain.bean.Train;
import com.mytrain.dao.TicketDao;
import com.mytrain.dao.TrainDao;

public class Fragment4 extends Fragment {
	//private String items[] = new String[]{"111","222","333"};
	private String username;
	private String password;
	private TextView tv_username;
	private Fragment4Interface f4i;
	private boolean isautologin;
	private ListView lv_ticket;
	private ListViewAdapter listViewAdapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment4, container,false);
		//Log.i("tag", "1111");
		username = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE).getString("username","xxxxx");;
		tv_username = (TextView) view.findViewById(R.id.tv_username);
		lv_ticket = (ListView) view.findViewById(R.id.lv_ticket);
		TicketDao ticketDao = new TicketDao(getActivity());
		String temp = tv_username.getText().toString();
		tv_username.setText(username+temp);
		tv_username.setGravity(Gravity.CENTER);
//		tv_username.setTextSize(30);
//重要部分		
//		List<Order> orderList = new ArrayList<Order>();
//		
//		List<Ticket> ticketList = ticketDao.selectTicket("username = ?", new String[]{username}, null, null, null, null);
//		
//		TrainDao traindao = new TrainDao(getActivity());
//		
//		
//		for(Ticket ticket : ticketList){
//			Order order = new Order();
//			order.setTicketId(ticket.getTicketId());
//			order.setTrainType(ticket.getTrainType());
//			List<Train> trainList = new ArrayList<Train>();
//			String trainType = ticket.getTrainType();
//			trainList = traindao.selectTrain("trainType like ?", new String[]{trainType}, null, null, null, null);
//			Train train = trainList.get(0);
//			order.setStart_station(train.getOrigin_station());
//			order.setTerminal_station(train.getTerminal_station());
//			order.setStart_date(train.getStart_time(true));
//			order.setStart_time(train.getStart_time(false));
//			orderList.add(order);
//		}

		listViewAdapter = new ListViewAdapter(getOrderList(), getActivity());
		lv_ticket.setAdapter(listViewAdapter);
		lv_ticket.setOnItemClickListener((OnItemClickListener)listViewAdapter);
		return view;
	}
	public void setFragment4Interface(Fragment4Interface f4interface){
		f4i = f4interface;
	}
	public interface Fragment4Interface{
		public void removefragment4();
	}
	
	public List<Order> getOrderList(){
		List<Order> orderList = new ArrayList<Order>();
		TicketDao ticketDao = new TicketDao(getActivity());
		List<Ticket> ticketList = ticketDao.selectTicket("username = ?", new String[]{username}, null, null, null, null);
		TrainDao traindao = new TrainDao(getActivity());
		
		
		for(Ticket ticket : ticketList){
			Order order = new Order();
			order.setTicketId(ticket.getTicketId());
			order.setTrainType(ticket.getTrainType());
			List<Train> trainList = new ArrayList<Train>();
			String trainType = ticket.getTrainType();
			trainList = traindao.selectTrain("trainType like ?", new String[]{trainType}, null, null, null, null);
			Train train = trainList.get(0);
			order.setStart_station(train.getOrigin_station());
			order.setTime((int)train.getStart_time().toMillis(true)/1000);
			order.setTerminal_station(train.getTerminal_station());
			order.setStart_date(train.getStart_time(true));
			order.setStart_time(train.getStart_time(false));
			order.setTicket_price(train.getTicket_price());
			orderList.add(order);
		}
		return orderList;
	}	
	
	@Override
	public void onResume() {
		List<Order> orderList = getOrderList();
		listViewAdapter.setOrderList(orderList);
		listViewAdapter.notifyDataSetChanged();
		super.onResume();
	}
}

