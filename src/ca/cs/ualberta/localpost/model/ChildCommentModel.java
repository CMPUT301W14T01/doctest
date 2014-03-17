package ca.cs.ualberta.localpost.model;

import android.graphics.Bitmap;

/**
 * This class is an extension of the Comment Model class.
 * It varies from the original by having a parent attribute.
 * It is used for our structural purposes when representing who is a child and whom its parent is
 * @author Team 01
 *
 */
public class ChildCommentModel extends CommentModel {

	protected CommentModel parent;
	
	/**
	 * Basic constructors for child comment model: 
	 * the different ones correspond to different steps of our process
	 * This constructor is used for creating the class
	 */
	public ChildCommentModel(){
		super();
	}
	/**
	 * This constructor is used while we can't yet add locations and pictures to our comments
	 * @param content
	 * @param title
	 */
	public ChildCommentModel(String content, String title) {
		super(content, title);
	}
	/**
	 * This will be our final constructor when we are able to add locations and pictures to our comments
	 * @param content
	 * @param location
	 * @param picture
	 */
	public ChildCommentModel(String content, android.location.Location location, Bitmap picture) {
		super(content, location, picture);
		//TODO specify parent
		// parent = 
	}
}
