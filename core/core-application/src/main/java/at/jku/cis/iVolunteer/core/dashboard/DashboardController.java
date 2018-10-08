package at.jku.cis.iVolunteer.core.dashboard;

import java.util.Date;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@RestController
public class DashboardController {

	@Autowired
	private CoreLoginService loginService;
	@Autowired
	private DashboardRepository dashboardRepository;

	@GetMapping("dashboard")
	public List<Dashboard> findAllForUser() {
		CoreUser user = loginService.getLoggedInParticipant();
		return dashboardRepository.findByUser(user);
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
		Date currentDate = new Date();
		dashboard.setCreationDate(currentDate);
		dashboard.setModificationDate(currentDate);
		return dashboardRepository.insert(dashboard);
	}

	@PutMapping(path = "/dashboard/{id}/dashlets")
	public Dashboard updateDashboardPages(@PathVariable("id") String id, @RequestBody List<Dashlet> dashlets) {
		Dashboard dashboardFromDb = dashboardRepository.findOne(id);
		if (dashboardFromDb == null) {
			throw new BadRequestException();
		}
		dashboardFromDb.setDashlets(dashlets);
		dashboardFromDb.setModificationDate(new Date());
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
