package at.jku.cis.iVolunteer.marketplace.configurable.relationship;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.configurable.class_.relationship.Relationship;

@Repository
public interface RelationshipRepository extends MongoRepository<Relationship, String> {

}
