package at.jku.cis.iVolunteer.marketplace.group;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.group.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
	public Group findByName(String name);

	public Group findById(String id);
}
