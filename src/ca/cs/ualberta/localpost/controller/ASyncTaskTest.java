package ca.cs.ualberta.localpost.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import ca.cs.ualberta.localpost.model.CommentModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import android.util.Log;

public class ASyncTaskTest extends AsyncTask<Object, Integer, ArrayList<CommentModel>> {
	private final Gson gson = new Gson();
	private static String URL = "http://cmput301.softwareprocess.es:8080/testing/t01/";
	private HttpClient client = new DefaultHttpClient();
	
	@Override
	protected ArrayList<CommentModel> doInBackground(Object... params) { //Object... parmas is the same as Objects[]
		ArrayList<CommentModel> model = new ArrayList<CommentModel>();
		Integer index = (Integer) params[0];
		if(index == 1){
			model.add(getComment(2));
			return model;
		}
		else if(index == 2){
			deleteComment();
		}
		return model;
	}//End doInBackGround
	
	public void pushComment(CommentModel model, int id){
		HttpPost pushRequest = new HttpPost(URL + String.valueOf(id));
		
		try {
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
		}
	}//End Push comment 

	public CommentModel getComment(final int id){
		HttpGet getRequest = new HttpGet(URL + String.valueOf(id));
	
		try {
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
		return null;
	}
	
	public void deleteComment(){
		HttpDelete getRequest = new HttpDelete(URL + "3");
		try {
			HttpResponse response = client.execute(getRequest);
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
	
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		return json;
		}
}
