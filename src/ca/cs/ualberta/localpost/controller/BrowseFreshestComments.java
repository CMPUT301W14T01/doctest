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
import ca.cs.ualberta.localpost.model.RootCommentModel;

public class BrowseFreshestComments implements BrowseTopLevelComments {
	ArrayList<RootCommentModel> rootComments;

		/**
		 * gets all the root comments from the rootcommentmodellits
		 */
	@Override
	public void getRootComments() {
		// TODO Auto-generated method stub
		//this.rootComments = RootCommentModelList.getList();
	}

		/**
		 * sorts the comments by the attribute specified by the tab
		 */
	@Override
	public ArrayList<RootCommentModel> sortRootComments(ArrayList<RootCommentModel> comments) {
		// TODO SortByFreshness
		return comments;
	}

		/**
		 * passes the sorted comments to the view
		 */
	@Override
	public ArrayList<RootCommentModel> passSortedRootComments() {
		getRootComments();
		sortRootComments(rootComments);
		return rootComments;
	}

}