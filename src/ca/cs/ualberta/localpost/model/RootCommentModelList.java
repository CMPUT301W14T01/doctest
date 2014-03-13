package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;

public class RootCommentModelList {

	private static ArrayList<CommentModel> list;
	
	public void addParent(CommentModel new_root){
		list.add(new_root);
	}

	public static ArrayList<CommentModel> getList() {
		return list;
	}
	
	
}
 
