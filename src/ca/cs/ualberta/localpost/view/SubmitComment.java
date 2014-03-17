package ca.cs.ualberta.localpost.view;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;

public class SubmitComment extends Activity {
	private String intentUsername;
	private StandardUserModel user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_comment);

		// Get the username from intent
		Bundle extras = getIntent().getExtras();
		intentUsername = extras.getString("username");
		Log.e("IntentUsername",intentUsername);
	}

	public void add_root(View view) throws InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		user = new StandardUserModel();
		user.setUsername(intentUsername);

		EditText titleView = (EditText) findViewById(R.id.commentTitle);
		String title = titleView.getText().toString();

		EditText contentView = (EditText) findViewById(R.id.textBody);
		String content = contentView.getText().toString();

		RootCommentModel new_root = new RootCommentModel(content, title);
		new_root.setAuthor(user.getUsername());

		Serialize.SaveInFile(new_root, SubmitComment.this);
		//Serialize.loadFromFile(SubmitComment.this);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_comment, menu);
		return true;
	}
}
