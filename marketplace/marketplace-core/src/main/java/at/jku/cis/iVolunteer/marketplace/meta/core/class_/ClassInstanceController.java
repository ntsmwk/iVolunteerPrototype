package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.clazz.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace._mapper.clazz.ClassInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.commons.DateTimeService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstanceDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@RestController
public class ClassInstanceController {

	@Autowired
	private ClassInstanceRepository classInstanceRepository;
	@Autowired
	private ClassDefinitionService classDefinitionService;
	@Autowired
	private ClassInstanceMapper classInstanceMapper;
	@Autowired
	private ClassDefinitionToInstanceMapper classDefinitionToInstanceMapper;
	@Autowired
	private DateTimeService dateTimeService;
	@Autowired
	private MarketplaceService marketplaceService;

	@PostMapping("/meta/core/class/instance/all/by-archetype/{archetype}/user/{userId}")
	private List<ClassInstanceDTO> getClassInstancesByArchetype(@PathVariable("archetype") ClassArchetype archeType,
			@PathVariable("userId") String userId, @RequestBody List<String> tenantIds) {
		List<ClassInstance> classInstances = new ArrayList<>();

		tenantIds = tenantIds.stream().distinct().collect(Collectors.toList());

		tenantIds.forEach(tenantId -> {
			classInstances.addAll(
					classInstanceRepository.getByUserIdAndClassArchetypeAndTenantId(userId, archeType, tenantId));
		});

		return classInstanceMapper.mapToDTO(classInstances);
	}

	// @PreAuthorize("hasAnyRole('VOLUNTEER')")
	@GetMapping("/meta/core/class/instance/all")
	private List<ClassInstanceDTO> getAllClassInstances(@RequestParam(value = "tId", required = true) String tenantId) {
		return classInstanceMapper.mapToDTO(classInstanceRepository.findByTenantId(tenantId));
	}

	@GetMapping("/meta/core/class/instance/{id}")
	private ClassInstance getClassInstanceById(@PathVariable("id") String id) {
		return classInstanceRepository.findOne(id);
	}

	@PostMapping("/meta/core/class/instances")
	private List<ClassInstance> getClassInstanceById(@RequestBody List<String> classInstantIds) {
		List<ClassInstance> classInstances = new ArrayList<>();

		classInstantIds.forEach(id -> {
			classInstances.add(classInstanceRepository.findOne(id));
		});
		return classInstances;
	}

	@PostMapping("/meta/core/class/instanceDTOs")
	private List<ClassInstanceDTO> mapClassInstanceToDTO(@RequestBody List<ClassInstance> classInstances) {
		return classInstanceMapper.mapToDTO(classInstances);
	}

	@GetMapping("/meta/core/class/instance/all/by-archetype/{archetype}")
	private List<ClassInstance> getClassInstancesByArchetype(@PathVariable("archetype") ClassArchetype classArchetype,
			@RequestParam(value = "tId", required = true) String tenantId) {
		List<ClassInstance> classInstances = new ArrayList<>();
		classInstanceRepository.getByClassArchetypeAndTenantId(classArchetype, tenantId);
		return classInstances;
	}

	@GetMapping("/meta/core/class/instance/all/tenant/{tenantId}/archetype/{archetype}/user/{userId}")
	private List<ClassInstance> getClassInstanceByTenantIdAndArchetype(@PathVariable("tenantId") String tenantId,
			@PathVariable("archetype") ClassArchetype classArchetype, @PathVariable("userId") String userId) {
		return classInstanceRepository.getByClassArchetypeAndTenantIdAndUserId(classArchetype, tenantId, userId);
	}

	@PostMapping("/meta/core/class/instance/from-definition/{classDefinitionId}/user/{volunteerId}")
	public ClassInstance createClassInstanceByClassDefinitionId(@PathVariable String classDefinitionId,
			@RequestParam(value = "tId", required = true) String tenantId, @PathVariable String volunteerId,
			@RequestBody Map<String, String> properties) {

		ClassDefinition classDefinition = this.classDefinitionService.getClassDefinitionById(classDefinitionId);

		if (classDefinition != null) {

			ClassInstance classInstance = this.classDefinitionToInstanceMapper.toTarget(classDefinition);

			classInstance.setUserId(volunteerId);
			classInstance.setTenantId(tenantId);
			classInstance.setIssuerId(tenantId);
			classInstance.setMarketplaceId(marketplaceService.getMarketplaceId());
			classInstance.setTimestamp(new Date());
			// classInstance.setIssued(false);

			classInstance.getProperties().forEach(p -> {
				if (properties.containsKey(p.getName())) {
					if (p.getType() == PropertyType.DATE) {
						String dateAsString = properties.get(p.getName());
						Date date = dateTimeService.parseMultipleDateFormats(dateAsString);

						if (date != null) {
							p.setValues(Collections.singletonList(date.getTime()));

						}
					} else {
						p.setValues(Collections.singletonList(properties.get(p.getName())));
					}
				}
			});
			return this.classInstanceRepository.save(classInstance);
		}

		return null;
	}

	@PostMapping("/meta/core/class/instance/new")
	public List<ClassInstance> createNewClassInstances(@RequestBody List<ClassInstance> classInstances) {
		return classInstanceRepository.save(classInstances);
	}

	@PostMapping("/meta/core/class/instance/newShared")
	public ClassInstanceDTO createNewSharedClassInstances(@RequestParam(value = "tId", required = true) String tenantId,
			@RequestBody String classInstanceId) {
		ClassInstance ci = classInstanceRepository.findOne(classInstanceId);

		ClassInstance ciNew = new ClassInstance();
		ciNew.setClassArchetype(ci.getClassArchetype());
		ciNew.setName(ci.getName());
		ciNew.setProperties(ci.getProperties());
		ciNew.setUserId(ci.getUserId());
		ciNew.setIssuerId(ci.getIssuerId());
		ciNew.setImagePath(ci.getImagePath());
		ciNew.setClassArchetype(ci.getClassArchetype());
		ciNew.setChildClassInstances(ci.getChildClassInstances());
		ciNew.setVisible(ci.isVisible());
		ciNew.setTabId(ci.getTabId());
		ciNew.setClassDefinitionId(ci.getClassDefinitionId());
		ciNew.setTimestamp(ci.getTimestamp());
		ciNew.setMarketplaceId(ci.getMarketplaceId());
		ciNew.setTenantId(tenantId);

		return classInstanceMapper.mapToDTO(Collections.singletonList(this.classInstanceRepository.save(ciNew)))
				.stream().findFirst().orElse(null);
	}

	@DeleteMapping("/meta/core/class/instance/{id}/delete")
	private void deleteClassInstance(@PathVariable("id") String id) {
		this.classInstanceRepository.delete(id);

	}

}
