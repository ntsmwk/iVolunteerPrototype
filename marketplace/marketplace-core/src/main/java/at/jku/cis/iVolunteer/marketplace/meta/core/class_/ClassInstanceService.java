package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.property.ClassPropertyToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria.Criteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria.SumCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

import at.jku.cis.iVolunteer.model.user.User;

@Service
public class ClassInstanceService {

	@Autowired
	private ClassPropertyService classPropertyService;
	@Autowired
	private ClassInstanceRepository classInstanceRepository;
	@Autowired
	private ClassDefinitionService classDefinitionService;
	@Autowired
	private MarketplaceService marketplaceService;
	@Autowired
	private ClassPropertyToPropertyInstanceMapper classPropertyToPropertyInstanceMapper;

	public List<ClassInstance> getAllClassInstances() {
		return classInstanceRepository.findAll();
	}

	public ClassInstance getClassInstance(User volunteer, String classDefinitionId, String tenantId) {
		return classInstanceRepository
				.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinitionId, tenantId).stream()
				.findFirst().orElse(null);
	}

	public ClassInstance getClassInstanceById(String taskId) {
		return classInstanceRepository.findOne(taskId);
	}

	public ClassInstance getClassInstanceByName(User volunteer, String classDefinitionName, String tenantId) {
		return getClassInstance(volunteer, classDefinitionService.getByName(classDefinitionName, tenantId).getId(),
				tenantId);
	}

	public List<ClassInstance> getClassInstances(User volunteer, String classDefinitionId, String tenantId) {
		return classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinitionId,
				tenantId);
	}

	public List<ClassInstance> getClassInstancesByName(User volunteer, String classDefinitionName, String tenantId) {
		return getClassInstances(volunteer, classDefinitionService.getByName(classDefinitionName, tenantId).getId(),
				tenantId);
	}

	public List<ClassInstance> getClassInstancesCreatedByRule(User volunteer, String derivationRuleId) {
		return classInstanceRepository.getByUserIdAndDerivationRuleId(volunteer.getId(), derivationRuleId);
	}

	public List<ClassInstance> getClassInstanceByArchetype(ClassArchetype classArchetype, String tenantId) {
		return classInstanceRepository.getByClassArchetypeAndTenantId(classArchetype, tenantId);
	}

	public List<ClassInstance> getClassInstanceByArchetypeAndUserId(ClassArchetype classArchetype, String userId,
			String tenantId) {
		return classInstanceRepository.getByClassArchetypeAndUserIdAndTenantId(classArchetype, userId, tenantId);
	}

	public List<ClassInstance> getClassInstanceByArchetypeAndUserId(ClassArchetype classArchetype, String userId) {
		return classInstanceRepository.getByClassArchetypeAndUserId(classArchetype, userId);
	}

	// public List<ClassInstance>
	// getClassInstanceByArcheTypeAndUserIdAndSubscribed(ClassArchetype
	// classArchetype, String userId, boolean subscribed) {
	// return
	// classInstanceRepository.getByClassArchetypeAndUserIdAndSubscribed(classArchetype,
	// userId, subscribed);
	// }
	//
	// public List<ClassInstance>
	// getClassInstanceByArcheTypeAndUserIdAndTenantIdAndSubscribed(ClassArchetype
	// classArchetype, String userId, String tenantId, boolean subscribed) {
	// return
	// classInstanceRepository.getByClassArchetypeAndTenantIdAndUserIdAndSubscribed(classArchetype,
	// tenantId, userId, subscribed);
	//
	// }

	public List<ClassInstance> filterInstancesByPropertyCriteria(User volunteer, String classDefinitionId,
			String tenantId, Criteria criteria) {
		List<ClassInstance> allInstances = getClassInstances(volunteer, classDefinitionId, tenantId);
		return filterInstancesByPropertyCriteria(allInstances, criteria);
	}

	public List<ClassInstance> filterInstancesByPropertyCriteria(List<ClassInstance> instances, Criteria criteria) {
		return criteria.meetCriteria(instances);
	}

	public Object sum(List<ClassInstance> instances, SumCriteria criteria) {
		return criteria.sum(instances);
	}

	public void setProperty(ClassInstance ci, String propertyId, Object propertyValue) {
		PropertyInstance<Object> pi = ci.getProperty(propertyId);
		pi.setValues(Arrays.asList(propertyValue));
	}

	public void addPropertyValue(ClassInstance ci, String propertyId, Object propertyValue) {
		PropertyInstance<Object> pi = ci.getProperty(propertyId);
		pi.getValues().add(propertyValue);
	}

	public ClassInstance newClassInstance(User volunteer, String classDefinitionId, String tenantId) {
		ClassDefinition classDefinition = classDefinitionService.getClassDefinitionById(classDefinitionId);
		ClassInstance ci = new ClassInstance();
		ci.setClassArchetype(ci.getClassArchetype());

		ci.setName(classDefinition.getName());
		ci.setClassDefinitionId(classDefinition.getId());
		ci.setUserId(volunteer.getId());
		ci.setMarketplaceId(marketplaceService.getMarketplaceId());
		ci.setTenantId(tenantId);
		// copy properties from target class
		List<PropertyInstance<Object>> propInstList = new ArrayList<PropertyInstance<Object>>();
		List<ClassProperty<Object>> propLicenseList = classPropertyService
				.getAllClassPropertiesFromClass(classDefinition.getId());
		propLicenseList.forEach(cp -> propInstList.add(classPropertyToPropertyInstanceMapper.toTarget(cp)));
		ci.setProperties(propInstList);
		return ci;
	}

	public ClassInstance saveClassInstance(ClassInstance classInstance) {
		return classInstanceRepository.save(classInstance);
	}

	public void deleteClassInstances(User volunteer, String classDefinitionId, String tenantId) {
		List<ClassInstance> instances = classInstanceRepository
				.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinitionId, tenantId);
		deleteClassInstances(instances);
	}

	public void deleteClassInstances(List<ClassInstance> instances) {
		for (ClassInstance ci : instances) {
			classInstanceRepository.delete(ci.getId());
		}
	}

	public List<ClassInstance> addOrUpdateClassDefinitions(List<ClassInstance> classInstances) {
		return classInstanceRepository.save(classInstances);
	}

	public List<ClassInstance> filterTaskInstancesByYear(int year, List<ClassInstance> classInstances) {
		if (year == 0) {
			return classInstances;
		}
		return classInstances.stream().filter(tI -> {
			PropertyInstance<Object> startDateProperty = tI.findProperty("Starting Date");
			if (startDateProperty != null && startDateProperty.getValues().size() == 1) {
				Date date = new Date((long) startDateProperty.getValues().get(0));
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				return calendar.get(Calendar.YEAR) == year;
			}
			return false;
		}).collect(Collectors.toList());
	}

}
