package ca.cs.ualberta.localpost.controller;

import java.util.ArrayList;

import ca.cs.ualberta.localpost.model.CommentModel;

public class PictureSort {

	public PictureSort(){
		
	}
	public ArrayList<CommentModel> WithPictures(ArrayList<CommentModel> comments){
		for (int i = 0; i < comments.size(); i++ ){
			if (comments.get(i).getPicture() == null){
				comments.remove(i);
			}
		}
		return comments;
	}
	
	public ArrayList<CommentModel> NoPictures(ArrayList<CommentModel> comments){
		for (int i = 0; i < comments.size(); i++ ){
			if (comments.get(i).getPicture() != null){
				comments.remove(i);
			}
		}
		return comments;
	}
	
	public ArrayList<CommentModel> AllComments(ArrayList<CommentModel> comments){
		return comments;
	}


}
