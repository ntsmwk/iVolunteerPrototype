package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import at.jku.cis.iVolunteer.marketplace._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.task.XDynamicField;;

@Component
public class XPropertyInstanceDynamicFieldMapper implements OneWayMapper<PropertyInstance<Object>, XDynamicField> {

	@Override
	public XDynamicField toTarget(PropertyInstance<Object> source) {
		if (source == null) {return null;}
		
		XDynamicField field = new XDynamicField();
		
		field.setId(source.getId());
		field.setName(source.getName());
		field.setDescription(source.getDescription());
		field.setCustom(false); //TODO
		field.setMultiple(false);
		field.setType(source.getType());
		field.setAllowedValues(source.getAllowedValues());
		field.setValues(source.getValues());
		field.setUnit(source.getUnit());
		field.setRequired(source.isRequired());
		field.setRequiredMessage(null);
		field.setFieldConstraints(source.getPropertyConstraints());
		field.setTimestamp(source.getTimestamp());
		field.setVisible(source.isVisible());
		field.setTabId(source.getTabId());
		
		return field;
	}

	@Override
	public List<XDynamicField> toTargets(List<PropertyInstance<Object>> sources) {
		if (sources == null) {
			return null;
		}
		
		List<XDynamicField> targets = new ArrayList<>();
		
		for (PropertyInstance<Object> source : sources) {
			targets.add(toTarget(source));
		}
		
		
		return targets;
	}

//	@Override
//	public PropertyInstance<Object> toSource(XDynamicField target) {
//		if (target == null) {return null; }
//		
//		PropertyInstance<Object> instances = new PropertyInstance<Object>();
//		i
//
//		return null;
//	}
//
//	@Override
//	public List<PropertyInstance<Object>> toSources(List<XDynamicField> targets) {
//		if (targets == null) {return null;}
//	
//		List<PropertyInstance<Object>> sources = new ArrayList<>();
//		for (XDynamicField target : targets) {
//			sources.add(toSource(target));
//		}
//		return sources;
//	}
	
	
	


	

}
