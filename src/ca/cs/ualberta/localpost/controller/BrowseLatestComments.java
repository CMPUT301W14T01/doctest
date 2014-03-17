package ca.cs.ualberta.localpost.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModelList;

public class BrowseLatestComments implements BrowseTopLevelComments {
	ArrayList<RootCommentModel> rootComments;

	@Override
	public void getRootComments() {
		// TODO Auto-generated method stub
		this.rootComments = RootCommentModelList.getList();
	}

	@Override
	public ArrayList<RootCommentModel> sortRootComments(ArrayList<RootCommentModel> comments) {
		Comparator<RootCommentModel> comparator = new Comparator<RootCommentModel>() {
			public int compare(RootCommentModel r1, RootCommentModel r2){
				return r2.getRadish() - r1.getRadish();
			}
		};
		return Collections.sort(comments, comparator);
	}

	@Override
	public ArrayList<RootCommentModel> passSortedRootComments() {
		getRootComments();
		sortRootComments(rootComments);
		return rootComments;
	}

}
