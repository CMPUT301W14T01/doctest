package ca.cs.ualberta.localpost.view;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SubmitComment extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_comment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_comment, menu);
		return true;
	}

}
