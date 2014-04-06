package ca.cs.ualberta.localpost.model;

import android.content.Context;

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

