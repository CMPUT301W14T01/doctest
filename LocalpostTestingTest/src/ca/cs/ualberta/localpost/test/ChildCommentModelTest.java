/**
 * 
 */
package ca.cs.ualberta.localpost.test;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import ca.cs.ualberta.localpost.model.ChildCommentModel;
import junit.framework.TestCase;

/**
 * @author timotei
 *
 */
public class ChildCommentModelTest extends TestCase {

	private ChildCommentModel model;
	/**
	 * @param name
	 */
	public ChildCommentModelTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test whether we can instantiate a ChilCommentModel
	 */
	public final void testChildCommentModel() {
		// TODO test this
		model = new ChildCommentModel(null);
		
		assertNotNull(model);		
	}

	/**
	 * Test whether we can instantiate a ChilCommentModel with content and title
	 * as arguments
	 */
	public final void testChildCommentModelStringString() {
		String comment_content = "This is a ContentTest1";
		String comment_title = "This is a TitleTest1";

		model = new ChildCommentModel(comment_content, comment_title);

		assertEquals(comment_content, model.getContent());
		assertEquals(comment_title, model.getTitle());
	}

	/**
	 * Test whether we can instantiate a ChilCommentModel with content, locaiton
	 * and picture as arguments.
	 */
	public final void testChildCommentModelStringLocationBitmap() {
		String comment_content = "This is a ContentTest3";
		String comment_title = "This is a TitleTest3";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		Address location = null;
		
		//Double latitude = 45.05024;
		//Double Longitude = 50.976563; 


		//Location location = new Location(provider);

		//location.setLatitude(0);
		//location.setLongitude(0);		

		//LocationManager locationManager;

		//Context context = activity;

		//LocationManager locationManager = (LocationManager) context
		//		.getSystemService(Context.LOCATION_SERVICE);

		//Criteria criteria = new Criteria();
		//String provider = locationManager.getBestProvider(criteria, false);
		//Location location = locationManager.getLastKnownLocation(provider);


		model = new ChildCommentModel(comment_content, comment_title, location, picture);

		assertEquals(comment_content, model.getContent());
		// Once location is implemented, we can get and return and actual location
		//assertEquals(location, model.getLocation());
		assertEquals(picture, model.getPicture());
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	

}
