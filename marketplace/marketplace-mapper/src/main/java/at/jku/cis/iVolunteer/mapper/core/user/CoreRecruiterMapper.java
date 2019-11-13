package at.jku.cis.iVolunteer.mapper.core.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreRecruiterDTO;

@Mapper
public abstract class CoreRecruiterMapper implements AbstractMapper<CoreRecruiter, CoreRecruiterDTO> {

}
