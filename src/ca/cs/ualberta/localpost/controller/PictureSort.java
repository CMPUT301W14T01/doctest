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

import ca.cs.ualberta.localpost.model.CommentModel;

public class PictureSort {

	public PictureSort(){
		
	}
	/**
	 * takes in an arraylist of comment models and removes all that dont have pictures
	 * @param comments
	 * @return
	 */
	public ArrayList<CommentModel> WithPictures(ArrayList<CommentModel> comments){
		for (int i = 0; i < comments.size(); i++ ){
			if (comments.get(i).getPicture() == null){
				comments.remove(i);
			}
		}
		return comments;
	}
	/**
	 * takes in an arraylist of comment models and removes all that have pictures
	 * @param comments
	 * @return
	 */
	public ArrayList<CommentModel> NoPictures(ArrayList<CommentModel> comments){
		for (int i = 0; i < comments.size(); i++ ){
			if (comments.get(i).getPicture() != null){
				comments.remove(i);
			}
		}
		return comments;
	}


}
