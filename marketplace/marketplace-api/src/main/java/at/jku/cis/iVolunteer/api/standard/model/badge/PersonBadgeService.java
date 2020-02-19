package at.jku.cis.iVolunteer.api.standard.model.badge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import jersey.repackaged.com.google.common.collect.Lists;

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
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("badgeID")).forEach(p -> p.setValues(Lists.asList(personBadge.getBadgeID(), new Object[0])));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("Name")).forEach(p -> p.setValues(Lists.asList(personBadge.getBadgeName(), new Object[0])));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("badgeDescription")).forEach(p -> p.setValues(Lists.asList(personBadge.getBadgeDescription(), new Object[0])));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("Starting Date")).forEach(p -> p.setValues(Lists.asList(personBadge.getBadgeIssuedOn(), new Object[0])));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("badgeIcon")).forEach(p -> p.setValues(Lists.asList(personBadge.getBadgeIcon(), new Object[0])));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerUUID")).forEach(p -> p.setValues(Lists.asList(personBadge.getiVolunteerUUID(), new Object[0])));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource")).forEach(p -> p.setValues(Lists.asList(personBadge.getiVolunteerSource(), new Object[0])));
		personBadgeClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID")).forEach(p -> p.setValues(Lists.asList(personBadge.getPersonID(), new Object[0])));
		
		personBadgeClassInstance.setUserId(userMappingService.getByExternalUserId(personBadge.getPersonID()).getiVolunteerUserId());

		classInstanceRepository.save(personBadgeClassInstance);		 
		// @formatter:on
	}

}
