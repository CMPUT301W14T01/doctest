package ca.cs.ualberta.localpost.controller;

import java.util.ArrayList;

import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModelList;

public class BrowseGreatestComments implements BrowseTopLevelComments {
	ArrayList<CommentModel> rootComments;

	@Override
	public void getRootComments() {
		// TODO Auto-generated method stub
		this.rootComments = RootCommentModelList.getList();
	}

	@Override
	public RootCommentModelList sortRootComments(RootCommentModelList comments) {
		// TODO SortByGreatness
		return null;
	}

	@Override
	public ArrayList<CommentModel> passSortedRootComments(ArrayList<CommentModel> comments) {
			return rootComments;
	}

}
