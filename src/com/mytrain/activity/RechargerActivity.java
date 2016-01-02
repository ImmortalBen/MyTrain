package com.mytrain.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mytrain.R;
import com.mytrain.bean.User;
import com.mytrain.service.UserService;

public class RechargerActivity extends Activity {
	private TextView tv_title; 
	private EditText edt_money;
	private ImageView img_back;
	private Button bt_recharge;
	private UserService us;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recharge);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("充值");
		us = new UserService(this);
		user = new User();
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		user.setUsername(sp.getString("username", ""));
		user.setUserpwd(sp.getString("password", ""));
		edt_money = (EditText) findViewById(R.id.edt_money);
		bt_recharge = (Button) findViewById(R.id.bt_recharge);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
		bt_recharge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int money = Integer.valueOf(edt_money.getText().toString());
				us.addMoney(user, money);
				Toast.makeText(RechargerActivity.this, "已充值"+money+"元", Toast.LENGTH_LONG).show();
				finish();
			}
		});
		img_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.in_from_left,
						R.anim.out_to_right);
			}
		});
	}
}
