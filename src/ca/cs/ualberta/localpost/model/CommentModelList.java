package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Bitmap;



public class CommentModelList {

	private ArrayList<CommentModel> list;
	
	public ArrayList<CommentModel> getList() {
		return list;
	}

	public void setList(ArrayList<CommentModel> list) {
		this.list = list;
	}
	
	public CommentModelList(){
		super();
	}

	public CommentModelList(RootCommentModel new_root){
		this.list = new ArrayList<CommentModel>();
		list.add(new_root);
		//TODO call from activity
		//ThreadList.updatingThreadlist(this);
	}
	
	public void AddchildComment(ChildCommentModel new_child, java.util.UUID postId){
		//TODO for loop over Threadlist (in Threadlist) look for which prefix corresponds to this child
		// and add it there.
		list.add(new_child);
	}
}
