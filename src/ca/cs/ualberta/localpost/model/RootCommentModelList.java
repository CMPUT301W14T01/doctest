package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;

public class RootCommentModelList {

	private static ArrayList<RootCommentModel> list;
	
	public void addParent(RootCommentModel new_root){
		list.add(new_root);
	}

	public static ArrayList<RootCommentModel> getList() {
		return list;
	}
	
	
}
 
