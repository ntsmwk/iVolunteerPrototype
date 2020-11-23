package at.jku.cis.iVolunteer.configurator.meta.core.relationship;

import java.util.List;

import org.kie.api.definition.type.Modifies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipType;

@Repository
public interface RelationshipRepository extends MongoRepository<Relationship, String> {

	List<Relationship> findBySourceAndRelationshipType(String source, RelationshipType relationshipType);

	List<Relationship> findByTargetAndRelationshipType(String target, RelationshipType relationshipType);
	
	List<Relationship> findBySource(String source);
	
	List<Relationship> findByTarget(String target);
	
	void deleteByIdIn(List<String> ids);

}
	