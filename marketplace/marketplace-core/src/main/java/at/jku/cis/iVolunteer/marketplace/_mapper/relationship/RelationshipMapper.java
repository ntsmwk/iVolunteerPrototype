package at.jku.cis.iVolunteer.marketplace._mapper.relationship;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Aggregation;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@Component
public class RelationshipMapper implements AbstractMapper<Relationship, RelationshipDTO> {

	@Autowired
	AssociationRelationshipDTOMapper associationMapper;
	@Autowired
	InheritanceRelationshipDTOMapper inheritanceMapper;
	@Autowired
	AggregationRelationshipDTOMapper aggregationMapper;

	@Override
	public RelationshipDTO toTarget(Relationship source) {
		if (source == null) {
			return null;
		} else if (source.getRelationshipType().equals(RelationshipType.ASSOCIATION)) {
			return associationMapper.toTarget((Association) source);
		} else if (source.getRelationshipType().equals(RelationshipType.INHERITANCE)) {
			return inheritanceMapper.toTarget((Inheritance) source);
		} else if (source.getRelationshipType().equals(RelationshipType.AGGREGATION)) {
			return aggregationMapper.toTarget((Aggregation) source);
		} else {
			throw new NotAcceptableException("RelationshipType not recognized");
		}
	}

	@Override
	public List<RelationshipDTO> toTargets(List<Relationship> sources) {
		if (sources == null) {
			return null;
		}

		List<RelationshipDTO> targets = new LinkedList<RelationshipDTO>();
		for (Relationship r : sources) {
			targets.add(this.toTarget(r));
		}

		return targets;
	}

	@Override
	public Relationship toSource(RelationshipDTO target) {

		if (target == null) {
			return null;
		} else if (target.getRelationshipType().equals(RelationshipType.ASSOCIATION)) {
			return associationMapper.toSource(target);
		} else if (target.getRelationshipType().equals(RelationshipType.INHERITANCE)) {
			return inheritanceMapper.toSource(target);
		} else if (target.getRelationshipType().equals(RelationshipType.AGGREGATION)) {
			return aggregationMapper.toSource(target);
		} else {
			throw new NotAcceptableException("RelationshipType not recognized");
		}
	}

	@Override
	public List<Relationship> toSources(List<RelationshipDTO> targets) {
		if (targets == null) {
			return null;
		}

		List<Relationship> sources = new LinkedList<Relationship>();
		for (RelationshipDTO dto : targets) {
			sources.add(this.toSource(dto));
		}

		return sources;
	}

}
