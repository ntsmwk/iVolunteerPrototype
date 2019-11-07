package at.jku.cis.iVolunteer.marketplace.meta.core.relationship;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.relationship.RelationshipMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

@RestController
public class RelationshipController {

	@Autowired RelationshipRepository relationshipRepository;
	@Autowired RelationshipMapper relationshipMapper;
	
	@GetMapping("/meta/core/relationship/all")
	private List<RelationshipDTO> findAllRelationships() {
		return relationshipMapper.toDTOs(relationshipRepository.findAll());
	}
	
	@GetMapping("/meta/core/relationship/{id}")
	RelationshipDTO getRelationshipById(@PathVariable("id") String id) {
		RelationshipDTO ret = relationshipMapper.toDTO(relationshipRepository.findOne(id));
		return ret;
	}
	
	@PutMapping("/meta/core/relationship/multiple")
	List<RelationshipDTO> getRelationshipsById(@RequestBody List<String> ids) {
		List<Relationship> relationships = new ArrayList<Relationship>();
		relationshipRepository.findAll(ids).forEach(relationships::add);
		return relationshipMapper.toDTOs(relationships);
	}
	
	@PutMapping("/meta/core/relationship/add-or-update")
	List<RelationshipDTO> addOrUpdateRelationships(@RequestBody List<RelationshipDTO> relationships) {
		System.out.println("Relationship # " + relationships.size());
		
		return relationshipMapper.toDTOs(relationshipRepository.save(relationshipMapper.toEntities(relationships)));
	}

	
	@PutMapping("/meta/core/relationship/add-inheritance")
	private Inheritance addInheritance(@RequestBody Inheritance inheritance) {
		System.out.println(inheritance.getClassId1() + "--" + inheritance.getClassId2());
		System.out.println(inheritance.getSuperClassId());
		
		return relationshipRepository.save(inheritance);
	
	}
	
	@PutMapping("/meta/core/relationship/add-association")
	private Association addAssociation(@RequestBody Association association) {
		System.out.println(association.getClassId1() + "--" + association.getClassId2());
		System.out.println(association.getParam2() + "--" + association.getParam1());

		
		return relationshipRepository.save(association);
		
	}
	
	@PutMapping("/meta/core/relationship/delete")
	private List<Relationship> deleteRelationship(@RequestBody List<String> idsToRemove) {
		for (String id : idsToRemove) {
			relationshipRepository.delete(id);
		}
		
		return relationshipRepository.findAll();
	}
	
	@GetMapping("/meta/core/relationship/start/{id}/all")
	List<Relationship> getRelationshipsWithStartId(@PathVariable("id") String id) {
		return null;
	}
	
	@GetMapping("/meta/core/relationship/end/{id}/all")
	List<Relationship> getRelationshipsWithEndId(@PathVariable("id") String id) {
		return null;
	}
	
//	  addRelationshipsInOneGo(marketplace: Marketplace, relationships: Relationship[]) {
//		    return this.http.post(`${marketplace.url}/meta/core/relationship/add`, relationships);
//		  }


	
	
}
