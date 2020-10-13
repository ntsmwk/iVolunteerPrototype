package at.jku.cis.iVolunteer.marketplace._mapper.relationship;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

@Component
public class AssociationRelationshipDTOMapper implements AbstractMapper<Association, RelationshipDTO> {

	@Override
	public RelationshipDTO toTarget(Association source) {

		if (source == null) {
			return null;
		}

		RelationshipDTO dto = new RelationshipDTO();
		dto.setId(source.getId());
		dto.setRelationshipType(source.getRelationshipType());
		dto.setSource(source.getSource());
		dto.setSourceCardinality(source.getSourceCardinality());
		dto.setTarget(source.getTarget());
		dto.setTargetCardinality(source.getTargetCardinality());

		return dto;
	}

	@Override
	public List<RelationshipDTO> toTargets(List<Association> sources) {

		if (sources == null) {
			return null;
		}

		List<RelationshipDTO> targets = new LinkedList<RelationshipDTO>();
		for (Association a : sources) {
			targets.add(this.toTarget(a));
		}

		return targets;
	}

	@Override
	public Association toSource(RelationshipDTO target) {

		if (target == null) {
			return null;
		}

		Association association = new Association();
		association.setId(target.getId());
		association.setRelationshipType(target.getRelationshipType());
		association.setSource(target.getSource());
		association.setSourceCardinality(target.getSourceCardinality());
		association.setTarget(target.getTarget());
		association.setTargetCardinality(target.getTargetCardinality());

		return association;
	}

	@Override
	public List<Association> toSources(List<RelationshipDTO> targets) {
		if (targets == null) {
			return null;
		}

		List<Association> sources = new LinkedList<Association>();
		for (RelationshipDTO dto : targets) {
			sources.add(this.toSource(dto));
		}

		return sources;
	}

}
