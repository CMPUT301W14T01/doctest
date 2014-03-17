/**
 * 
 */
package ca.cs.ualberta.localpost.test;

import junit.framework.TestCase;
import ca.cs.ualberta.localpost.AndroidMacAddressProvider;
import ca.cs.ualberta.localpost.model.StandardUserModel;

/**
 * @author timotei
 *
 */
public class StandardUserModelTest extends TestCase {

	private StandardUserModel model;
	
	public StandardUserModelTest() {
		super();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test whether we can instantiate a new StandardUserModel
	 */
	public final void testStandardUserModel() {
		try {
			model = new StandardUserModel();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}		
		assertNotNull(model);
	}
	
	/**
	 * Test whether we can retrieve the username after creating the 
	 * StandardUserModel
	 */
	public final void testGetUsername() {
		try {
			model = new StandardUserModel();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}		
		assertEquals("anonymous",model.getUsername());
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.model.StandardUserModel#setUsername(java.lang.String)}.
	 */
	public final void testSetUsername() {
		// Instantiate a new StandardUserModel
		try {
			model = new StandardUserModel();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}		
		
		// Create a new username string
		String username = "TestUsername";
		
		// Set the username to the string created above
		try {
			model.setUsername(username);
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(username,model.getUsername());
	}

	/**
	 * Test whether we can retrieve the mac address after creating the 
	 * StandardUserModel
	 */
	public final void testGetMac() {
		try {
			model = new StandardUserModel();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		AndroidMacAddressProvider macprov = new AndroidMacAddressProvider();
		String macAddress = macprov.getMacAddress();
		assertEquals(macAddress,model.getMac());
	}
	
	/**
	 * Test whether we can retrieve the tripcode after creating the 
	 * StandardUserModel
	 */
	public final void testGetTripcode() {
		try {
			model = new StandardUserModel();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}		
		assertNotNull(model.getTripcode());
	}

	/**
	 * Test whether we can generate the tripcode after creating the 
	 * StandardUserModel
	 */
	public final void testGenTripcode() {
		fail("Not yet implemented"); // TODO
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
