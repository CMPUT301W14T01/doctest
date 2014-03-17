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
import java.util.ArrayList;
import java.util.Iterator;

import ca.cs.ualberta.localpost.model.CommentModel;

import com.google.gson.Gson;

/**
 * Controller that draws threads and gets comment models that
 * are flattened(not expanded/viewable
 * 
 * @author Team Radish
 *
 */
public class ThreadViewController {

	public ThreadViewController(){		
	}
	
	/**
	 * This function retrieves an array list of children IDs
	 * that are a reply to a another comment
	 * @param comment model containing the comment 
	 * @return TODO
	 */
	public String getFlattened(CommentModel comment){
		//Class<? extends CommentModel> cloneType = this.getClass();
		//CommentModel clone = cloneType.newInstance();

		ArrayList<java.util.UUID> childIDs;
		Gson summary = new Gson();
		return ""; // summary.toJson();

	}

	/**
	 * This function draws/expands the comment tree (all the replies)
	 * recursively
	 * @param children: comment models that have a parent. A parent
	 *  can be either a Root Comment (Thread comment) or another child
	 * @param depth: nested level of the child model
	 */
	private void tailRecurseDrawable(ArrayList<CommentModel> children, int depth){

		if(children.isEmpty())
			return; //@ BASE CASE

		Iterator<CommentModel> branches = children.iterator();
		while(branches.hasNext()){
			// Should iteratively inject data into views
		}

		tailRecurseDrawable(children, depth+1);
		//tail specifies no operations beyond the recursion
	}
}
