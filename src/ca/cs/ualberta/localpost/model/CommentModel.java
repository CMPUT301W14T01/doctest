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
package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.UUID;

import com.google.gson.Gson;

import android.graphics.Bitmap;

/**
 * This class is the base abstract class for our comment models.
 * It hold all the attributes a comment should have, as well as 
 * the proper setters and getters 
 * @author Team 01
 *
 */
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
	
	/**
	 * Basic constructor that was used for testing purposes
	 * 
	 */
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
	
	/**
	 * Basic constructor that assigns the base values for a comment except for the content and title which are parameters that passed
	 * in. 
	 * @param content : represents the content of the comment
	 * @param title : represents the title of a comment
	 */
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

	/**
	 * Constructor accepting a picture 
	 * @param content : represents the content of the comment
	 * @param title : represents the title of a comment
	 * @param picture : is the picture that is attached to the comment
	 */
	public CommentModel(String content, String title,
			Bitmap picture) {
		try {
			user = new StandardUserModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		this.title = title;
		this.content = content;
		this.radish = 0;
		this.setPostId(UUID.randomUUID());
		this.timestamp = date;
		this.picture = picture;
		this.author = getAuthor();
	}
	
	/**
	 * Final constructor that will be used once location and pictures are added to our comments
	 * known error : title needs to be added as a parameter 
	 * @param content : represents the content of the comment
	 * @param location : is the location the comment was made from or said to be made from by the user
	 * @param picture : is the picture that is attached to the comment
	 */
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
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	/**
	 * Fetches the user's username
	 * 
	 */
	public String getAuthor() {
		this.author = user.getUsername();
		return this.author;
	}

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
	/**
	 * Increment the radish count aka ratings
	 */
	public void incRadish() {
		this.radish += 1;
	}
	/**
	 * Decrement radish count
	 */
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
	 * @param postId : the postId to set
	 */
	public void setPostId(java.util.UUID postId) {
		this.postId = postId;
	}
	
	
}
