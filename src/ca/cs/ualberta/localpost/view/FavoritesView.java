
package ca.cs.ualberta.localpost.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ca.cs.ualberta.localpost.controller.CommentListAdapter;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.CommentModel;

import com.google.gson.Gson;

/**
 * View that Displays all of the users favourited comments
 * @author Team 01
 *
 */
public class FavoritesView extends Activity {
	private ListView listView;
	private CommentListAdapter adapter;
	private ArrayList<CommentModel> model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorites_view);

		listView = (ListView) findViewById(R.id.favoritesList);
		Log.e("Favorites", "Starts the loading");
		model = Serialize.loadFromFile("favoritecomment.json", this);
		adapter = new CommentListAdapter(FavoritesView.this, R.id.custom_adapter,model);
		listView.setAdapter(adapter);
	
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//Toast.makeText(getApplicationContext(),"ThreadView Under Construction", Toast.LENGTH_SHORT).show();
				Gson gson = new Gson();
				String modelString = gson.toJson(model.get(position));
				Intent myIntent = new Intent(getApplicationContext(),ThreadView.class);
				myIntent.putExtra("CommentModel", modelString);
				
				startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorites_view, menu);
		return true;
	}
}
