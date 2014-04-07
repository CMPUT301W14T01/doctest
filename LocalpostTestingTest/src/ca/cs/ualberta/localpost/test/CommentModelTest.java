package ca.cs.ualberta.localpost.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.graphics.Bitmap;
import android.location.Address;
import android.test.InstrumentationTestCase;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.view.GPSLocation;

/**
 * This files tests both the CommentModel with all its
 * getters, setters and any other functions.
 * 
 * @author team01
 * 
 */
public class CommentModelTest extends InstrumentationTestCase {

	private RootCommentModel model;
	
	public CommentModelTest() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test whether we can instantiate a CommentModel with content, title,
	 * picture and context as arguments.
	 */
	public final void testCommentModelStringStringBitmapContext() {
		String comment_content = "This is a ContentTest1";
		String comment_title = "This is a TitleTest1";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		assertNotNull(model);
	}

	/**
	 * Test whether we can instantiate a CommentModel with content, title, location,
	 * picture and context as arguments.
	 */
	public final void testCommentModelStringStringAddressBitmapContext() {
		
		String comment_content = "This is a ContentTest2";
		String comment_title = "This is a TitleTest2";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		GPSLocation gpsLocation = new GPSLocation(getInstrumentation().getTargetContext());
		Address address = gpsLocation.getAddress();
		
		model = new RootCommentModel(comment_content, comment_title, address, picture, getInstrumentation().getContext());
		
		assertNotNull(model);
	}

	/**
	 * Test the title getter
	 */
	public final void testGetTitle() {
		String comment_content = "This is a ContentTest3";
		String comment_title = "This is a TitleTest3";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		assertNotNull(model.getTitle());
	}

	/**
	 * Test the author getter
	 */
	public final void testGetAuthor() {
		String comment_content = "This is a ContentTest4";
		String comment_title = "This is a TitleTest4";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		assertNotNull(model.getAuthor());
	}

	/**
	 * Test the author setter
	 */
	public final void testSetAuthor() {
		String comment_content = "This is a ContentTest5";
		String comment_title = "This is a TitleTest5";
		String author = "testAuthor";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		model.setAuthor(author);
		
		assertEquals(model.getAuthor(), author);
	}

	/**
	 * Test the title setter
	 */
	public final void testSetTitle() {
		String comment_content = "This is a ContentTest6";
		String comment_title = "This is a TitleTest6";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, null, picture, getInstrumentation().getContext());

		model.setTitle(comment_title);
		
		assertNotNull(model.getTitle(), comment_title);
	}

	/**
	 * test the picture getter
	 */
	public final void testGetPicture() {
		String comment_content = "This is a ContentTest7";
		String comment_title = "This is a TitleTest7";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
	}

	/**
	 * Test the picture setter
	 */
	public final void testSetPicture() {
		String comment_content = "This is a ContentTest8";
		String comment_title = "This is a TitleTest8";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, null, getInstrumentation().getContext());
		
		model.setPicture(picture);
		
		assertEquals(model.getPicture(), picture);
		
	}

	/**
	 * Test the content getter
	 */
	public final void testGetContent() {
		String comment_content = "This is a ContentTest9";
		String comment_title = "This is a TitleTest9";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		assertNotNull(model.getContent());
	}

	/**
	 * Test the content setter
	 */
	public final void testSetContent() {
		String comment_content = "This is a ContentTest10";
		String comment_title = "This is a TitleTest10";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(null, comment_title, picture, getInstrumentation().getContext());
		
		model.setContent(comment_content);
		
		assertEquals(model.getContent(), comment_content);
	}

	/**
	 * Test the address getter
	 */
	public final void testGetAddress() {
		String comment_content = "This is a ContentTest11";
		String comment_title = "This is a TitleTest11";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		GPSLocation gpsLocation = new GPSLocation(getInstrumentation().getTargetContext());
		Address address = gpsLocation.getAddress();
		
		model = new RootCommentModel(comment_content, comment_title, address, picture, getInstrumentation().getContext());
		
		model.setAddress(address);
		
		assertNotNull(model.getAddress());
	}

	/**
	 * Test the address setter
	 */
	public final void testSetAddress() {
		String comment_content = "This is a ContentTest12";
		String comment_title = "This is a TitleTest12";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		GPSLocation gpsLocation = new GPSLocation(getInstrumentation().getTargetContext());
		Address address = gpsLocation.getAddress();
		
		model = new RootCommentModel(comment_content, comment_title, null, picture, getInstrumentation().getContext());
		
		model.setAddress(address);
		
		assertNotNull(model.getAddress());
	}

	/**
	 * Test the timestamp getter
	 */
	public final void testGetTimestamp() {
		String comment_content = "This is a ContentTest13";
		String comment_title = "This is a TitleTest13";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		assertNotNull(model.getTimestamp());
	}

	/**
	 * Test the timestamp setter
	 */
	public final void testSetTimestamp() {
		String comment_content = "This is a ContentTest14";
		String comment_title = "This is a TitleTest14";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		long date = new Date().getTime();
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
	
		model.setTimestamp(date);
		
		assertEquals(model.getTimestamp(), date);
	}

	/**
	 * Test the radish getter
	 */
	public final void testGetRadish() {
		String comment_content = "This is a ContentTest15";
		String comment_title = "This is a TitleTest15";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		model.setFreshness(0);
		
		assertNotNull(model.getRadish());
	}

	/**
	 * Test the freshness getter
	 */
	public final void testGetFreshness() {
		String comment_content = "This is a ContentTest16";
		String comment_title = "This is a TitleTest16";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		model.setFreshness(4);
		
		assertEquals(model.getFreshness(), 4);
	}

	/**
	 * Test the setter
	 */
	public final void testSetFreshness() {
		String comment_content = "This is a ContentTest17";
		String comment_title = "This is a TitleTest17";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());

		model.setFreshness(5);
		
		assertEquals(model.getFreshness(), 5);
	}

	/**
	 * Test increasing the rating
	 */
	public final void testIncRadish() {
		String comment_content = "This is a ContentTest18";
		String comment_title = "This is a TitleTest18";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		model.incRadish();
		
		assertEquals(model.getRadish(), 1);
	}

	/**
	 * Test decreasing the rating
	 */
	public final void testDecRadish() {
		String comment_content = "This is a ContentTest19";
		String comment_title = "This is a TitleTest19";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		model.incRadish();
		model.decRadish();
		
		assertEquals(model.getRadish(), 0);
		
	}

	/**
	 * Test the postid getter
	 */
	public final void testGetPostId() {
		String comment_content = "This is a ContentTest20";
		String comment_title = "This is a TitleTest20";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		assertNotNull(model.getPostId());
	}

	/**
	 * Test the postid setter
	 */
	public final void testSetPostId() {
		String comment_content = "This is a ContentTest21";
		String comment_title = "This is a TitleTest21";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		
		java.util.UUID postId = UUID.randomUUID();
		model.setPostId(postId);
		
		assertEquals(model.getPostId(), postId);
	}

	/**
	 * Test the children getter
	 */
	public final void testGetChildren() {
		String comment_content = "This is a ContentTest23";
		String comment_title = "This is a TitleTest23";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		java.util.UUID postId = UUID.randomUUID();
		
		model.addChild(postId.toString());
		
		assertNotNull(model.getChildren());
	}

	/**
	 * Test adding new children
	 */
	public final void testAddChild() {
		String comment_content = "This is a ContentTest24";
		String comment_title = "This is a TitleTest24";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		java.util.UUID postId = UUID.randomUUID();
		
		model.addChild(postId.toString());
		
		assertNotNull(model.getChildren());
	}

	/**
	 * Test the children setter
	 */
	public final void testSetChildren() {
		String comment_content = "This is a ContentTest25";
		String comment_title = "This is a TitleTest25";
		Bitmap picture = HelperFunctions.generateBitmap(50,50);
		
		model = new RootCommentModel(comment_content, comment_title, picture, getInstrumentation().getContext());
		java.util.UUID postId = UUID.randomUUID();
		
		ArrayList<String> children = new ArrayList<String>();
		
		children.add(postId.toString());

		model.setChildren(children);
		
		assertEquals(model.getChildren(), children);
	}

}
