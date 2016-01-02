package com.mytrain.activity;

import com.mytrain.R;
import com.mytrain.bean.Contact;
import com.mytrain.dao.ContactDao;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddContactActivity extends Activity {
	private EditText et_name;
	private EditText et_cardId;
	private TextView tv_title;
	private ImageView img_back;
	private Button bt_addContact;
	private int userId;
	private String name;
	private String cardId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontact);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("增加联系人");
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
		img_back.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.in_from_left,
						R.anim.out_to_right);
			}
		});
		SharedPreferences sp = this.getSharedPreferences("user", MODE_PRIVATE);
		userId = sp.getInt("userId", 0);
		addContact();
	}
	
	public void addContact(){
		et_name = (EditText)findViewById(R.id.et_name);
		et_cardId = (EditText)findViewById(R.id.et_cardId);
		bt_addContact = (Button)findViewById(R.id.bt_addcontact);
		
		bt_addContact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				name =et_name.getText().toString();
				cardId = et_cardId.getText().toString();
				if(TextUtils.isEmpty(name)){
					et_name.setError("联系人姓名不能为空！");
				}else if (TextUtils.isEmpty(cardId)){
					et_cardId.setError("身份证号码不能为空！");
				}else{
					Contact contact = new Contact();
					contact.setUserId(userId);
					contact.setName(name);
					contact.setCardId(cardId);
					ContactDao cd = new ContactDao(AddContactActivity.this);
					cd.addContact(contact);
					finish();
				}
			}
		});
	}
}
