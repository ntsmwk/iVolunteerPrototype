package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.management.relation.Relation;
import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.property.ClassPropertyToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.AssociationCardinality;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;
import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;
import at.jku.cis.iVolunteer.model.meta.form.EnumRepresentation;
import at.jku.cis.iVolunteer.model.meta.form.FormConfiguration;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class ClassInstanceService {

	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private ClassPropertyToPropertyInstanceMapper classPropertyToPropertyInstanceMapper;

	public ClassInstance getClassInstance(Volunteer volunteer, String classDefinitionId, String tenantId) {
		return classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinitionId, tenantId).stream().findFirst().orElse(null);
	}

	public ClassInstance getClassInstanceByName(Volunteer volunteer, String classDefinitionName, String tenantId) {
		return getClassInstance(volunteer, classDefinitionService.getByName(classDefinitionName, tenantId).getId(), tenantId);
	}

	public List<ClassInstance> getClassInstances(Volunteer volunteer, String classDefinitionId, String tenantId) {
		return classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinitionId, tenantId);
	}

	public List<ClassInstance> getClassInstancesByName(Volunteer volunteer, String classDefinitionName, String tenantId){
		return getClassInstances(volunteer, classDefinitionService.getByName(classDefinitionName, tenantId).getId(), tenantId);
	}
	
	public List<ClassInstance> filterInstancesByPropertyCriteria(Volunteer volunteer, String classDefinitionId, String tenantId, Criteria criteria){
		List<ClassInstance> allInstances = getClassInstances(volunteer, classDefinitionId, tenantId);
		return filterInstancesByPropertyCriteria(allInstances, criteria);
	}
	
	public List<ClassInstance> filterInstancesByPropertyCriteria(List<ClassInstance> instances, Criteria criteria){
		return criteria.meetCriteria(instances);
	}
	
	public void setProperty(ClassInstance ci, String propertyId, Object propertyValue) {
		PropertyInstance<Object> pi = ci.getProperty(propertyId);
		pi.setValues(Arrays.asList(propertyValue));
		classInstanceRepository.save(ci);
	}
	
	public void addPropertyValue(ClassInstance ci, String propertyId, Object propertyValue) {
		PropertyInstance<Object> pi = ci.getProperty(propertyId);
		pi.getValues().add(propertyValue);
		classInstanceRepository.save(ci);
	}

	public ClassInstance newClassInstance(Volunteer volunteer, String classDefinitionId, String tenantId) {
		ClassDefinition classDefinition = classDefinitionService.getClassDefinitionById(classDefinitionId, tenantId);
		ClassInstance ci;
		switch (classDefinition.getClassArchetype()) {
		case ACHIEVEMENT: ci = new AchievementClassInstance(); break;
		case COMPETENCE: ci = new CompetenceClassInstance(); break;
		case TASK: ci = new TaskClassInstance(); break;
		case FUNCTION: ci = new FunctionClassInstance(); break;
		default: ci = null; 
		}
		ci.setName(classDefinition.getName());
		ci.setClassDefinitionId(classDefinition.getId());
		ci.setUserId(volunteer.getId());
		ci.setMarketplaceId(marketplaceService.getMarketplaceId());
		ci.setTenantId(tenantId);
		// copy properties from target class
		List<PropertyInstance<Object>> propInstList = new ArrayList<PropertyInstance<Object>>();
		List<ClassProperty<Object>> propLicenseList = classDefinition.getProperties();
		propLicenseList.forEach(cp -> propInstList.add(classPropertyToPropertyInstanceMapper.toTarget(cp)));
		ci.setProperties(propInstList);
		return newClassInstance(ci);
	}
	
	public ClassInstance newClassInstance(ClassInstance classInstance) {
		return classInstanceRepository.save(classInstance);
	}

	public void deleteClassInstances(Volunteer volunteer, String classDefinitionId, String tenantId) {
		List<ClassInstance> instances = classInstanceRepository.
				getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classDefinitionId, tenantId);   
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

}
