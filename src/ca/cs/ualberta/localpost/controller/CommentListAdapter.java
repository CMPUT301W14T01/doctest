/**
 * The MIT License (MIT)
 * Copyright (c) 2014 Timotei Albu, David Chau-Tran, Alain Clark, Shawn Anderson, Mickael Zerihoun
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 *	this software and associated documentation files (the "Software"), to deal in
 *	the Software without restriction, including without limitation the rights to
 *	use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so,
 *	subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *  
 */

package ca.cs.ualberta.localpost.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;
import ca.cs.ualberta.localpost.view.R;

/**
 * Custom ListView adapter that allows us to change the
 * layout of each listview element according our own
 * specifications.
 * @author Team 01
 *
 */

public class CommentListAdapter extends ArrayAdapter<CommentModel> {
	Context context;

	public CommentListAdapter(Context context, int resourceId, ArrayList<CommentModel> list1) {
		super(context,resourceId,list1);
		this.context = context;
	}
    private class ViewHolder {
        TextView username;
        TextView title;
        TextView radish;
        TextView location;
        TextView timestamp;
        ImageView picture; 
    }

    public View getView(int position,View convertView, ViewGroup parent){
		ViewHolder holder = null;
		CommentModel model = getItem(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.comment_list_adapter, null);
			if(model.Ismarked() && model.getTrueid().equals(StandardUserModel.getInstance().getTripcode())){
				convertView.setBackgroundColor(Color.YELLOW);
			}
			holder = new ViewHolder();
			holder.username = (TextView) convertView.findViewById(R.id.CommentAuthor);
			holder.title = (TextView) convertView.findViewById(R.id.commentTitle);
			holder.radish = (TextView) convertView.findViewById(R.id.CommentRadish);
			holder.location = (TextView) convertView.findViewById(R.id.CommentLocation);
			holder.timestamp = (TextView) convertView.findViewById(R.id.commentDate);
			holder.picture = (ImageView) convertView.findViewById(R.id.CommentPicture);
			
			holder.username.setTextSize(4*getContext().getResources().getDisplayMetrics().density);
			holder.title.setTextSize(5*getContext().getResources().getDisplayMetrics().density);
			holder.radish.setTextSize(4*getContext().getResources().getDisplayMetrics().density);
			holder.location.setTextSize(4*getContext().getResources().getDisplayMetrics().density);
			holder.timestamp.setTextSize(4*getContext().getResources().getDisplayMetrics().density);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		SimpleDateFormat format = new SimpleDateFormat("HH:mm MM/dd/yy");
		
		holder.title.setText(model.getTitle());
		holder.username.setText(model.getAuthor()+ " - ");
		holder.radish.setText(Integer.toString(model.getRadish()));
		holder.picture.setImageBitmap(model.getPicture());
		if (model.getAddress() == null){
			holder.location.setText(" - @ No location - ");
		}
		else{
			if(model.getAddress().getAddressLine(0).length()>20){
				String temp = model.getAddress().getAddressLine(0).substring(0,20)+"...";
				holder.location.setText(" - @ " + temp +" - ");
			}
			else
				holder.location.setText(" - @ " + model.getAddress().getAddressLine(0)+" - ");
		}
		//Log.e("Address", model.getAddress().getAddressLine(0).toString());
		holder.timestamp.setText(format.format(new Date(model.getTimestamp())));				
		return convertView;
	}
}
