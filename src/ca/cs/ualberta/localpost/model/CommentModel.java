package ca.cs.ualberta.localpost.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.graphics.Bitmap;
import ca.cs.ualberta.localpost.model.UserModel;

public abstract class CommentModel {
	
	//TODO change timestamp to string in UML
	public String content;
	public android.location.Location location;
	private UserModel author;
	private ArrayList<CommentModel> children = new ArrayList<CommentModel>();
	private String timestamp;
	private java.util.UUID postId;
	private int radish;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public Bitmap picture;

	
	

	public CommentModel(String content){
		this.content = content;
		this.radish = 0;
		this.setPostId(UUID.randomUUID());
		//this.location = location;
		Date date = new Date();
		this.timestamp = dateFormat.format(date);
		//this.picture = picture;
		//this.author = UserModel.getAuthor();
		
	}
	public CommentModel(String content, android.location.Location location, Bitmap picture){
		this.content = content;
		this.radish = 0;
		this.setPostId(UUID.randomUUID());
		this.location = location;
		Date date = new Date();
		this.timestamp = dateFormat.format(date);
		this.picture = picture;
		//this.author = UserModel.getAuthor();
		
	}

	public Bitmap getPicture(){
		return picture;
	}
	
	public void setPicture(Bitmap picture){
		this.picture = picture;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public android.location.Location getLocation() {
		return location;
	}

	public void setLocation(android.location.Location location) {
		this.location = location;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		Date date = new Date();
		this.timestamp = dateFormat.format(date);
	}

	public int getRadish() {
		return radish;
	}

	public void incRadish(int radish) {
		this.radish += 1;
	}
	
	public void decRadish(int radish) {
		this.radish -= 1;
	}
	/**
	 * @return the postId
	 */
	public java.util.UUID getPostId() {
		return postId;
	}
	/**
	 * @param postId the postId to set
	 */
	public void setPostId(java.util.UUID postId) {
		this.postId = postId;
	}

}
