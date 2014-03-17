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

package ca.cs.ualberta.localpost.view;

import ca.cs.ualberta.localpost.model.CommentModel;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

/**
 * NOT DONE OR IMPLEMENTED
 * Displays a comment and all the replies associated
 * with the comment
 * @author Team 01
 */
public class ThreadView extends Activity {
	private TableLayout table;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		//Creates a Table
		table = (TableLayout) findViewById(R.id.table_layout);

		threadExpand("Parent 1", 0);
		threadExpand("Child 1", 1);
		threadExpand("Child 2", 2);
		threadExpand("Child 3", 3);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);
		threadExpand("Parent 1", 0);

		// commentText = (TextView) view.findViewById(R.id.commentTitle);
		// turnipText = (TextView) view.findViewById(R.id.turnip);
		// posterText = (TextView) view.findViewById(R.id.posterName);
		// geoText = (TextView) view.findViewById(R.id.geolocation);
	}

	// TODO move to CommentModel args for tRD
	public void threadExpand(String text, int level) {
		final int marginBase = 20;
		final int depthTolerance = 5;
		
		TableRow row = (TableRow) LayoutInflater.from(this)
				.inflate(R.layout.row, null);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		
		// Pad root comments vertically
		// and child comments horizontally
		if(level == 0){
			params.setMargins(0,15,0,0);
		}else if(level < depthTolerance){
			params.setMargins(level*marginBase, 0, 0, 0);
		}
		row.setLayoutParams(params);

		((TextView) row.findViewById(R.id.commentTitle)).setText(text);
		table.addView(row);
		row = null;
	}
	//-------------
	
	public void threadExpand(CommentModel comment, int level) {
		//TODO Build from threadExpand(String, int)
		threadExpand(comment.getContent(),level);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thread_view, menu);
		return true;
	}

}
