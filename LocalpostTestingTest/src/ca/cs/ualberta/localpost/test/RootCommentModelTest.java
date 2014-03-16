/**
 * This files tests both the RootCommentModel and the CommentModel with all their 
 * getters, setters and any other functions.
 * To be implemented: Location testing once we implement location based services
 */
package ca.cs.ualberta.localpost.test;

import java.util.Date;
import java.util.UUID;

import junit.framework.TestCase;
import android.graphics.Bitmap;
import android.location.Location;
import ca.cs.ualberta.localpost.model.RootCommentModel;

/**
 * @author timotei
 *
 */
public class RootCommentModelTest extends
TestCase {

	private RootCommentModel model;

	protected void setUp() throws Exception {
		super.setUp();		
	}

	/**
	 * @param name
	 */
	public RootCommentModelTest() {
		super();
	}

	/**
	 * Test whether we can create a model
	 */
	public final void testRootCommentModel() {
		// Instantiate a RootCommentModel
		model = new RootCommentModel();

		// Check if the new model is instantiated, meaning that is should not be null
		assertNotNull(model);
	}

	/**
	 * Test whether we can create a model with a new Content and Title
	 */
	public final void testRootCommentModelStringString() {
		String comment_content = "This is a ContentTest2";
		String comment_title = "This is a TitleTest2";

		model = new RootCommentModel(comment_content, comment_title);

		assertEquals(comment_content, model.getContent());
		assertEquals(comment_title, model.getTitle());
	}

	/**
	 * Test whether we can create a model with a new Content, Location and Picture
	 */
	public final void testRootCommentModelStringLocationBitmap() {
		String comment_content = "This is a ContentTest3";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		Location location = null;
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


		model = new RootCommentModel(comment_content, location, picture);

		assertEquals(comment_content, model.getContent());
		// Once location is implemented, we can get and return and actual location
		//assertEquals(location, model.getLocation());
		assertEquals(picture, model.getPicture());

	}

	/**
	 * Test whether we can retrieve an Author
	 */
	public final void testGetAuthor() {
		model = new RootCommentModel();
		
		assertEquals("anonymous",model.getAuthor());
	}

	/**
	 * Test whether we can set an Author
	 */
	public final void testSetAuthor() {
		model = new RootCommentModel();
		String author = "TestAuthor";
		model.setAuthor(author);
		
		assertEquals(author,model.getAuthor());
	}
	
	/**
	 * Test whether we can retrieve the Title
	 */
	public final void testGetTitle() {
		model = new RootCommentModel();
		String title = model.getTitle();
		
		assertNull(title);
	}

	/**
	 * Test whether the Title can be set
	 */
	public final void testSetTitle() {
		model = new RootCommentModel();
		String title = "TestTitle";
		model.setTitle(title);
		
		assertEquals(title, model.getTitle());
	}
	
	/**
	 * Test whether we can set a picture
	 */
	public final void testSetPicture() {
		model = new RootCommentModel();
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		model.setPicture(picture);
		
		assertEquals(picture, model.getPicture());
	}

	/**
	 * Test whether we can get a picture. Use the setPicture method we previously tested
	 */
	public final void testGetPicture() {
		model = new RootCommentModel();
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		// Have to assign a picture as the default picture initialization is null
		// which makes it hard to test as is whether retrieval works 
		model.setPicture(picture);
		
		assertNotNull(model.getPicture());
	}

	/**
	 * Test whether we can set the content
	 */
	public final void testSetContent() {
		model = new RootCommentModel();
		String content = "TestContent";
		model.setContent(content);
		
		assertEquals(content, model.getContent());
	}
	
	/**
	 * Test whether we can get the content that was set which 
	 * forces us to use the previous set function as the default initialization is null 
	 */
	public final void testGetContent() {
		model = new RootCommentModel();
		String content = "TestContent";
		model.setContent(content);
				
		assertNotNull(model.getContent());
	}

	/**
	 * Test whether we can retrieve the timestamp
	 */
	public final void testGetTimestamp() {
		model = new RootCommentModel();
		
		assertNotNull(model.getTimestamp());
	}

	/**
	 * Test whether we can set a new timestamp
	 */
	public final void testSetTimestamp() {
		long date = new Date().getTime();
		model = new RootCommentModel();
		model.setTimestamp(date);
		
		assertEquals(date, model.getTimestamp());
	}

	/**
	 * Test whether we can retrieve our counter
	 */
	public final void testGetRadish() {
		model = new RootCommentModel();
		
		// Radish variable initialization should be 0
		assertEquals(0, model.getRadish());
	}

	/**
	 * Test  whether we can increase our counter
	 */
	public final void testIncRadish() {
		model = new RootCommentModel();
		model.incRadish();
		
		// After initialization of the variable, the radish increase function is 
		// called once which means that the value of the variable should 
		// be 1.
		assertEquals(1, model.getRadish());
	}

	/**
	 * Test  whether we can decrease our counter
	 */
	public final void testDecRadish() {
		model = new RootCommentModel();
		model.decRadish();
		
		// After initialization of the variable radish, the decrease function is 
		// called once which means that the value of the variable should 
		// be -1.
		assertEquals(-1, model.getRadish());		
	}

	/**
	 * Test whether we can retrieve the postId
	 */
	public final void testGetPostId() {
		model = new RootCommentModel();
		
		assertNotNull(model.getPostId());
	}

	/**
	 * Test whether we can set the postId
	 */
	public final void testSetPostId() {
		model = new RootCommentModel();
		java.util.UUID postId = UUID.randomUUID();
		model.setPostId(postId);
		
		assertEquals(postId, model.getPostId());
	}
	
	// TODO
	/**
	 * Test whether we can get the location
	 */
	public final void testGetLocation() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test whether we can set the location
	 */
	public final void testSetLocation() {
		fail("Not yet implemented"); // TODO
	}
}
