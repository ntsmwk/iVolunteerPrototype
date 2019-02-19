package at.jku.cis.iVolunteer.marketplace.comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.comment.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String>{

}
