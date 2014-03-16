package ca.cs.ualberta.localpost.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import android.content.Context;

public class StandardUserModel extends UserModel {

	public StandardUserModel() throws InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		super();
		//this.username = "anonymous";
		// this.tripcode = genTripcode();
		
	}

	@Override
	public String getUsername(){
		return super.getUsername();
	}

	@Override
	public void setUsername(String username) throws InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		super.setUsername(username);
	}

	@Override
	public String getTripcode() {
		return super.getTripcode();
	}

	@Override
	public String genTripcode() throws NoSuchAlgorithmException,
			InvalidKeyException, UnsupportedEncodingException {
		return super.genTripcode();
	}

	@Override
	public String getMac() {
		return super.getMac();
	}
}
