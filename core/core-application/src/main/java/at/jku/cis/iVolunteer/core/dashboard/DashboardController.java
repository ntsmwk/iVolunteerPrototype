package at.jku.cis.iVolunteer.core.dashboard;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.dashboard.Dashboard;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;

@RestController
public class DashboardController {

	@Autowired private CoreLoginService loginService;
	@Autowired private CoreVolunteerRepository volunteerRepository;

	@Autowired private DashboardRepository dashboardRepository;

	@GetMapping(path = "/dashboard")
	public List<Dashboard> find(@RequestParam(name = "participantId", required = false) String participantId) {
		if (!StringUtils.isEmpty(participantId)) {
			return dashboardRepository.findByUser(volunteerRepository.findOne(participantId));
		}
		return dashboardRepository.findAll();
	}

	@GetMapping(path = "/dashboard/{id}")
	public Dashboard findById(@PathVariable("id") String id) {
		Dashboard dashboardFromDb = dashboardRepository.findOne(id);
		if (dashboardFromDb == null) {
			throw new NotFoundException();
		}
		return dashboardFromDb;
	}

	@PostMapping(path = "/dashboard")
	public Dashboard create(@RequestBody Dashboard dashboard) {
		dashboard.setId(null);
		dashboard.setUser(loginService.getLoggedInParticipant());
		return dashboardRepository.insert(dashboard);
	}

	@PutMapping(path = "/dashboard/{id}")
	public Dashboard updateDashboardPages(@PathVariable("id") String id, @RequestBody Dashboard dashboard) {
		Dashboard dashboardFromDb = dashboardRepository.findOne(id);
		if (dashboardFromDb == null) {
			throw new BadRequestException();
		}
		dashboardFromDb.setName(dashboard.getName());
		dashboardFromDb.setDashlets(dashboard.getDashlets());
		return dashboardRepository.save(dashboardFromDb);
	}

	@DeleteMapping(path = "/dashboard/{id}")
	public void delete(@PathVariable("id") String id) {
		Dashboard dashboardFromDb = dashboardRepository.findOne(id);
		if (dashboardFromDb == null) {
			throw new BadRequestException();
		}
		dashboardRepository.delete(dashboardFromDb);
	}
}
