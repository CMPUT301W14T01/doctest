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
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import ca.cs.ualberta.localpost.model.ChildCommentModel;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.view.MainActivity;

import com.google.gson.Gson;

public class Serialize {
	private static Gson gson = new Gson();

	public static void SaveInFile(CommentModel new_root, Context context) {
		String filename = null;
		if(new_root instanceof RootCommentModel){
			filename = "rootcomment.json";
		}
		else if(new_root instanceof ChildCommentModel){
			filename = "childcomment.json";
		}
		if(context.getClass().equals(MainActivity.class)){
			filename = "favoritecomment.json";
		}
				
		String modelJson = gson.toJson(new_root);
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

	public static ArrayList<RootCommentModel> loadFromFile(String filename, Context context) {
		ArrayList<RootCommentModel> model = new ArrayList<RootCommentModel>();
		FileInputStream FileOpen;
		try {
			FileOpen = context.getApplicationContext().openFileInput(filename);
			InputStreamReader FileReader = new InputStreamReader(FileOpen);
			BufferedReader buffer = new BufferedReader(FileReader);

			String input;
			while ((input = buffer.readLine()) != null) {
				if(filename.equals("rootcomment.json") || filename.equals("favoritecomment.json")){
					RootCommentModel obj = gson.fromJson(input,RootCommentModel.class);
					model.add(obj);
				}
				else if(filename.equals("childcomment.json")){
					Log.e("Child", input);
					ChildCommentModel obj2 = gson.fromJson(input,ChildCommentModel.class);
					Log.e("ObjChild", obj2.getTitle());
				}
			}
			return model;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}// End loadFromFile
}
