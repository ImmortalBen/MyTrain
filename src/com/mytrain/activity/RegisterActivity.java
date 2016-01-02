package com.mytrain.activity;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mytrain.R;
import com.mytrain.bean.User;
import com.mytrain.dao.UserDao;
import com.mytrain.service.UserService;

public class RegisterActivity extends Activity implements OnClickListener,OnFocusChangeListener{
	private TextView tv_title;
	private ImageView img_back;
	private EditText signup_username;
	private EditText signup_pwd;
	private EditText signup_email;
	private EditText signup_phone;
	private EditText signup_cardid;
	private Button sign_up ;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_signup);
	initview();
	signup_username.setOnFocusChangeListener(this);
	signup_pwd.setOnFocusChangeListener(this);
	signup_email.setOnFocusChangeListener(this);
	signup_phone.setOnFocusChangeListener(this);
	signup_cardid.setOnFocusChangeListener(this);
	sign_up.setOnClickListener(this);
	img_back.setOnClickListener(this);
}
private void initview() {
	// TODO Auto-generated method stub
	tv_title=(TextView) findViewById(R.id.tv_title);
	signup_username=(EditText) findViewById(R.id.signup_username);
	signup_pwd=(EditText) findViewById(R.id.signup_userpwd);
	signup_email=(EditText) findViewById(R.id.signup_email);
	signup_phone=(EditText) findViewById(R.id.signup_phone);
	signup_cardid=(EditText) findViewById(R.id.signup_cardid);
	sign_up=(Button) findViewById(R.id.sign_up);
	tv_title.setText(R.string.sign_up);
	img_back = (ImageView) findViewById(R.id.img_back);
	img_back.setVisibility(View.VISIBLE);
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.sign_up:
		String username= signup_username.getText().toString().trim();
		String userpwd= signup_pwd.getText().toString().trim();
		String email= signup_email.getText().toString().trim();
		String phone= signup_phone.getText().toString().trim();
		String cardid= signup_cardid.getText().toString().trim();		
		if(TextUtils.isEmpty(userpwd)){
			signup_pwd.setError("密码不能为空！");
			signup_pwd.requestFocus();
			}else
		if(TextUtils.isEmpty(email)){
			signup_email.setError("电子邮箱不能为空！");
			signup_email.requestFocus();
			}else
		if(TextUtils.isEmpty(phone)){
			signup_phone.setError("联系电话不能为空！");
			signup_phone.requestFocus();
			}else
		if(TextUtils.isEmpty(cardid)){
			signup_cardid.setError("身份证号码不能为空！");
			signup_cardid.requestFocus();
			}else{
			UserDao us =new UserDao(this);
			User user = new User();
			user.setUsername(username);
			user.setUserpwd(userpwd);
			user.setEmail(email);
			user.setPhone(phone);
			user.setCardID(cardid);
			us.addUser(user);
//			Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();
	        Toast.makeText(this, "注册成功！", Toast.LENGTH_LONG).show();
	        finish();
			}
		break;
	case R.id.img_back:
		finish();
		overridePendingTransition(R.anim.in_from_left,
				R.anim.out_to_right);
		break;
	}
	
}
@Override
public void onFocusChange(View v, boolean arg1) {
	// TODO Auto-generated method stub
	String username= signup_username.getText().toString().trim();
	switch (v.getId()) {
	case R.id.signup_username:	
		break;
	case R.id.signup_userpwd:
	case R.id.signup_email:		
	case R.id.signup_phone:				
	case R.id.signup_cardid:
		UserService Us = new UserService(this);
		if(TextUtils.isEmpty(username)){
			signup_username.setError("用户名不能为空！");
		    signup_username.requestFocus();
		}else
			if(Us.checkUsername(username)){
				Toast.makeText(this, "抱歉！"+username+"已经被注册了╭(╯ε╰)╮", Toast.LENGTH_LONG).show();
//				signup_username.setError("用户名不能为空！");
			    signup_username.requestFocus();
			}					
			
		break;


	}
}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
	finish();
	return super.onKeyDown(keyCode, event);
}
}
