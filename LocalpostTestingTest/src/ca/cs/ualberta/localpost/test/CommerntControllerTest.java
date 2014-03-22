/**
 * 
 */
package ca.cs.ualberta.localpost.test;

import junit.framework.TestCase;
import android.app.Activity;
import ca.cs.ualberta.localpost.controller.CommentController;
import ca.cs.ualberta.localpost.model.RootCommentModel;

/**
 * @author timotei
 *
 */
public class CommerntControllerTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * @param name
	 */
	public CommerntControllerTest() {
		super();
	}

	/**
	 * Test whether we can instantiate a CommentController
	 */
	public final void testCommentController() {
		RootCommentModel model = new RootCommentModel("TestContent", "TestTitle");
		Activity activity = new Activity();
		
		CommentController controller = new CommentController(model, activity);
		
		assertNotNull(controller);		
	}

	/**
	 * Test whether we can update the content of the comment
	 */
	public final void testUpdateContent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.controller.CommentController#spawnComment()}.
	 */
	public final void testSpawnComment() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.controller.CommentController#pushComment(ca.cs.ualberta.localpost.model.CommentModel)}.
	 */
	public final void testPushComment() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.controller.CommentController#getComment(ca.cs.ualberta.localpost.model.CommentModel)}.
	 */
	public final void testGetComment() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.controller.CommentController#updateComment(java.lang.String, java.lang.String)}.
	 */
	public final void testUpdateComment() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link ca.cs.ualberta.localpost.controller.CommentController#getEntityContent(org.apache.http.HttpResponse)}.
	 */
	public final void testGetEntityContent() {
		fail("Not yet implemented"); // TODO
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
