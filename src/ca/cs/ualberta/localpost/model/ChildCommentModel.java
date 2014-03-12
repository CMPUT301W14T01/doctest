package ca.cs.ualberta.localpost.model;

import android.graphics.Bitmap;

public class ChildCommentModel extends CommentModel {

	protected CommentModel parent;
	public ChildCommentModel(String content) {
		super(content);
	}
		
	public ChildCommentModel(String content, android.location.Location location, Bitmap picture) {
		super(content, location, picture);
		//TODO specify parent
		// parent = 
	}
}
