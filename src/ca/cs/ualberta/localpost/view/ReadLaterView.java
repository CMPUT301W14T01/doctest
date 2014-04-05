package ca.cs.ualberta.localpost.view;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;
import ca.cs.ualberta.localpost.controller.CommentListAdapter;
import ca.cs.ualberta.localpost.model.CommentModel;

public class ReadLaterView extends Activity {
	private ListView listView;
	private CommentListAdapter adapter;
	private ArrayList<CommentModel> model =  new ArrayList<CommentModel>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_later);
		
		listView = (ListView) findViewById(R.id.readLaterList);
		adapter = new CommentListAdapter(this,R.id.custom_adapter, model);
		
		listView.setAdapter(adapter);
		Toast.makeText(this, "ReadLater", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_later_view, menu);
		return true;
	}

}
