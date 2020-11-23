package at.jku.cis.iVolunteer.configurator.meta.core.class_;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinitionDTO;

@Component
public class ClassDefinitionMapper {

	@Autowired private ClassConfigurationRepository classConfigurationRepository;

	List<ClassDefinitionDTO> mapToDTO(List<ClassDefinition> classDefinitions) {

		List<ClassDefinitionDTO> classDefinitionDTOs = classDefinitions.stream().map(cd -> {
			ClassDefinitionDTO dto = new ClassDefinitionDTO();

			dto.setId(cd.getId());
			dto.setParentId(cd.getParentId());
			dto.setConfigurationId(cd.getConfigurationId());
			dto.setName(cd.getName());
			dto.setProperties(cd.getProperties());
			dto.setClassArchetype(cd.getClassArchetype());
			dto.setCollector(cd.isCollector());
			dto.setWriteProtected(cd.isWriteProtected());
			dto.setImagePath(cd.getImagePath());
			dto.setRoot(cd.isRoot());
			dto.setVisible(cd.isVisible());
			dto.setTabId(cd.getTabId());

			setConfigurationName(cd, dto);
			return dto;

		}).collect(Collectors.toList());
		return classDefinitionDTOs;
	}

	private void setConfigurationName(ClassDefinition cd, ClassDefinitionDTO dto) {
		// @formatter:off
		List<ClassConfiguration> configurations = this.classConfigurationRepository.findByTenantId(cd.getTenantId());
		String configurationName = configurations
			.stream()
			.filter(c -> c.getId().equals(cd.getConfigurationId()))
			.map(c -> c.getName())
			.findFirst()
			.orElse("");
		dto.setConfigurationName(configurationName);
		// @formatter:on
	}

}
