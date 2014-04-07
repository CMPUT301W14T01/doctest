package ca.cs.ualberta.localpost.test;

import android.graphics.Bitmap;
import android.location.Address;
import android.test.InstrumentationTestCase;
import ca.cs.ualberta.localpost.model.ChildCommentModel;
import ca.cs.ualberta.localpost.view.GPSLocation;

/**
 * 
 * @author team01
 *
 */
public class ChildCommentModelTest extends InstrumentationTestCase {

	private ChildCommentModel model;
	
	public ChildCommentModelTest() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	/**
	 * Test whether we can instantiate a ChildCommentModel 
	 */
	public final void testChildCommentModelContext() {

		model = new ChildCommentModel(getInstrumentation().getContext());
		
		assertNotNull(model);
	}

	/**
	 * Test whether we can instantiate a ChildCommentModel with content, title,
	 * picture and context as arguments.
	 */
	public final void testChildCommentModelStringStringBitmapContext() {
		String comment_content = "This is a ContentTest1";
		String comment_title = "This is a TitleTest1";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new ChildCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		assertNotNull(model);
	}

	/**
	 * Test whether we can instantiate a ChildCommentModel with content, title, location,
	 * picture and context as arguments.
	 */
	public final void testChildCommentModelStringStringAddressBitmapContext() {
		String comment_content = "This is a ContentTest2";
		String comment_title = "This is a TitleTest2";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		GPSLocation gpsLocation = new GPSLocation(getInstrumentation().getTargetContext());
		Address address = gpsLocation.getAddress();
		
		model = new ChildCommentModel(comment_content, comment_title, address, picture, getInstrumentation().getContext());
		
		assertNotNull(model);
		
	}

}
