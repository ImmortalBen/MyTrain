package com.mytrain.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mytrain.R;

public class AboutActivity extends Activity implements OnClickListener{
	private TextView tv_title;
	private TextView tv_version;
	private Button bt_update;
	private Button bt_aboutus;
	private ImageView img_back;
 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.about_mytrain);
	initView();
	tv_title.setText("关于MyTrain");
	tv_version.setText("MyTrain 1.0");
	bt_update.setOnClickListener(this);	
	bt_aboutus.setOnClickListener(this);
	img_back.setOnClickListener(this);
}

private void initView() {
	// TODO Auto-generated method stub
	tv_title = (TextView) findViewById(R.id.tv_title);
	tv_version = (TextView) findViewById(R.id.mytrain_version);
	bt_update = (Button) findViewById(R.id.bt_update);
	bt_aboutus = (Button) findViewById(R.id.bt_aboutus);
	img_back = (ImageView) findViewById(R.id.img_back);
	img_back.setVisibility(View.VISIBLE);
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.bt_update:
		Toast.makeText(this, "已经是最新版本！", Toast.LENGTH_LONG).show();
		break;
	case R.id.bt_aboutus :
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.img_admin);
		builder.setTitle("MyTrain开发小组");
		builder.setMessage("廖锦天、尹恺彬、庄智植、林泳豪、李永亮、陆耀聪");
		builder.setNeutralButton("点赞", mylistener());
		builder.create().show();
		break;
	case R.id.img_back:
		finish();
		overridePendingTransition(R.anim.in_from_left,
				R.anim.out_to_right);
	    break;
	}
	
}

private android.content.DialogInterface.OnClickListener mylistener() {
	// TODO Auto-generated method stub
	Toast.makeText(this, "谢谢支持！你的点赞是我们最大的动力！", Toast.LENGTH_LONG).show();
	return null;
}


}
