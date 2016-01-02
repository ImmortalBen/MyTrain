package com.mytrain.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.mytrain.R;
import com.mytrain.activity.MainActivity;
import com.mytrain.activity.RegisterActivity;
import com.mytrain.bean.User;
import com.mytrain.dao.UserDao;
import com.mytrain.service.UserService;

public class Fragment2 extends Fragment implements OnClickListener,OnCheckedChangeListener{
	View view;
	private Button login_bt;
	private Button register_bt;
	private EditText username_edt;
	private EditText password_edt;
	private  Fragment2Interface f2i;
	private Switch sw;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			view = inflater.inflate(R.layout.fragment2, container,false);
			setHasOptionsMenu(true);
			initFragment2();
			login_bt.setOnClickListener(this);
			register_bt.setOnClickListener(this);
			sw.setOnCheckedChangeListener(this);
			return view;
	}
	public void initFragment2(){

		login_bt = (Button) view.findViewById(R.id.button_login);
		register_bt = (Button) view.findViewById(R.id.button_signup);

		sw = (Switch) view.findViewById(R.id.auto_login);
		login_bt = (Button) view.findViewById(R.id.button_login);
		register_bt = (Button) view.findViewById(R.id.button_signup);

		username_edt = (EditText) view.findViewById(R.id.editText_username);
		password_edt = (EditText) view.findViewById(R.id.editText_userpsw);	
	}

	public void setFragment2Interface(Fragment2Interface interface2){
		f2i = interface2;
	}
	public interface Fragment2Interface{
		public void removefragment2();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button_login:
		login();
		//f2i.removefragment2();
		//getActivity().invalidateOptionsMenu();
		break;
		case R.id.button_signup:
			Intent intent=new Intent();
			intent.setClass(getActivity(), RegisterActivity.class);
			startActivity(intent);
			break;
	}
}	
	public void login(){
		String username = username_edt.getText().toString();
		String password = password_edt.getText().toString();
		User user = new User();
		user.setUsername(username);
		user.setUserpwd(password);
		UserService us = new UserService(getActivity());
		if(us.checkUser(username, password)){
			UserDao ud = new UserDao(getActivity());
			user = ud.selectUser("username = ?", new String[]{username}, null, null, null, null);
			SharedPreferences sp = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
			Editor edt = sp.edit();
			edt.putString("username", username);
			edt.putString("password", password);
			edt.putInt("userId", user.getUserID());
			edt.commit();
			MainActivity.islogin = true;
			MainActivity.lo_logout.setVisibility(View.VISIBLE);
			
			
			f2i.removefragment2();
		}
		else{
			password_edt.setText("");
			Toast.makeText(getActivity(), "用户名或者密码错误", Toast.LENGTH_SHORT).show();		
		}
		
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(isChecked){
			MainActivity.isautologin = true;
		}
		else{
			MainActivity.isautologin = false;
		}
	}

}
