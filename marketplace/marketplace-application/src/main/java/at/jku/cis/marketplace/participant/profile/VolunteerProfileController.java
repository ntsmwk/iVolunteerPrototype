package at.jku.cis.marketplace.participant.profile;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.marketplace.exception.ForbiddenException;
import at.jku.cis.marketplace.exception.VerificationFailureException;
import at.jku.cis.marketplace.participant.Participant;
import at.jku.cis.marketplace.participant.VolunteerRepository;
import at.jku.cis.marketplace.security.LoginService;

@RestController
@RequestMapping("/volunteer")
public class VolunteerProfileController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private VolunteerRepository volunteerRepository;
	@Autowired
	private VolunteerProfileRepository volunteerProfileRepository;

	@GetMapping("/{volunteerId}/profile")
	public VolunteerProfile getProfile(@PathVariable("volunteerId") String volunteerId) {
		if (!isLoggedIn(volunteerId)) {
			throw new ForbiddenException();
		}
		return volunteerProfileRepository.findByVolunteer(volunteerRepository.findOne(volunteerId));
	}

	@GetMapping("/{volunteerId}/profile/task")
	public Set<TaskEntry> getTaskList(@PathVariable("volunteerId") String volunteerId) {
		return getProfile(volunteerId).getTaskList();
	}

	@GetMapping("/{volunteeId}/profile/task/{taskEntryId}")
	public TaskEntry getTaskEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("taskEntryId") String taskEntryId) {
		Stream<TaskEntry> taskEntries = getTaskList(volunteerId).stream();
		return taskEntries.filter(filterByTaskEntryId(taskEntryId)).findFirst().orElse(null);
	}

	private Predicate<TaskEntry> filterByTaskEntryId(String taskEntryId) {
		return taskEntry -> taskEntry.getId().equals(taskEntryId);
	}

	@PostMapping("/{volunteerId}/profile/task")
	public void addTaskEntry(@PathVariable("volunteerId") String volunteerId, @RequestBody TaskEntry taskEntry) {
		VolunteerProfile volunteerProfile = getProfile(volunteerId);
		
		//TODO verifier call
		//if (verifierService.verify(taskEntry)) {
		//	volunteerProfile.getTaskList().add(taskEntry);
		//	volunteerProfileRepository.save(volunteerProfile);
		//} else {
		//	throw new VerificationFailureException();
		//}
	}

	@DeleteMapping("/{volunteerId}/profile/task/{taskEntryId}")
	public void deleteTaskEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("taskEntryId") String taskEntryId) {
		TaskEntry taskEntry = getTaskEntry(volunteerId, taskEntryId);
		if (taskEntry == null) {
			return;
		}
		VolunteerProfile volunteerProfile = getProfile(volunteerId);
		volunteerProfile.getTaskList().remove(taskEntry);
		volunteerProfileRepository.save(volunteerProfile);
	}

	@GetMapping("/{volunteerId}/profile/competence")
	public Set<CompetenceEntry> getCompetenceList(@PathVariable("volunteerId") String volunteerId) {
		return getProfile(volunteerId).getCompetenceList();
	}

	@GetMapping("/{volunteerId}/profile/competence/{competenceEntryId}")
	public CompetenceEntry getCompetenceEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("competenceEntryId") String competenceEntryId) {
		Stream<CompetenceEntry> competenceEntries = getCompetenceList(volunteerId).stream();
		return competenceEntries.filter(filterByCompetenceEntryId(competenceEntryId)).findFirst().orElse(null);
	}

	private Predicate<CompetenceEntry> filterByCompetenceEntryId(String competenceEntryId) {
		return competenceEntry -> competenceEntry.getId().equals(competenceEntryId);
	}

	@PostMapping("/{volunteerId}/profile/competence")
	public void addCompetenceEntry(@PathVariable("volunteerId") String volunteerId,
			@RequestBody CompetenceEntry competenceEntry) {
		VolunteerProfile volunteerProfile = getProfile(volunteerId);
		
		//TODO verifier call
//		if (verifierService.verify(competenceEntry)) {
//			volunteerProfile.getCompetenceList().add(competenceEntry);
//			volunteerProfileRepository.save(volunteerProfile);
//		} else {
//			throw new VerificationFailureException();
//		}
	}

	@DeleteMapping("/{volunteerId}/profile/competence/{competenceEntryId}")
	public void deleteCompetenceEntry(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("competenceEntryId") String competenceEntryId) {
		CompetenceEntry competenceEntry = getCompetenceEntry(volunteerId, competenceEntryId);
		if (competenceEntry == null) {
			return;
		}
		VolunteerProfile volunteerProfile = getProfile(volunteerId);
		volunteerProfile.getCompetenceList().remove(competenceEntry);
		volunteerProfileRepository.save(volunteerProfile);
	}

	private boolean isLoggedIn(String volunteerId) {
		Participant participant = loginService.getLoggedInParticipant();
		return StringUtils.equals(volunteerId, participant.getId());
	}

}
