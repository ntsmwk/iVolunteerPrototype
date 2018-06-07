package at.jku.cis.iVolunteer.lib.mapper.competence;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.lib.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.competence.dto.CompetenceDTO;

@Mapper
public abstract class CompetenceMapper implements AbstractMapper<Competence, CompetenceDTO> {


}
