package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
import at.jku.cis.iVolunteer.model.task.XDynamicFieldBlock;
import at.jku.cis.iVolunteer.model.task.XTaskTemplate;

@Component
public class XFormEntryToTaskTemplateMapper implements OneWayMapper<FormEntry, XTaskTemplate> {

	@Autowired XClassPropertyDynamicFieldMapper classPropertyToDynamicFieldMapper;

	@Autowired ClassConfigurationRepository classConfigurationRepository;

	@Override
	public XTaskTemplate toTarget(FormEntry source) {

		if (source == null) {
			return null;
		}

		XTaskTemplate template = new XTaskTemplate();
		String classConfigurationId = source.getClassDefinitions().get(0).getConfigurationId();
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(classConfigurationId);

		template.setId(source.getClassDefinitions().get(0).getId());
		template.setTenantId(source.getClassDefinitions().get(0).getTenantId());
		template.setTitle(source.getClassDefinitions().get(0).getName());
		template.setDescription(source.getClassDefinitions().get(0).getDescription());
		if (classConfiguration != null) {
			template.setConfigurationName(classConfiguration.getName());
		}
		List<XDynamicFieldBlock> dynamic = new ArrayList<>();

		int listEnd = source.getClassDefinitions().size() - 1;

		for (int i = listEnd; i >= 0; i--) {
			if (source.getClassDefinitions().get(i).getLevel() > 1) {
				XDynamicFieldBlock dynamicBlock = new XDynamicFieldBlock();
				dynamicBlock.setTitle(source.getClassDefinitions().get(i).getName());
				dynamicBlock.setFields(new ArrayList<>());
				dynamicBlock.getFields().addAll(classPropertyToDynamicFieldMapper
						.toTargets(source.getClassDefinitions().get(i).getProperties()));
				dynamic.add(dynamicBlock);
			}
		}
		template.setDynamicBlocks(dynamic);

		return template;
	}

	@Override
	public List<XTaskTemplate> toTargets(List<FormEntry> sources) {
		if (sources == null) {
			return null;
		}
		List<XTaskTemplate> targets = new ArrayList<>();
		for (FormEntry source : sources) {
			targets.add(toTarget(source));
		}
		return targets;
	}

}
