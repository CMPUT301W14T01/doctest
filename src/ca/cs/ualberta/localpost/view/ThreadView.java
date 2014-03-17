package ca.cs.ualberta.localpost.view;

import ca.cs.ualberta.localpost.model.CommentModel;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class ThreadView extends Activity {
	private TableLayout table;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		table = (TableLayout) findViewById(R.id.table_layout);

		threadExpand("Parent 1", 0);
		threadExpand("Child 1", 1);
		threadExpand("Child 2", 2);
		threadExpand("Child 3", 3);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);

		// commentText = (TextView) view.findViewById(R.id.commentTitle);
		// turnipText = (TextView) view.findViewById(R.id.turnip);
		// posterText = (TextView) view.findViewById(R.id.posterName);
		// geoText = (TextView) view.findViewById(R.id.geolocation);
	}

	
	// @TODO TB rebuilt!
	public void threadExpand(String text, int level) {
		final int marginBase = 20;
		final int depthTolerance = 5;
		
		TableRow row = (TableRow) LayoutInflater.from(this)
				.inflate(R.layout.row, null);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		
		if(level < depthTolerance){
			params.setMargins(level*marginBase, 0, 0, 0);
		}
		row.setLayoutParams(params);

		((TextView) row.findViewById(R.id.commentTitle)).setText(text);
		table.addView(row);
		row = null;
	}
	//-------------
	
	public void threadExpand(CommentModel comment, int level) {
//		final int marginBase = 20;
//		final int depthTolerance = 5;
//		
//		TableRow row = (TableRow) LayoutInflater.from(this)
//				.inflate(R.layout.row, null);
//		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT);
//		
//		if(level < depthTolerance){
//			params.setMargins(level*marginBase, 0, 0, 0);
//		}
//		row.setLayoutParams(params);
//
//		((TextView) row.findViewById(R.id.commentTitle)).setText(comment.getContent());
//		table.addView(row);
//		row = null;
		
		//Fix Later
		threadExpand(comment.getContent(),level);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thread_view, menu);
		return true;
	}

}
