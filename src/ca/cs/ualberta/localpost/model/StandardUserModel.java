package ca.cs.ualberta.localpost.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class StandardUserModel extends UserModel {
	 private static StandardUserModel instance = null;
	 
	 protected StandardUserModel() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
	      // Exists only to defeat instantiation.
		 super();
	   }
	 public static StandardUserModel getInstance() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
	      if(instance == null) {
	         instance = new StandardUserModel();
	      }
	      return instance;
	   }

}

