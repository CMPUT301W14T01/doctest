package ca.cs.ualberta.localpost.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;


import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.CommentModelList;

import com.google.gson.Gson;

public class Serialize {

	public static ArrayList<CommentModelList> comment_list;
	public static final String FILENAME = "comfile.save";
	
	public Serialize(Activity mainactivity){
		comment_list = loadFromFile(mainactivity);
	}
	
	
		
	//Load the list of counters into an ArrayList<CommentModelList> named comment_thread
	private ArrayList<CommentModelList> loadFromFile(Activity mainactivity){
		ArrayList<CommentModelList> comment_thread = new ArrayList<CommentModelList>();
		try{
			FileInputStream fis = mainactivity.openFileInput(FILENAME);
	        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
	        String line = in.readLine();      
	                
	        while (line != null) {
	        		CommentModelList new_thread = deserialize(line);
	                comment_thread.add(new_thread);
	                line = in.readLine();
	        }

	} catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	} catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}
	return comment_thread;
		}
	
	//Deserialize for loading
	private static CommentModelList deserialize(String retrieved_item){
		Gson new_item = new Gson();
		CommentModelList commentlist_retrieved = new_item.fromJson(retrieved_item, CommentModelList.class);
		return commentlist_retrieved;
	}
	
	
		//Save the array list to FILENAME when doing so over write everything
		public static void SaveInFile(ArrayList<CommentModelList> commentlist, Activity mainactivity){
			try{
				FileOutputStream fos = mainactivity.openFileOutput(FILENAME, Context.MODE_PRIVATE);
				CommentModelList count;
				for(int i = 0; i < comment_list.size(); ++i){
			    	count = comment_list.get(i);
			    	fos.write((serialize(count) + "\n").getBytes());	    	
				
				}
				fos.close();
			} catch (FileNotFoundException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Serialize for saving purposes
		private static String serialize(CommentModelList commentlist){
			Gson new_item = new Gson();
			String item = new_item.toJson(commentlist);
			return item;
		}
		

		
	}
