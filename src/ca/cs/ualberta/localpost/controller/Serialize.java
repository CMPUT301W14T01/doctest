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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import ca.cs.ualberta.localpost.model.ChildCommentModel;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Serialize an Object
 * @author Team Radish
 *
 */
public class Serialize {
	private static Gson gson;
	//public static final String rootcomment = "rootcomment.json";
	//public static final String childcomment = "childcomment.json";
	
	public static final String favoritecomment = "favoritecomment.json";
	public static final String cachedrootcomment = "cachedrootcomment.json";
	public static final String cachedchildcomment = "cachedchildcomment.json";
	public static final String historycomment = "historycomment.json";
	public static final String userprofile = "userprofile.json";
	private static String filename = null;
	
	
	
	/**
	 * This function takes comment model, serializes it and saves it
	 * to a local file using GSON.
	 * @param new_root comment model that will be saved in file
	 * @param context // TODO
	 */
	public static void SaveComment(CommentModel new_root, Context context, String parentid) {
		constructGson();
		String modelJson = null;
		Log.e("Saving", "Calls saving");
		if(new_root instanceof RootCommentModel && (parentid == null)){
			filename = cachedrootcomment;
		}
		else if(new_root instanceof ChildCommentModel){
			filename = parentid;
		}
		else if(parentid.equals("favourite")){
			filename = favoritecomment;
			Log.e("Saving", "Finds the right file");
		}
		else if(parentid.equals("history")){
			filename = historycomment;	
		}
		modelJson = gson.toJson(new_root);
		write(modelJson, context);
	}
	
	
	public static void SaveUser(StandardUserModel user, Context context) {
		check_if_exist(userprofile, context);
		filename = userprofile;
		String modelJson = gson.toJson(user);
		write(modelJson, context);
	}
	
	public static void write(String modelJson, Context context){
		try {
			OutputStream outputStream = context.getApplicationContext()
					.openFileOutput(filename, Context.MODE_APPEND);
			OutputStreamWriter fileWriter = new OutputStreamWriter(outputStream);
			fileWriter.write(modelJson + "\r\n");
			fileWriter.close();
			Log.e("Saving", "Finishes writting");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void check_if_exist(String file, Context context){
		File listfile = context.getFilesDir();
		for(String c: listfile.list()){
			Log.e("SAVING", "list of files");
			if(c.equals(file)){
				Log.e("SAVING", "files do exist");
				File dir = context.getFilesDir();
				File file1 = new File(dir, file);
				boolean deleted = file1.delete();
			}
		}
		
	}
	
	public static void update(CommentModel updatedroot, Context context, String file){
		ArrayList<CommentModel> rootlist = new ArrayList<CommentModel>();
		String dir = null;
		if(file.equals(favoritecomment)){
			dir = "favourite";
		}
		else{
			dir = "history";
		}
		rootlist = loadFromFile(file, context);
		check_if_exist(file, context);
		for(CommentModel r : rootlist){
			if(r.getPostId().toString().equals(updatedroot.getPostId().toString())){
				r.setChildren(updatedroot.getChildren());
			}
			SaveComment(r, context, dir);
		}
	}
	


	/**
	 * Load GSON objects from a file, deserialize them and pass them to an ArrayList as 
	 * RootCommentModels. 
	 * @param filename file containing all serialized GSON objects that we saved
	 * @param context // TODO
	 * @return arraylist of RootCommentModels
	 */
	public static ArrayList<CommentModel> loadFromFile(String filename, Context context) {
		constructGson();
		ArrayList<CommentModel> model = new ArrayList<CommentModel>();
		FileInputStream FileOpen;
		Log.e("Loading", "Starts loading");
		try {
			FileOpen = context.getApplicationContext().openFileInput(filename);
			InputStreamReader FileReader = new InputStreamReader(FileOpen);
			BufferedReader buffer = new BufferedReader(FileReader);
			
			String input;
			while ((input = buffer.readLine()) != null) {
				if(filename.equals(cachedrootcomment) || filename.equals(favoritecomment)){
					Log.e("Loading", "Loads from right file");
					Log.e("Loading", filename);
					RootCommentModel obj = gson.fromJson(input,RootCommentModel.class);
					model.add(obj);
				}

				
				else if(filename.equals(userprofile)){
					StandardUserModel obj2 = gson.fromJson(input,StandardUserModel.class);
				}
			}
			return model;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public static HashMap<String,ChildCommentModel> loadchildFromFile(String filename, Context context) {
		constructGson();
		ChildCommentModel model = new ChildCommentModel();
		HashMap<String,ChildCommentModel> hash = new HashMap<String,ChildCommentModel>();
		FileInputStream FileOpen;
		try {
			FileOpen = context.getApplicationContext().openFileInput(filename);
			InputStreamReader FileReader = new InputStreamReader(FileOpen);
			BufferedReader buffer = new BufferedReader(FileReader);

			String input;
			while ((input = buffer.readLine()) != null) {					
					model = gson.fromJson(input,ChildCommentModel.class);
					hash.put(model.getPostId().toString(), model);
				}
			return hash;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hash;
	}
	
	public static StandardUserModel loaduser(Context context) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		constructGson();
		StandardUserModel user = StandardUserModel.getInstance();
		filename = userprofile;
		FileInputStream FileOpen;
		try {
			FileOpen = context.getApplicationContext().openFileInput(filename);
			InputStreamReader FileReader = new InputStreamReader(FileOpen);
			BufferedReader buffer = new BufferedReader(FileReader);

			String input;
			while ((input = buffer.readLine()) != null) {
					user = gson.fromJson(input,StandardUserModel.class);	
			}
			return user;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	private static void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		gson = builder.create();
	}
}
