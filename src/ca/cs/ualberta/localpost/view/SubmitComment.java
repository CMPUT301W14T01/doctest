package ca.cs.ualberta.localpost.view;

import java.util.Date;

import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.CommentModelList;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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
	
	public void add_root(View view){
		String author = MainActivity.getModel().getUsername();
		EditText titleView = (EditText) findViewById(R.id.commentTitle);
		String title = titleView.getText().toString();
		EditText contentView = (EditText) findViewById(R.id.textBody);
		String content = contentView.getText().toString();
		RootCommentModel new_root = new RootCommentModel(content, title);
		CommentModelList new_thread = new CommentModelList(new_root);
		MainActivity.getList().updatingThreadlist(new_thread);
		//Serialize.SaveInFile(MainActivity.getList().getThreadlist(), this);
	}

}
