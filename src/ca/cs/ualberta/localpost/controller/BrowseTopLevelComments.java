package ca.cs.ualberta.localpost.controller;

import java.util.ArrayList;

import ca.cs.ualberta.localpost.model.RootCommentModelList;

public interface BrowseTopLevelComments {
	
	public RootCommentModelList getRootComments();
	public RootCommentModelList sortRootComments(RootCommentModelList comments);
	public RootCommentModelList passSortedRootComments(RootCommentModelList comments);
	

}
