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
import ca.cs.ualberta.localpost.controller.BrowseFreshestComments;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.RootCommentModel;

public class FreshestTabView extends Fragment {
	private ListView listView;
	private CommentListAdapter adapter;
	ArrayList<RootCommentModel> model;  

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab, container, false);
		
		model = Serialize.loadFromFile("rootcomment.json", getActivity());

		listView = (ListView) rootView.findViewById(R.id.commentList);
		registerForContextMenu(listView);

		adapter = new CommentListAdapter(getActivity(), R.id.custom_adapter,model);

		listView.setAdapter(adapter);

		// Clicking on a list item will take us to the replies(child comments)
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(getActivity(),"ThreadView Under Construction", Toast.LENGTH_SHORT).show();
				//TODO pass intent to ThreadView
				// Intent myIntent = new Intent(getActivity(),MainActivity.class);
				// startActivity(myIntent);
			}
		});
		return rootView;
	}
	@Override
	public void onResume() {
		super.onResume();
		model = Serialize.loadFromFile("rootcomment.json", getActivity());
		adapter = new CommentListAdapter(getActivity(), R.id.custom_adapter,model);
		listView.setAdapter(adapter);
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
		//Get item list index
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case Menu.FIRST:// Rename
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
			Serialize.SaveInFile(model.get(index), getActivity());
			return true;
		}
		return super.onContextItemSelected(item);
	}

	// Use updateCommentArray() on like onResume or something or on Refresh or
	// something
	public void updateCommentArray() {
		BrowseFreshestComments browse = new BrowseFreshestComments();
		this.model = browse.passSortedRootComments();
	}
}
