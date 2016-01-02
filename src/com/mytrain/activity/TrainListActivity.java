package com.mytrain.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mytrain.R;
import com.mytrain.bean.Ticket;
import com.mytrain.bean.Train;
import com.mytrain.bean.User;
import com.mytrain.dao.TicketDao;
import com.mytrain.dao.TrainDao;
import com.mytrain.service.UserService;

public class TrainListActivity extends Activity {

	private ListView listView;
	private TrainDao traindao = new TrainDao(this);
	private TicketDao ticketdao = new TicketDao(this);
	private List<Train> trains = new ArrayList<Train>();
	private UserService us = new UserService(this);
	private SimpleAdapter adapter;
	private String origin_station;
	private String terminal_station;
	private int selected_position;
	private User user = new User();
	private String username = new String();
	private SharedPreferences sp;
	private List<Map<String, Object>> Itemlists = new ArrayList<Map<String, Object>>();
	public static Boolean islogin = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trainlist);
		initTrainList();
		initOnClickDialog();
	}

	public void initTrainList() {
		listView = (ListView) findViewById(R.id.lv_train);
		// 从fragment4获得intent:始发站、终点站和时间
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		origin_station = bundle.getString("origin_station");
		terminal_station = bundle.getString("terminal_station");
		int time = bundle.getInt("time");
		int todaysec = time+86400;
		String timeString = String.valueOf(time);
		String todayString = String.valueOf(todaysec);
		Toast.makeText(this, todayString, Toast.LENGTH_LONG).show();
		// 从数据库查找符合条件的列车班次
		trains = traindao
				.selectTrain(
						"origin_station like ? and terminal_station like ? and start_time > ? and start_time < ?",
						new String[] { origin_station, terminal_station,
								timeString,todayString }, null, null, "start_time asc",
						null);
		
		for (int i = 0; i < trains.size(); i++) {
			Map<String, Object> Itemlist = new HashMap<String, Object>();
			Train train = trains.get(i);
			Itemlist.put("type", train.getTrainType());
			Itemlist.put("origin", train.getOrigin_station());
			Itemlist.put("terminal", train.getTerminal_station());
			Itemlist.put("sdate", train.getStart_time(true));// true返回日期“2015-07-22”
			Itemlist.put("stime", train.getStart_time(false)); // false返回时间
			Itemlist.put("rdate", train.getReach_time(true));
			Itemlist.put("rtime", train.getReach_time(false));
			Itemlist.put("price", "￥" + train.getTicket_price() + "元");
			Itemlist.put("seat", train.getSeat_remain_num());
			Itemlists.add(Itemlist);
		}
		// 将列车班次信息放入listview adapter
		
		adapter = new SimpleAdapter(this, Itemlists, R.layout.item_train,
				new String[] { "type", "origin", "terminal", "sdate", "stime",
						"rdate", "rtime", "price", "seat" }, new int[] {
						R.id.tv_trainType, R.id.tv_originStation,
						R.id.tv_terminalStation, R.id.tv_startDate,
						R.id.tv_startTime, R.id.tv_reachDate,
						R.id.tv_reachTime, R.id.tv_ticketprice,
						R.id.tv_remainSeat });
		listView.setAdapter(adapter);
	}

	public void initOnClickDialog() {
		// AlertDialog.Builder
		sp = getSharedPreferences("user", MODE_PRIVATE);
		username = sp.getString("username", "");
		user.setUsername(username);
		user.setUserpwd(sp.getString("password", ""));
		if (username.equals("")) {

			islogin = false;
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Editor edt = sp.edit();
					edt.putBoolean("isClick", true);
					edt.commit();
					Toast.makeText(TrainListActivity.this, "请先登录！",
							Toast.LENGTH_LONG).show();
					finish();
				}

			});
		} else {
			islogin = false;
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					// position 代表被点击的列表项id
					selected_position = position;
					AlertDialog.Builder b = new AlertDialog.Builder(
							TrainListActivity.this).setTitle("是否确定购买该车次的车票？")
							.setPositiveButton("确定", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Train train = trains.get(selected_position);
									if (train.getSeat_remain_num() <= 0) {
										Toast.makeText(TrainListActivity.this,
												"该车次已售完！", Toast.LENGTH_LONG)
												.show();
									} else if(us.subMoney(user, train.getTicket_price())){
										// 更新该班列车的剩余座位	
										int remain_seat = train
												.getSeat_remain_num() - 1;
										train.setSeat_remain_num(remain_seat);
										traindao.updateTrain(train);
										Itemlists.get(selected_position).put("seat", remain_seat);	
										adapter.notifyDataSetChanged();	
										// 新建username对应下账号的ticket

										Ticket ticket = new Ticket();
										ticket.setTrainType(train
												.getTrainType());
										ticket.setUserName(username);
										ticket.setCarriage_num(
												train.getSeat_total_num(),
												train.getSeat_remain_num());
										ticket.setSeat_num(
												train.getSeat_total_num(),
												train.getSeat_remain_num());
										ticket.setBuy_time();
										ticketdao.addTicket(ticket);
										Toast.makeText(TrainListActivity.this,
												"购票成功！", Toast.LENGTH_LONG)
												.show();
									}
									else{
										Toast.makeText(TrainListActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
									}
								}

							}).setNegativeButton("取消", new OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									Toast.makeText(TrainListActivity.this,
											"取消购票！", Toast.LENGTH_LONG).show();
								}
							});
					b.create().show();

				}

			});
		}
	}
}
