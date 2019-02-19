package at.jku.cis.iVolunteer.marketplace.group;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.group.GroupMapper;
import at.jku.cis.iVolunteer.marketplace.group.interaction.GroupInteractionController;
import at.jku.cis.iVolunteer.marketplace.group.interaction.GroupInteractionRepository;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.group.Group;
import at.jku.cis.iVolunteer.model.group.dto.GroupDTO;
import at.jku.cis.iVolunteer.model.group.interaction.GroupInteraction;
import at.jku.cis.iVolunteer.model.group.interaction.GroupStatus;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.Volunteer;


@RestController
public class GroupController {
	
	
	@Autowired
	private GroupMapper groupMapper;
	
	
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupInteractionRepository groupInteractionRepository;
	
	@Autowired
	private LoginService loginService;
	 
	
	
	
	@GetMapping("/group")
	public List<Group> findAll() {
		return groupRepository.findAll();
	}
	
	@PostMapping("/group")
	public GroupDTO createGroup(@RequestBody GroupDTO groupDto, @RequestHeader("Authorization") String token) {
		Group group = groupMapper.toEntity(groupDto);
		GroupInteraction groupInteraction = new GroupInteraction();
		groupInteraction.setUser(this.loginService.getLoggedInParticipant());
		groupInteraction.setStatus(GroupStatus.ADMIN);
		groupInteraction = groupInteractionRepository.save(groupInteraction);
		group.getUser().add(groupInteraction);
		group = groupRepository.insert((group));
		return groupMapper.toDTO(group);
	}
	
	@GetMapping("/group/{id}")
	public Group findById(@PathVariable("id") String id) {
		return groupRepository.findById(id);
	}
	
	@DeleteMapping("/group/{id}")
	public void deleteById(@PathVariable("id") String id) {
		groupRepository.delete(id);
	}
	
	
	

}
