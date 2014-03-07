package ca.cs.ualberta.localpost.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import ca.cs.ualberta.localpost.model.UserModel;

public abstract class CommentModel {

	public CommentModel(String content, android.location.Location location){
		this.content = content;
		this.radish = 0;
		this.postId = UUID.randomUUID();
		this.location = location;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		this.timestamp = dateFormat.format(date);
		//this.author = UserModel.getAuthor();
		
	}
	
	//TODO change timestamp to string in UML
	public String content;
	public android.location.Location location;
	private UserModel author;
	private ArrayList<CommentModel> children = new ArrayList<CommentModel>();
	private String timestamp;
	private java.util.UUID postId;
	private int radish;
	
}
