package ca.cs.ualberta.localpost.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import android.graphics.Bitmap;

public abstract class CommentModel {
	private String title;
	private String content;
	private android.location.Location location;
	private String author;
	private ArrayList<CommentModel> children = new ArrayList<CommentModel>();
	private long timestamp;
	private java.util.UUID postId;
	private int radish;
	private Bitmap picture;
	long date = new Date().getTime();
	StandardUserModel user;
	
	public CommentModel(String content, String title) {
		this.title = title;
		this.content = content;
		this.radish = 0;
		this.setPostId(UUID.randomUUID());
		this.timestamp = date;
		this.author = "";
		// this.location = location;
		// this.picture = picture;
	}

	public CommentModel() {
		super();
	}

	public CommentModel(String content, android.location.Location location,
			Bitmap picture) {
		this.content = content;
		this.radish = 0;
		this.setPostId(UUID.randomUUID());
		this.location = location;
		this.timestamp = date;
		this.picture = picture;
		// this.author = UserModel.getUsername();
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return this.author;
	}
//
	public void setAuthor(String author) {
		try {
			this.author = author;
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

	public String getTimestamp() {
		return String.valueOf(timestamp);
	}

	public void setTimestamp(String timestamp) {
		long date = new Date().getTime();
		this.timestamp = date;
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
	 * @param postId
	 *            the postId to set
	 */
	public void setPostId(java.util.UUID postId) {
		this.postId = postId;
	}
}
