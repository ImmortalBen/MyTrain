package com.mytrain.activity;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.mytrain.fragment.Fragment2;
import com.mytrain.fragment.Fragment2.Fragment2Interface;
import com.mytrain.fragment.Fragment4;
import com.mytrain.fragment.Fragment4.Fragment4Interface;

public class Myadapter extends FragmentStatePagerAdapter implements Fragment2Interface,Fragment4Interface{
	private List<Fragment> listFragment;
	private Fragment4 fr4;
	private boolean ischeck;
	private FragmentManager fm;
	private Fragment win1;
	
	public Myadapter(FragmentManager fm,List<Fragment> list,boolean ischeck) {
		super(fm);
		this.fm = fm;
		listFragment = list;
		this.ischeck = ischeck;
	}
	@Override
	public int getItemPosition(Object object) {
		if ((object instanceof Fragment2
				&& win1 instanceof Fragment4) || (object instanceof Fragment4 && win1 instanceof Fragment2) )
			return POSITION_NONE;
		return POSITION_UNCHANGED;
	}
	@Override
	public Fragment getItem(int position) {
		if (position == 1) {
			if (!MainActivity.isautologin && !MainActivity.islogin) {
				win1 = new Fragment2();
				((Fragment2) win1).setFragment2Interface(this);
			}
			else{
				win1 = new Fragment4();
			}
			return win1;		
		}
		else
			return listFragment.get(position);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFragment.size();
	}

	@Override
	public void removefragment2() {
		fm.beginTransaction()
		.remove(win1).commit();
		Fragment4 fr4 = new Fragment4();
		win1 = fr4;
		notifyDataSetChanged();	
	}
	@Override
	public void removefragment4() {
		if(win1 instanceof Fragment4){
			((Fragment4) win1).setFragment4Interface(this);
		}
		fm.beginTransaction()
		.remove(win1).commit();
		Fragment2 fr2 = new Fragment2();
		win1 = fr2;
		notifyDataSetChanged();	
	}

}
