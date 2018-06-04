package at.jku.cis.iVolunteer.lib.mapper.competence;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.competence.dto.CompetenceDTO;

@Service
public class CompetenceMapper extends AbstractSpringMapper<Competence, CompetenceDTO> {

	protected CompetenceMapper() {
		super(Competence.class, CompetenceDTO.class);
	}

}
