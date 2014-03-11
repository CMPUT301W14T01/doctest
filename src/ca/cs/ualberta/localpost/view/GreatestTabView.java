package ca.cs.ualberta.localpost.view;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import ca.cs.ualberta.localpost.model.RootCommentModel;
 
public class GreatestTabView extends Fragment {
	private ListView listView;
	ArrayList<RootCommentModel> model = new ArrayList<RootCommentModel>();
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.tab, container, false);
        
		listView = (ListView) rootView.findViewById(R.id.commentList);
		CommentListAdapter adapter = new CommentListAdapter(getActivity(), R.id.custom_adapter, model);
		listView.setAdapter(adapter);
		
		//Clicking on a list item will take us to the replies(child comments)
		listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	//Intent myIntent = new Intent(getActivity(),MainActivity.class);
	        	//Pass the index so we can parse it in From Server.
	        	Log.e("Index",String.valueOf(id));
	        	Log.e("Position",String.valueOf(position));
	   	     	Toast.makeText(getActivity(), "Button is clicked", Toast.LENGTH_SHORT).show();
	   	     	//startActivity(myIntent);
	        }
	    });//End On click
         
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
}
