package ca.cs.ualberta.localpost.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import ca.cs.ualberta.localpost.controller.Serialize;

import android.content.Context;

public class StandardUserModel extends UserModel {
	 private static StandardUserModel instance = null;
	 
	 protected StandardUserModel(Context context) {
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

}

