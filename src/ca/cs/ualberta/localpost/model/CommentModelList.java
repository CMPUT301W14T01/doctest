package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Bitmap;



public class CommentModelList {

	private ArrayList<CommentModel> list;
	
	public CommentModelList(String content, android.location.Location location, Bitmap picture) {
		this.list = new ArrayList<CommentModel>();
		CommentModel new_root = new RootCommentModel(content, location, picture);
		list.add(new_root);
		//TODO send new list to elastic search?
	}
	
	public void AddchildComment(String content, android.location.Location location, Bitmap picture){
		CommentModel new_comment = new ChildCommentModel(content, location, picture);
		list.add(new_comment);
	}
}
