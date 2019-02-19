package at.jku.cis.iVolunteer.model.comment;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {
	private String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	

}
