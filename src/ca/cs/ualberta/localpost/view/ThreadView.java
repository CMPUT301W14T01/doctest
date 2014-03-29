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

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import ca.cs.ualberta.localpost.model.ChildCommentModel;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;
//-- Temporary import for tests

/**
 * Displays a comment and all the replies associated
 * with the comment
 * @author Team 01
 */
public class ThreadView extends Activity {
	private TableLayout table;
	private final int marginBase = 10;
	private final int depthTolerance = 5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		//Creates a Table
		table = (TableLayout) findViewById(R.id.table_layout);
		testDrawable();
	}

	public void threadExpand(CommentModel comment, int level) {
		draw(comment, level);
		++level;
		ArrayList<CommentModel> commentChildren = comment.getChildren();
		if(level<depthTolerance && !commentChildren.isEmpty()){
			for(CommentModel c : commentChildren){
				threadExpand(c, level);
				Log.e("test",c.getPostId().toString());
			}
		}
	}
	
	public void draw(final CommentModel comment, int level){
		TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.row, null);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		// Pad root comments vertically and child comments horizontally
		if(level < depthTolerance){
			params.setMargins(level*marginBase, 0, 0, 0);
		}
		
		row.setLayoutParams(params);
		
		//Register onclick
		registerForContextMenu(row);
		
		row.setOnClickListener(new View.OnClickListener() 
		{                   
		    @Override
		    public void onClick(View v)
		    {

		    	Toast.makeText(getApplicationContext(), comment.getPostId().toString(), Toast.LENGTH_SHORT).show();
		    }
		});
		
		Log.e("getUUID",comment.getPostId().toString());

		((TextView) row.findViewById(R.id.commentTitle)).setText(comment.getContent());
		table.addView(row);
		row = null;
	}
	
	public void testDrawable(){
		/*
		 * ("test0","title0")
		 * 			|-- ("test1","title1")
		 * 			|-- ("test2","title2")
		 * 						|-- ("test3","title3")
		 */
		
		CommentModel testCommentTree = new RootCommentModel("test0","title0");
			CommentModel tct1 = new ChildCommentModel("test1","title1");
			CommentModel tct2 = new ChildCommentModel("test2","title2");
				tct2.addChild(new ChildCommentModel("test3","title3"));
			CommentModel tct4 = new ChildCommentModel("test4","test4");
		testCommentTree.addChild((ChildCommentModel) tct1);
		testCommentTree.addChild((ChildCommentModel) tct2);
		testCommentTree.addChild((ChildCommentModel) tct4);

		threadExpand(testCommentTree,0);
	}
	
	public void recursiveSearch(String content, CommentModel comment) {
		ArrayList<CommentModel> commentChildren = comment.getChildren();
		if (!commentChildren.isEmpty()) {
			for (CommentModel c : commentChildren) {
				if (c.getContent().equals(content)) {
					// c.addChild(reply);
					Log.e("Found It", "Found");
					break;
				} else {
					recursiveSearch(content, c);
				}
			}
		} else {
			Log.e("ArrayEmpty", "ArrayEmpty");
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		Log.e("Context",v.toString());
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, Menu.FIRST, 0, "Reply");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case Menu.FIRST:
			Toast.makeText(getApplicationContext(), "Reply", Toast.LENGTH_SHORT).show();
			table.getChildAt(1);
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thread_view, menu);
		return true;
	}

}
