package at.jku.cis.iVolunteer.mapper.project;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.project.Project;
import at.jku.cis.iVolunteer.model.project.dto.ProjectDTO;

@Mapper
public abstract class ProjectMapper implements AbstractMapper<Project, ProjectDTO> {

}
