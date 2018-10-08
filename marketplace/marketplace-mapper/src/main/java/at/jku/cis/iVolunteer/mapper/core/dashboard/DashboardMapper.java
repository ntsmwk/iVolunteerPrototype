package at.jku.cis.iVolunteer.mapper.core.dashboard;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.core.user.CoreHelpSeekerMapper;
import at.jku.cis.iVolunteer.mapper.core.user.CoreVolunteerMapper;
import at.jku.cis.iVolunteer.model.core.dashboard.Dashboard;
import at.jku.cis.iVolunteer.model.core.dashboard.dto.DashboardDTO;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreHelpSeekerDTO;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreUserDTO;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreVolunteerDTO;

@Mapper(uses = { DashletMapper.class })
public abstract class DashboardMapper implements AbstractMapper<Dashboard, DashboardDTO> {

	@Autowired
	private CoreVolunteerMapper volunteerMapper;
	@Autowired
	private CoreHelpSeekerMapper helpSeekerMapper;

	protected CoreUserDTO mapToCoreUserDTO(CoreUser user) {
		if (user instanceof CoreHelpSeeker) {
			return helpSeekerMapper.toDTO((CoreHelpSeeker) user);
		}
		return volunteerMapper.toDTO((CoreVolunteer) user);
	}

	protected CoreUser mapToCoreUser(CoreUserDTO user) {
		if (user instanceof CoreHelpSeekerDTO) {
			return helpSeekerMapper.toEntity((CoreHelpSeekerDTO) user);
		}
		return volunteerMapper.toEntity((CoreVolunteerDTO) user);
	}
}
