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

public class ChangeInformationActivity extends Activity {
	private TextView tv_username;
	private TextView tv_title;
	private EditText edt_email;
	private EditText edt_phone;
	private EditText edt_cardId;
	private ImageView img_back;
	private Button change;
	private String username;
	private String password;
	private User user;
	private UserService us;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changeinformation);
		initChangeInfoActivity();
		tv_title.setText("修改基本信息");
		us = new UserService(this);
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		username = sp.getString("username", "");
		password = sp.getString("password", "");
		user = new User();
		user.setUsername(username);
		user.setUserpwd(password);
		user = us.lookForInfo(user);
		tv_username.setText(user.getUsername());
		edt_email.setText(user.getEmail());
		edt_cardId.setText(user.getCardID());
		edt_phone.setText(user.getPhone());
		img_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.in_from_left,
						R.anim.out_to_right);
			}
		});
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				user.setEmail(edt_email.getText().toString());
				user.setCardID(edt_cardId.getText().toString());
				user.setPhone(edt_phone.getText().toString());
//				Log.i("tag", "1111");
				us.UpdateUser(user);
//				Log.i("tag", "2222");
				Toast.makeText(ChangeInformationActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
	public void initChangeInfoActivity(){
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_title = (TextView) findViewById(R.id.tv_title);
		edt_email = (EditText) findViewById(R.id.edt_email);
		edt_phone = (EditText) findViewById(R.id.edt_phone);
		edt_cardId = (EditText) findViewById(R.id.edt_cardId);
		change = (Button) findViewById(R.id.change);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
	}
}
