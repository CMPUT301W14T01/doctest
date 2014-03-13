package ca.cs.ualberta.localpost.controller;

import java.util.ArrayList;

import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModelList;

public interface BrowseTopLevelComments {
	
	public void getRootComments();
	public RootCommentModelList sortRootComments(RootCommentModelList comments);
	public ArrayList<CommentModel> passSortedRootComments(ArrayList<CommentModel> comments);
	

}
