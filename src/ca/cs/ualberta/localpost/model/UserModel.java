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

package ca.cs.ualberta.localpost.model;

import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import ca.cs.ualberta.localpost.AndroidMacAddressProvider;

/**
 * This class is a model of a user. It has attributes username and tripcode. It also has a function that generates the tripcode as a function of 
 * username and mac address. 
 * @author team01
 *
 */

public abstract class UserModel {

	protected String username;
	protected String tripcode;

	/** Constructors
	 * 
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public UserModel() throws InvalidKeyException, NoSuchAlgorithmException,
			UnsupportedEncodingException {
		super();
		this.username = "anonymous";
		//this.tripcode = genTripcode();
	}

	// GET USERNAME
	public String getUsername() {
		return this.username;

	}

	public void setUsername(String username) throws InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		this.username = username;
		//this.tripcode = genTripcode();
	}

	// GET TRIPCODE
	public String getTripcode() {
		return tripcode;
	}

	/** GERNERATE TRIPCODE
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 */
	public String genTripcode() throws NoSuchAlgorithmException,
			InvalidKeyException, UnsupportedEncodingException {
		// get the mac address
		String macAddress = getMac();

		// hash the username using Hmac algorithm with mac address as the key
		SecretKeySpec keySpec = new SecretKeySpec(macAddress.getBytes(),
				"HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(keySpec);
		byte[] result = mac.doFinal(username.getBytes());
		String trip = new String(result, "UTF-8");

		// return the trip code
		return trip;

	}

	/** get the mac address from the AndroidMacAddressProvider class
	 * 
	 * @return
	 */
	public String getMac() {
		AndroidMacAddressProvider macprov = new AndroidMacAddressProvider();
		String macAddress = macprov.getMacAddress().toString();
		return macAddress;
	}

}
