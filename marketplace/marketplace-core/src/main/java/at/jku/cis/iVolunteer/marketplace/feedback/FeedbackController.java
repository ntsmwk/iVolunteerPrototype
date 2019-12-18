package at.jku.cis.iVolunteer.marketplace.feedback;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.feedback.Feedback;

@RestController
public class FeedbackController {

	@Autowired FeedbackRepository feedbackRepository;
	
	@GetMapping("/meta/feedback/by-recipient/{recipientId}")
	private List<Feedback> getFeedbackByRecipientId(@PathVariable("recipientId") String recipientId) {
		return feedbackRepository.getByRecipientId(recipientId);
	}
	
	
	
	
}
