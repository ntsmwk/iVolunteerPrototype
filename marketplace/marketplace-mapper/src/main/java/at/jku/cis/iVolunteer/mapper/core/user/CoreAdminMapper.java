package at.jku.cis.iVolunteer.mapper.core.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreAdmin;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreAdminDTO;

@Mapper
public abstract class CoreAdminMapper implements AbstractMapper<CoreAdmin, CoreAdminDTO> {

}
