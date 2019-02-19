package at.jku.cis.iVolunteer.marketplace.group.interaction;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.group.interaction.GroupInteraction;

@Repository
public interface GroupInteractionRepository extends MongoRepository<GroupInteraction, String> {

}
