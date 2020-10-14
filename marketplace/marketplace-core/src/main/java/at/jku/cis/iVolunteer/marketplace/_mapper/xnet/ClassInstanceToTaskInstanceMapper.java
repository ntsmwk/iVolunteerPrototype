package at.jku.cis.iVolunteer.marketplace._mapper.xnet;
// TODO remove
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.kie.api.task.model.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
//import at.jku.cis.iVolunteer.marketplace.security.LoginService;
//import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
//import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
//import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
//import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
//import at.jku.cis.iVolunteer.model.task.DynamicTaskBlock;
//import at.jku.cis.iVolunteer.model.task.RequiredTaskBlock;
//import at.jku.cis.iVolunteer.model.task.TaskDefinition;
//import at.jku.cis.iVolunteer.model.task.TaskField;
//import at.jku.cis.iVolunteer.model.task.TaskInstance;
//import at.jku.cis.iVolunteer.model.user.User;;
//
//@Component
//public class ClassInstanceToTaskInstanceMapper implements AbstractMapper<ClassInstance, TaskInstance> {
//
//	@Autowired
//	PropertyInstanceToTaskFieldMapper propertyInstanceToTaskFieldMapper;
//	@Autowired
//	ClassInstanceService classInstanceService;
//	@Autowired
//	LoginService loginService;
//
//	@Override
//	public TaskInstance toTarget(ClassInstance source) {
//		if (source == null) {
//			return null;
//		}
//
//		TaskInstance taskInstance = new TaskInstance();
//		RequiredTaskBlock required = new RequiredTaskBlock();
//		List<DynamicTaskBlock> dynamic = new ArrayList<>();
//
//		ArrayList<ArrayList<PropertyInstance<Object>>> sortedFields = sortPropertiesByLevel(source.getProperties());
//
//		required.setExpired(source.isExpired());
//		required.setName(source.getName());
//		required.setId(source.getId());
//		required.setImage(source.getImagePath());
//
//		required.setStartDate(
//				propertyInstanceToTaskFieldMapper.toTarget(findProperty("Starting Date", source.getProperties())));
//		required.setEndDate(
//				propertyInstanceToTaskFieldMapper.toTarget(findProperty("End Date", source.getProperties())));
//		required.setDescripiton(
//				propertyInstanceToTaskFieldMapper.toTarget(findProperty("Description", source.getProperties())));
//		required.setPlace(propertyInstanceToTaskFieldMapper.toTarget(findProperty("Location", source.getProperties())));
//
//		for (int i = 2; i < sortedFields.size(); i++) {
//			DynamicTaskBlock dynamicBlock = new DynamicTaskBlock();
//			dynamicBlock.setFields(propertyInstanceToTaskFieldMapper.toTargets(sortedFields.get(i)));
//			dynamic.add(dynamicBlock);
//		}
//
//		taskInstance.setRequired(required);
//		taskInstance.setDynamic(dynamic);
//
//		return taskInstance;
//	}
//
//	private PropertyInstance<Object> findProperty(String name, List<PropertyInstance<Object>> properties) {
//		PropertyInstance<Object> property = properties.stream().filter(p -> p.getName().equals(name)).findAny()
//				.orElse(null);
//		return property;
//	}
//
//	// private class MaxLevelReturn {
//	// int maxLevel;
//	// boolean hasLevel0;
//	// boolean hasLevel1;
//	//
//	// public MaxLevelReturn(int maxLevel, boolean hasLevel0, boolean hasLevel1) {
//	// this.maxLevel = maxLevel;
//	// this.hasLevel0 = hasLevel0;
//	// this.hasLevel1 = hasLevel1;
//	// }
//	// }
//
//	private int findMaxLevel(List<PropertyInstance<Object>> propertyInstances) {
//		int maxLevel = 0;
//		boolean hasLevel0 = false;
//		boolean hasLevel1 = false;
//
//		for (PropertyInstance<Object> instance : propertyInstances) {
//			maxLevel = Math.max(maxLevel, instance.getLevel());
//			hasLevel0 = hasLevel0 || instance.getLevel() == 0;
//			hasLevel1 = hasLevel1 || instance.getLevel() == 1;
//		}
//
//		if (!hasLevel0 && !hasLevel1) {
//			maxLevel = maxLevel + 2;
//		} else if (!hasLevel0 || !hasLevel1) {
//			maxLevel++;
//		}
//		return maxLevel;
//	}
//
//	private ArrayList<ArrayList<PropertyInstance<Object>>> sortPropertiesByLevel(
//			List<PropertyInstance<Object>> propertyInstances) {
//		ArrayList<ArrayList<PropertyInstance<Object>>> sorted = new ArrayList<ArrayList<PropertyInstance<Object>>>();
//
//		int maxLevel = findMaxLevel(propertyInstances);
//
//		for (int i = 0; i < maxLevel + 1; i++) {
//			sorted.add(new ArrayList<PropertyInstance<Object>>());
//		}
//
//		for (PropertyInstance<Object> pi : propertyInstances) {
//			sorted.get(pi.getLevel()).add(pi);
//		}
//
//		return sorted;
//	}
//
//	@Override
//	public List<TaskInstance> toTargets(List<ClassInstance> sources) {
//
//		if (sources == null) {
//			return null;
//		}
//
//		List<TaskInstance> targets = new ArrayList<>();
//
//		for (ClassInstance source : sources) {
//			targets.add(toTarget(source));
//		}
//
//		return targets;
//
//	}
//
//	@Override
//	public ClassInstance toSource(TaskInstance target) {
//		if (target == null) {
//			return null;
//		}
//
//		ClassInstance classInstance = new ClassInstance();
//
//		if (target.getRequired().getId() != null) {
//			classInstance = classInstanceService.getClassInstanceById(target.getRequired().getId());
//		}
//		User user = loginService.getLoggedInUser();
//
//		// //TODO DEBUG TESTING
//		// user = new User();
//		// user.setId("5f71ca22e5ccdd629ee45d47");
//		// //--------
//
//		if (user == null) {
//			return null;
//		}
//
//		if (classInstance == null) {
//			classInstance = new ClassInstance();
//			classInstance.setUserId(user.getId());
//		}
//
//		classInstance.setId(target.getRequired().getId());
//		classInstance.setName(target.getRequired().getName());
//		classInstance.setExpired(target.getRequired().isExpired());
//		classInstance.setImagePath(target.getRequired().getImage());
//
//		PropertyInstance<Object> startDate = propertyInstanceToTaskFieldMapper
//				.toSource(target.getRequired().getStartDate());
//		startDate.setLevel(1);
//		findAndReplaceProperty(startDate, classInstance);
//
//		PropertyInstance<Object> endDate = propertyInstanceToTaskFieldMapper
//				.toSource(target.getRequired().getEndDate());
//		endDate.setLevel(1);
//		findAndReplaceProperty(endDate, classInstance);
//
//		PropertyInstance<Object> description = propertyInstanceToTaskFieldMapper
//				.toSource(target.getRequired().getDescripiton());
//		description.setLevel(1);
//		findAndReplaceProperty(description, classInstance);
//
//		PropertyInstance<Object> location = propertyInstanceToTaskFieldMapper.toSource(target.getRequired().getPlace());
//		location.setLevel(1);
//		findAndReplaceProperty(location, classInstance);
//
//		for (int i = 0; i < target.getDynamic().size(); i++) {
//			DynamicTaskBlock block = target.getDynamic().get(i);
//			for (TaskField field : block.getFields()) {
//
//				PropertyInstance<Object> pi = propertyInstanceToTaskFieldMapper.toSource(field);
//				pi.setLevel(i + 2);
//				classInstance.getProperties().add(pi);
//			}
//		}
//		return classInstance;
//	}
//
//	private void findAndReplaceProperty(PropertyInstance<Object> newProperty, ClassInstance instance) {
//		for (PropertyInstance<Object> p : instance.getProperties()) {
//			if (p.getId().equals(newProperty.getId())) {
//				p.setId(newProperty.getId());
//				p.setName(newProperty.getName());
//				p.setType(newProperty.getType());
//				p.setRequired(newProperty.isRequired());
//				p.setUnit(newProperty.getUnit());
//				p.setAllowedValues(newProperty.getAllowedValues());
//				p.setPropertyConstraints(newProperty.getPropertyConstraints());
//				p.setValues(newProperty.getValues());
//				return;
//			}
//		}
//		instance.getProperties().add(newProperty);
//	}
//
//	@Override
//	public List<ClassInstance> toSources(List<TaskInstance> targets) {
//		if (targets == null) {
//			return null;
//		}
//
//		List<ClassInstance> sources = new ArrayList<>();
//		for (TaskInstance target : targets) {
//			sources.add(toSource(target));
//		}
//		return sources;
//	}
//
//}
