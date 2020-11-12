package at.jku.cis.iVolunteer.model._mapper.xnet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstanceStatus;
import at.jku.cis.iVolunteer.model.meta.core.property.Location;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.task.XDynamicFieldBlock;
import at.jku.cis.iVolunteer.model.task.XTaskSerialized;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;

@Component
public class XTaskInstanceToTaskSerializedMapper implements OneWayMapper<TaskInstance, XTaskSerialized> {

	@Autowired XPropertyInstanceDynamicFieldMapper xPropertyInstanceToDynamicFieldMapper;
	
	@Override
	public XTaskSerialized toTarget(TaskInstance source) {
		if (source == null) {
			return null;
		}

		// (null / false) === TODO

		XTaskSerialized ts = new XTaskSerialized();
		ts.setId(source.getId());
		ts.setTitle(source.getName());
		ts.setDescription(source.getDescription());
		ts.setImagePath(source.getImagePath());
		ts.setStatus(source.getStatus());

		ArrayList<ArrayList<PropertyInstance<Object>>> sortedFields = sortPropertiesByLevel(source.getProperties());
		ts.setDynamicBlocks(new ArrayList<>());
		PropertyInstance<Object> startDateField = findProperty("Starting Date", source.getProperties());
		ts.setStartDate(startDateField == null || startDateField.getValues().size() == 0 ? null :  new Date((Long) startDateField.getValues().get(0)));
		
		PropertyInstance<Object> endDateField =  findProperty("End Date", source.getProperties());
		ts.setEndDate(endDateField == null || endDateField.getValues().size() == 0 ? null :  new Date((Long) endDateField.getValues().get(0)));

		PropertyInstance<Object> locationField = findProperty("Location", source.getProperties());
		Location location = locationField == null || locationField.getValues().size() == 0 ? null :  (Location) locationField.getValues().get(0);

		ts.setGeoInfo(new XGeoInfo(location));
		
		for (int i = 2; i < sortedFields.size(); i++) {
			XDynamicFieldBlock dynamicBlock = new XDynamicFieldBlock();
			dynamicBlock.setFields(xPropertyInstanceToDynamicFieldMapper.toTargets(sortedFields.get(i)));
			ts.getDynamicBlocks().add(dynamicBlock);
		}

		return ts;
	}

	@Override
	public List<XTaskSerialized> toTargets(List<TaskInstance> sources) {
		if (sources == null) {
			return null;
		}

		List<XTaskSerialized> targets = new ArrayList<>();
		for (TaskInstance source : sources) {
			targets.add(toTarget(source));
		}

		return targets;

	}
	

	private PropertyInstance<Object> findProperty(String name, List<PropertyInstance<Object>> properties) {
		PropertyInstance<Object> property = properties.stream().filter(p -> p.getName().equals(name)).findAny()
				.orElse(null);
		return property;
	}

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
