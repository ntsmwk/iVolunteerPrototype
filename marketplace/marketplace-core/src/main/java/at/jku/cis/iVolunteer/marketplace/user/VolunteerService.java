package at.jku.cis.iVolunteer.marketplace.user;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.property.ClassPropertyToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerService.LicenseType;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class VolunteerService {
	
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private ClassPropertyToPropertyInstanceMapper classPropertyToPropertyInstanceMapper;
	
	public static final String CERTIFICATE_DRIVING_LICENSE = "Driving License";
	public static final String CERTIFICATE_DRIVING_LICENSE_CAR = "Driving License Car";
	public static final String CERTIFICATE_DRIVING_LICENSE_TRUCK = "Driving License Truck";
	public static final String CERTIFICATE_DRIVING_LICENSE_BUS = "Driving License Bus";
	public static final String CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE = "Driving License Motorcycle";
	public static final String PROPERTY_LICENSE_TYPE = "License Type";
	public static final String PROPERTY_DRIVING_LEVEL = "Driving Level";
	public static final String PROPERTY_EVIDENCE = "Evidence"; 
	public static final String COMPETENCE_DRIVING = "Driving";
	public static final String COMPETENCE_DRIVING_CAR = "Car Driving";
	public static final String COMPETENCE_DRIVING_TRUCK = "Truck Driving";
	public static final String COMPETENCE_DRIVING_BUS = "Bus Driving";
	public static final String COMPETENCE_DRIVING_MOTORCYCLE = "Motorcycle Driving";

	public enum LicenseType{
		A, B, C, D, E
	}
	
	public enum DrivingLevel{
		// levels from https://www.semanticscholar.org/paper/DRIVER-COMPETENCE-IN-A-HIERARCHICAL-PERSPECTIVE%3B-Per%C3%A4aho-Keskinen/c64e45ece27720782367038220abe008924151a2
		LEVEL1("Vehicle manoeuvring"), LEVEL2("Mastery of traffic situations"), 
		LEVEL3("Goals and context of driving"), LEVEL4("Goals for life and skills for living");
				
		private String levelDesc;
		
		DrivingLevel(String levelDesc) {
			this.levelDesc = levelDesc;
		}
		
		public String getDescription() {
			return levelDesc;
		}
	}
	
	public void obtainVolunteerDetails(Volunteer volunteer) {
		//VolunteerExtendedView volDetails = new VolunteerExtendedView(vol);
		//System.out.println("vol details: " + volDetails.currentAge());
		System.out.println("getting more informations from the volunteer " + volunteer.getUsername());
		//return volDetails;
	}
	
	public String sayHello() {
		return "Hello my darling";
	}

	
	public int currentAge(Volunteer volunteer) {
		LocalDate birthDay = volunteer.getBirthday().toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		Period diff = Period.between(birthDay, LocalDate.now());
		return diff.getYears();
	}
	
	public boolean hasDriverLicense(Volunteer volunteer, String tenantId, LicenseType type) {
		ClassDefinition drivingLicenseClass = getClassDefinition(ClassArchetype.ACHIEVEMENT, type, tenantId);
		List<ClassInstance> compDriving = classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), drivingLicenseClass.getId(), tenantId);
		if (compDriving.size() > 0)
			return true;
		return false;
	}
	
	public void addLicense(Volunteer volunteer, String tenantId, LicenseType licenseType) {
		// obtain class for license certificate
		ClassDefinition classCert = getClassDefinition(ClassArchetype.ACHIEVEMENT, licenseType, tenantId); // MapperKlasse daraus machen!
		
		List<ClassInstance> licenseCertList = classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classCert.getId(), tenantId);
		if (licenseCertList.size() == 0) {
			ClassInstance licenseCert = createInstance(classCert, volunteer, tenantId);
			classInstanceRepository.save(licenseCert);
		}

	}
	
	private ClassInstance createInstance(ClassDefinition target, Volunteer volunteer, String tenantId) {
		ClassInstance ci;
		switch (target.getClassArchetype()) {
		case ACHIEVEMENT: ci = new AchievementClassInstance(); break;
		case COMPETENCE: ci = new CompetenceClassInstance(); break;
		case TASK: ci = new TaskClassInstance(); break;
		default: ci = null; 
		}
		ci.setName(target.getName());
		ci.setClassDefinitionId(target.getId());
		ci.setUserId(volunteer.getId());
		ci.setMarketplaceId(marketplaceService.getMarketplaceId());
		ci.setTenantId(tenantId);
		// copy properties from target class
		List<PropertyInstance<Object>> propInstList = new ArrayList<PropertyInstance<Object>>();
		List<ClassProperty<Object>> propLicenseList = target.getProperties();
		for (ClassProperty<Object> cp: propLicenseList) {
			propInstList.add(classPropertyToPropertyInstanceMapper.toTarget(cp));
		}
		ci.setProperties(propInstList);
        classInstanceRepository.save(ci);
		return ci;
	}
	
	public boolean hasAchievment(Volunteer volunteer, String tenantId, String achievementName) {
		return hasClassInstance(volunteer, tenantId, achievementName);
	}
	
	public boolean hasCompetence(Volunteer volunteer, String tenantId, String compName) {
		return hasClassInstance(volunteer, tenantId, compName);
	}
	
	public boolean hasClassInstance(Volunteer volunteer, String tenantId, String className) {
		ClassDefinition classDefinition = getClassDefinition(className, tenantId);
		return classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinition.getId(), tenantId).size() > 0;
	}
	
	public CompetenceClassInstance getCompetence(Volunteer volunteer, String tenantId, String compName) {
		return (CompetenceClassInstance) getClassInstance(volunteer, tenantId, compName).get(0);
	}
	
	public AchievementClassInstance getAchievement(Volunteer volunteer, String tenantId, String className) {
		return (AchievementClassInstance) getClassInstance(volunteer, tenantId, className).get(0);
	}
	
	public List<ClassInstance> getClassInstance(Volunteer volunteer, String tenantId, String className) {
		ClassDefinition classDefinition = getClassDefinition(className, tenantId);
		return classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinition.getId(), tenantId);
	}
	
	// XXX ev. auch Liste hier übergeben, dann kann Liste immer wieder gefiltert werden
	public List<ClassInstance> getFilteredInstancesByProperty(Volunteer volunteer, String tenantId, String className, String propertyName, String propertyValue){
		System.out.println(" filter elements: property = " + propertyName + " filter: " + propertyValue);
		ClassDefinition classDefinition = getClassDefinition(className, tenantId);
		List<ClassInstance> allInstances = classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinition.getId(), tenantId);
		System.out.println(" elements before filter = " + allInstances.size() + ", after filter: " + allInstances.stream().filter(i -> i.getProperty(propertyName).getValues().get(0).equals(propertyValue)).collect(Collectors.toList()).size());
		return allInstances.stream().filter(i -> i.getProperty(propertyName).getValues().get(0).equals(propertyValue)).collect(Collectors.toList());
	}
	
	
	public ClassInstance addClassInstance(Volunteer volunteer, String tenantId, String className) {
	    ClassDefinition classComp = getClassDefinition(className, tenantId); // classDefinitionRepository.findByNameAndTenantId(compName, tenantId);	
		//if (classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classComp.getId(), tenantId).size() == 0) {
		ClassInstance ci = createInstance(classComp, volunteer, tenantId);
		classInstanceRepository.save(ci);
		return ci;
		//}
		//return null;
	}
	
	public void setProperty(ClassInstance ci, String propertyName, Object propertyValue) {
		System.out.println("...====.... set property " + propertyName + " === " + propertyValue);
		PropertyInstance<Object> pi = ci.getProperty(propertyName);
		pi.setValues(Arrays.asList(propertyValue));
		classInstanceRepository.save(ci);
		System.out.println("aus der DB --> " + classInstanceRepository.findOne(ci.getId()).getProperty(propertyName).getValues().get(0));
	}
	
	public void addPropertyValue(ClassInstance ci, String propertyName, Object propertyValue) {
		System.out.println("adding new property values to " + propertyName + " value: " + propertyValue.getClass().getName());
		PropertyInstance<Object> pi = ci.getProperty(propertyName);
		pi.getValues().add(propertyValue);
		pi.setValues(pi.getValues());
		classInstanceRepository.save(ci);
	}
	
	public boolean propertyValueEquals(PropertyInstance pi, String compareValue) {
		String propValue = (String) pi.getValues().get(0);
		return propValue.contentEquals(compareValue);
	}
	
	public boolean propertyValuesContain(PropertyInstance pi, Object compareValue) {
		return pi.getValues().stream().anyMatch(item -> item.equals(compareValue));
	}
	
	
	// Methode auslagern als Service 
	public ClassDefinition getClassDefinition(String className, String tenantId) {
		return classDefinitionRepository.findByNameAndTenantId(className, tenantId);
	}
	
	public ClassDefinition getClassDefinition(ClassArchetype classArchetype, LicenseType licenseType, String tenantId) {
		String cName = null;
		switch(licenseType) {
		case B:	if (classArchetype.equals(ClassArchetype.ACHIEVEMENT))
				cName = CERTIFICATE_DRIVING_LICENSE_CAR;
		else 
			    cName = COMPETENCE_DRIVING_CAR;
		    	break;
		case C: if (classArchetype.equals(ClassArchetype.ACHIEVEMENT))
				cName = CERTIFICATE_DRIVING_LICENSE_TRUCK;
		else 
			    cName = COMPETENCE_DRIVING_TRUCK;
				break;
		case D: if (classArchetype.equals(ClassArchetype.ACHIEVEMENT))
				cName = CERTIFICATE_DRIVING_LICENSE_BUS;
		else
			    cName = COMPETENCE_DRIVING_BUS;
		    	break;
		case A: if (classArchetype.equals(ClassArchetype.ACHIEVEMENT))
				cName = CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE;
		else
			    cName = COMPETENCE_DRIVING_MOTORCYCLE;
		        break;
		}		
		return getClassDefinition(cName, tenantId);
	}
}
