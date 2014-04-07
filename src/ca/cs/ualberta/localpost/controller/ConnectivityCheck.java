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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * The purpose of this class is to check for connectivity. It checks for both wifi and 3G network.
 * @author Team 01
 *
 */
public class ConnectivityCheck {
	     
	    private Context _context;
	    /**
	     * The constructor simply gives the class a context because it is required when checking for connectivtiy
	     * @param context
	     */
	    public ConnectivityCheck(Context context){
	        this._context = context;
	    }
	 
	    /**
	     * This function checks for connectivity and returns a boolean depending on its status.
	     * If there is connectivity it returns true, else it returns false.
	     * @return boolean
	     */
	    public boolean isConnectingToInternet(){
	        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null) 
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null) 
	                  for (int i = 0; i < info.length; i++) 
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	 
	          }
	          return false;
	    }
	}

