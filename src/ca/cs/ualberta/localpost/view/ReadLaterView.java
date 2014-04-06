package ca.cs.ualberta.localpost.view;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import ca.cs.ualberta.localpost.controller.CommentListAdapter;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.CommentModel;

public class ReadLaterView extends Activity {
	private ListView listView;
	private CommentListAdapter adapter;
	private ArrayList<CommentModel> model =  new ArrayList<CommentModel>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_later);
		
		model = Serialize.loadFromFile("readlater.json", this);
		listView = (ListView) findViewById(R.id.readLaterList);
		adapter = new CommentListAdapter(this,R.id.custom_adapter, model);
		
		listView.setAdapter(adapter);
		Toast.makeText(this, "ReadLater", Toast.LENGTH_SHORT).show();
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

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
		getMenuInflater().inflate(R.menu.read_later_view, menu);
		return true;
	}

}
