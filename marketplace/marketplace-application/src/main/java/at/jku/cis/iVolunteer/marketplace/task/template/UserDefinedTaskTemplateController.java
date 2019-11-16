package at.jku.cis.iVolunteer.marketplace.task.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.ws.rs.NotAcceptableException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyItemMapper;
import at.jku.cis.iVolunteer.mapper.task.template.UserDefinedTaskTemplateStubMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyItem;
import at.jku.cis.iVolunteer.model.task.template.MultiUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.SingleUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;

@RestController
public class UserDefinedTaskTemplateController {

	@Autowired private UserDefinedTaskTemplateStubMapper userDefinedTaskTemplateStubMapper;

	@Autowired private UserDefinedTaskTemplateRepository userDefinedTaskTemplateRepository;
	@Autowired private PropertyItemMapper propertyItemMapper;

	@Autowired PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired PropertyDefinitionToClassPropertyMapper propertyDefintionToClassPropertyMapper;

	List<UserDefinedTaskTemplate> templates;

	@GetMapping("/tasktemplate/user")
	public List<?> findAll(@RequestParam(value = "stub", required = true) boolean stub) {
		List<UserDefinedTaskTemplate> t = userDefinedTaskTemplateRepository.findAll();
		if (stub) {
			return userDefinedTaskTemplateStubMapper.toTargets(t);
		} else {
			return t;
		}
	}

	@GetMapping("/tasktemplate/user/{templateId}")
	public UserDefinedTaskTemplate findTemplateById(@PathVariable("templateId") String templateId) {
		UserDefinedTaskTemplate userDefinedTaskTemplate = userDefinedTaskTemplateRepository.findOne(templateId);

		return userDefinedTaskTemplate;
	}

	@GetMapping("/tasktemplate/user/{templateId}/{subtemplateId}")
	public UserDefinedTaskTemplate findSubTemplateById(@PathVariable("templateId") String templateId,
			@PathVariable("subtemplateId") String subtemplateId) {
		return ((MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId)).getTemplates()
				.stream().filter(t -> t.getId().equals(subtemplateId)).findFirst().get();
	}

	@GetMapping("/tasktemplate/user/names")
	public List<String> getAllTemplateNames() {
		List<String> templateNames = userDefinedTaskTemplateRepository.findAll().parallelStream()
				.map(UserDefinedTaskTemplate::getName).collect(Collectors.toList());
		return templateNames;

	}

	// TODO @mwe change to work with class definitions / class instances!!
	@GetMapping("/properties/{propId}/parents")
	public List<PropertyItem> getPropertyParents(@PathVariable("propId") String propId,
			@RequestParam(value = "templateId", required = true) String templateId,
			@RequestParam(value = "subtemplateId", required = false) String subtemplateId) {

		if (subtemplateId != null) {
			MultiUserDefinedTaskTemplate root = (MultiUserDefinedTaskTemplate) this.userDefinedTaskTemplateRepository
					.findOne(templateId);
			SingleUserDefinedTaskTemplate nested = root.getTemplates().stream()
					.filter(t -> t.getId().equals(subtemplateId)).findFirst().get();

			List<UserDefinedTaskTemplate> items = new ArrayList<>();
			items.add(root);
			items.add(nested);

			return propertyItemMapper.toTargets(items);

		}

		return null;
	}

	@PostMapping("/tasktemplate/user/new")
	public UserDefinedTaskTemplate createRootTemplate(@RequestBody String[] params,
			@RequestParam(value = "type", required = true) String type) {
		if (type.equals("single")) {

			SingleUserDefinedTaskTemplate taskTemplate = (SingleUserDefinedTaskTemplate) createTemplate(params[0],
					params[1], "single");

			taskTemplate.setTemplateProperties(new ArrayList<ClassProperty<Object>>());

			return userDefinedTaskTemplateRepository.save(taskTemplate);

		} else if (type.equals("multi")) {

			MultiUserDefinedTaskTemplate taskTemplate = (MultiUserDefinedTaskTemplate) createTemplate(params[0],
					params[1], "multi");
			taskTemplate.setTemplates(new ArrayList<SingleUserDefinedTaskTemplate>());

			return userDefinedTaskTemplateRepository.save(taskTemplate);

		} else {
			System.out.println("should not happen");
			return null;
		}
	}

	@PostMapping("/tasktemplate/user/{templateId}/new")
	public UserDefinedTaskTemplate createSubTemplate(@PathVariable("templateId") String templateId,
			@RequestBody String[] params) {
		SingleUserDefinedTaskTemplate newTaskTemplate = (SingleUserDefinedTaskTemplate) createTemplate(params[0],
				params[1], "single");
		newTaskTemplate.setId(ObjectId.get().toHexString());
		newTaskTemplate.setTemplateProperties(new ArrayList<ClassProperty<Object>>());

		MultiUserDefinedTaskTemplate t = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		List<SingleUserDefinedTaskTemplate> subTemplates;
		if (t.getTemplates() == null) {
			subTemplates = new ArrayList<>();
		} else {
			subTemplates = t.getTemplates();
		}

		subTemplates.add(newTaskTemplate);

		t.setTemplates(subTemplates);

		System.out.println(
				newTaskTemplate.getId() + " " + newTaskTemplate.getName() + " " + newTaskTemplate.getDescription());

		return userDefinedTaskTemplateRepository.save(t);
	}

	@PostMapping("/tasktemplate/user/{templateId}/new-copy")
	public UserDefinedTaskTemplate createTemplateFromExisting(@PathVariable("templateId") String templateId,
			@RequestBody String[] params) {
		System.out.println("Create template from Copy called");
		UserDefinedTaskTemplate existingTemplate = userDefinedTaskTemplateRepository.findOne(templateId);

		if (existingTemplate == null) {
			throw new NotAcceptableException("no template with id " + templateId + " in database - should not happen");
		}

		existingTemplate.setId(null);
		existingTemplate.setName(params[0]);
		existingTemplate.setDescription(params[1]);

		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(existingTemplate);
		return ret;

	}

	private UserDefinedTaskTemplate createTemplate(String name, String description, String type) {
		UserDefinedTaskTemplate taskTemplate;

		if (type.equals("single")) {
			taskTemplate = new SingleUserDefinedTaskTemplate();
		} else if (type.equals("multi")) {
			taskTemplate = new MultiUserDefinedTaskTemplate();
		} else {
			return null;
		}

		taskTemplate.setName(name);
		taskTemplate.setDescription(description);
		return taskTemplate;
	}

	@PutMapping("/tasktemplate/user/{templateId}/update")
	public UserDefinedTaskTemplate updateRootTemplate(@PathVariable("templateId") String templateId,
			@RequestBody String[] params) {
		UserDefinedTaskTemplate taskTemplate = userDefinedTaskTemplateRepository.findOne(templateId);

		if (params[0] != null) {
			taskTemplate.setName(params[0]);
		}
		if (params[1] != null) {
			taskTemplate.setDescription(params[1]);
		}

		return userDefinedTaskTemplateRepository.save(taskTemplate);
	}

	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/update")
	public UserDefinedTaskTemplate updateNestedTemplate(@PathVariable("templateId") String templateId,
			@PathVariable("subtemplateId") String subtemplateId, @RequestBody String[] params) {

		MultiUserDefinedTaskTemplate rootTemplate = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		int index = rootTemplate.getTemplates().indexOf(new SingleUserDefinedTaskTemplate(subtemplateId));

		if (index < 0) {
			throw new NotAcceptableException(
					"no subtemplate with id \" + subtemplateId + \" in database - should not happen");
		}

		if (params[0] != null) {
			rootTemplate.getTemplates().get(index).setName(params[0]);
		}

		if (params[1] != null) {
			rootTemplate.getTemplates().get(index).setDescription(params[1]);
		}

		return userDefinedTaskTemplateRepository.save(rootTemplate);
	}

	@DeleteMapping("/tasktemplate/user/{templateId}")
	public boolean deleteTemplate(@PathVariable("templateId") String templateId) {
		userDefinedTaskTemplateRepository.delete(templateId);

		boolean ret = !userDefinedTaskTemplateRepository.exists(templateId);

		return ret;

	}

	@DeleteMapping("/tasktemplate/user/{templateId}/{subtemplateId}")
	public boolean deleteTemplate(@PathVariable("templateId") String templateId,
			@PathVariable("subtemplateId") String subtemplateId) {
		MultiUserDefinedTaskTemplate root = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		List<SingleUserDefinedTaskTemplate> remainingSubTemplates = root.getTemplates().stream()
				.filter((t -> !t.getId().equals(subtemplateId))).collect(Collectors.toList());

		root.setTemplates(remainingSubTemplates);

		boolean ret = !remainingSubTemplates.contains(new SingleUserDefinedTaskTemplate(subtemplateId));
		userDefinedTaskTemplateRepository.save(root);

		return ret;
	}

	@PutMapping("/tasktemplate/user/{templateId}/updatetemplateorder")
	public UserDefinedTaskTemplate updateTemplateOrderNested(@PathVariable("templateId") String templateId,
			@RequestBody List<SingleUserDefinedTaskTemplate> templates) {

		MultiUserDefinedTaskTemplate root = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);
		root.setTemplates(templates);
		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(root);

		return ret;
	}

	///////////////////////////////////////////////////////
	////////// Property-Manipulation
	///////////////////////////////////////////////////////

	@GetMapping("/tasktemplate/user/{templateId}/{subtemplateId}/{propId}")
	public ClassProperty<Object> getPropertyFromSubtemplate(@PathVariable("templateId") String templateId,
			@PathVariable("subtemplateId") String subtemplateId, @PathVariable("propId") String propId) {

		return ((MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId)).getTemplates()
				.stream().filter(t -> t.getId().equals(subtemplateId)).findFirst().get().getTemplateProperties()
				.stream().filter(p -> p.getId().equals(propId)).findFirst().get();

	}

	// Add Properties to Single Template
	@PutMapping("/tasktemplate/user/{templateId}/addproperties")
	public UserDefinedTaskTemplate addProperties(@PathVariable("templateId") String templateId,
			@RequestBody String[] propIds) {

		SingleUserDefinedTaskTemplate t = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		for (String propId : propIds) {

			if (!t.getTemplateProperties().stream().anyMatch(i -> i.getId().equals(propId))) {
				PropertyDefinition<Object> p = propertyDefinitionRepository.findOne(propId);
				t.getTemplateProperties().add(propertyDefintionToClassPropertyMapper.toTarget(p));
				System.out.println("added " + p.getId());
			}
		}

		return userDefinedTaskTemplateRepository.save(t);

	}

	// Add Properties to SubTemplate
	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/addproperties")
	public UserDefinedTaskTemplate addProperties(@PathVariable("templateId") String templateId,
			@PathVariable("subtemplateId") String subtemplateId, @RequestBody String[] propIds) {
		MultiUserDefinedTaskTemplate rootTemplate = ((MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId));

		if (rootTemplate == null) {
			throw new NotAcceptableException("no template with id " + templateId + " in database - should not happen");
		}

		int[] indexArr = { 0 }; // we want the index of the found subtemplate to get quick access to in order to
								// replace it
		SingleUserDefinedTaskTemplate subTemplate;

		try {
			subTemplate = rootTemplate.getTemplates().stream().filter(sub -> {

				boolean b = sub.getId().equals(subtemplateId);
				if (!b) {
					int i = indexArr[0];
					i++;
					indexArr[0] = i;
				}
				return b;
			}).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new NotAcceptableException(
					"no subtemplate with id " + subtemplateId + " in database - should not happen");
		}

		for (String propId : propIds) {
			PropertyDefinition<Object> p = propertyDefinitionRepository.findOne(propId);

			if (p != null && !subTemplate.getTemplateProperties().stream().anyMatch(i -> i.getId().equals(propId))) {
				subTemplate.getTemplateProperties().add(propertyDefintionToClassPropertyMapper.toTarget(p));
			}
		}
		rootTemplate.getTemplates().set(indexArr[0], subTemplate);

		return userDefinedTaskTemplateRepository.save(rootTemplate);
	}

	// Update Properties from Single Template
	@PutMapping("/tasktemplate/user/{templateId}/updateproperties")
	public UserDefinedTaskTemplate updateProperties(@PathVariable("templateId") String templateId,
			@RequestBody ClassProperty<Object>[] classProperties) {
		System.out.println(classProperties[0].getDefaultValues().size());

		if (classProperties[0].getType().equals(PropertyType.TEXT)) {
			System.out.println("values: " + classProperties[0].getDefaultValues().get(0));
		}

		SingleUserDefinedTaskTemplate t = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		List<ClassProperty<Object>> propertyList = Arrays.asList(classProperties);

		// Recursively set properties
		List<ClassProperty<Object>> returnProperties = setPropertiesRec(propertyList, t.getTemplateProperties());

		t.setTemplateProperties(returnProperties);

		return userDefinedTaskTemplateRepository.save(t);
	}

	// Update Properties from Subtemplate
	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/updateproperties")
	public UserDefinedTaskTemplate updateProperties(@PathVariable("templateId") String templateId,
			@PathVariable("subtemplateId") String subtemplateId, @RequestBody ClassProperty<Object>[] classProperties) {

		MultiUserDefinedTaskTemplate rootTemplate = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		if (rootTemplate == null) {
			throw new NotAcceptableException("no template with id " + templateId + " in database - should not happen");
		}

		int[] indexArr = { 0 }; // we want the index of the found subtemplate to get quick access to in order to
								// replace it
		SingleUserDefinedTaskTemplate subTemplate;

		try {
			subTemplate = rootTemplate.getTemplates().stream().filter(sub -> {

				boolean b = sub.getId().equals(subtemplateId);
				if (!b) {
					int i = indexArr[0];
					i++;
					indexArr[0] = i;
				}
				return b;
			}).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new NotAcceptableException(
					"no subtemplate with id " + subtemplateId + " in database - should not happen");
		}

		// replace entry in arraylist
		ArrayList<ClassProperty<Object>> propertyList = new ArrayList<>(Arrays.asList(classProperties));
		List<ClassProperty<Object>> returnProperties = setPropertiesRec(propertyList,
				subTemplate.getTemplateProperties());

		rootTemplate.getTemplates().get(indexArr[0]).setTemplateProperties(returnProperties);

		return this.userDefinedTaskTemplateRepository.save(rootTemplate);
	}

	private List<ClassProperty<Object>> setPropertiesRec(List<ClassProperty<Object>> updateProperties,
			List<ClassProperty<Object>> currentProperties) {
		List<ClassProperty<Object>> returnProperties = new ArrayList<>();

		for (ClassProperty<Object> p : updateProperties) {
			if (!p.getType().equals(PropertyType.MULTI)
					&& currentProperties.stream().anyMatch(cur -> p.getId().equals(cur.getId()))) {

				ClassProperty<Object> updateProperty = p;
				ClassProperty<Object> currentProperty = currentProperties.stream()
						.filter(cur -> p.getId().equals(cur.getId())).findFirst().get();

				currentProperty.setDefaultValues(updateProperty.getDefaultValues());

				returnProperties.add(currentProperty);
			}

//			} else if (p.getType().equals(PropertyType.MULTI)) {
//				System.out.println("\nMULTIPLE--> " + p.getId());
//				
//				MultiProperty currentProperty = (MultiProperty) currentProperties.stream().filter(cur -> p.getId().equals(cur.getId())).findFirst().get();
//				
//				List<Property> nestedList= setPropertiesRec(dto.getProperties(), currentProperty.getProperties() );
//				
//				currentProperty.setProperties(nestedList);
//				
//				returnProperties.add(currentProperty);
//				System.out.println();
//			}

		}

		return returnProperties;
	}

	// Update Properties from Single Template
	@PutMapping("/tasktemplate/user/{templateId}/updatepropertyorder")
	public UserDefinedTaskTemplate updatePropertyOrderSingle(@PathVariable("templateId") String templateId,
			@RequestBody ClassProperty<Object>[] classProperties) {

		SingleUserDefinedTaskTemplate rootTemplate = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		if (rootTemplate == null) {
			throw new NotAcceptableException("no template with id " + templateId + " in database - should not happen");
		}

		List<ClassProperty<Object>> newProperties = Arrays.asList(classProperties);

		// Brute Forcing my Way
		rootTemplate.setTemplateProperties(newProperties);

		return userDefinedTaskTemplateRepository.save(rootTemplate);
	}

	// Update Properties from Nested Template inside a Root Template
	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/updatepropertyorder")
	public UserDefinedTaskTemplate updatePropertyOrderNested(@PathVariable("templateId") String templateId,
			@PathVariable("subtemplateId") String subtemplateId, @RequestBody ClassProperty<Object>[] classProperties) {

		MultiUserDefinedTaskTemplate rootTemplate = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		if (rootTemplate == null) {
			throw new NotAcceptableException("no template with id " + templateId + " in database - should not happen");
		}

		int[] indexArr = { 0 }; // we want the index of the found subtemplate to get quick access to in order to
								// replace it
		SingleUserDefinedTaskTemplate subTemplate;

		try {
			subTemplate = rootTemplate.getTemplates().stream().filter(sub -> {

				boolean b = sub.getId().equals(subtemplateId);
				if (!b) {
					int i = indexArr[0];
					i++;
					indexArr[0] = i;
				}
				return b;
			}).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new NotAcceptableException(
					"no subtemplate with id " + subtemplateId + " in database - should not happen");
		}

		// Brute Forcing my Way
		List<ClassProperty<Object>> newProperties = Arrays.asList(classProperties);

		rootTemplate.getTemplates().get(indexArr[0]).setTemplateProperties(newProperties);

		return userDefinedTaskTemplateRepository.save(rootTemplate);
	}

	@PutMapping("/tasktemplate/user/{templateId}/deleteproperties")
	public UserDefinedTaskTemplate deleteProperties(@PathVariable("templateId") String templateId,
			@RequestBody List<String> propIds) {

		SingleUserDefinedTaskTemplate t = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		ArrayList<ClassProperty<Object>> remainingProperties = t.getTemplateProperties().stream()
				.filter(prop -> propIds.stream().noneMatch(id -> prop.getId().equals(id)))
				.collect(Collectors.toCollection(ArrayList::new));

		t.setTemplateProperties(remainingProperties);

		return userDefinedTaskTemplateRepository.save(t);
	}

	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/deleteproperties")
	public UserDefinedTaskTemplate deleteProperties(@PathVariable("templateId") String templateId,
			@PathVariable(value = "subtemplateId") String subtemplateId, @RequestBody String[] propIds) {

		MultiUserDefinedTaskTemplate rootTemplate = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository
				.findOne(templateId);

		int[] indexArr = { 0 }; // we want the index of the found subtemplate to get quick access to in order to
								// replace it
		SingleUserDefinedTaskTemplate subTemplate;

		try {
			subTemplate = rootTemplate.getTemplates().stream().filter(sub -> {

				boolean b = sub.getId().equals(subtemplateId);
				if (!b) {
					int i = indexArr[0];
					i++;
					indexArr[0] = i;
				}
				return b;
			}).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new NotAcceptableException(
					"no subtemplate with id " + subtemplateId + " in database - should not happen");
		}

		ArrayList<ClassProperty<Object>> remainingProperties = subTemplate.getTemplateProperties().stream()
				.filter(prop -> Arrays.stream(propIds).noneMatch(id -> prop.getId().equals(id)))
				.collect(Collectors.toCollection(ArrayList::new));

		rootTemplate.getTemplates().get(indexArr[0]).setTemplateProperties(remainingProperties);

		return userDefinedTaskTemplateRepository.save(rootTemplate);
	}
}
