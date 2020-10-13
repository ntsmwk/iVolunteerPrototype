package at.jku.cis.iVolunteer.api.standard.model.certificate;

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
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class PersonCertificateService {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired private UserMappingService userMappingService;

	
	public void savePersonCertificate(List<PersonCertificate> personCertificates, String tenantId) {
		ClassDefinition personCertificateClassDefinition = classDefinitionService.getByName("PersonCertificate", tenantId);
		if (personCertificateClassDefinition != null) {
			for (PersonCertificate personCertificate : personCertificates) {
				savePersonCertificate(personCertificateClassDefinition, personCertificate);
			}
		}
	}

	private void savePersonCertificate(ClassDefinition personCertificateClassDefinition,
			PersonCertificate personCertificate) {
		// @formatter:off
		ClassInstance personCertificateClassInstance = classDefinition2InstanceMapper.toTarget(personCertificateClassDefinition);
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateID")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getCertificateID())));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("Name")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getCertificateName())));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateDescription")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getCertificateDescription())));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("Starting Date")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getCertificateIssuedOn())));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("End Date")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getCertificateValidUntil())));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateIcon")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getCertificateIcon())));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerUUID")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getiVolunteerUUID())));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getiVolunteerSource())));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID")).forEach(p -> p.setValues(Collections.singletonList(personCertificate.getPersonID())));
		personCertificateClassInstance.setUserId(userMappingService.getByExternalUserId(personCertificate.getPersonID()).getiVolunteerUserId());

		classInstanceRepository.save(personCertificateClassInstance);		 
		// @formatter:on
	}

}
