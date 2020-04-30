package at.jku.cis.iVolunteer.marketplace._mapper.relationship;


import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Composition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

@Component
public class CompositionRelationshipDTOMapper implements AbstractMapper<Composition, RelationshipDTO> {

	@Override
	public RelationshipDTO toTarget(Composition source) {
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
	public List<RelationshipDTO> toTargets(List<Composition> sources) {
		if (sources == null) {
			return null;
		}
		
		List<RelationshipDTO> targets = new LinkedList<RelationshipDTO>();
		for (Composition i : sources) {
			targets.add(this.toTarget(i));
		}
		
		return targets;
	}

	@Override
	public Composition toSource(RelationshipDTO target) {
		if (target == null) {
			return null;
		}
		
		Composition composition = new Composition();
		
		composition.setId(target.getId());
		composition.setRelationshipType(target.getRelationshipType());
		composition.setSource(target.getSource());
		composition.setTarget(target.getTarget());
	
		return composition;
	}

	@Override
	public List<Composition> toSources(List<RelationshipDTO> targets) {
		if (targets == null) {
			return null;
		}
		
		List<Composition> sources = new LinkedList<Composition>();
		for (RelationshipDTO dto : targets) {
			sources.add(this.toSource(dto));
		}
		
		return sources;
	}


}
