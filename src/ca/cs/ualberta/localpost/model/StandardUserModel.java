package ca.cs.ualberta.localpost.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class StandardUserModel extends UserModel {
	
	
	
	public StandardUserModel() throws InvalidKeyException,
	NoSuchAlgorithmException, UnsupportedEncodingException {
		this.username = "anonymous";
		//this.tripcode = genTripcode();
}

	/*
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getUsername();
	}
	*/

	@Override
	public void setUsername(String username) throws InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		super.setUsername(username);
	}


	@Override
	public String getTripcode() {
		// TODO Auto-generated method stub
		return super.getTripcode();
	}


	@Override
	public String genTripcode() throws NoSuchAlgorithmException,
			InvalidKeyException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return super.genTripcode();
	}


	@Override
	public String getMac() {
		// TODO Auto-generated method stub
		return super.getMac();
	}
	





}
