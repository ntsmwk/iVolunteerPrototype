package at.jku.cis.iVolunteer.marketplace.meta.core.relationship;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace._mapper.relationship.RelationshipMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

@Service
public class RelationshipService {

	@Autowired RelationshipRepository relationshipRepository;
	@Autowired RelationshipMapper relationshipMapper;

//	public List<RelationshipDTO> findAllRelationships() {
//		return relationshipMapper.toTargets(relationshipRepository.findAll());
//	}

//	public RelationshipDTO getRelationshipById(@PathVariable("id") String id) {
//		return relationshipMapper.toTarget(relationshipRepository.findOne(id));
//	}

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

//	@PutMapping("/meta/core/relationship/add-inheritance")
//	private Inheritance addInheritance(@RequestBody Inheritance inheritance) {
//		return relationshipRepository.save(inheritance);
//
//	}
//
//	@PutMapping("/meta/core/relationship/add-association")
//	private Association addAssociation(@RequestBody Association association) {
//		System.out.println(association.getSource() + "--" + association.getTarget());
//		System.out.println(association.getSourceCardinality() + "--" + association.getTargetCardinality());
//
//		return relationshipRepository.save(association);
//
//	}

	public List<RelationshipDTO> deleteRelationship(@RequestBody List<String> idsToRemove) {
		for (String id : idsToRemove) {
			relationshipRepository.delete(id);
		}

		return relationshipMapper.toTargets(relationshipRepository.findAll());
	}

//	List<RelationshipDTO> getRelationshipsWithStartId(@PathVariable("id") String id) {
//		return null;
//	}
//
//	List<RelationshipDTO> getRelationshipsWithEndId(@PathVariable("id") String id) {
//		return null;
//	}

//	  addRelationshipsInOneGo(marketplace: Marketplace, relationships: Relationship[]) {
//		    return this.http.post(`${marketplace.url}/meta/core/relationship/add`, relationships);
//		  }

}
