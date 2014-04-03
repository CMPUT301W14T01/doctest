package ca.cs.ualberta.localpost.controller;

import java.util.ArrayList;

import ca.cs.ualberta.localpost.model.CommentModel;

public class PictureSort {

	
	public ArrayList<CommentModel> WithPictures(ArrayList<CommentModel> comments){
		for (CommentModel comment : comments){
			if (comment.getPicture() == null){
				comments.remove(comments.indexOf(comment));
			}
		}
		return comments;
	}
	
	public ArrayList<CommentModel> NoPictures(ArrayList<CommentModel> comments){
		for (CommentModel comment : comments){
			if (comment.getPicture() != null){
				comments.remove(comments.indexOf(comment));
			}
		}
		return comments;
	}
	
	public ArrayList<CommentModel> AllComments(ArrayList<CommentModel> comments){
		return comments;
	}


}
