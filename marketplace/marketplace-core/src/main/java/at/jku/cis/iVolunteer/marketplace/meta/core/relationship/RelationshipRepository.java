package at.jku.cis.iVolunteer.marketplace.meta.core.relationship;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@Repository
public interface RelationshipRepository extends MongoRepository<Relationship, String> {

	List<Relationship> findBySourceAndRelationshipType(String source, RelationshipType relationshipType);

	List<Relationship> findByTargetAndRelationshipType(String target, RelationshipType relationshipType);
	
	List<Relationship> findBySource(String source);
	
	List<Relationship> findByTarget(String target);
}
	