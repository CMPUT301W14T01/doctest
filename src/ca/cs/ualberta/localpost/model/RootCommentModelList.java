package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;

public class RootCommentModelList {


	private ArrayList<RootCommentModel> list;
	

	public ArrayList<RootCommentModel> getList() {
		for (int i = 0; i < ThreadList.getThreadlist().size(); ++i ){
			list.add((RootCommentModel) ThreadList.getThreadlist().get(i).getList().get(1));
		}

		return list;
	}
	
	
}
 
