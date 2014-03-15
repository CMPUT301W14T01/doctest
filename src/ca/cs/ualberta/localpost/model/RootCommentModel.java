package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class RootCommentModel extends CommentModel {
	
	public RootCommentModel(){
		super();
	}
	
	public RootCommentModel(String content, String title) {
		super(content, title);
	}
	
	public RootCommentModel(String content,android.location.Location location, Bitmap picture) {
		super(content, location, picture);
	}

}
