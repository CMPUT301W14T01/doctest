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

import android.location.Address;
import ca.cs.ualberta.localpost.AndroidMacAddressProvider;

/**
 * This class is a model of a user. It has attributes username and tripcode. It also has a function that generates the tripcode as a function of 
 * username and mac address. 
 * @author team01
 *
 */

public abstract class UserModel {

	protected String username;
	protected Address address;
	protected String tripcode;

	/** Constructors
	 * 

	 */
	public UserModel() {
		super();
		this.username = "anonymous";
		//this.address = getAddress();
		this.tripcode = genTripcode();
	}

	// GET USERNAME
	public String getUsername() {
		return this.username;

	}
	
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 * @return 
	 */
	public Address setAddress(Address address) {
		return this.address = address;
	}

	public void setUsername(String username){
		this.username = username;
		this.tripcode = genTripcode();
	}

	// GET TRIPCODE
	public String getTripcode() {
		return tripcode;
	}

	/** GERNERATE TRIPCODE
	 * 
	 * @return tripcode

	 */
	public String genTripcode(){
		// get the mac address
		//String macAddress = getMac();
		

		
		return "tripcode";

	}

	/** get the mac address from the AndroidMacAddressProvider class
	 * 
	 * @return Mac Address used for tripcode
	 */
	public String getMac() {
		AndroidMacAddressProvider macprov = new AndroidMacAddressProvider();
		String macAddress = macprov.getMacAddress().toString();
		return macAddress;
	}

}
