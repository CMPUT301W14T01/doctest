package ca.cs.ualberta.localpost.controller;

import java.util.ArrayList;

import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModelList;

public interface BrowseTopLevelComments {
	
	public void getRootComments();
	public ArrayList<RootCommentModel> sortRootComments(ArrayList<RootCommentModel> comments);
	public ArrayList<RootCommentModel> passSortedRootComments();
	

}
