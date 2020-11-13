//package at.jku.cis.iVolunteer.marketplace.feedback;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import at.jku.cis.iVolunteer.model.feedback.Feedback;
//
//@RestController
//public class FeedbackController {
//
//	@Autowired FeedbackRepository feedbackRepository;
//	
//	@GetMapping("/meta/feedback/by-recipient/{userId}")
//	private List<Feedback> getFeedbackByRecipientId(@PathVariable("userId") String userId) {
//		List<Feedback> feedbackList = feedbackRepository.getByUserId(userId);
//		List<Feedback> returnList = new ArrayList<>();
//		for (Feedback entry : feedbackList) {
//			if (entry.getFeedbackType() != null) {
//				returnList.add(entry);
//			}
//		}
//		
//		return returnList;
//	}
//	
//	
//	
//	
//}
