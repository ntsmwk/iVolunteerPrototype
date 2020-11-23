package at.jku.cis.iVolunteer.model._mapper.xnet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.jku.cis.iVolunteer.model._httpresponses.XTaskSubscribedResponse;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.Location;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.task.XDynamicField;
import at.jku.cis.iVolunteer.model.task.XDynamicFieldBlock;
import at.jku.cis.iVolunteer.model.task.XTask;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;

@Component
public class XTaskInstanceToTaskMapper {

	@Autowired
	XPropertyInstanceDynamicFieldMapper xPropertyInstanceToDynamicFieldMapper;
	@Autowired
	XUserMapper xUserMapper;
	@Autowired
	ObjectMapper objectMapper;

	public XTask toTarget(TaskInstance source, List<? extends User> subscribedUsers) {
		if (source == null) {
			return null;
		}
		XTask task = new XTask();
		task.setId(source.getId());
		task.setTitle(source.getName());
		task.setDescription(source.getDescription());
		task.setTenantId(source.getTenantId());
		task.setDescription(source.getDescription());
		task.setImagePath(source.getImagePath());
		task.setStatus(source.getStatus());

		ArrayList<ArrayList<PropertyInstance<Object>>> sortedFields = sortPropertiesByLevel(source.getProperties());
		task.setDynamicBlocks(new ArrayList<>());

		PropertyInstance<Object> startDateField = source.findProperty("Starting Date");
		task.setStartDate(startDateField == null || startDateField.getValues().size() == 0 ? null
				: new Date((Long) startDateField.getValues().get(0)));
		PropertyInstance<Object> endDateField = source.findProperty("End Date");
		task.setEndDate(endDateField == null || endDateField.getValues().size() == 0 ? null
				: new Date((Long) endDateField.getValues().get(0)));

		PropertyInstance<Object> locationField = source.findProperty("Location");

		Location location = null;
		if (locationField != null && locationField.getValues().size() > 0) {
			location = objectMapper.convertValue(locationField.getValues().get(0), Location.class);
		}

		task.setGeoInfo(new XGeoInfo(location));

		for (int i = 2; i < sortedFields.size(); i++) {
			XDynamicFieldBlock dynamicBlock = new XDynamicFieldBlock();
			dynamicBlock.setFields(xPropertyInstanceToDynamicFieldMapper.toTargets(sortedFields.get(i)));
			task.getDynamicBlocks().add(dynamicBlock);
		}

		if (subscribedUsers == null || subscribedUsers.size() <= 0) {
			task.setSubscribedUsers(new ArrayList<>());
		} else {
			task.setSubscribedUsers(xUserMapper.toTargets(subscribedUsers));
		}
		task.setBadges(null);

		return task;
	}

	public XTaskSubscribedResponse toTaskSubscribedResponse(TaskInstance source, List<? extends User> subscribedUsers) {

		if (source == null) {
			return null;
		}
		XTaskSubscribedResponse xt = new XTaskSubscribedResponse();
		xt.setId(source.getId());
		xt.setTitle(source.getName());
		xt.setDescription(source.getDescription());
		xt.setTenantId(source.getTenantId());
		xt.setDescription(source.getDescription());
		xt.setImagePath(source.getImagePath());
		xt.setStatus(source.getStatus());

		ArrayList<ArrayList<PropertyInstance<Object>>> sortedFields = sortPropertiesByLevel(source.getProperties());
		xt.setDynamicBlocks(new ArrayList<>());

		PropertyInstance<Object> startDateField = source.findProperty("Starting Date");
		xt.setStartDate(startDateField == null || startDateField.getValues().size() == 0 ? null
				: new Date((Long) startDateField.getValues().get(0)));
		PropertyInstance<Object> endDateField = source.findProperty("End Date");
		xt.setEndDate(endDateField == null || endDateField.getValues().size() == 0 ? null
				: new Date((Long) endDateField.getValues().get(0)));

		PropertyInstance<Object> locationField = source.findProperty("Location");

		Location location = null;
		if (locationField != null && locationField.getValues().size() > 0) {
			location = objectMapper.convertValue(locationField.getValues().get(0), Location.class);
		}

		xt.setGeoInfo(new XGeoInfo(location));

		for (int i = 2; i < sortedFields.size(); i++) {
			XDynamicFieldBlock dynamicBlock = new XDynamicFieldBlock();
			dynamicBlock.setFields(xPropertyInstanceToDynamicFieldMapper.toTargets(sortedFields.get(i)));
			xt.getDynamicBlocks().add(dynamicBlock);
		}

		if (subscribedUsers == null || subscribedUsers.size() <= 0) {
			xt.setSubscribedUsers(new ArrayList<>());
		} else {
			xt.setSubscribedUsers(xUserMapper.toTargets(subscribedUsers));
		}
		xt.setBadges(null);

		return xt;
	}

	// public List<XTask> toTargets(List<TaskInstance> sources) {
	// if (sources == null) {
	// return null;
	// }
	//
	// List<XTask> targets = new ArrayList<>();
	// for (TaskInstance source : sources) {
	// targets.add(toTarget(source));
	// }
	//
	// return targets;
	// }

	public TaskInstance toSource(XTask target) {
		if (target == null) {
			return null;
		}

		TaskInstance instance = new TaskInstance();
		instance.setId(target.getId());
		instance.setName(target.getTitle());
		instance.setDescription(target.getDescription());
		instance.setUserId(null);
		instance.setIssuerId(target.getTenantId());
		instance.setImagePath(target.getImagePath());
		instance.setClassArchetype(ClassArchetype.TASK);
		instance.setVisible(true);
		instance.setTabId(0);
		instance.setBlockchainDate(new Date());
		instance.setLevel(0);
		if (target.getSubscribedUsers() == null || target.getSubscribedUsers().size() <= 0) {
			instance.setSubscribedVolunteerIds(new ArrayList<>());
		} else {
			instance.setSubscribedVolunteerIds(
					target.getSubscribedUsers().stream().map(u -> u.getId()).collect(Collectors.toList()));
		}
		if (target.getStatus() != null) {
			instance.setStatus(target.getStatus());
		}
		instance.setTenantId(target.getTenantId());

		PropertyInstance<Object> startDate = new PropertyInstance<Object>();

		if (target.getStartDate() != null) {
			startDate.setValues(Collections.singletonList(target.getStartDate().getTime()));
			startDate.setId(new ObjectId().toHexString());
			startDate.setName("Starting Date");
			startDate.setType(PropertyType.DATE);
			startDate.setLevel(1);
			instance.getProperties().add(startDate);
		}

		if (target.getEndDate() != null) {
			PropertyInstance<Object> endDate = new PropertyInstance<Object>();
			endDate.setId(new ObjectId().toHexString());
			endDate.setValues(Collections.singletonList(target.getEndDate().getTime()));
			endDate.setName("End Date");
			endDate.setType(PropertyType.DATE);
			endDate.setLevel(1);
			instance.getProperties().add(endDate);
		}

		if (target.getGeoInfo() != null) {
			PropertyInstance<Object> location = new PropertyInstance<Object>();
			location.setId(new ObjectId().toHexString());
			location.setValues(Collections.singletonList(new Location(target.getGeoInfo())));
			location.setName("Location");
			location.setType(PropertyType.LOCATION);
			location.setLevel(1);
			instance.getProperties().add(location);
		}

		for (int i = 0; i < target.getDynamicBlocks().size(); i++) {
			XDynamicFieldBlock block = target.getDynamicBlocks().get(i);
			for (XDynamicField field : block.getFields()) {

				PropertyInstance<Object> pi = xPropertyInstanceToDynamicFieldMapper.toSource(field);
				pi.setLevel(i + 2);
				instance.getProperties().add(pi);
			}
		}

		return instance;
	}

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
