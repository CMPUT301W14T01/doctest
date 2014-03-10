package ca.cs.ualberta.localpost.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	public TabsPagerAdapter(FragmentManager frag){
		super(frag);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch(arg0){
		case 0:
			return new FreshestTabView();
		case 1:
			return new GreatestTabView();
		case 2:
			return new LastestTabView();
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
