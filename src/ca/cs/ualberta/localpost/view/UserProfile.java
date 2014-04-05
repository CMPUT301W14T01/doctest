/**
 * The MIT License (MIT)
 * Copyright (c) 2014 Timotei Albu, David Chau-Tran, Alain Clark, Shawn Anderson, Mickael Zerihoun
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 *	this software and associated documentation files (the "Software"), to deal in
 *	the Software without restriction, including without limitation the rights to
 *	use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so,
 *	subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *  
 */

package ca.cs.ualberta.localpost.view;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ca.cs.ualberta.localpost.controller.CommentListAdapter;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;

import com.google.gson.Gson;

/**
 * This view will display a users information
 * The user will be able to change their user name, 
 * view their comment history and view their favourited comments
 * @author Team 01
 */
public class UserProfile extends Activity implements OnClickListener {
	/** Grabs the layouts respect to their functionality*/
	private RelativeLayout usernameLayout;
	private RelativeLayout favoriteLayout;
	private RelativeLayout geoLayout;
	
	public static final int OBTAIN_EDIT_COMMENT_CODE = 100;
	public static final int OBTAIN_EDIT_USER_LOCATION_CODE = 101;

//	/**Grabs Username via preferences*/
//	private SharedPreferences app_preferences;
//	private SharedPreferences.Editor editor;

	/**Views related to the activity view*/
	private TextView userNameText;
	private ListView listView;
	private CommentListAdapter adapter;

	/**Arraylist of RootCommentModels */
	private ArrayList<CommentModel> model;
	
	private StandardUserModel user;
	Gson gson;
	Address address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		
		gson = new Gson();
		
		try {
			user = Serialize.loaduser(getApplicationContext());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Set Views and Layouts
		userNameText = (TextView) findViewById(R.id.profileUsername);
		usernameLayout = (RelativeLayout) findViewById(R.id.profileUsernameLayout);
		favoriteLayout = (RelativeLayout) findViewById(R.id.profileFavoriteLayout);
		geoLayout = (RelativeLayout) findViewById(R.id.profileGeoLayout);

		// Makes the Layouts clickable
		usernameLayout.setOnClickListener(this);
		favoriteLayout.setOnClickListener(this);
		geoLayout.setOnClickListener(this);

		userNameText.setText(user.getUsername());
		
		listView = (ListView) findViewById(R.id.profileCommentList);
		
		registerForContextMenu(listView);

		adapter = new CommentListAdapter(getApplicationContext(), R.id.custom_adapter, model);

		// Populate listview with user posted comments
		model = Serialize.loadFromFile("historycomment.json",
				getApplicationContext());

		listView = (ListView) findViewById(R.id.profileCommentList);
		registerForContextMenu(listView);
		adapter = new CommentListAdapter(UserProfile.this,R.id.custom_adapter, model);
		listView.setAdapter(adapter);

		//Sets onClickListener for each lisview element
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(getApplicationContext(),	"ThreadView Under Construction", Toast.LENGTH_SHORT)
				//		.show();
				Gson gson = new Gson();
				String modelString = gson.toJson(model.get(position));
				Intent myIntent = new Intent(getApplicationContext(),ThreadView.class);
				myIntent.putExtra("CommentModel", modelString);
				
				startActivity(myIntent);
			}
		});
	}

	/**
	 * Overrides onResume. This will update the listView with data 
	 * that has been added.
	 */
	@Override 
	public void onResume(){
		super.onResume();
		model = Serialize.loadFromFile("historycomment.json", UserProfile.this);
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
			Intent intentFavorites = new Intent(getApplicationContext(), FavoritesView.class);
			startActivity(intentFavorites);
			break;
		case R.id.profileGeoLayout:
			Intent intentUserLocation = new Intent(getApplicationContext(), MapsView.class);
			startActivityForResult(intentUserLocation, OBTAIN_EDIT_USER_LOCATION_CODE);
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == OBTAIN_EDIT_COMMENT_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				Gson gson = new Gson();
				String returnObj = data.getStringExtra("returnObj");
				String returnIndex = data.getStringExtra("returnIndex");
				RootCommentModel edited_root = gson.fromJson(returnObj,RootCommentModel.class);

				model.set(Integer.valueOf(returnIndex), edited_root);
					
				File dir = getFilesDir();
				File file = new File(dir, "rootcomment.json");
				boolean deleted = file.delete();
				
				for(CommentModel m: model){
					Serialize.SaveComment(m, UserProfile.this,null);
				}
			}
		}
		if (requestCode == OBTAIN_EDIT_USER_LOCATION_CODE) {
			if (resultCode == RESULT_OK) {
				String intentIndex = data.getStringExtra("address");
				address = gson.fromJson(intentIndex,
						android.location.Address.class);
				user.setAddress(address);
				Serialize.SaveUser(user, getApplicationContext());
			} else
				super.onActivityResult(requestCode, resultCode, data);
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
				try {
					user = Serialize.loaduser(getApplicationContext());
				} catch (Exception e) {
					e.printStackTrace();
				}
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
				try {
					user.setUsername(String.valueOf(value));
					Serialize.SaveUser(user, getApplicationContext());
					//user.setAddress(new GPSLocation(getApplicationContext()).getAddress());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			startActivityForResult(myIntent, OBTAIN_EDIT_COMMENT_CODE);
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
