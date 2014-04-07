package ca.cs.ualberta.localpost.test;

import android.graphics.Bitmap;
import android.location.Address;
import android.test.InstrumentationTestCase;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.view.GPSLocation;

/**
 * This files tests both the RootCommentModel and the CommentModel with all their 
 * getters, setters and any other functions.
 * To be implemented: Location testing once we implement location based services
 *
 * @author team01
 * 
 */
public class RootCommentModelTest extends InstrumentationTestCase {

	private RootCommentModel model;
	
	public RootCommentModelTest() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test whether we can instantiate a RootCommentModel with content, title,
	 * picture and context as arguments.
	 */
	public final void testRootCommentModelStringStringBitmapContext() {
		String comment_content = "This is a ContentTest1";
		String comment_title = "This is a TitleTest1";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		assertNotNull(model);
		
	}

	/**
	 * Test whether we can instantiate a RootCommentModel with content, title, location,
	 * picture and context as arguments.
	 */
	public final void testRootCommentModelStringStringAddressBitmapContext() {
		String comment_content = "This is a ContentTest2";
		String comment_title = "This is a TitleTest2";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		GPSLocation gpsLocation = new GPSLocation(getInstrumentation().getTargetContext());
		Address address = gpsLocation.getAddress();
		
		model = new RootCommentModel(comment_content, comment_title, address, picture, getInstrumentation().getContext());
		
		assertNotNull(model);
	}
}
