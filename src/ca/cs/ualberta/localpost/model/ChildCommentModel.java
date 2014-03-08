package ca.cs.ualberta.localpost.model;

public class ChildCommentModel extends CommentModel {

	protected CommentModel parent;
	public ChildCommentModel(String content, android.location.Location location) {
		super(content, location);
	}
	
	

}
