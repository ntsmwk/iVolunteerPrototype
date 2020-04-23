package at.jku.cis.iVolunteer.marketplace._mapper.z_etc;
//package at.jku.cis.iVolunteer.mapper.property;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import at.jku.cis.iVolunteer.mapper.OneWayMapper;
//import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyItem;
//import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;
//
//@Component
//public class PropertyItemMapper implements OneWayMapper<UserDefinedTaskTemplate, PropertyItem> {
//
//	@Override
//	public PropertyItem toTarget(UserDefinedTaskTemplate source) {
//
//		if (source == null) {
//			return null;
//		}
//
//		PropertyItem item = new PropertyItem();
//		item.setId(source.getId());
//		item.setName(source.getName());
//
//		return item;
//	}
//
//	@Override
//	public List<PropertyItem> toTargets(List<UserDefinedTaskTemplate> sources) {
//		if (sources == null) {
//			return null;
//		}
//
//		List<PropertyItem> list = new ArrayList<PropertyItem>(sources.size());
//		for (UserDefinedTaskTemplate item : sources) {
//			list.add(toTarget(item));
//		}
//		return list;
//	}
//}