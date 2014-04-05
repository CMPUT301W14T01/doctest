/**
 * 
 */
package ca.cs.ualberta.localpost.test;

import ca.cs.ualberta.localpost.model.StandardUserModel;
import junit.framework.TestCase;

/**
 * @author timotei
 *
 */
public class StandardUserModelTest extends TestCase {

	StandardUserModel model;

	public StandardUserModelTest() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test whether we can instantiate a new StandardUserModel
	 */
	public final void testStandardUserModel() {
		try {
			model = StandardUserModel.getInstance();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}		
		assertNotNull(model);
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.model.StandardUserModel#getInstance()}.
	 */
	public final void testGetInstance() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test whether we can retrieve the username after creating the 
	 * StandardUserModel
	 */
	public final void testGetUsername() {
		try {
			model = StandardUserModel.getInstance();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		assertNotNull(model.getUsername());
	}

	/**
	 * Test whether we can retrieve the address after creating the 
	 * StandardUserModel
	 */
	public final void testGetAddress() {
		try {
			model = StandardUserModel.getInstance();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test whether we can set the address after creating the 
	 * StandardUserModel
	 */
	public final void testSetAddress() {
		try {
			model = StandardUserModel.getInstance();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test whether we set retrieve the username after creating the 
	 * StandardUserModel
	 */
	public final void testSetUsername() {
		try {
			model = StandardUserModel.getInstance();
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
	 * Test method for {@link ca.cs.ualberta.localpost.model.UserModel#getTripcode()}.
	 */
	public final void testGetTripcode() {
		try {
			model = StandardUserModel.getInstance();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.model.UserModel#genTripcode()}.
	 */
	public final void testGenTripcode() {
		try {
			model = StandardUserModel.getInstance();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.model.UserModel#getMac()}.
	 */
	public final void testGetMac() {
		try {
			model = StandardUserModel.getInstance();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		fail("Not yet implemented"); // TODO
	}

}
