package at.jku.cis.iVolunteer.mapper.core.dashboard;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.dashboard.Dashlet;
import at.jku.cis.iVolunteer.model.core.dashboard.dto.DashletDTO;

@Mapper
public abstract class DashletMapper implements AbstractMapper<Dashlet, DashletDTO> {

	
}
