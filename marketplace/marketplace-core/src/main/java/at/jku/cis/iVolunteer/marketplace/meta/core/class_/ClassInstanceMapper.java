package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.hash.Hasher;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Service
public class ClassInstanceMapper {

	@Autowired
	private Hasher hasher;

	List<ClassInstanceDTO> mapToDTO(List<ClassInstance> classInstances) {
		List<ClassInstanceDTO> classInstanceDTOs = classInstances.stream().map(ci -> {
			ClassInstanceDTO dto = new ClassInstanceDTO();
			
			dto.setId(ci.getId());
			dto.setIssuerId(ci.getIssuerId());
			dto.setBlockchainDate(ci.getTimestamp());
			dto.setClassArchetype(ci.getClassArchetype());
			dto.setImagePath(ci.getImagePath());
			dto.setPublished(ci.isPublished());
			dto.setInUserRepository(ci.isInUserRepository());
			dto.setInIssuerInbox(ci.isInIssuerInbox());
			dto.setNewFakeData(ci.isNewFakeData());
			dto.setMV(ci.isMV());
			
			dto.setHash(hasher.generateHash(ci));

			PropertyInstance<Object> name = ci.getProperties().stream().filter(p -> "Name".equals(p.getName()))
					.findFirst().orElse(null);
			if (name != null) {
				dto.setName((String) name.getValues().get(0));
			}

			PropertyInstance<Object> purpose = ci.getProperties().stream().filter(p -> "purpose".equals(p.getName()))
					.findFirst().orElse(null);
			if (purpose != null) {
				dto.setPurpose((String) purpose.getValues().get(0));
			}

			PropertyInstance<Object> startingDate = ci.getProperties().stream()
					.filter(p -> "Starting Date".equals(p.getName())).findFirst().orElse(null);
			if (startingDate != null) {
				dto.setDateFrom((Date) startingDate.getValues().get(0));
			}

			PropertyInstance<Object> endDate = ci.getProperties().stream().filter(p -> "End Date".equals(p.getName()))
					.findFirst().orElse(null);
			if (endDate != null) {
				dto.setDateTo((Date) endDate.getValues().get(0));
			}

			PropertyInstance<Object> duration = ci.getProperties().stream().filter(p -> "duration".equals(p.getName()))
					.findFirst().orElse(null);
			if (duration != null) {
				dto.setDuration((String) duration.getValues().get(0));
			}

			PropertyInstance<Object> location = ci.getProperties().stream().filter(p -> "Location".equals(p.getName()))
					.findFirst().orElse(null);
			if (location != null) {
				dto.setLocation((String) location.getValues().get(0));
			}

			PropertyInstance<Object> description = ci.getProperties().stream()
					.filter(p -> "Description".equals(p.getName())).findFirst().orElse(null);
			if (description != null) {
				dto.setDescription((String) description.getValues().get(0));
			}

			PropertyInstance<Object> rank = ci.getProperties().stream().filter(p -> "rank".equals(p.getName()))
					.findFirst().orElse(null);
			if (rank != null) {
				dto.setRank((String) rank.getValues().get(0));
			}

			PropertyInstance<Object> taskType1 = ci.getProperties().stream()
					.filter(p -> "taskType1".equals(p.getName())).findFirst().orElse(null);
			if (taskType1 != null) {
				dto.setTaskType1((String) taskType1.getValues().get(0));
			}

			PropertyInstance<Object> taskType2 = ci.getProperties().stream()
					.filter(p -> "taskType2".equals(p.getName())).findFirst().orElse(null);
			if (taskType2 != null) {
				dto.setTaskType2((String) taskType2.getValues().get(0));
			}

			PropertyInstance<Object> taskType3 = ci.getProperties().stream()
					.filter(p -> "taskType3".equals(p.getName())).findFirst().orElse(null);
			if (taskType3 != null) {
				dto.setTaskType3((String) taskType3.getValues().get(0));
			}
			
			return dto;
		}).collect(Collectors.toList());

		return classInstanceDTOs;
	}

}
