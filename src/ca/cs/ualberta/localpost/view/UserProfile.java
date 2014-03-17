package ca.cs.ualberta.localpost.view;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.RootCommentModel;

import com.google.gson.Gson;

public class UserProfile extends Activity implements OnClickListener {
	private RelativeLayout usernameLayout;
	private RelativeLayout favoriteLayout;
	private RelativeLayout geoLayout;

	private SharedPreferences app_preferences;
	private SharedPreferences.Editor editor;

	private TextView userNameText;
	private ListView listView;
	private CommentListAdapter adapter;

	private ArrayList<RootCommentModel> model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);

		// Set Views and Layouts
		userNameText = (TextView) findViewById(R.id.profileUsername);
		usernameLayout = (RelativeLayout) findViewById(R.id.profileUsernameLayout);
		favoriteLayout = (RelativeLayout) findViewById(R.id.profileFavoriteLayout);
		geoLayout = (RelativeLayout) findViewById(R.id.profileGeoLayout);

		// Makes the Layouts clickable
		usernameLayout.setOnClickListener(this);
		favoriteLayout.setOnClickListener(this);
		geoLayout.setOnClickListener(this);

		// Gets the username and sets it
		app_preferences = getApplicationContext().getSharedPreferences("PREF",
				MODE_PRIVATE);

		String getUsername = app_preferences.getString("username", "anonymous");
		userNameText.setText(getUsername);

		// Populate listview with user posted comments
		model = Serialize.loadFromFile("rootcomment.json",
				getApplicationContext());

		listView = (ListView) findViewById(R.id.profileCommentList);
		registerForContextMenu(listView);
		adapter = new CommentListAdapter(UserProfile.this,R.id.custom_adapter, model);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(),
						"ThreadView Under Construction", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
	
	@Override 
	public void onResume(){
		super.onResume();
		model = Serialize.loadFromFile("rootcomment.json", UserProfile.this);
		adapter = new CommentListAdapter(UserProfile.this, R.id.custom_adapter,model);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.profileUsernameLayout:
			editUsernameDialog(v);
			break;
		case R.id.profileFavoriteLayout:
			Intent intent = new Intent(this, FavoritesView.class);
			startActivity(intent);
			break;
		case R.id.profileGeoLayout:
			Toast.makeText(getApplicationContext(), "Geolocal is Under con.",
					Toast.LENGTH_SHORT).show();
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == Activity.RESULT_OK) {
				Gson gson = new Gson();
				String returnObj = data.getStringExtra("returnObj");
				String returnIndex = data.getStringExtra("returnIndex");
				RootCommentModel edited_root = gson.fromJson(returnObj,RootCommentModel.class);

				model.set(Integer.valueOf(returnIndex), edited_root);
	
				
				File dir = getFilesDir();
				File file = new File(dir, "rootcomment.json");
				boolean deleted = file.delete();
				
				for(RootCommentModel m: model){
					Serialize.SaveInFile(m, UserProfile.this);
				}
			}
		}
	}

	public void editUsernameDialog(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Enter New Username");
		final EditText input = new EditText(this);
		builder.setView(input);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Editable value = input.getText();

				SharedPreferences app_preferences = getApplicationContext()
						.getSharedPreferences("PREF", MODE_PRIVATE);

				SharedPreferences.Editor editor = app_preferences.edit();
				editor.clear();
				editor.putString("username", value.toString());
				editor.commit();
				userNameText.setText(value.toString());
				Toast.makeText(getApplicationContext(), "Username Changed",
						Toast.LENGTH_SHORT).show();
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "Cancelled",
						Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, Menu.FIRST, 0, "Edit Comment");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Gson gson = new Gson();
		switch (item.getItemId()) {
		case Menu.FIRST:
			int index = (int) info.id;
			Intent myIntent = new Intent(this, EditComment.class);
			String x = gson.toJson(model.get(index));
			myIntent.putExtra("ModelObj", x);
			myIntent.putExtra("Index", String.valueOf(index));
			startActivityForResult(myIntent, 1);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}

}
