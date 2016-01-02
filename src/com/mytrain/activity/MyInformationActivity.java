package com.mytrain.activity;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytrain.R;
import com.mytrain.bean.User;
import com.mytrain.service.UserService;

public class MyInformationActivity extends Activity implements OnClickListener{
	private User user;
	private TextView tv_title;
	private TextView tv_username;
	private TextView tv_email;
	private TextView tv_cardId;
	private TextView tv_phone;
	private ImageView img_back;
	private TextView tv_money;
	private UserService us;
	//private Map<String,String> map = new HashMap<String,String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.i("tag", "3333333");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinformation);
		initInformation();
		tv_title.setText("我的基本信息");
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		user.setUsername(sp.getString("username", ""));
		user.setUserpwd(sp.getString("password", ""));
		us = new UserService(this);
		user = us.lookForInfo(user);
		tv_username.setText(user.getUsername());
		tv_email.setText(user.getEmail());
		tv_cardId.setText(user.getCardID());
		tv_phone.setText(user.getPhone());
		tv_money.setText(String.valueOf(user.getMoney())+"元");
		img_back.setOnClickListener(this);
	}
	
	public void initInformation(){
		user = new User();
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_email = (TextView) findViewById(R.id.tv_email);
		tv_cardId = (TextView) findViewById(R.id.tv_cardId);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_money = (TextView)findViewById(R.id.tv_money);
		tv_title = (TextView) findViewById(R.id.tv_title);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.in_from_left,
				R.anim.out_to_right);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		finish();
		return super.onKeyDown(keyCode, event);
	}
}
