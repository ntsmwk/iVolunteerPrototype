package at.jku.cis.iVolunteer.api.standard.model.personrole;

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
public class PersonRoleService {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired private UserMappingService userMappingService;

	public void savePersonRoles(List<PersonRole> personRoles) {
		ClassDefinition personRoleClassDefinition = classDefinitionService.getByName("PersonRole");
		if (personRoleClassDefinition != null) {
			for (PersonRole personRole : personRoles) {
				savePersonRole(personRoleClassDefinition, personRole);
			}
		}
	}

	private void savePersonRole(ClassDefinition personRoleClassDefinition, PersonRole personRole) {
		// @formatter:off
		ClassInstance personRoleClassInstance = classDefinition2InstanceMapper.toTarget(personRoleClassDefinition);
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("roleID")).forEach(p -> p.setValues(Lists.asList(personRole.getRoleID(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("roleType")).forEach(p -> p.setValues(Lists.asList(personRole.getRoleType(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("roleName")).forEach(p -> p.setValues(Lists.asList(personRole.getRoleName(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("roleDescription")).forEach(p -> p.setValues(Lists.asList(personRole.getRoleDescription(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("organisationID")).forEach(p -> p.setValues(Lists.asList(personRole.getOrganisationID(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("organisationName")).forEach(p -> p.setValues(Lists.asList(personRole.getOrganisationName(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("dateFrom")).forEach(p -> p.setValues(Lists.asList(personRole.getDateFrom(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("dateTo")).forEach(p -> p.setValues(Lists.asList(personRole.getDateTo(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource")).forEach(p -> p.setValues(Lists.asList(personRole.getiVolunteerSource(), new Object[0])));
		personRoleClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID")).forEach(p -> p.setValues(Lists.asList(personRole.getPersonID(), new Object[0])));
		
		personRoleClassInstance.setUserId(userMappingService.getByExternalUserId(personRole.getPersonID()).getiVolunteerUserId());

		classInstanceRepository.save(personRoleClassInstance); 
		// @formatter:on
	}

}
