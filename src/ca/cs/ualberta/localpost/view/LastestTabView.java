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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import ca.cs.ualberta.localpost.controller.CommentListAdapter;
import ca.cs.ualberta.localpost.controller.ElasticSearchOperations;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.controller.SortFreshestComments;
import ca.cs.ualberta.localpost.controller.SortLatestComments;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;


/**
 * Displays the comments from most recent post date
 * 
 * @author Team 01
 *
 */
public class LastestTabView extends Fragment {
	private ListView listView;
	ArrayList<CommentModel> model;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		//Inflates the view with a list view. Also populates listview
		View rootView = inflater.inflate(R.layout.tab, container,false);
		ElasticSearchOperations task = new ElasticSearchOperations();

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

		CommentListAdapter adapter = new CommentListAdapter(getActivity(), R.id.custom_adapter,model);

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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0,Menu.FIRST,0,"UpTurnip");
		menu.add(0,Menu.FIRST+1,0,"DownTurnip");
		menu.add(0,Menu.FIRST+2,0,"Favorite");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		//AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId())
		{
			case Menu.FIRST://Rename
				Log.e("Increment","UpTurnip");
	   	     	Toast.makeText(getActivity(), "UpTurnip", Toast.LENGTH_SHORT).show();
				return true;
			case Menu.FIRST+1:
				Log.e("Decrement","DownTurnip");
   	     		Toast.makeText(getActivity(), "DownTurnip is clicked", Toast.LENGTH_SHORT).show();
				return true;
			case Menu.FIRST+2:
				Log.e("Fav","Favorite");
				Toast.makeText(getActivity(), "Favorite Clicked", Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onContextItemSelected(item);
	}
//	public void updateCommentArray(){
//		BrowseLatestComments browse = new BrowseLatestComments();
//		this.model = browse.passSortedRootComments();
//	}
}
