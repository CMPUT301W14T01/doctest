package ca.cs.ualberta.localpost.view;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.RootCommentModel;

public class UserProfile extends Activity implements OnClickListener {
	private RelativeLayout usernameLayout;
	private RelativeLayout favoriteLayout;
	private RelativeLayout geoLayout;

	private SharedPreferences app_preferences;
	private SharedPreferences.Editor editor;

	private TextView userNameText;
	private ListView listView;

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

		// Populate Listview
		model = Serialize.loadFromFile("rootcomment.json",getApplicationContext());

		listView = (ListView) findViewById(R.id.profileCommentList);
		CommentListAdapter adapter = new CommentListAdapter(UserProfile.this,R.id.custom_adapter, model);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(getApplicationContext(),"ThreadView Under Construction", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.profileUsernameLayout:
			editUsernameDialog(v);
			break;
		case R.id.profileFavoriteLayout:
			Intent intent = new Intent(this, FavoritesView.class);
			startActivity(intent);
			break;
		case R.id.profileGeoLayout:
			Toast.makeText(getApplicationContext(), "Geolocal is pressed",
					Toast.LENGTH_SHORT).show();
			break;
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}

}
