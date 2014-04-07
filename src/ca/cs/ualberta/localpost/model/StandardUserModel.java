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

/**
 * Standard User model is used to instantiate new users. Implements the singleton design pattern
 * @author team01
 *
 */
public class StandardUserModel extends UserModel {
	 private static StandardUserModel instance = null;
	 
	 public StandardUserModel(Context context) {
	      // Exists only to defeat instantiation.
		 super(context);
	   }
	 public static StandardUserModel getInstance(Context context) {
	      if(instance == null) {
	         instance = new StandardUserModel(context);
	      }
	      return instance;
	   }
	 
	 public static StandardUserModel getInstance() {
			 return instance;
	   }
	 
	 public static StandardUserModel setInstance(StandardUserModel user) {
		 return instance = user;
	 }




}

