package at.jku.cis.iVolunteer.model._mapper.xnet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.task.XDynamicField;
import at.jku.cis.iVolunteer.model.task.XDynamicFieldBlock;
import at.jku.cis.iVolunteer.model.task.XTask;

@Component
public class XTaskInstanceToTaskMapper implements AbstractMapper<TaskInstance, XTask> {

	@Autowired XPropertyInstanceDynamicFieldMapper xPropertyInstanceToDynamicFieldMapper;

	@Override
	public XTask toTarget(TaskInstance source) {
		if (source == null) {
			return null;
		}
		// (null / false) === TODO
		XTask task = new XTask();
		task.setId(source.getId());
		task.setTitle(source.getName());
		task.setTenant(source.getTenantId());
		task.setDescription(source.getDescription());
		task.setStartDate(null);
		task.setEndDate(null);
		task.setImagePath(source.getImagePath());
		task.setClosed(false);
// TODO
		task.setGeoInfo(null);
//		task.(propertyInstanceToTaskFieldMapper.toTarget(findProperty("Location", source.getProperties())));

		ArrayList<ArrayList<PropertyInstance<Object>>> sortedFields = sortPropertiesByLevel(source.getProperties());
		task.setDynamicFields(new ArrayList<>());
		task.setStartDate((Date) xPropertyInstanceToDynamicFieldMapper
				.toTarget(findProperty("Starting Date", source.getProperties())).getValues().get(0));
		task.setEndDate((Date) xPropertyInstanceToDynamicFieldMapper
				.toTarget(findProperty("End Date", source.getProperties())).getValues().get(0));

		for (int i = 2; i < sortedFields.size(); i++) {
			XDynamicFieldBlock dynamicBlock = new XDynamicFieldBlock();
			dynamicBlock.setFields(xPropertyInstanceToDynamicFieldMapper.toTargets(sortedFields.get(i)));
			task.getDynamicFields().add(dynamicBlock);
		}

		task.setSubscribedUsers(null);
		task.setBadges(null);

		return task;
	}

	@Override
	public List<XTask> toTargets(List<TaskInstance> sources) {
		if (sources == null) {
			return null;
		}

		List<XTask> targets = new ArrayList<>();
		for (TaskInstance source : sources) {
			targets.add(toTarget(source));
		}

		return targets;

	}

	@Override
	public TaskInstance toSource(XTask target) {
		if (target == null) {
			return null;
		}

		TaskInstance instance = new TaskInstance();
		instance.setId(target.getId());
		instance.setName(target.getTitle());
		instance.setUserId(null);
		instance.setIssuerId(target.getTenant());
		instance.setImagePath(target.getImagePath());
		instance.setClassArchetype(ClassArchetype.TASK);
		instance.setVisible(true);
		instance.setTabId(0);
		instance.setBlockchainDate(new Date());
		instance.setLevel(0);
		instance.setSubscribedVolunteerIds(null);
		instance.setStatus("status");

//	 TODO DynamicFieldBlocks
		instance.setProperties(null);

		PropertyInstance<Object> startDate = new PropertyInstance<Object>();
		startDate.setValues(Collections.singletonList(target.getStartDate()));
		startDate.setType(PropertyType.DATE);
		startDate.setLevel(1);

//findAndReplaceProperty(startDate, classInstance);

		PropertyInstance<Object> endDate = new PropertyInstance<Object>();
		endDate.setValues(Collections.singletonList(target.getEndDate()));
		endDate.setType(PropertyType.DATE);
		endDate.setLevel(1);

//TODO from geoinfo
//PropertyInstance<Object> location = propertyInstanceToTaskFieldMapper.toSource(target.getRequired().getPlace());

		for (int i = 0; i < target.getDynamicFields().size(); i++) {
			XDynamicFieldBlock block = target.getDynamicFields().get(i);
			for (XDynamicField field : block.getFields()) {

				PropertyInstance<Object> pi = xPropertyInstanceToDynamicFieldMapper.toSource(field);
				pi.setLevel(i + 2);
				instance.getProperties().add(pi);
			}
		}

		return instance;
	}

	@Override
	public List<TaskInstance> toSources(List<XTask> targets) {
		if (targets == null) {
			return null;
		}
		List<TaskInstance> sources = new ArrayList<>();
		for (XTask target : targets) {
			sources.add(toSource(target));
		}
		return sources;
	}

	private PropertyInstance<Object> findProperty(String name, List<PropertyInstance<Object>> properties) {
		PropertyInstance<Object> property = properties.stream().filter(p -> p.getName().equals(name)).findAny()
				.orElse(null);
		return property;
	}

// private class MaxLevelReturn {
// int maxLevel;
// boolean hasLevel0;
// boolean hasLevel1;
//
// public MaxLevelReturn(int maxLevel, boolean hasLevel0, boolean hasLevel1) {
// this.maxLevel = maxLevel;
// this.hasLevel0 = hasLevel0;
// this.hasLevel1 = hasLevel1;
// }
// }

	private int findMaxLevel(List<PropertyInstance<Object>> propertyInstances) {
		int maxLevel = 0;
		boolean hasLevel0 = false;
		boolean hasLevel1 = false;

		for (PropertyInstance<Object> instance : propertyInstances) {
			maxLevel = Math.max(maxLevel, instance.getLevel());
			hasLevel0 = hasLevel0 || instance.getLevel() == 0;
			hasLevel1 = hasLevel1 || instance.getLevel() == 1;
		}

		if (!hasLevel0 && !hasLevel1) {
			maxLevel = maxLevel + 2;
		} else if (!hasLevel0 || !hasLevel1) {
			maxLevel++;
		}
		return maxLevel;
	}

	private ArrayList<ArrayList<PropertyInstance<Object>>> sortPropertiesByLevel(
			List<PropertyInstance<Object>> propertyInstances) {
		ArrayList<ArrayList<PropertyInstance<Object>>> sorted = new ArrayList<ArrayList<PropertyInstance<Object>>>();

		int maxLevel = findMaxLevel(propertyInstances);

		for (int i = 0; i < maxLevel + 1; i++) {
			sorted.add(new ArrayList<PropertyInstance<Object>>());
		}

		for (PropertyInstance<Object> pi : propertyInstances) {
			sorted.get(pi.getLevel()).add(pi);
		}

		return sorted;
	}

}
