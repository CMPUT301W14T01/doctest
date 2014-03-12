package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;

public class RootCommentModelList {

	private ArrayList<CommentModel> list;
	
	public void addParent(CommentModel new_root){
		list.add(new_root);
	}
}
