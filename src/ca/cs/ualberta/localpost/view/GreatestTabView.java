package ca.cs.ualberta.localpost.view;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import ca.cs.ualberta.localpost.model.RootCommentModel;
 
public class GreatestTabView extends Fragment {
	private ListView listView;
	ArrayList<RootCommentModel> model = new ArrayList<RootCommentModel>();
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.tab, container, false);
        
		listView = (ListView) rootView.findViewById(R.id.commentList);
		CommentListAdapter adapter = new CommentListAdapter(getActivity(), R.id.custom_adapter, model);
		listView.setAdapter(adapter); 
         
        return rootView;
    }
}
