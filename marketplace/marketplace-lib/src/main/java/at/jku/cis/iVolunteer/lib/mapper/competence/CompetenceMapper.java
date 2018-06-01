package at.jku.cis.iVolunteer.lib.mapper.competence;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.competence.dto.CompetenceDTO;

public class CompetenceMapper extends AbstractSpringMapper<Competence, CompetenceDTO> {

	public final static CompetenceMapper INSTANCE = new CompetenceMapper();

	protected CompetenceMapper() {
		super(Competence.class, CompetenceDTO.class);
	}

}
