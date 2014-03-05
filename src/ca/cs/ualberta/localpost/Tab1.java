package ca.cs.ualberta.localpost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Tab1 extends Fragment {
	
	public View OnCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.activity_tab1, container,false);
		return rootView;
	}

}
