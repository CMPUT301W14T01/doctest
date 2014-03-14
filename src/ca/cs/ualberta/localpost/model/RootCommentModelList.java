package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;

import ca.cs.ualberta.localpost.view.MainActivity;

public class RootCommentModelList {


	private static ArrayList<RootCommentModel> list;
	
	
	public static ArrayList<RootCommentModel> getList() {
		list = new ArrayList<RootCommentModel>();
		for (int i = 0; i < MainActivity.getList().getThreadlist().size(); ++i ){
			list.add((RootCommentModel) MainActivity.getList().getThreadlist().get(i).getList().get(0));
		}

		return list;
	}
	
	
}
 
