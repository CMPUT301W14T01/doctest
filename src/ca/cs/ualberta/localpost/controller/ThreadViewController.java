package ca.cs.ualberta.localpost.controller;

import java.util.ArrayList;
import java.util.Iterator;

import ca.cs.ualberta.localpost.model.CommentModel;

import com.google.gson.Gson;

public class ThreadViewController {

	public ThreadViewController(){
		
	}
	
	//TODO re-implement
	public String getFlattened(CommentModel comment){
		//Class<? extends CommentModel> cloneType = this.getClass();
		//CommentModel clone = cloneType.newInstance();

		ArrayList<java.util.UUID> childIDs;
		Gson summary = new Gson();
		return ""; // summary.toJson();

	}

	private void tailRecurseDrawable(ArrayList<CommentModel> children, int depth){

		if(children.isEmpty())
			return; //@ BASE CASE

		Iterator<CommentModel> branches = children.iterator();
		while(branches.hasNext()){
			// Should iteratively inject data into views
		}

		tailRecurseDrawable(children, depth+1);
		//tail specifies no operations beyond the recursion
	}
}
