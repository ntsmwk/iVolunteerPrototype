package at.jku.cis.iVolunteer.marketplace.feedback;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.feedback.Feedback;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String>{

	List<Feedback> getByUserId(String userId);

}
