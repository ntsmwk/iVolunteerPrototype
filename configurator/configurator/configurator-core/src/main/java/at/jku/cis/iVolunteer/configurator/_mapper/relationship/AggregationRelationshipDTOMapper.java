package at.jku.cis.iVolunteer.configurator._mapper.relationship;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.configurator.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Aggregation;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipDTO;

@Component
public class AggregationRelationshipDTOMapper implements AbstractMapper<Aggregation, RelationshipDTO> {

	@Override
	public RelationshipDTO toTarget(Aggregation source) {
		if (source == null) {
			return null;
		}

		RelationshipDTO dto = new RelationshipDTO();
		dto.setId(source.getId());
		dto.setRelationshipType(source.getRelationshipType());
		dto.setSource(source.getSource());
		dto.setTarget(source.getTarget());

		return dto;
	}

	@Override
	public List<RelationshipDTO> toTargets(List<Aggregation> sources) {
		if (sources == null) {
			return null;
		}

		List<RelationshipDTO> targets = new LinkedList<RelationshipDTO>();
		for (Aggregation a : sources) {
			targets.add(this.toTarget(a));
		}

		return targets;
	}

	@Override
	public Aggregation toSource(RelationshipDTO target) {
		if (target == null) {
			return null;
		}

		Aggregation aggregation = new Aggregation();

		aggregation.setId(target.getId());
		aggregation.setRelationshipType(target.getRelationshipType());
		aggregation.setSource(target.getSource());
		aggregation.setTarget(target.getTarget());

		return aggregation;
	}

	@Override
	public List<Aggregation> toSources(List<RelationshipDTO> targets) {
		if (targets == null) {
			return null;
		}

		List<Aggregation> sources = new LinkedList<Aggregation>();
		for (RelationshipDTO dto : targets) {
			sources.add(this.toSource(dto));
		}

		return sources;
	}

}
