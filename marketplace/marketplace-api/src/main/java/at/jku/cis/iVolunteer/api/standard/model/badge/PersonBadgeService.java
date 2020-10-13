package at.jku.cis.iVolunteer.api.standard.model.badge;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace._mapper.clazz.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Service
public class PersonBadgeService {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired private UserMappingService userMappingService;

	public void savePersonBadges(List<PersonBadge> personBadges, String tenantId) {
		ClassDefinition personBadgeClassDefinition = classDefinitionService.getByName("PersonBadge", tenantId);
		if (personBadgeClassDefinition != null) {
			for (PersonBadge personBadge : personBadges) {
				savePersonBadge(personBadgeClassDefinition, personBadge);
			}
		}
	}

	private void savePersonBadge(ClassDefinition personBadgeClassDefinition, PersonBadge personBadge) {
		// @formatter:off
		ClassInstance personBadgeClassInstance = classDefinition2InstanceMapper.toTarget(personBadgeClassDefinition);
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("badgeID")).forEach(p -> p.setValues(Collections.singletonList(personBadge.getBadgeID())));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("Name")).forEach(p -> p.setValues(Collections.singletonList(personBadge.getBadgeName())));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("badgeDescription")).forEach(p -> p.setValues(Collections.singletonList(personBadge.getBadgeDescription())));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("Starting Date")).forEach(p -> p.setValues(Collections.singletonList(personBadge.getBadgeIssuedOn())));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("badgeIcon")).forEach(p -> p.setValues(Collections.singletonList(personBadge.getBadgeIcon())));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerUUID")).forEach(p -> p.setValues(Collections.singletonList(personBadge.getiVolunteerUUID())));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource")).forEach(p -> p.setValues(Collections.singletonList(personBadge.getiVolunteerSource())));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID")).forEach(p -> p.setValues(Collections.singletonList(personBadge.getPersonID())));
		
		personBadgeClassInstance.setUserId(userMappingService.getByExternalUserId(personBadge.getPersonID()).getiVolunteerUserId());

		classInstanceRepository.save(personBadgeClassInstance);		 
		// @formatter:on
	}

}
