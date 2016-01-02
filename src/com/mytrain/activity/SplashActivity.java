package com.mytrain.activity;

import com.mytrain.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//全屏
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	//无标题
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.splash_activity);
	new Thread(){
		public void run(){
			try{
				Thread.sleep(1500);
		   	    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
	    		startActivity(intent);
	    		SplashActivity.this.finish();
	    		//overridePendingTransition(R.anim.zoom_out, R.anim.zoom_in);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}.start();
}
}
