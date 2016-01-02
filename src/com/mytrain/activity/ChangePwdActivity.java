package com.mytrain.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
import com.mytrain.service.UserService;

public class ChangePwdActivity extends Activity implements OnClickListener,OnFocusChangeListener{
	private TextView tv_username;
	private TextView tv_title;
	private EditText edt_oldpassword;
	private EditText edt_newpassword;
	private EditText edt_confirmpassword;
	private ImageView img_back;
	private Button bt_submit;
	private String oldpassword;
	private String newpassword;
	private String confirmpassword;
	private String username;
	private String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword);
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		username = sp.getString("username", "");
		password = sp.getString("password", "");
		initChangePwdActivity();
		tv_title.setText("修改密码");
		bt_submit.setOnClickListener(this);
	}
	public void initChangePwdActivity(){
		username = getIntent().getExtras().getString("username");
		password = getIntent().getExtras().getString("password");
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_username.setText(username);
		edt_oldpassword = (EditText) findViewById(R.id.edt_oldpassword);
		edt_newpassword = (EditText) findViewById(R.id.edt_newpassword);
		edt_confirmpassword = (EditText) findViewById(R.id.edt_confirmpassword);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
		edt_oldpassword.setOnFocusChangeListener(this);
		edt_newpassword.setOnFocusChangeListener(this);
		edt_confirmpassword.setOnFocusChangeListener(this);
		img_back.setOnClickListener(this);
		bt_submit = (Button) findViewById(R.id.bt_submit);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_submit:
			oldpassword = edt_oldpassword.getText().toString();
			newpassword = edt_newpassword.getText().toString();
			confirmpassword = edt_confirmpassword.getText().toString();
			
			if(!newpassword.equals(confirmpassword)){
				edt_confirmpassword.setError("密码不一致");
			}
			else{
				UserService us = new UserService(this);
				User user = new User();
				user.setUsername(tv_username.getText().toString());
				user.setUserpwd(oldpassword);
				if(us.changePwd(user, newpassword)){
					Toast.makeText(this, "密码修改完成",Toast.LENGTH_SHORT).show();;
					SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
					Editor edt = sp.edit();
					edt.remove(password);
					edt.putString("password", newpassword);
					edt.commit();
					finish();
					overridePendingTransition(R.anim.in_from_left,
							R.anim.out_to_right);
				}else{
					Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
				}
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		finish();
		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.edt_oldpassword:
				//isOldPwdFocus(hasFocus);
			break;
		case R.id.edt_newpassword:
			isNewPwdFocus(hasFocus);
		default:
			isConfirmPwdFocus(hasFocus);
			break;
		}	
	}
//	public void isOldPwdFocus(boolean hasFocus){
//		if(hasFocus){
//			String oldpassword = edt_oldpassword.getText().toString();
//			if( oldpassword == null || "".equals(oldpassword)){
//				edt_oldpassword.setError("密码为空");
//			}else{}
//		}
//	}
	
	public void isNewPwdFocus(boolean hasFocus){
		String oldpassword = edt_oldpassword.getText().toString();
		if(hasFocus){
			if(!password.equals(oldpassword)){
				edt_oldpassword.requestFocus();
				edt_oldpassword.setError("密码不正确");
			}
		}
	}
	
	public void isConfirmPwdFocus(boolean hasFocus){
		String oldpassword = edt_oldpassword.getText().toString();
		String newpassword = edt_newpassword.getText().toString();
		if(hasFocus){
			if(!password.equals(oldpassword)){
				edt_oldpassword.requestFocus();
				edt_oldpassword.setError("密码不正确");
			}else if("".equals(newpassword) || newpassword == null){
				edt_newpassword.requestFocus();
			}
		}
	}

}
