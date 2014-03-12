package ca.cs.ualberta.localpost.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;





import ca.cs.ualberta.localpost.AndroidMacAddressProvider;


public abstract class UserModel {

	public String username;
	public String tripcode;
	
	//Constructors
	public UserModel() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
		this.username = "anonymous";
		this.tripcode = genTripcode();
	}
	
	//////
	
	//GET USERNAME
	public String getUsername() {
		return username;
	}

	//SET USERNAME automatically updates Tripcode
	public void setUsername(String username) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		this.username = username;
		this.tripcode = genTripcode();
	}

	//GET TRIPCODE
	public String getTripcode() {
		return tripcode;
	}
	
	//GERNERATE TRIPCODE
	public String genTripcode() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException{
		//get the mac address
		String macAddress = getMac();
		
		//hash the username using Hmac algorithm with mac address as the key
		SecretKeySpec keySpec = new SecretKeySpec(macAddress.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(keySpec);
		byte[] result = mac.doFinal(username.getBytes());
		String trip = new String(result, "UTF-8");
		
		//return the trip code
		return trip;
		
	}
	
	//get the mac address from the AndroidMacAddressProvider class
	public String getMac(){
		AndroidMacAddressProvider macprov = new AndroidMacAddressProvider();
		String macAddress = macprov.getMacAddress();
		return macAddress;
	}
	

	
}
