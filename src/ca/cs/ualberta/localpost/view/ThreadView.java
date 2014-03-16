package ca.cs.ualberta.localpost.view;

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

		function("Parent 1", 0);
		function("Child 1", 1);
		function("Child 2", 2);
		function("Child 3", 3);
		function("Parent 1", 0);
		function("Parent 1", 0);
		function("Parent 1", 0);
		function("Parent 1", 0);
		function("Parent 1", 0);
		function("Parent 1", 0);
		function("Parent 1", 0);
		function("Parent 1", 0);

		// commentText = (TextView) view.findViewById(R.id.commentTitle);
		// turnipText = (TextView) view.findViewById(R.id.turnip);
		// posterText = (TextView) view.findViewById(R.id.posterName);
		// geoText = (TextView) view.findViewById(R.id.geolocation);
	}

	public void function(String text, int level) {
		TableRow row = (TableRow) LayoutInflater.from(this)
				.inflate(R.layout.row, null);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		if (level == 1)
			params.setMargins(20, 0, 0, 0);
		else if (level == 2)
			params.setMargins(40, 0, 0, 0);
		else if (level == 3)
			params.setMargins(60, 0, 0, 0);
		else if (level == 4)
			params.setMargins(80, 0, 0, 0);
		row.setLayoutParams(params);

		((TextView) row.findViewById(R.id.commentTitle)).setText(text);
		table.addView(row);
		row = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thread_view, menu);
		return true;
	}

}
