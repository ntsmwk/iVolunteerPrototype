package at.jku.cis.iVolunteer.marketplace.participant.profile;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.competence.CompetenceEntryToCompetenceMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.mapper.participant.profile.CompetenceEntryMapper;
import at.jku.cis.iVolunteer.mapper.participant.profile.TaskEntryMapper;
import at.jku.cis.iVolunteer.mapper.participant.profile.VolunteerProfileMapper;
import at.jku.cis.iVolunteer.marketplace.participant.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.competence.dto.CompetenceDTO;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.exception.ForbiddenException;
import at.jku.cis.iVolunteer.model.participant.Participant;
import at.jku.cis.iVolunteer.model.participant.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.participant.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.participant.profile.VolunteerProfile;
import at.jku.cis.iVolunteer.model.participant.profile.dto.CompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.participant.profile.dto.TaskEntryDTO;
import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerProfileDTO;

@RestController
@RequestMapping("/volunteer")
public class VolunteerProfileController {

	@Autowired
	private VerifierRestClient verifierRestClient;
	@Autowired
	private VolunteerRepository volunteerRepository;
	@Autowired
	private VolunteerProfileMapper volunteerProfileMapper;
	@Autowired
	private VolunteerProfileRepository volunteerProfileRepository;
	@Autowired
	private TaskEntryMapper taskEntryMapper;
	@Autowired
	private CompetenceEntryMapper competenceEntryMapper;

	@Autowired
	private LoginService loginService;

	@Autowired
	private CompetenceMapper competenceMapper;

	@Autowired
	private CompetenceEntryToCompetenceMapper competenceEntryToCompetenceMapper;

	@GetMapping("/{volunteerId}/profile")
	public VolunteerProfileDTO getVolunteerProfile(@PathVariable("volunteerId") String volunteerId) {
		return volunteerProfileMapper.toDTO(findVolunteerProfile(volunteerId));
	}

	private VolunteerProfile findVolunteerProfile(String volunteerId) {
		if (!isLoggedIn(volunteerId)) {
			throw new ForbiddenException();
		}
		return volunteerProfileRepository.findByVolunteer(volunteerRepository.findOne(volunteerId));
	}

	@GetMapping("/{volunteerId}/profile/task")
	public Set<TaskEntryDTO> getTaskList(@PathVariable("volunteerId") String volunteerId) {
		return getVolunteerProfile(volunteerId).getTaskList();
	}

	@GetMapping("/{volunteeId}/profile/task/{taskEntryId}")
	public TaskEntryDTO getTaskEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("taskEntryId") String taskEntryId) {
		return taskEntryMapper.toDTO(findTaskEntry(volunteerId, taskEntryId));
	}

	private TaskEntry findTaskEntry(String volunteerId, String taskEntryId) {
		Stream<TaskEntry> taskEntries = findVolunteerProfile(volunteerId).getTaskList().stream();
		return taskEntries.filter(taskEntry -> taskEntry.getId().equals(taskEntryId)).findFirst().orElse(null);
	}

	@PostMapping("/{volunteerId}/profile/task")
	public void addTaskEntry(@PathVariable("volunteerId") String volunteerId, @RequestBody TaskEntryDTO taskEntryDto) {
		VolunteerProfile volunteerProfile = findVolunteerProfile(volunteerId);

		if (verifierRestClient.verifyTaskEntry(taskEntryDto)) {
			volunteerProfile.getTaskList().add(taskEntryMapper.toEntity(taskEntryDto));
			volunteerProfileRepository.save(volunteerProfile);
		} else {
			throw new BadRequestException();
		}
	}

	@DeleteMapping("/{volunteerId}/profile/task/{taskEntryId}")
	public void deleteTaskEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("taskEntryId") String taskEntryId) {
		TaskEntry taskEntry = findTaskEntry(volunteerId, taskEntryId);
		if (taskEntry == null) {
			return;
		}
		VolunteerProfile volunteerProfile = findVolunteerProfile(volunteerId);
		volunteerProfile.getTaskList().remove(taskEntry);
		volunteerProfileRepository.save(volunteerProfile);
	}

	@GetMapping("/{volunteerId}/profile/competenceEntry")
	public Set<CompetenceEntryDTO> getCompetenceEntryList(@PathVariable("volunteerId") String volunteerId) {
		Set<CompetenceEntryDTO> competenceList = getVolunteerProfile(volunteerId).getCompetenceList();
		return competenceList;
	}

	@GetMapping("/{volunteerId}/profile/competence")
	public List<CompetenceDTO> getCompetenceList(@PathVariable("volunteerId") String volunteerId) {

		VolunteerProfileDTO volunteerProfile = getVolunteerProfile(volunteerId);
		if (volunteerProfile != null) {
			List<CompetenceDTO> competenceList = volunteerProfile.getCompetenceList().stream()
					.map((CompetenceEntryDTO competenceEntry) -> competenceEntryMapper.toEntity(competenceEntry))
					.map((CompetenceEntry competenceEntry) -> competenceEntryToCompetenceMapper
							.toCompetence(competenceEntry))
					.map((Competence competence) -> competenceMapper.toDTO(competence)).collect(Collectors.toList());
			return competenceList;
		}
		return Lists.emptyList();
	}

	@GetMapping("/{volunteerId}/profile/competence/{competenceEntryId}")
	public CompetenceEntryDTO getCompetenceEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("competenceEntryId") String competenceEntryId) {
		return competenceEntryMapper.toDTO(findCompetenceEntry(volunteerId, competenceEntryId));
	}

	private CompetenceEntry findCompetenceEntry(String volunteerId, String competenceEntryId) {
		Stream<CompetenceEntry> competenceEntries = findVolunteerProfile(volunteerId).getCompetenceList().stream();
		return competenceEntries.filter(filterByCompetenceEntryId(competenceEntryId)).findFirst().orElse(null);
	}

	private Predicate<CompetenceEntry> filterByCompetenceEntryId(String competenceEntryId) {
		return competenceEntry -> competenceEntry.getId().equals(competenceEntryId);
	}

	@PostMapping("/{volunteerId}/profile/competence")
	public void addCompetenceEntry(@PathVariable("volunteerId") String volunteerId,
			@RequestBody CompetenceEntryDTO competenceEntryDto) {
		VolunteerProfile volunteerProfile = findVolunteerProfile(volunteerId);

		if (verifierRestClient.verifyCompetenceEntry(competenceEntryDto)) {
			volunteerProfile.getCompetenceList().add(competenceEntryMapper.toEntity(competenceEntryDto));
			volunteerProfileRepository.save(volunteerProfile);
		} else {
			throw new BadRequestException();
		}
	}

	@DeleteMapping("/{volunteerId}/profile/competence/{competenceEntryId}")
	public void deleteCompetenceEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("competenceEntryId") String competenceEntryId) {
		CompetenceEntry competenceEntry = findCompetenceEntry(volunteerId, competenceEntryId);
		if (competenceEntry == null) {
			return;
		}
		VolunteerProfile volunteerProfile = findVolunteerProfile(volunteerId);
		volunteerProfile.getCompetenceList().remove(competenceEntry);
		volunteerProfileRepository.save(volunteerProfile);
	}

	private boolean isLoggedIn(String volunteerId) {
		Participant participant = loginService.getLoggedInParticipant();
		return StringUtils.equals(volunteerId, participant.getId());
	}

}
