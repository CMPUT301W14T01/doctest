/**
 * 
 */
package ca.cs.ualberta.localpost.controller;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.bool;
import android.view.View;

import com.google.gson.Gson;

import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.UserModel;
import ca.cs.ualberta.localpost.view.MainActivity;
import ca.cs.ualberta.localpost.view.R;


/**
 * @author timotei
 *
 */
public class CommentController {
	
	private final Gson gson = new Gson();
	private static String URL = "http://cmput301.softwareprocess.es:8080/testing/t01/";
	private HttpClient client = new DefaultHttpClient();
	
	private RootCommentModel model;
	private MainActivity activity;

	public CommentController(RootCommentModel model, MainActivity activity){
		this.model = model;
		this.activity = activity;
	}
	
	public boolean updateContent(String newText, UserModel editedBy, String author){
		if (false){
			return true;
		}		
		return false;
	}
	
	public View spawnComment(){
		return null;		
	}

}
