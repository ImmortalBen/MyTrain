package com.mytrain.fragment;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mytrain.R;
import com.mytrain.activity.TrainListActivity;
import com.mytrain.bean.Order;
import com.mytrain.bean.Train;
import com.mytrain.bean.User;
import com.mytrain.dao.TicketDao;
import com.mytrain.dao.TrainDao;
import com.mytrain.service.UserService;



public class ListViewAdapter extends BaseAdapter implements OnItemClickListener{
	private List<Order> list;
	private LayoutInflater mInflater;
	private Context context;
	public Boolean is_delete = false;
	public UserService us;
	public User user;
	public ListViewAdapter(List<Order> list1,Context context) {
		list = list1;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		us = new UserService(context);
		user = new User();
		SharedPreferences sp = context.getSharedPreferences("user", context.MODE_PRIVATE);
		user.setUsername(sp.getString("username", ""));
		user.setUserpwd(sp.getString("password", ""));
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_ticket, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_traintype = (TextView) convertView.findViewById(R.id.tv_trainType);
			//viewHolder.tv_ = (TextView) convertView.findViewById(R.id.tv_time);
			viewHolder.start_station = (TextView) convertView.findViewById(R.id.start_station);
			viewHolder.terminal_station = (TextView) convertView.findViewById(R.id.terminal_station);
			viewHolder.tv_startDate = (TextView) convertView.findViewById(R.id.tv_startDate);
			viewHolder.tv_startTime = (TextView) convertView.findViewById(R.id.tv_startTime);
			viewHolder.img_arrow = (ImageView) convertView.findViewById(R.id.img_arrow);
//			viewHolder.bt_changeticket = (Button) convertView.findViewById(R.id.bt_changeticket);
//			viewHolder.bt_returnticket = (Button) convertView.findViewById(R.id.bt_returnticket);
			convertView.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_traintype.setText(list.get(position).getTrainType());
		viewHolder.start_station.setText(list.get(position).getStart_station());
		viewHolder.terminal_station.setText(list.get(position).getTerminal_station());
		viewHolder.tv_startDate.setText(list.get(position).getStart_date());
		viewHolder.tv_startTime.setText(list.get(position).getStart_time());
		
//用按钮来实现
//		viewHolder.bt_changeticket.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context,TrainListActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putString("origin_station", list.get(position).getStart_station());
//				bundle.putString("terminal_station", list.get(position).getTerminal_station());
//				bundle.putInt("time", list.get(position).getTime());
//				intent.putExtras(bundle);
//				context.startActivity(intent);
//				TicketDao td = new TicketDao(context);
//				td.deleteTicket(String.valueOf(list.get(position).getTicketId()));
//				list.remove(position);
////				list = GetOrderList.getOrderList(context);
////				setOrderList(list);
//				notifyDataSetChanged();
//			}
//		});
//		viewHolder.bt_returnticket.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				AlertDialog.Builder b = new AlertDialog.Builder(context).setTitle("是否确认退票？")
//						.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface arg0, int arg1) {
//								// TODO Auto-generated method stub
//								TicketDao td = new TicketDao(context);
//								td.deleteTicket(String.valueOf(list.get(position).getTicketId()));
//								list.remove(position);
//								notifyDataSetChanged();
//								Toast.makeText(context, "退票成功！", Toast.LENGTH_LONG).show();
//							}
//						}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface arg0, int arg1) {
//								// TODO Auto-generated method stub
//								
//							}
//						});
//				b.create().show();
//			}
//		});
		
		
		return convertView;
	}
	
	public void setOrderList(List<Order> list){
		this.list = list;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		final int pos = position;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("请选择操作：").setPositiveButton("改签", new android.content.DialogInterface.OnClickListener(){
			//点击改签
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("是否要改签?").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener(){
					//确定改签
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context,TrainListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("origin_station", list.get(pos).getStart_station());
						bundle.putString("terminal_station", list.get(pos).getTerminal_station());
						bundle.putInt("time", list.get(pos).getTime());
						intent.putExtras(bundle);
						context.startActivity(intent);
						TicketDao td = new TicketDao(context);
						us.addMoney(user, list.get(pos).getTicket_price());
						td.deleteTicket(String.valueOf(list.get(pos).getTicketId()));
						is_delete = true;
						list.remove(pos);
//				list = GetOrderList.getOrderList(context);
//				setOrderList(list);
						notifyDataSetChanged();	//取消改签
					}}).setNegativeButton("取消", null).create().show();
				
			}
		}).setNegativeButton("退票", new android.content.DialogInterface.OnClickListener(){
			//点击退票
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AlertDialog.Builder b = new AlertDialog.Builder(context).setTitle("是否确认退票？")
						.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
							//确定退票
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								TicketDao td = new TicketDao(context);
								us = new UserService(context);
								us.addMoney(user, list.get(pos).getTicket_price());
								td.deleteTicket(String.valueOf(list.get(pos).getTicketId()));
								is_delete = true;
								list.remove(pos);
								notifyDataSetChanged();
								Toast.makeText(context, "退票成功！", Toast.LENGTH_LONG).show();
							}
						}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
							//取消退票
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								
							}
						});
				b.create().show();
			}	
			}).create().show();
		if (is_delete) {
			TrainDao td = new TrainDao(context);
			Train train = td.selectTrain("trainType = ?",
					new String[] { list.get(position).getTrainType() }, null,
					null, null, null).get(0);
			train.setSeat_remain_num(train.getSeat_remain_num()+1);
			td.updateTrain(train);
			is_delete = false;
		}
	}	 
}
