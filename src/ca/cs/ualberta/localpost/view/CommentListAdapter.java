package ca.cs.ualberta.localpost.view;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.cs.ualberta.localpost.model.*;

public class CommentListAdapter extends ArrayAdapter<RootCommentModel> {
	Context context;

	public CommentListAdapter(Context context, int resourceId, ArrayList<RootCommentModel> list1) {
		super(context,resourceId,list1);
		this.context = context;
	}
    private class ViewHolder {
        TextView username;
        TextView title;
        TextView turnip;
        TextView location;
        TextView date;
    }
	
    public View getView(int position,View convertView, ViewGroup parent){
		ViewHolder holder = null;
		RootCommentModel model = getItem(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.comment_list_adapter, null);
			holder = new ViewHolder();
			holder.username = (TextView) convertView.findViewById(R.id.CommentUsername);
			holder.title = (TextView) convertView.findViewById(R.id.commentTitle);
			holder.turnip = (TextView) convertView.findViewById(R.id.CommentTurnip);
			holder.location = (TextView) convertView.findViewById(R.id.CommentLocation);
			holder.date = (TextView) convertView.findViewById(R.id.commentDate);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.title.setText(model.getTitle());
//		holder.username.setText("Posted by " + model.getUserName());
//		holder.turnip.setText(model.getTurnip());
//		holder.location.setText("At " + model.getLocation());
//		holder.date.setText("On " + model.getDate());		
		return convertView;
	}
}
