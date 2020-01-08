package at.jku.cis.iVolunteer.api.standard.model.certificate;

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
public class PersonCertificateService {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired private UserMappingService userMappingService;

	
	public void savePersonCertificate(List<PersonCertificate> personCertificates) {
		ClassDefinition personCertificateClassDefinition = classDefinitionService.getByName("PersonCertificate");
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
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateID")).forEach(p -> p.setValues(Lists.asList(personCertificate.getCertificateID(), new Object[0])));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateName")).forEach(p -> p.setValues(Lists.asList(personCertificate.getCertificateName(), new Object[0])));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateDescription")).forEach(p -> p.setValues(Lists.asList(personCertificate.getCertificateDescription(), new Object[0])));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateIssuedOn")).forEach(p -> p.setValues(Lists.asList(personCertificate.getCertificateIssuedOn(), new Object[0])));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateValidUntil")).forEach(p -> p.setValues(Lists.asList(personCertificate.getCertificateValidUntil(), new Object[0])));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("certificateIcon")).forEach(p -> p.setValues(Lists.asList(personCertificate.getCertificateIcon(), new Object[0])));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerUUID")).forEach(p -> p.setValues(Lists.asList(personCertificate.getiVolunteerUUID(), new Object[0])));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource")).forEach(p -> p.setValues(Lists.asList(personCertificate.getiVolunteerSource(), new Object[0])));
		personCertificateClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID")).forEach(p -> p.setValues(Lists.asList(personCertificate.getPersonID(), new Object[0])));
		personCertificateClassInstance.setUserId(userMappingService.getByExternalUserId(personCertificate.getPersonID()).getiVolunteerUserId());

		classInstanceRepository.save(personCertificateClassInstance);		 
		// @formatter:on
	}

}
