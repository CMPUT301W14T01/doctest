package ca.cs.ualberta.localpost.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import android.content.Context;
import android.util.Log;
import ca.cs.ualberta.localpost.model.RootCommentModel;

import com.google.gson.Gson;

public class Serialize {
	private static Gson gson = new Gson();
	
	public static void SaveInFile(RootCommentModel new_root, Context context) {
		String modelJson = gson.toJson(new_root);
		try {
			OutputStream outputStream = context.getApplicationContext()
					.openFileOutput("text.json", Context.MODE_APPEND);
			OutputStreamWriter fileWriter = new OutputStreamWriter(outputStream);
			fileWriter.write(modelJson+"\r\n");
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadFromFile(Context context) {
		FileInputStream FileOpen;
		try {
			FileOpen = context.getApplicationContext().openFileInput("text.json");
			InputStreamReader FileReader = new InputStreamReader(FileOpen);
			BufferedReader buffer = new BufferedReader(FileReader);
			
			String input;
			while ((input = buffer.readLine()) != null) {
				Log.e("input",input);
				RootCommentModel obj = gson.fromJson(input, RootCommentModel.class);
				Log.e("OBJ",obj.getTitle());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//End loadFromFile
}
