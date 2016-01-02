package com.mytrain.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mytrain.R;
import com.mytrain.activity.MainActivity;
import com.mytrain.activity.MoveToPage2;
import com.mytrain.activity.TrainListActivity;
import com.mytrain.bean.Train;
import com.mytrain.bean.History;
import com.mytrain.dao.HistoryDao;
import com.mytrain.dao.TicketDao;

public class Fragment1 extends Fragment {
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sp = getActivity().getSharedPreferences("user",
				MainActivity.MODE_PRIVATE);
		MainActivity.isclick = sp.getBoolean("isClick", false);
		if (MainActivity.isclick) {
			refreshListView();
			movetopage2();
			Editor edt = sp.edit();
			edt.remove("isClick");
			edt.commit();
		}
		if(is_search){
			refreshListView();
			//Toast.makeText(getActivity(), "更新历史", Toast.LENGTH_LONG).show();
			is_search = false;
		}
	}

	View view;
	private Spinner sp_start;
	private Spinner sp_terminal;
	private String origin_station;
	private String terminal_station;
	private ArrayAdapter<CharSequence> adapter;
	private Time sendTime;
	private long timeMillis;
	private Button bt_search;
	private ImageView bt_swap;
	private EditText et_date;
	private TextView tv_clear;
	private Calendar calendar;
	private SharedPreferences sp;
	private List<Map<String, Object>> historylists;
	private List<History> historyList = new ArrayList<History>();
	private ListView listview;
	private SimpleAdapter simpleadapter;
	private int pos_start, pos_terminal;
	private Boolean is_search = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment1, container, false);
		initFragment1();
		initListView();
		return view;
	}

	public void initFragment1() {
		sp_start = (Spinner) view.findViewById(R.id.start_station);
		sp_terminal = (Spinner) view.findViewById(R.id.terminal_station);
		bt_swap = (ImageView) view.findViewById(R.id.bt_swap);
		// 将可选内容与ArrayAdapter连接起来
		adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.stations, R.layout.myspinner);

		// 设置下拉列表的风格
		// adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		adapter.setDropDownViewResource(R.layout.myspinner);
		// 将adapter 添加到spinner中
		sp_start.setAdapter(adapter);
		sp_terminal.setAdapter(adapter);
		// 添加事件Spinner事件监听
		sp_start.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				origin_station = adapter.getItem(pos).toString();
				pos_start = pos;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		sp_terminal.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				terminal_station = adapter.getItem(pos).toString();
				pos_terminal = pos;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		// 设置默认值
		sp_start.setVisibility(View.VISIBLE);
		sp_terminal.setVisibility(View.VISIBLE);
		bt_swap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				origin_station = adapter.getItem(pos_terminal).toString();
				terminal_station = adapter.getItem(pos_start).toString();
				sp_terminal.setSelection(pos_start);
				sp_start.setSelection(pos_terminal);
			}
		});

		// 绑定设置日期和查询的Listener
		sendTime = new Time();
		et_date = (EditText) view.findViewById(R.id.et_date);
		et_date.setOnClickListener(new myListener1());
		Date date = new Date();
		calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		String today = new String(calendar.get(Calendar.YEAR) + "年"
				+ (int) (calendar.get(Calendar.MONTH) + 1) + "月"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "日");
		today = today.concat(getWeekdayString(true));
		et_date.setText(today);
		bt_search = (Button) view.findViewById(R.id.bt_search);
		bt_search.setOnClickListener(new myListener2());

	}

	class myListener1 implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			new DatePickerDialog(getActivity(),
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							// TODO Auto-generated method stub

							sendTime.set(0,0,0,dayOfMonth, month, year);
							timeMillis = sendTime.toMillis(true);
							sendTime.set(timeMillis);
							String today = year + "年" + (month + 1) + "月"
									+ dayOfMonth + "日";
							today = today.concat(getWeekdayString(false));
							et_date.setText(today);

						}
					}, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH)).show();
		}

	}

	class myListener2 implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			is_search = true;
			History history = new History();
			history.setSearch_time();
			history.setOrigin_station(origin_station);
			history.setTerminal_station(terminal_station);
			HistoryDao hdao = new HistoryDao(getActivity());
			hdao.addHistory(history);
			Intent intent = new Intent(getActivity(), TrainListActivity.class);
			if (timeMillis <= 1000) {
				sendTime.setToNow();
				sendTime.set(0,0,0,sendTime.monthDay, sendTime.month, sendTime.year);
				timeMillis = sendTime.toMillis(true);
			}
			timeMillis /= 1000;
			int timesec = (int) timeMillis;
			Bundle bundle = new Bundle();
			bundle.putString("origin_station", origin_station);
			bundle.putString("terminal_station", terminal_station);
			bundle.putInt("time", timesec);
			intent.putExtras(bundle);
			getActivity().startActivity(intent);
			getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

		}

	}

	public void initListView() {
		listview = (ListView)view.findViewById(R.id.lv_history);
		HistoryDao hdao = new HistoryDao(getActivity());
		historyList = hdao.selectHistory(null, new String[] {}, null, null,
				"search_time desc", null);
		historylists = new ArrayList<Map<String, Object>>();
		int size = historyList.size()<5?historyList.size():5;
		for (int i = 0; i < size; i++) {
			Map<String, Object> Itemlist = new HashMap<String, Object>();
			History history = historyList.get(i);
			Itemlist.put("origin", history.getOrigin_station());
			Itemlist.put("terminal", history.getTerminal_station());
			historylists.add(Itemlist);
		}
		// listview adapter
		simpleadapter = new SimpleAdapter(getActivity(), historylists, R.layout.item_history,
				new String[] { "origin", "terminal"}, new int[] {
						R.id.tv_originStation,
						R.id.tv_terminalStation});
		listview.setAdapter(simpleadapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				origin_station = historyList.get(position).getOrigin_station();
				terminal_station = historyList.get(position).getTerminal_station();
				int origin_pos = adapter.getPosition(origin_station);
				int terminal_pos = adapter.getPosition(terminal_station);
				sp_start.setSelection(origin_pos);
				sp_terminal.setSelection(terminal_pos);
			}
			
		});
		tv_clear = (TextView) view.findViewById(R.id.tv_clearHistory);
		tv_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				HistoryDao hd = new HistoryDao(getActivity());
				hd.deleteHistory();
				refreshListView();
				Toast.makeText(getActivity(), "历史记录已清除", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void refreshListView(){
		HistoryDao hdao = new HistoryDao(getActivity());
		historyList = hdao.selectHistory(null, new String[] {}, null, null,
				"search_time desc", null);
		historylists = new ArrayList<Map<String, Object>>();
		int size = historyList.size()<5?historyList.size():5;
		for (int i = 0; i < size; i++) {
			Map<String, Object> Itemlist = new HashMap<String, Object>();
			History history = historyList.get(i);
			Itemlist.put("origin", history.getOrigin_station());
			Itemlist.put("terminal", history.getTerminal_station());
			historylists.add(Itemlist);
		}
		simpleadapter = new SimpleAdapter(getActivity(), historylists, R.layout.item_history,
				new String[] { "origin", "terminal"}, new int[] {
						R.id.tv_originStation,
						R.id.tv_terminalStation});
		listview.setAdapter(simpleadapter);
		simpleadapter.notifyDataSetChanged();
	}
	
	public String getWeekdayString(Boolean date) {
		int day;
		if (date) {
			day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		} else {
			day = sendTime.weekDay;
		}

		String weekday = new String();
		switch (day) {
		case 1:
			weekday = "星期一";
			break;
		case 2:
			weekday = "星期二";
			break;
		case 3:
			weekday = "星期三";
			break;
		case 4:
			weekday = "星期四";
			break;
		case 5:
			weekday = "星期五";
			break;
		case 6:
			weekday = "星期六";
			break;
		case 0:
			weekday = "星期日";
			break;
		default:
			weekday = String.valueOf(day);
			break;
		}
		return weekday;
	}

	public void movetopage2() {
		MoveToPage2 mtp2 = (MoveToPage2) getActivity();
		mtp2.movetopage2();
	}

}
