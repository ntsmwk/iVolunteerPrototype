package at.jku.cis.iVolunteer.marketplace._mapper.relationship;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

@Component
public class InheritanceRelationshipDTOMapper implements AbstractMapper<Inheritance, RelationshipDTO> {

	@Override
	public RelationshipDTO toTarget(Inheritance source) {
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
	public List<RelationshipDTO> toTargets(List<Inheritance> sources) {
		if (sources == null) {
			return null;
		}

		List<RelationshipDTO> targets = new LinkedList<RelationshipDTO>();
		for (Inheritance i : sources) {
			targets.add(this.toTarget(i));
		}

		return targets;
	}

	@Override
	public Inheritance toSource(RelationshipDTO target) {
		if (target == null) {
			return null;
		}

		Inheritance inheritance = new Inheritance();

		inheritance.setId(target.getId());
		inheritance.setRelationshipType(target.getRelationshipType());
		inheritance.setSource(target.getSource());
		inheritance.setTarget(target.getTarget());

		return inheritance;
	}

	@Override
	public List<Inheritance> toSources(List<RelationshipDTO> targets) {
		if (targets == null) {
			return null;
		}

		List<Inheritance> sources = new LinkedList<Inheritance>();
		for (RelationshipDTO dto : targets) {
			sources.add(this.toSource(dto));
		}

		return sources;
	}

}
