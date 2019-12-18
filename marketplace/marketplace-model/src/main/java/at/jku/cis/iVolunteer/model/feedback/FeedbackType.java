package at.jku.cis.iVolunteer.model.feedback;

import com.fasterxml.jackson.annotation.JsonCreator;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;

public enum FeedbackType {
	
	KUDOS("KUDOS"), STARRATING("STARRATING");
	
	private final String feedbackType;

	private FeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getFeedbackType() {
		return this.feedbackType;
	}

	@Override
	public String toString() {
		return feedbackType;
	}

	@JsonCreator
	public static FeedbackType getFromFeedbackType(String feedbackType) {
		for (FeedbackType t : FeedbackType.values()) {
			if (t.getFeedbackType().equals(feedbackType)) {
				return t;
			}
		}
		throw new IllegalArgumentException();

	}
}
