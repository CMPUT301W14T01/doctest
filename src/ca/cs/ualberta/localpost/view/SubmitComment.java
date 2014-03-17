package ca.cs.ualberta.localpost.view;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;

/**
 * This activity allows the user to enter and submit a new comment
 * @author Team 01
 *
 */
public class SubmitComment extends Activity {
	/**Grabs the username from an intent */
	private String intentUsername;
	
	/**Creates a new StandardUserModel object */
	private StandardUserModel user;
	
	/**Button used to submit the comment */
	private Button postButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_comment);

		/**Grabs uername from MainActivity via intent */
		Bundle extras = getIntent().getExtras();
		intentUsername = extras.getString("username");
		
		/**SetText for the button */
		postButton = (Button) findViewById(R.id.postButton);
		postButton.setText("Submit Comment");
	}

	/**Adds a new root. Puts all the input data into a RootCommentModel
	 * and writes to a .json file.
	 * @param view Takes in view from SubmitComment
	 * @throws InvalidKeyException  Checks for invalid keys
	 * @throws NoSuchAlgorithmException Checks if algorithm used is valid
	 * @throws UnsupportedEncodingException Checks if there is an encoding problem
	 */
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
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_comment, menu);
		return true;
	}
}
