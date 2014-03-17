/** 
  * Code sourced from: 
  * https://github.com/zztimy/PicPoster/blob/master/src/ca/ualberta/cs/picposter/controller/PicPosterController.java
  * 
  * Changes made by @author timotei
  *//*

package ca.cs.ualberta.localpost.controller;

import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

*//**
 * Controller for PicPost that scales Bitmaps, checks Text, etc.
 * @author zjullion
 *//*
public class BitmapController {


	public static final int MAX_BITMAP_DIMENSIONS = 50;
	public static final int MAX_TEXT_LENGTH = 100;
	
	
	//private PicPosterModelList model;
	//private PicPosterActivity activity;
	
	
	public BitmapController(PicPosterModelList model, PicPosterActivity activity) {
		this.model = model;
		this.activity = activity;
	}
	
	
	*//**
	 * Adds the new pic post to the model, adding a default pic and text if none is assigned, and 
	 * scaling the pic and shortening text as necessary.
	 * @param pic the Bitmap to be displayed in the post
	 * @param text the String to be displayed in the post
	 *//*
	public void addPicPost(Bitmap pic, String text) {
		
		//Assign a default pic if there is none:
		//if (pic == null)
		//	pic = BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.no_img);
		
		//Scale the pic if it is too large:
		if (pic.getWidth() > MAX_BITMAP_DIMENSIONS || pic.getHeight() > MAX_BITMAP_DIMENSIONS) {
			double scalingFactor = pic.getWidth() * 1.0 / MAX_BITMAP_DIMENSIONS;
			if (pic.getHeight() > pic.getWidth())
				scalingFactor = pic.getHeight() * 1.0 / MAX_BITMAP_DIMENSIONS;
			
			int newWidth = (int)Math.round(pic.getWidth() / scalingFactor);
			int newHeight = (int)Math.round(pic.getHeight() / scalingFactor);
			
			pic = Bitmap.createScaledBitmap(pic, newWidth, newHeight, false);
		}
		
		//Shorten the text if it is too long:
		if (text.length() > MAX_TEXT_LENGTH) {
			text = text.substring(0, MAX_TEXT_LENGTH);
		}
		
		this.model.addPicPost(pic, text, new Date());
	}
}*/