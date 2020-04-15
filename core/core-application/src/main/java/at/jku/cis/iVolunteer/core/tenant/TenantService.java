package at.jku.cis.iVolunteer.core.tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@Service
public class TenantService {

	@Autowired private TenantRepository tenantRepository;
	@Autowired private CoreVolunteerRepository volunteerRepository;

	public List<Tenant> getTenantsByVolunteer(String volunteerId) {
		CoreVolunteer volunteer = volunteerRepository.findOne(volunteerId);
		if (volunteer != null) {
			return volunteer.getSubscribedTenants().stream().map(tId -> tenantRepository.findOne(tId))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

}
