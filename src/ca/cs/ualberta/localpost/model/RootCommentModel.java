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

package ca.cs.ualberta.localpost.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;

/**
 * This class represents top level comments aka "root" comments
 * It differs from a child comment by it's lack of a parent attribute
 * @author Team 01
 *
 */
public class RootCommentModel extends CommentModel {
	

	/**
	 * This constructor is used while we can't yet add locations to our comments
	 * @param content : the content of the comment
	 * @param title : the comment's title
	 * @param picture : the comment's picture
	 * @param context : context is passed down from the activity to get the standard user model instance
	 */
	public RootCommentModel(String content, String title, Bitmap picture, Context context) {
		super(content, title, picture, context);
	}
	
	/**
	 * This will be our final constructor when we are able to add locations and pictures to our comments
	 * known error : a title needs to be added 
	 * @param content : the content of the comment
	 * @param title   : the title of the comment
	 * @param address : it's location
	 * @param picture : the attached picture
	 * @param context : context is passed down from the activity to get the standard user model instance
	 */
	public RootCommentModel(String content, String title, Address address, Bitmap picture, Context context) {
		super(content, title, address, picture, context);
	}

}
