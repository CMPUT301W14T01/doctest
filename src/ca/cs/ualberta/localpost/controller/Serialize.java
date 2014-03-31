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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import ca.cs.ualberta.localpost.model.ChildCommentModel;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;
import ca.cs.ualberta.localpost.view.MainActivity;

import com.google.gson.Gson;

/**
 * Serialize an Object
 * @author Team Radish
 *
 */
public class Serialize {
	private static Gson gson = new Gson();
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
	public static void SaveComment(CommentModel new_root, Context context) {
		
		if(new_root instanceof RootCommentModel){
			filename = cachedrootcomment;
		}
		else if(new_root instanceof ChildCommentModel){
			filename = cachedchildcomment;
		}
		if(context.getClass().equals(MainActivity.class)){
			filename = favoritecomment;
		}
				
		String modelJson = gson.toJson(new_root);
		write(modelJson, context);
	}
	
	public static void SaveUser(StandardUserModel user, Context context) {
		
		filename = userprofile;
		String modelJson = gson.toJson(user);
		write(modelJson, context);
	}
	
	public static void write(String modelJson, Context context){
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
	 * Load GSON objects from a file, deserialize them and pass them to an ArrayList as 
	 * RootCommentModels. 
	 * @param filename file containing all serialized GSON objects that we saved
	 * @param context // TODO
	 * @return arraylist of RootCommentModels
	 */
	public static ArrayList<RootCommentModel> loadFromFile(String filename, Context context) {
		ArrayList<RootCommentModel> model = new ArrayList<RootCommentModel>();
		FileInputStream FileOpen;
		try {
			FileOpen = context.getApplicationContext().openFileInput(filename);
			InputStreamReader FileReader = new InputStreamReader(FileOpen);
			BufferedReader buffer = new BufferedReader(FileReader);

			String input;
			while ((input = buffer.readLine()) != null) {
				if(filename.equals(cachedrootcomment) || filename.equals(favoritecomment)){
					RootCommentModel obj = gson.fromJson(input,RootCommentModel.class);
					model.add(obj);
				}
				else if(filename.equals(cachedchildcomment)){
					Log.e("Child", input);
					ChildCommentModel obj2 = gson.fromJson(input,ChildCommentModel.class);
					Log.e("ObjChild", obj2.getTitle());
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
	public static StandardUserModel loaduser(Context context) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
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
}
