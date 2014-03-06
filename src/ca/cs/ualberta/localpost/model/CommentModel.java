package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class CommentModel {

	public CommentModel(String content){
		this.content = content;
		//this.author = "anonymous";
	}
	
	public CommentModel(String content, String author){
		this.content = content;
		//this.author = author;
	}
	public String content;
	public android.location.Location location;
	private UserModel author;
	private ArrayList<CommentModel> children = new ArrayList<CommentModel>();
	private Date timestamp;
	private java.util.UUID postId = UUID.randomUUID();
	
	
}
