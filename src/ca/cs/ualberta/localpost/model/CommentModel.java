package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.UUID;

import com.google.gson.Gson;

import android.graphics.Bitmap;

public abstract class CommentModel extends Observable{
	private String title;
	private String content;
	private android.location.Location location;
	private String author;
	private ArrayList<CommentModel> children = new ArrayList<CommentModel>();
	private long timestamp;
	private java.util.UUID postId = UUID.randomUUID();
	private int radish;
	private Bitmap picture;
	long date = new Date().getTime();
	StandardUserModel user;
	
	public CommentModel() {
		super();
		try {
			user = new StandardUserModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		this.title = null;
		this.content = null;
		this.radish = 0;
		this.setPostId(postId);
		this.timestamp = date;
		this.author = getAuthor();
		// this.location = location;
		// this.picture = picture;
	}
	
	public CommentModel(String content, String title) {
		try {
			user = new StandardUserModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		this.title = title;
		this.content = content;
		this.radish = 0;
		this.setPostId(postId);
		this.timestamp = date;
		this.author = getAuthor();
		// this.location = location;
		// this.picture = picture;
	}

	public CommentModel(String content, android.location.Location location,
			Bitmap picture) {
		try {
			user = new StandardUserModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		this.content = content;
		this.radish = 0;
		this.setPostId(UUID.randomUUID());
		this.location = location;
		this.timestamp = date;
		this.picture = picture;
		this.author = getAuthor();
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		this.author = user.getUsername();
		return this.author;
	}
//
	public void setAuthor(String author) {
		try {
			user.setUsername(author);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Bitmap getPicture() {
		return picture;
	}

	public void setPicture(Bitmap picture) {
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

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getRadish() {
		return radish;
	}

	public void incRadish() {
		this.radish += 1;
	}

	public void decRadish() {
		this.radish -= 1;
	}

	/**
	 * @return the postId
	 */
	public java.util.UUID getPostId() {
		return postId;
	}

	/**
	 * @param postId
	 *            the postId to set
	 */
	public void setPostId(java.util.UUID postId) {
		this.postId = postId;
	}
	
	public String getFlattenedClone(){
		//Class<? extends CommentModel> cloneType = this.getClass();
		//CommentModel clone = cloneType.newInstance();
		CommentModel clone = null;
		try {
			clone = (CommentModel) this.clone();
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Have cloned 'this', swap for UUIDs of children
		
		
		ArrayList<java.util.UUID> childIDs;
		Gson summary = new Gson();
		return summary.toJson(clone);
		
		
	}
}
