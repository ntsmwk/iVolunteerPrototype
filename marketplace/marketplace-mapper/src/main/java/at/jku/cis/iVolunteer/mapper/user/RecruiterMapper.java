package at.jku.cis.iVolunteer.mapper.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.Recruiter;
import at.jku.cis.iVolunteer.model.user.dto.RecruiterDTO;

@Mapper
public abstract class RecruiterMapper implements AbstractMapper<Recruiter, RecruiterDTO> {

}
