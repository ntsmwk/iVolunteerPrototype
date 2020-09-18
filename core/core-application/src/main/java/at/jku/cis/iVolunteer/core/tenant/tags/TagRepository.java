package at.jku.cis.iVolunteer.core.tenant.tags;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.tenant.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {
}
