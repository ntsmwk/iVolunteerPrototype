package at.jku.cis.iVolunteer.model.feedback;

import java.util.List;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.hash.IHashObject;

public class Feedback extends IVolunteerObject implements IHashObject {
	
	String name;
	String description;
	
	FeedbackType feedbackType;
	int feedbackValue;
		
	List<String> iVolunteerObjecIds; //Für was
	
	String recipientId; //Für wen
	String issuerId; //Von wem
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FeedbackType getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(FeedbackType feedbackType) {
		this.feedbackType = feedbackType;
	}

	public int getFeedbackValue() {
		return feedbackValue;
	}

	public void setFeedbackValue(int feedbackValue) {
		this.feedbackValue = feedbackValue;
	}

	public List<String> getiVolunteerObjecIds() {
		return iVolunteerObjecIds;
	}

	public void setiVolunteerObjecIds(List<String> iVolunteerObjecIds) {
		this.iVolunteerObjecIds = iVolunteerObjecIds;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	@Override
	public String toHashObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
