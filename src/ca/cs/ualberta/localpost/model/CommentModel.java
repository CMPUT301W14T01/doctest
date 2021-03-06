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
import java.util.Observable;
import java.util.Random;
import java.util.UUID;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;

/**
 * This class is the base abstract class for our comment models.
 * It hold all the attributes a comment should have, as well as 
 * the proper setters and getters 
 * @author Team 01
 *
 */
public abstract class CommentModel extends Observable{
	Random rand = new Random();
	//Drawables for thread/commentView
	private String title;
	private Address address;
	private String author;
	private String content;
	private Bitmap picture = null;
	private long timestamp;

	//Internal data for controllers
	private ArrayList<String> children = new ArrayList<String>();
	private java.util.UUID postId = UUID.randomUUID();
	private int radish;
	private int freshness;
	long date = new Date().getTime();
	StandardUserModel user;
	
	/**
	 * Basic constructor that was used for testing purposes and creating holders
	 * 
	 */
	public CommentModel(Context context) {
		super();
		try {
			user = StandardUserModel.getInstance(context);
		} catch (Exception e) {
			e.printStackTrace();		
		}
		this.title = null;
		this.content = null;
		this.picture = null;
		this.radish = rand.nextInt(100);
		this.setPostId(postId);
		this.timestamp = date;
		this.author = getAuthor();
		this.address = user.getAddress();
	}

	/**
	 * Constructor accepting a picture 
	 * @param content : represents the content of the comment
	 * @param title : represents the title of a comment
	 * @param picture : is the picture that is attached to the comment
	 * @param context : context is passed down from the activity to get the standard user model instance
	 */
	public CommentModel(String content, String title,
			Bitmap picture, Context context) {
		try {
			user = StandardUserModel.getInstance(context);
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
		this.address = user.getAddress();

		
	}
	
	/**
	 * Final constructor that will be used once location and pictures are added to our comments
	 * known error : title needs to be added as a parameter 
	 * @param content : represents the content of the comment
	 * @param title   : title of the comment
	 * @param address : is the location the comment was made from or said to be made from by the user
	 * @param picture : is the picture that is attached to the comment
	 * @param context : context is passed down from the activity to get the standard user model instance
	 */
	public CommentModel(String content, String title, Address address,
			Bitmap picture, Context context) {
		try {
			user = StandardUserModel.getInstance(context);
		} catch (Exception e) {
			e.printStackTrace();		
		}
		this.content = content;
		
		this.radish = 0;
		this.setPostId(UUID.randomUUID());
		this.address = address;
		this.timestamp = date;
		this.picture = picture;
		this.author = getAuthor();
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		this.author = user.getUsername();
		return this.author;
	}

	public void setAuthor(String author) {
		user.setAuthor(author); 
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
	
	public int getFreshness() {
		return freshness;
	}
	public int setFreshness(int fresh) {
		return this.freshness = fresh;
	}
 
	public void incRadish() {
		this.radish += 1;
	}
 
	public void decRadish() {
		this.radish -= 1;
	}

	public java.util.UUID getPostId() {
		return postId;
	}

	public void setPostId(java.util.UUID postId) {
		this.postId = postId;
	}

	public ArrayList<String> getChildren() {
		return children;
	}

	public void addChild(String string) {
		this.children.add(string);
	}

	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}
}
