package at.jku.cis.iVolunteer.api.standard.model.personrole;

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
public class PersonRoleService {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired private UserMappingService userMappingService;

	public void savePersonRoles(List<PersonRole> personRoles, String tenantId) {
		ClassDefinition personRoleClassDefinition = classDefinitionService.getByName("PersonRole", tenantId);
		if (personRoleClassDefinition != null) {
			for (PersonRole personRole : personRoles) {
				savePersonRole(personRoleClassDefinition, personRole);
			}
		}
	}

	private void savePersonRole(ClassDefinition personRoleClassDefinition, PersonRole personRole) {
		// @formatter:off
		ClassInstance personRoleClassInstance = classDefinition2InstanceMapper.toTarget(personRoleClassDefinition);
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("roleID")).forEach(p -> p.setValues(Collections.singletonList(personRole.getRoleID())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("roleType")).forEach(p -> p.setValues(Collections.singletonList(personRole.getRoleType())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("Name")).forEach(p -> p.setValues(Collections.singletonList(personRole.getRoleName())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("roleDescription")).forEach(p -> p.setValues(Collections.singletonList(personRole.getRoleDescription())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("organisationID")).forEach(p -> p.setValues(Collections.singletonList(personRole.getOrganisationID())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("organisationName")).forEach(p -> p.setValues(Collections.singletonList(personRole.getOrganisationName())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("Starting Date")).forEach(p -> p.setValues(Collections.singletonList(personRole.getDateFrom())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("End Date")).forEach(p -> p.setValues(Collections.singletonList(personRole.getDateTo())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource")).forEach(p -> p.setValues(Collections.singletonList(personRole.getiVolunteerSource())));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID")).forEach(p -> p.setValues(Collections.singletonList(personRole.getPersonID())));
		
		personRoleClassInstance.setUserId(userMappingService.getByExternalUserId(personRole.getPersonID()).getiVolunteerUserId());

		classInstanceRepository.save(personRoleClassInstance); 
		// @formatter:on
	}

}
