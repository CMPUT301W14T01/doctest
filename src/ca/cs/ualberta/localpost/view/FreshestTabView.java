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

import android.os.Bundle;
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
import ca.cs.ualberta.localpost.controller.ElasticSearchOperations;
import ca.cs.ualberta.localpost.controller.SortFreshestComments;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.CommentModel;

/**
 * This View will show all the comments that are
 * in the area around the user.
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
		
		//Inflates the view with a list view. Also populates listview
		View rootView = inflater.inflate(R.layout.tab, container, false);
		ElasticSearchOperations task = new ElasticSearchOperations();
		//task.execute(4,null,null);
		try {
			model = task.execute(3,null,null).get();
			for (CommentModel c : model) {
			Serialize.SaveComment(c, getActivity());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		SortFreshestComments sort = new SortFreshestComments();
		model = sort.sortComments(model);
//		model = Serialize.loadFromFile("rootcomment.json", getActivity());
//
		listView = (ListView) rootView.findViewById(R.id.commentList);
		
		registerForContextMenu(listView);

		adapter = new CommentListAdapter(getActivity(), R.id.custom_adapter,model);

		listView.setAdapter(adapter);

		// Clicking on a list item will take us to the replies(child comments)
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(getActivity(),"ThreadView Under Construction", Toast.LENGTH_SHORT).show();
//				 Intent myIntent = new Intent(getActivity(),ThreadView.class);
//				 startActivity(myIntent);
			}
		});
		return rootView;
	}
	
	/**
	 * Overrides onResume. This will update the listView with data 
	 * that has been added.
	 */
//	@Override
//	public void onResume() {
//		super.onResume();
//		model = Serialize.loadFromFile("rootcomment.json", getActivity());
//		adapter = new CommentListAdapter(getActivity(), R.id.custom_adapter,model);
//		listView.setAdapter(adapter);
//	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, Menu.FIRST, 0, "UpRad");
		menu.add(0, Menu.FIRST + 1, 0, "DownRad");
		menu.add(0, Menu.FIRST + 2, 0, "Favorite");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//Get item list index
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
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
			//TODO Check if that comment already exists favorites.json
			int index = (int) info.id;
			Toast.makeText(getActivity(), "Comment has been Favorited",Toast.LENGTH_SHORT).show();
			Serialize.SaveComment(model.get(index), getActivity());
			return true;
		}
		return super.onContextItemSelected(item);
	}

//	// Use updateCommentArray() on like onResume or something or on Refresh or
//	// something
//	public void updateCommentArray() {
//		BrowseFreshestComments browse = new BrowseFreshestComments();
//		this.model = browse.passSortedRootComments();
//	}
}
