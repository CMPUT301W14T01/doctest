package ca.cs.ualberta.localpost.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import ca.cs.ualberta.localpost.view.MainActivity;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest(Class<MainActivity> name) {
		super(MainActivity.class);
		Intent intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
		//startActivity(intent);
		
	}

	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testGetModel() {
		fail("Not yet implemented"); // TODO
	}

	public final void testOnTabReselected() {
		fail("Not yet implemented"); // TODO
	}

	public final void testOnTabSelected() {
		fail("Not yet implemented"); // TODO
	}

	public final void testOnTabUnselected() {
		fail("Not yet implemented"); // TODO
	}

	public final void testOnCreateOptionsMenuMenu() {
		fail("Not yet implemented"); // TODO
	}

	public final void testOnOptionsItemSelectedMenuItem() {
		fail("Not yet implemented"); // TODO
	}

}
