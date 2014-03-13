/**
 * 
 */
package ca.cs.ualberta.localpost.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.UserModel;
import ca.cs.ualberta.localpost.view.MainActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @author timotei
 *
 */
public class CommentController {
	
	private final Gson gson = new Gson();
	private static String URL = "http://cmput301.softwareprocess.es:8080/testing/t01/";
	public HttpClient httpclient = new DefaultHttpClient();
	
	private RootCommentModel model;
	private Activity activity;

	public CommentController(RootCommentModel model, Activity activity){
		this.model = model;
		this.activity = activity;
	}
	
	public boolean updateContent(String newText, String editedByID, String author){
		Boolean update = true;
		Boolean delete = true;
		Boolean insert = true;
		Boolean get = true;
		
		if (update){
			try {
				updateComment(newText, editedByID);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		/*if (insert){
			pushComment(newText, author);
			return true;
		}
		if (get){
			updateComment(model, author);
			return true;
		}*/
		return true;
	}
		
	
	public View spawnComment(){
		return null;		
	}
	
	// pushComment(), getComment(), updateComment(), deleteComment(), getEntityContent source code from:
	// https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	public void pushComment(CommentModel comment) throws IllegalStateException, IOException{
		
		// Test variables in place until we are able to communicate with th views
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.CANADA);
		UUID author = UUID.randomUUID();
		Date date = new Date();
		
		comment.setAuthor("anon4" + author.toString());
		comment.setContent("Content test");
		comment.setLocation(null);
		comment.setPicture(null);
		comment.setPostId(author);		
		comment.setTimestamp(dateFormat.format(date).toString());
		comment.setTitle("Title test");	
		// <-- End of test variables
		
		HttpPost pushRequest = new HttpPost(URL + String.valueOf(comment.getPostId()));		
		StringEntity stringentity = null;
		try {
			stringentity = new StringEntity(gson.toJson(comment));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pushRequest.setHeader("Accept","application/json");

		pushRequest.setEntity(stringentity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(pushRequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String status = response.getStatusLine().toString();
		System.out.println(status);
		HttpEntity entity = response.getEntity();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
		String output;
		System.err.println("Output from Server -> ");
		while ((output = br.readLine()) != null) {
			System.err.println(output);
		}

		try {
			entity.consumeContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pushRequest.abort();
	}
		
		/*try {
			pushRequest.setEntity(new StringEntity(gson.toJson(model)));

			HttpResponse response = client.execute(pushRequest);
			HttpEntity entity = response.getEntity();
			
			//For Printing; Remove when unneeded
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			
			String output = reader.readLine();
			while(output != null){
				Log.e("Elastic Search",output);
				output = reader.readLine();
				} 
			//
			
			Log.e("Success","Pushed");
		}
		catch (IOException exception) {
			Log.e("Error", "Error sending Model: " + exception.getMessage());
		}*/
	//End Push comment 

	public void getComment(CommentModel comment){
	
		try{
			HttpGet getRequest = new HttpGet(URL + String.valueOf(model.getPostId()));//S4bRPFsuSwKUDSJImbCE2g?pretty=1

			getRequest.addHeader("Accept","application/json");

			HttpResponse response = httpclient.execute(getRequest);

			String status = response.getStatusLine().toString();
			System.out.println(status);

			String json = getEntityContent(response);

			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<CommentModel>>(){}.getType();
			// Now we expect to get a CommentModel response
			ElasticSearchResponse<CommentModel> esResponse = gson.fromJson(json, elasticSearchResponseType);
			// We get the comment from it!
			comment = esResponse.getSource();
			System.out.println(comment.toString());
			getRequest.abort();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		/*try {
			HttpResponse response = client.execute(getRequest);
		
			String json = getEntityContent(response);
			
			//Deserialize the JSON objects
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<CommentModel>>(){}.getType();
			ElasticSearchResponse<CommentModel> esResponse = gson.fromJson(json, elasticSearchResponseType);

			//Get Recipes
			CommentModel model = esResponse.getSource();
			
			return model;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;*/
	}
	
	public void updateComment(String str, String id) throws ClientProtocolException, IOException{
		//HttpPost updateComment = new HttpPost(URL + String.valueOf(model.getPostId()) +"_update");
		HttpPost updateComment = new HttpPost(URL + id +"_update");
		String query = 	"{\"script\" : \"ctx._source." + str + "}";
		StringEntity stringentity = new StringEntity(query);

		updateComment.setHeader("Accept","application/json");
		updateComment.setEntity(stringentity);

		HttpResponse response = httpclient.execute(updateComment);
		String status = response.getStatusLine().toString();
		System.out.println(status);

		String json = getEntityContent(response);
		updateComment.abort();
	}
	
	/*public void deleteComment(){
		HttpDelete deleteComment = new HttpDelete(URL + String.valueOf(model.getPostId()));
		
		deleteComment.addHeader("Accept","application/json");

		HttpResponse response;
		try {
			response = httpclient.execute(deleteComment);
			// Test Code below -->
			String status = response.getStatusLine().toString();
			System.out.println(status);

			HttpEntity entity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output;
			System.err.println("Output from Server -> ");
			while ((output = br.readLine()) != null) {
				System.err.println(output);
			}
			entity.consumeContent();
			// Down to here <-- Test
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		deleteComment.abort();
		
		try {
			HttpResponse response = httpclient.execute(deleteComment);
			HttpEntity entity = response.getEntity();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			
			String output = reader.readLine();
			while(output != null){
				Log.e("Elastic Search",output);
				output = reader.readLine();
				} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//End deleteComment
*/	
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:"+json);
		return json;
		}


//Main Test
	public static void main(String [] args){
		
		Activity activity = new Activity();
		RootCommentModel model = new RootCommentModel(null, null, null);
		
		CommentController client = new CommentController(model, activity);
		try {
			client.pushComment(model);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
