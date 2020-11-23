package at.jku.cis.iVolunteer.marketplace.meta.core.relationship;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.cis.iVolunteer.marketplace._mapper.relationship.RelationshipMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

@Service
public class RelationshipService {

	@Autowired RelationshipRepository relationshipRepository;
	@Autowired RelationshipMapper relationshipMapper;

	public List<RelationshipDTO> getRelationshipsByIdAsDTO(@RequestBody List<String> ids) {
		List<Relationship> relationships = getRelationshipsById(ids);
		return relationshipMapper.toTargets(relationships);
	}
	
	public List<Relationship> getRelationshipsById(List<String> ids) {
		List<Relationship> relationships = new ArrayList<Relationship>();
		relationshipRepository.findAll(ids).forEach(relationships::add);
		return relationships;
	}

	public List<RelationshipDTO> addOrUpdateRelationships(@RequestBody List<RelationshipDTO> relationships) {
		return relationshipMapper.toTargets(relationshipRepository.save(relationshipMapper.toSources(relationships)));

	}

	public List<RelationshipDTO> deleteRelationship(@RequestBody List<String> idsToRemove) {
		for (String id : idsToRemove) {
			relationshipRepository.delete(id);
		}

		return relationshipMapper.toTargets(relationshipRepository.findAll());
	}

}
