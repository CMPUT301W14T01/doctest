package ca.cs.ualberta.localpost.test;

import android.location.Address;
import android.test.InstrumentationTestCase;
import ca.cs.ualberta.localpost.model.StandardUserModel;
import ca.cs.ualberta.localpost.view.GPSLocation;

/**
 * Test the StandardUserModelTest which is used to instantiate new 
 * users
 * @author team01
 *
 */
public class StandardUserModelTest extends InstrumentationTestCase {

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
	 * Test instantiating a new StandardUserModel
	 */
	public final void testStandardUserModel() {
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		assertNotNull(model);		
	}

	/**
	 * Test getting the instance context
	 */
	public final void testGetInstanceContext() {
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		assertNotNull(model);
	}

	/**
	 * Test getting the instance
	 */
	public final void testGetInstance() {
		assertNotNull(StandardUserModel.getInstance());
	}

	/**
	 * Test getters for the username
	 */
	public final void testGetUsername() {
		String testUsername = "testUsername";
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		model.setUsername(testUsername);
		
		assertEquals(model.getUsername(), testUsername);
	}

	/**
	 * Test getters for the address
	 */
	public final void testGetAddress() {
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		GPSLocation gpsLocation = new GPSLocation(getInstrumentation().getTargetContext());
		Address address = gpsLocation.getAddress();
		
		model.setAddress(address);
		
		assertEquals(model.getAddress(), address);
	}

	/**
	 * Test setters for the address
	 */
	public final void testSetAddress() {
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		GPSLocation gpsLocation = new GPSLocation(getInstrumentation().getTargetContext());
		Address address = gpsLocation.getAddress();
		
		model.setAddress(address);
		
		assertNotNull(model.getAddress());
	}

	/**
	 * Test setters for the username
	 */
	public final void testSetUsername() {
		String testUsername = null;
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		model.setUsername(testUsername);
		
		assertNull(model.getUsername());
	}

	/**
	 * Test getters for the tripcode
	 */
	public final void testGetTripcode() {
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		model.genTripcode(getInstrumentation().getTargetContext());
		
		assertNotNull(model.getTripcode());
	}

	/**
	 * Test generating the tripcode
	 */
	public final void testGenTripcode() {
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		model.genTripcode(getInstrumentation().getTargetContext());
		
		assertNotNull(model.getTripcode());
	}

	/**
	 * Test getters for the mac address
	 */
	public final void testGetMac() {
		model = new StandardUserModel(getInstrumentation().getTargetContext());
		
		assertNotNull(model.getMac(getInstrumentation().getTargetContext()));
	}

	/**
	 * Test setters for the author
	 */
	public final void testSetAuthor() {
		model = new StandardUserModel(getInstrumentation().getContext());
		
		String author = "testAuthor";
		
		model.setAuthor(author);
		
		assertEquals(model.getUsername(), author);
		
	}

}
