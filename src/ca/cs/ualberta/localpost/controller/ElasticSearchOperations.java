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

package ca.cs.ualberta.localpost.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

import ca.cs.ualberta.localpost.model.ChildCommentModel;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class ElasticSearchOperations extends AsyncTask<Object, Integer, ArrayList<CommentModel>> {

	private static Gson gson;
	private static String URL = "http://cmput301.softwareprocess.es:8080/testing/zerihoun/";
	private HttpClient httpclient = new DefaultHttpClient();

	public ElasticSearchOperations() {
		constructGson();
	}
	
	@Override
	// Params(Used to determine function,postID used for pushing, CommentModel)
	protected ArrayList<CommentModel> doInBackground(Object... params) { // parms[]
		Integer index = (Integer) params[0]; // Determines if we are pushing or
												// getting
		java.util.UUID UUID = (java.util.UUID) params[1]; // postID used in pushModel

		if (index == 1) {
			try {
				if (params[2] instanceof RootCommentModel) {
					RootCommentModel model = (RootCommentModel) params[2];
					pushComment(model, UUID);
				} else if (params[2] instanceof ChildCommentModel) {
					ChildCommentModel model = (ChildCommentModel) params[2];
					pushComment(model, UUID);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (index == 2) {
			
		} else if (index == 3) {
			try {
				return getAllComments();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Remove when unneeded.
		else if (index == 4) {
			deleteComment(1);
		}
		return null;
	}// End doInBackGround

	public void pushComment(CommentModel model, java.util.UUID UUID) {
		HttpPost pushRequest = new HttpPost(URL + UUID);
		try {
			pushRequest.setEntity(new StringEntity(gson.toJson(model)));

			HttpResponse response = httpclient.execute(pushRequest);
			HttpEntity entity = response.getEntity();

			// For Printing; Remove when unneeded
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			String output = reader.readLine();
			while (output != null) {
				output = reader.readLine();
			}
		} catch (Exception e) {
			Log.e("Error", "Error sending Model: " + e.getMessage());
		}
	}

	public RootCommentModel getComment(int id) {
		HttpGet getRequest = new HttpGet(URL + String.valueOf(id));
		try {
			HttpResponse response = httpclient.execute(getRequest);

			String json = getEntityContent(response);

			Type returnType = new TypeToken<ElasticSearchResponse<RootCommentModel>>() {
			}.getType();
			ElasticSearchResponse<RootCommentModel> esResponse = gson.fromJson(
					json, returnType);

			RootCommentModel comment = esResponse.getSource();
			return comment;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<CommentModel> getAllComments() throws ClientProtocolException, IOException {
		ArrayList<CommentModel> returnArray = new ArrayList<CommentModel>();
		HttpGet search = new HttpGet(URL + "_search");
		HttpResponse response = httpclient.execute(search);

		String status = response.getStatusLine().toString();
		System.out.println(status);

		String json = getEntityContent(response);

		Type searchType = new TypeToken<ElasticSearchSearchResponse<RootCommentModel>>() {}.getType();
		ElasticSearchSearchResponse<RootCommentModel> esResponse = gson.fromJson(json, searchType);

		for (ElasticSearchResponse<RootCommentModel> r : esResponse.getHits()) {
			RootCommentModel model = r.getSource();
			returnArray.add(model);
		}
		return returnArray;
	}

	public void deleteComment(int id) {
		HttpDelete delRequest = new HttpDelete(URL + String.valueOf(id));
		try {
			HttpResponse response = httpclient.execute(delRequest);
			HttpEntity entity = response.getEntity();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// End deleteComment

	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:" + json);
		return json;
	}
	
	/**
	 * Constructs a Gson with a custom serializer / desserializer registered for Bitmaps.
	 */
	private static void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		gson = builder.create();
	}
}