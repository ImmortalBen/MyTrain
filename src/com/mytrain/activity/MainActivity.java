package com.mytrain.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mytrain.R;
import com.mytrain.fragment.Fragment1;
import com.mytrain.fragment.Fragment2;
import com.mytrain.fragment.Fragment3;
import com.mytrain.fragment.Fragment3.LogOut;

public class MainActivity extends FragmentActivity implements OnClickListener,
		MoveToPage2, LogOut {

	private android.support.v4.view.ViewPager viewPage;
	private List<Fragment> list;
	private Fragment fr1;
	private Fragment fr2;
	private Fragment fr3;
	private FragmentStatePagerAdapter fpa;
	private ImageButton tab01;
	private ImageButton tab02;
	private ImageButton tab03;
	private FrameLayout frameLayout;
	private TextView tv_title;
	private Myadapter myadapter;
	private String username;
	private String password;
	public static boolean islogin = false;
	public static boolean isautologin = false;
	public static boolean isclick = false;
	public static LinearLayout lo_logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		tab01.setOnClickListener(this);
		tab02.setOnClickListener(this);
		frameLayout.setOnClickListener(this);
		tab03.setOnClickListener(this);
		viewPage.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				resetBgColor();
				switch (position) {
				case 0:
					// tab01.setBackgroundColor(getResources().getColor(R.color.orange));
					tv_title.setText("班次查询");
					break;
				case 1:
					// frameLayout.setBackgroundColor(getResources().getColor(R.color.orange));
					tv_title.setText("订票查询");
					break;
				default:
					// tab03.setBackgroundColor(getResources().getColor(R.color.orange));
					tv_title.setText("我的账户");
					break;
				}

			}

			// fragement滑动切换时bottom_layout的tab选项的颜色渐变
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				ArgbEvaluator evaluator = new ArgbEvaluator();
				int evaluate1 = (Integer) evaluator.evaluate(positionOffset,
						getResources().getColor(R.color.orange), getResources()
								.getColor(R.color.gainsboro));
				int evaluate2 = (Integer) evaluator.evaluate(positionOffset,
						getResources().getColor(R.color.gainsboro),
						getResources().getColor(R.color.orange));
				if (position == 0) {
					tab01.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tab01.setBackgroundColor(evaluate1);
					frameLayout.setBackgroundColor(evaluate2);
				} else if (position == 1) {
					frameLayout.setBackgroundColor(getResources().getColor(
							R.color.orange));
					frameLayout.setBackgroundColor(evaluate1);
					tab03.setBackgroundColor(evaluate2);
				} else if (position == 2) {
					tab03.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tab03.setBackgroundColor(evaluate1);
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void initView() {
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		username = sp.getString("username", "");
		// password = sp.getString("password", "");
		if ("".equals(username)) {
			islogin = false;
			isautologin = false;
		} else {
			islogin = true;
			isautologin = true;
		}
		viewPage = (ViewPager) findViewById(R.id.viewpage);
		tab01 = (ImageButton) findViewById(R.id.tab01);
		tab02 = (ImageButton) findViewById(R.id.tab02);
		tab03 = (ImageButton) findViewById(R.id.tab03);
		frameLayout = (FrameLayout) findViewById(R.id.layout2);
		tv_title = (TextView) findViewById(R.id.tv_title);
		list = new ArrayList<Fragment>();
		fr1 = new Fragment1();
		fr2 = new Fragment2();
		fr3 = new Fragment3();
		list.add(fr1);
		list.add(fr2);
		list.add(fr3);
		boolean ischeck = false;
		myadapter = new Myadapter(getSupportFragmentManager(), list, ischeck);
		viewPage.setAdapter(myadapter);
		// viewPage.setAdapter(fpa);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		setIconEnable(menu, true);
		// SubMenu sm= menu.addSubMenu("123");
		if(islogin){
			MenuItem item1 = menu.add(0, 1, 0, "注销");
			MenuItem item2 = menu.add(0, 2, 1, "退出");
		}
		else{
			MenuItem item1 = menu.add(0, 2, 0, "退出");		
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			// 注销
			logout();
			moveToPage2();
			break;
		case 2:
			Toast.makeText(MainActivity.this, "退出！", Toast.LENGTH_LONG).show();
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);

			// MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
			m.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab01:
			viewPage.setCurrentItem(0, true);
			break;
		case R.id.tab02:
		case R.id.layout2:
			viewPage.setCurrentItem(1, true);
			break;
		case R.id.tab03:
			viewPage.setCurrentItem(2, true);
			break;
		default:
			break;
		}
	}

	public void resetBgColor() {
		tab01.setBackgroundColor(getResources().getColor(R.color.gainsboro));
		// tab02.setBackgroundColor(getResources().getColor(R.color.gainsboro));
		frameLayout.setBackgroundColor(getResources().getColor(
				R.color.gainsboro));
		tab03.setBackgroundColor(getResources().getColor(R.color.gainsboro));
	}

	public void setUsrnandPwd(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected void onDestroy() {
		if (isautologin == false) {
			SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
			Editor edt = sp.edit();
			edt.remove("username");
			edt.remove("password");
			edt.commit();
		}
		super.onDestroy();
	}

	public void moveToPage2() {
		viewPage.setCurrentItem(1);
	}

	@Override
	public void movetopage2() {
		viewPage.setCurrentItem(1);

	}

	@Override
	public void logout() {
		Toast.makeText(MainActivity.this, "注销！", Toast.LENGTH_LONG).show();
		SharedPreferences sp = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove("username");
		editor.remove("password");
		editor.commit();
		isautologin = false;
		islogin = false;
		myadapter.removefragment4();
		// myadapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if(islogin){
			MenuItem item1 = menu.add(0, 1, 0, "注销");
			MenuItem item2 = menu.add(0, 2, 1, "退出");
		}
		else{
			MenuItem item1 = menu.add(0, 2, 0, "退出");		
		}
		return super.onPrepareOptionsMenu(menu);
	}

}
