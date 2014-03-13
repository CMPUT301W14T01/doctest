package ca.cs.ualberta.localpost.controller;

import java.util.Date;

import android.location.Location;

public class FreshnessAlgorithm {
	
	int freshness;
	
	public FreshnessAlgorithm(int greatness, Date date, Location commentLocation, Location userLocation){
		int distance = (int)commentLocation.distanceTo(userLocation);
		Date currentDate = new Date();
		int oldness = daysBetween(date, currentDate);
		float fresh = (2 * (float)greatness / (float)oldness) * (1000 / (float)distance) + 3;
		this.freshness = (int)fresh;
	}
	
	private int daysBetween(Date date1, Date date2){

		long diff = date2.getTime() - date1.getTime();
		int diffDays =  (int) (diff / (24* 1000 * 60 * 60));
		return diffDays;
	}

}
