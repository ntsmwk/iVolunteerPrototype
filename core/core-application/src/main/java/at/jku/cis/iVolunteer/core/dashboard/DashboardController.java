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
import at.jku.cis.iVolunteer.mapper.core.dashboard.DashboardMapper;
import at.jku.cis.iVolunteer.mapper.core.dashboard.DashletMapper;
import at.jku.cis.iVolunteer.model.core.dashboard.Dashboard;
import at.jku.cis.iVolunteer.model.core.dashboard.dto.DashboardDTO;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;

@RestController
public class DashboardController {

	@Autowired
	private CoreLoginService loginService;
	@Autowired
	private CoreVolunteerRepository volunteerRepository;

	@Autowired
	private DashletMapper dashletMapper;
	@Autowired
	private DashboardMapper dashboardMapper;
	@Autowired
	private DashboardRepository dashboardRepository;

	@GetMapping(path = "/dashboard")
	public List<DashboardDTO> find(@RequestParam(name = "participantId", required = false) String participantId) {
		if (!StringUtils.isEmpty(participantId)) {
			return dashboardMapper.toDTOs(dashboardRepository.findByUser(volunteerRepository.findOne(participantId)));
		}
		return dashboardMapper.toDTOs(dashboardRepository.findAll());
	}

	@GetMapping(path = "/dashboard/{id}")
	public DashboardDTO findById(@PathVariable("id") String id) {
		Dashboard dashboardFromDb = dashboardRepository.findOne(id);
		if (dashboardFromDb == null) {
			throw new NotFoundException();
		}
		return dashboardMapper.toDTO(dashboardFromDb);
	}

	@PostMapping(path = "/dashboard")
	public DashboardDTO create(@RequestBody DashboardDTO dashboardDto) {
		Dashboard dashboard = dashboardMapper.toEntity(dashboardDto);
		dashboard.setId(null);
		dashboard.setName(dashboardDto.getName());
		dashboard.setUser(loginService.getLoggedInParticipant());
		return dashboardMapper.toDTO(dashboardRepository.insert(dashboard));
	}

	@PutMapping(path = "/dashboard/{id}")
	public DashboardDTO updateDashboardPages(@PathVariable("id") String id, @RequestBody DashboardDTO dashboardDto) {
		Dashboard dashboardFromDb = dashboardRepository.findOne(id);
		if (dashboardFromDb == null) {
			throw new BadRequestException();
		}
		dashboardFromDb.setName(dashboardDto.getName());
		dashboardFromDb.setDashlets(dashletMapper.toEntities(dashboardDto.getDashlets()));
		return dashboardMapper.toDTO(dashboardRepository.save(dashboardFromDb));
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
