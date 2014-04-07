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
import ca.cs.ualberta.localpost.model.ChildCommentModel;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Serialize an Object for caching purposes
 * @author Team Radish
 *
 */
public class Serialize {
	private static Gson gson;	
	public static final String favoritecomment = "favoritecomment.json";
	public static final String cachedrootcomment = "cachedrootcomment.json";
	public static final String cachedchildcomment = "cachedchildcomment.json";
	public static final String historycomment = "historycomment.json";
	public static final String readlater = "readlater.json";
	public static final String userprofile = "userprofile.json";
	private static String filename = null;
	
	
	
	/**
	 * This function takes comment model and turns it into a Json string
	 * @param new_root comment model that will be saved in file
	 * @param context takes in the activities context
	 * @param parentid helps determine which file to save in
	 */
	public static void SaveComment(CommentModel new_root, Context context, String parentid) {
		constructGson();
		String modelJson = null;
		
		if(new_root instanceof RootCommentModel && (parentid == null)){
			filename = cachedrootcomment;
		}
		else if(new_root instanceof ChildCommentModel){
			filename = parentid;
		}
		else if(parentid.equals("favourite")){
			filename = favoritecomment;
		}
		else if(parentid.equals("history")){
			filename = historycomment;	
		}
		else if(parentid.equals("readlater")){
			filename = readlater;
			
		}
		ArrayList<CommentModel> rootlist = loadFromFile(filename, context);
		for(CommentModel r: rootlist){
			if(new_root.getPostId().equals(r.getPostId())){
				return;
			}
		}
		modelJson = gson.toJson(new_root);
		write(modelJson, context);
	}
	
	/**
	 * Takes in a user profile and saves it in memory for later re use
	 * @param user is the user profile that is to be saved
	 * @param context is the activitie's context
	 */
	public static void SaveUser(StandardUserModel user, Context context) {
		filename = userprofile;
		String modelJson = gson.toJson(user);
		
		writeuser(modelJson, context);
	}
	
	/**
	 * Takes a CommentModel in a Json string and writes it to the filename that was specified while appending it
	 * @param modelJson is the data that needs to be saved
	 * @param context is the activitie's context
	 */
	public static void write(String modelJson, Context context){
		try {
			
			OutputStream outputStream = context.getApplicationContext()
					.openFileOutput(filename, Context.MODE_APPEND);
			OutputStreamWriter fileWriter = new OutputStreamWriter(outputStream);
			fileWriter.write(modelJson + "\r\n");
			fileWriter.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes a UserProfile Json string and writes it to the filename that was specified while overwriting previous profile information
	 * @param modelJson is the data that needs to be saved
	 * @param context is the activitie's context
	 */
	public static void writeuser(String modelJson, Context context){
		try {
			
			OutputStream outputStream = context.getApplicationContext()
					.openFileOutput(filename, Context.MODE_PRIVATE);
			OutputStreamWriter fileWriter = new OutputStreamWriter(outputStream);
			fileWriter.write(modelJson + "\r\n");
			fileWriter.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function checks for the existance of "file" and deletes it for the sake of creating a new cache instead of appending an infinite amount of cache
	 * @param file is the file to save to
	 * @param context is the activity's context
	 */
	public static void check_if_exist(String file, Context context){
		File listfile = context.getFilesDir();
		for(String c: listfile.list()){
			
			if(c.equals(file)){
				
				File dir = context.getFilesDir();
				File file1 = new File(dir, file);
				boolean deleted = file1.delete();
			}
		}
		
	}
	/**
	 * This function goes through a file and updates cached comments so they match in terms of children, radish etc with the one that is saved to elastic search
	 * It also deletes the old one after retrieving the comments from it before saving it into a new cache
	 * @param updatedroot the comment that needs to be updated
	 * @param context the activity's context for io purposes
	 * @param file the file the comment is being updated in.
	 */
	public static void update(CommentModel updatedroot, Context context, String file){
		ArrayList<CommentModel> rootlist = new ArrayList<CommentModel>();
		String dir = dir(file);
		rootlist = loadFromFile(file, context);
		
		check_if_exist(file, context);
		for(CommentModel r : rootlist){
			if(r.getPostId().toString().equals(updatedroot.getPostId().toString())){
				r = updatedroot;
				
			}
			SaveComment(r, context, dir);
		}
		
	}

	/**
	 * Chooses the right dir to save to.
	 * @param file file to save to
	 * @return returns the string of the file we want to save in
	 */
	private static String dir(String file) {
		String dir = null;
		if (file.equals(favoritecomment)) {
			dir = "favourite";
		} else if (file.equals(readlater)) {
			dir = "readlater";
		} else {
			dir = "history";
		}
		return dir;
	}
	


	/**
	 * Load GSON objects from a file, deserialize them and pass them to an ArrayList as 
	 * RootCommentModels. Depending on the filename it will load from different caches
	 * @param filename file containing all serialized GSON objects that we saved
	 * @param context activity's context
	 * @return arraylist of RootCommentModels
	 */
	public static ArrayList<CommentModel> loadFromFile(String filename, Context context) {
		constructGson();
		ArrayList<CommentModel> model = new ArrayList<CommentModel>();
		FileInputStream FileOpen;
		
		try {
			FileOpen = context.getApplicationContext().openFileInput(filename);
			InputStreamReader FileReader = new InputStreamReader(FileOpen);
			BufferedReader buffer = new BufferedReader(FileReader);
			
			String input;
			while ((input = buffer.readLine()) != null) {
				if(filename.equals(cachedrootcomment) || filename.equals(favoritecomment) || filename.equals(historycomment) || filename.equals(readlater) ){
					
					RootCommentModel obj = gson.fromJson(input,RootCommentModel.class);
					model.add(obj);
				}
			}
			return model;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * Loads children from cache files and inserts them into a hashmap. This hashmap can be accessed at constant time hence why it is used
	 * @param filename is the filename to load from
	 * @param context is the activity's context for io purposes
	 * @return A thread in the form of a hashmap 
	 */
	public static HashMap<String,ChildCommentModel> loadchildFromFile(String filename, Context context) {
		constructGson();
		ChildCommentModel model = new ChildCommentModel(context);
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
	/**
	 * This function simply loads a user profile from the user profile cache
	 * @param context is the activity's context for io purposes
	 * @return The user's userprofile
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static StandardUserModel loaduser(Context context) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		constructGson();
		StandardUserModel user = new StandardUserModel(context);
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
			StandardUserModel.setInstance(user);
			return user;
		} catch (IOException e) {
			e.printStackTrace();
		}
		StandardUserModel.setInstance(user);

		return user;
	}
	
	/**
	 * Constructs a Gson with a custom serializer / desserializer registered for
	 * Bitmaps.
	 */
	private static void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		gson = builder.create();
	}
}
