package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Service
public class ClassInstanceMapper {

	List<ClassInstanceDTO> mapToDTO(List<ClassInstance> classInstances) {
		List<ClassInstanceDTO> classInstanceDTOs = classInstances.stream().map(ci -> {

			ClassInstanceDTO dto = new ClassInstanceDTO();
			dto.setBlockchainDate(ci.getTimestamp());

			PropertyInstance<Object> name = ci.getProperties().stream().filter(p -> "Name".equals(p.getName()))
					.findFirst().orElse(null);
			dto.setName((String) name.getValues().get(0));

			PropertyInstance<Object> purpose = ci.getProperties().stream().filter(p -> "purpose".equals(p.getName()))
					.findFirst().orElse(null);
			dto.setPurpose((String) purpose.getValues().get(0));

			PropertyInstance<Object> startingDate = ci.getProperties().stream()
					.filter(p -> "Starting Date".equals(p.getName())).findFirst().orElse(null);
			dto.setDateFrom((String) startingDate.getValues().get(0));

			PropertyInstance<Object> endDate = ci.getProperties().stream()
					.filter(p -> "End Date".equals(p.getName())).findFirst().orElse(null);
			dto.setDateTo((String) endDate.getValues().get(0));

			PropertyInstance<Object> duration = ci.getProperties().stream()
					.filter(p -> "duration".equals(p.getName())).findFirst().orElse(null);
			dto.setDuration((String) duration.getValues().get(0));

			PropertyInstance<Object> location = ci.getProperties().stream().filter(p -> "Location".equals(p.getName()))
					.findFirst().orElse(null);
			dto.setLocation((String) location.getValues().get(0));

			PropertyInstance<Object> description = ci.getProperties().stream()
					.filter(p -> "Description".equals(p.getName())).findFirst().orElse(null);
			dto.setDescription((String) description.getValues().get(0));

			PropertyInstance<Object> rank = ci.getProperties().stream().filter(p -> "rank".equals(p.getName()))
					.findFirst().orElse(null);
			dto.setRank((String) rank.getValues().get(0));

			PropertyInstance<Object> taskType1 = ci.getProperties().stream()
					.filter(p -> "taskType1".equals(p.getName())).findFirst().orElse(null);
			dto.setTaskType1((String) taskType1.getValues().get(0));

			PropertyInstance<Object> taskType2 = ci.getProperties().stream()
					.filter(p -> "taskType2".equals(p.getName())).findFirst().orElse(null);
			dto.setTaskType2((String) taskType2.getValues().get(0));

			PropertyInstance<Object> taskType3 = ci.getProperties().stream()
					.filter(p -> "taskType3".equals(p.getName())).findFirst().orElse(null);
			dto.setTaskType3((String) taskType3.getValues().get(0));

			return dto;
		}).collect(Collectors.toList());

		return classInstanceDTOs;
	}

}
