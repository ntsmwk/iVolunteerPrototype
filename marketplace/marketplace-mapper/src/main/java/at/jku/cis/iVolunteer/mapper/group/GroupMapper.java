package at.jku.cis.iVolunteer.mapper.group;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.group.Group;
import at.jku.cis.iVolunteer.model.group.dto.GroupDTO;

@Mapper
public abstract class GroupMapper implements AbstractMapper<Group, GroupDTO> {

}
