package at.jku.cis.iVolunteer.marketplace.volunteer.profile;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.competence.CompetenceEntryToCompetenceMapper;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.exception.ForbiddenException;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.volunteer.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.VolunteerProfile;

@RestController
@RequestMapping("/volunteer")
public class VolunteerProfileController {

	@Autowired private VerifierRestClient verifierRestClient;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private VolunteerProfileRepository volunteerProfileRepository;
	@Autowired private LoginService loginService;
	@Autowired private CompetenceEntryToCompetenceMapper competenceEntryToCompetenceMapper;

	@GetMapping("/{volunteerId}/profile")
	public VolunteerProfile getVolunteerProfile(@PathVariable("volunteerId") String volunteerId) {
		return findVolunteerProfile(volunteerId);
	}

	private VolunteerProfile findVolunteerProfile(String volunteerId) {
		if (!isLoggedIn(volunteerId)) {
			throw new ForbiddenException();
		}
		return volunteerProfileRepository.findByVolunteer(volunteerRepository.findOne(volunteerId));
	}

	@GetMapping("/{volunteerId}/profile/task")
	public Set<TaskEntry> getTaskList(@PathVariable("volunteerId") String volunteerId) {
		return getVolunteerProfile(volunteerId).getTaskList();
	}

	@GetMapping("/{volunteeId}/profile/task/{taskEntryId}")
	public TaskEntry getTaskEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("taskEntryId") String taskEntryId) {
		return findTaskEntry(volunteerId, taskEntryId);
	}

	private TaskEntry findTaskEntry(String volunteerId, String taskEntryId) {
		Stream<TaskEntry> taskEntries = findVolunteerProfile(volunteerId).getTaskList().stream();
		return taskEntries.filter(taskEntry -> taskEntry.getId().equals(taskEntryId)).findFirst().orElse(null);
	}

	@PostMapping("/{volunteerId}/profile/task")
	public void addTaskEntry(@PathVariable("volunteerId") String volunteerId, @RequestBody TaskEntry taskEntry,
			@RequestHeader("authorization") String authorization) {
		VolunteerProfile volunteerProfile = findVolunteerProfile(volunteerId);

		if (verifierRestClient.verifyTaskEntry(taskEntry, authorization)) {
			volunteerProfile.getTaskList().add(taskEntry);
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
	public Set<CompetenceEntry> getCompetenceEntryList(@PathVariable("volunteerId") String volunteerId) {
		Set<CompetenceEntry> competenceList = getVolunteerProfile(volunteerId).getCompetenceList();
		return competenceList;
	}

	@GetMapping("/{volunteerId}/profile/competence")
	public List<Competence> getCompetenceList(@PathVariable("volunteerId") String volunteerId) {

		VolunteerProfile volunteerProfile = getVolunteerProfile(volunteerId);
		if (volunteerProfile != null) {
			List<Competence> competenceList = volunteerProfile.getCompetenceList().stream()
					.map((CompetenceEntry competenceEntry) -> competenceEntryToCompetenceMapper
							.toCompetence(competenceEntry))
					.collect(Collectors.toList());
			return competenceList;
		}
		return Collections.emptyList();
	}

	@GetMapping("/{volunteerId}/profile/competence/{competenceEntryId}")
	public CompetenceEntry getCompetenceEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("competenceEntryId") String competenceEntryId) {
		return findCompetenceEntry(volunteerId, competenceEntryId);
	}

	private CompetenceEntry findCompetenceEntry(String volunteerId, String competenceEntryId) {
		Stream<CompetenceEntry> competenceEntries = findVolunteerProfile(volunteerId).getCompetenceList().stream();
		return competenceEntries.filter(filterByCompetenceEntryId(competenceEntryId)).findFirst().orElse(null);
	}

	private Predicate<CompetenceEntry> filterByCompetenceEntryId(String competenceEntryId) {
		return competenceEntry -> competenceEntry.getCompetenceId().equals(competenceEntryId);
	}

	@PostMapping("/{volunteerId}/profile/competence")
	public void addCompetenceEntry(@PathVariable("volunteerId") String volunteerId,
			@RequestBody CompetenceEntry competenceEntry, @RequestHeader("authorization") String authorization) {
		VolunteerProfile volunteerProfile = findVolunteerProfile(volunteerId);

		if (verifierRestClient.verifyCompetenceEntry(competenceEntry, authorization)) {
			volunteerProfile.getCompetenceList().add(competenceEntry);
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
		User participant = loginService.getLoggedInParticipant();
		return StringUtils.equals(volunteerId, participant.getId());
	}

}
