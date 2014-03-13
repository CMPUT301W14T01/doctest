package ca.cs.ualberta.localpost.model;

import java.util.ArrayList;

import ca.cs.ualberta.localpost.view.MainActivity;
import ca.cs.ualberta.localpost.controller.Serialize;

public class ThreadList {

	private static ArrayList<CommentModelList> threadlist;
	public ThreadList(MainActivity mainActivity) {
		threadlist = Serialize.loadFromFile(mainActivity);
	}
	public static ArrayList<CommentModelList> getThreadlist() {
		return threadlist;
	}
	public void updatingThreadlist(CommentModelList new_thread) {
		for(int i = 0; i < threadlist.size(); ++i){
			if (((new_thread.getList()).get(1)).getPostId().equals((threadlist.get(i)).getList().get(1).getPostId() )  ){
				threadlist.remove(i);
			}
		}
		threadlist.add(new_thread);
		//TODO call from activity
		//Serialize.SaveInFile(threadlist, this);
	}
	
	
}