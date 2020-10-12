package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
import at.jku.cis.iVolunteer.model.task.XDynamicFieldBlock;
import at.jku.cis.iVolunteer.model.task.XTaskTemplate;

public class XFormEntryToTaskTemplateMapper implements OneWayMapper<FormEntry, XTaskTemplate> {
	
	@Autowired XClassPropertyDynamicFieldMapper classPropertyToDynamicFieldMapper;

	@Override
	public XTaskTemplate toTarget(FormEntry source) {

		if (source == null) {
			return null;
		}

		XTaskTemplate template = new XTaskTemplate();
		template.setId(source.getId());
		template.setTenant(source.getClassDefinitions().get(0).getTenantId());
		
		
		List<XDynamicFieldBlock> dynamic = new ArrayList<>();

		int listEnd = source.getClassDefinitions().size() - 1;

		for (int i = listEnd; i >= 0; i--) {
			if (source.getClassDefinitions().get(i).getLevel() > 1) {
				XDynamicFieldBlock dynamicBlock = new XDynamicFieldBlock();
				dynamicBlock.setTitle(source.getClassDefinitions().get(i).getName());
				dynamicBlock.setFields(new ArrayList<>());
				dynamicBlock.getFields().addAll(
						classPropertyToDynamicFieldMapper.toTargets(source.getClassDefinitions().get(i).getProperties()));
				dynamic.add(dynamicBlock);
			}
		}
//
//		required.setExpired(false);
//		required.setName(source.getClassDefinitions().get(0).getName());
//		required.setId(source.getClassDefinitions().get(0).getId());
//		required.setImage(source.getClassDefinitions().get(0).getImagePath());
//
//		required.setStartDate(
//				classPropertyToTaskFieldMapper.toTarget(findProperty("Starting Date", source.getClassProperties())));
//		required.setEndDate(
//				classPropertyToTaskFieldMapper.toTarget(findProperty("End Date", source.getClassProperties())));
//		required.setDescripiton(
//				classPropertyToTaskFieldMapper.toTarget(findProperty("Description", source.getClassProperties())));
//		required.setPlace(
//				classPropertyToTaskFieldMapper.toTarget(findProperty("Location", source.getClassProperties())));
//
		template.setDynamicFields(dynamic);
//		taskDefinition.setRequired(required);

		return template;
	}

	@Override
	public List<XTaskTemplate> toTargets(List<FormEntry> sources) {
		if (sources == null) {return null;}
		List<XTaskTemplate> targets = new ArrayList<>();
		for(FormEntry source : sources) {
			targets.add(toTarget(source));
		}
		return targets;
	}


}
