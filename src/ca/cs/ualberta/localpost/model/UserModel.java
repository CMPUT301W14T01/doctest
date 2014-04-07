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


import android.content.Context;
import android.location.Address;
import android.util.Log;
import ca.cs.ualberta.localpost.controller.AndroidMacAddressProvider;

/**
 * This class is a model of a user. It has attributes username and tripcode. It also has a function that generates the tripcode as a function of 
 * username and mac address. 
 * @author team01
 *
 */
public abstract class UserModel {

	protected String username;
	protected Address address = null;
	protected String tripcode;

	/** Constructors
	 * 

	 */
	public UserModel(Context context) {
		super();
		this.username = "anonymous";
		this.address = getAddress();
		this.tripcode = genTripcode(context);
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
		//this.tripcode = genTripcode();
	}

	// GET TRIPCODE
	public String getTripcode() {
		return tripcode;
	}

	/** GERNERATE TRIPCODE
	 * 
	 * @return tripcode

	 */
	public String genTripcode(Context context){
		// get the mac address
		String macAddress = getMac(context);
		String trip = Integer.toString(macAddress.hashCode());
		return trip;

	}

	/** get the mac address from the AndroidMacAddressProvider class
	 * 
	 * @return Mac Address used for tripcode
	 */
	public String getMac(Context context) {
		AndroidMacAddressProvider macprov = new AndroidMacAddressProvider();
		String macAddress = macprov.getMacAddress(context).toString();
		return macAddress;
	}

	public void setAuthor(String author) {
		try {
			setUsername(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
