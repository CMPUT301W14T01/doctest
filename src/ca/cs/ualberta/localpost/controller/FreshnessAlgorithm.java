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

import java.util.Date;
import java.util.List;

import android.location.Address;
import android.location.Location;
import android.util.Log;

/**
 * This algorithm takes data from a comment and also the location of a user and combines that information to determining the relative freshness of
 * that comment to that user. freshness grows inversely with days old and distance from user. It grows positively with greatness
 * @author team01
 *
 */

public class FreshnessAlgorithm {
	
	int freshness;
	
	/**
	 * runs the algorithm
	 * @param greatness
	 * @param l
	 * @param address
	 * @param address2
	 */
	public FreshnessAlgorithm(int greatness, long l, Address address, Address address2){
		float[] results = new float [1];
		Location.distanceBetween(address.getLatitude(), address.getLongitude(), address2.getLatitude(), address2.getLongitude(), results);
		int distance = (int)results[0];
		Date currentDate = new Date();
		int oldness = daysBetween(l, currentDate);
		float fresh = (2 * (float)greatness / (float)(oldness + 1)) * (1000 / (float)(distance + 1)) + 3;
		this.freshness = (int)fresh;
	}
	
	/**
	 * returns number of days between two dates
	 * @param date1
	 * @param date2
	 * @return the difference bewteen the days
	 */
	private int daysBetween(long date1, Date date2){

		long diff = date2.getTime() - date1;
		int diffDays =  (int) (diff / (1000 * 60 * 60));
		return diffDays;
	}

	public int getFreshness() {
		return freshness;
	}


}
