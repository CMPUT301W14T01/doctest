/**
 * The MIT License (MIT)
 * Copyright (c) 2014 Timotei Albu, David Chau-Tran, Alain Clark, Shawn Anderson, Mickael Zerihoun
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 *	this software and associated documentation files (the "Software"), to deal in
 *	the Software without restriction, including without limitation the rights to
 *	use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so,
 *	subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *  
 */

package ca.cs.ualberta.localpost.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import ca.cs.ualberta.localpost.controller.CommentListAdapter;
import ca.cs.ualberta.localpost.controller.ConnectivityCheck;
import ca.cs.ualberta.localpost.controller.ElasticSearchOperations;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.CommentModel;

import com.google.gson.Gson;

/**
 * This View will show all the comments that are in the area around the user.
 * 
 * @author Team 01
 * 
 */
public class FreshestTabView extends Fragment {
	private ListView listView;
	private CommentListAdapter adapter;
	ArrayList<CommentModel> model;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflates the view with a list view. Also populates listview
		View rootView = inflater.inflate(R.layout.tab, container, false);
		
		//ElasticSearchOperations task = new ElasticSearchOperations();
		// task.execute(4,null,null);
		//
		listView = (ListView) rootView.findViewById(R.id.commentList);

		return rootView;
	}

	/**
	 * Overrides onResume. This will update the listView with data that has been
	 * added.
	 */
	// @Override
	public void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable() {
			//ArrayList<CommentModel> model = new ArrayList<CommentModel>();
			public void run() {
				ConnectivityCheck conn = new ConnectivityCheck(getActivity());
				if (conn.isConnectingToInternet()) {
					
					try {
						model = new ElasticSearchOperations().execute(3, null,
								null, null).get();
						Serialize.check_if_exist("cachedrootcomment.json", getActivity());
						for (CommentModel c : model) {
							Serialize.SaveComment(c, getActivity(), null);
							Serialize.update(c, getActivity(), "favoritecomment.json");
							Serialize.update(c, getActivity(), "historycomment.json");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					
					model = Serialize.loadFromFile("cachedrootcomment.json", getActivity());
				}
				
				adapter = new CommentListAdapter(
						getActivity(), R.id.custom_adapter, model);
				listView.setAdapter(adapter);
				registerForContextMenu(listView);
				
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
						Gson gson = new Gson();
						String modelString = gson.toJson(model.get(position));
						Intent myIntent = new Intent(getActivity(),
								ThreadView.class);
						myIntent.putExtra("CommentModel", modelString);
						
						startActivity(myIntent);
						
					}
				});
			}
		}, 750);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, Menu.FIRST, 0, "UpRad");
		menu.add(0, Menu.FIRST + 1, 0, "DownRad");
		menu.add(0, Menu.FIRST + 2, 0, "Favorite");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Get item list index
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case Menu.FIRST:
			Log.e("Increment", "UpRad");
			Toast.makeText(getActivity(), "UpRad", Toast.LENGTH_SHORT).show();

			return true;
		case Menu.FIRST + 1:
			Log.e("Decrement", "DownRad");
			Toast.makeText(getActivity(), "DownRad", Toast.LENGTH_SHORT).show();
			return true;
		case Menu.FIRST + 2:
			// TODO Check if that comment already exists favorites.json
			int index = (int) info.id;
			Toast.makeText(getActivity(), "Comment has been Favorited",
					Toast.LENGTH_SHORT).show();
			Serialize.SaveComment(model.get(index), getActivity(), "favourite");
			return true;
		}
		return super.onContextItemSelected(item);
	}
}
