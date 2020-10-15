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

import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyParentItemMapper;
import at.jku.cis.iVolunteer.mapper.task.template.UserDefinedTaskTemplateMapper;
import at.jku.cis.iVolunteer.mapper.task.template.UserDefinedTaskTemplateStubMapper;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.model.property.MultipleProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.dto.PropertyParentItemDTO;
import at.jku.cis.iVolunteer.model.task.template.MultiUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.SingleUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.UserDefinedTaskTemplateDTO;


@RestController
public class UserDefinedTaskTemplateController {

	@Autowired
	private UserDefinedTaskTemplateMapper userDefinedTaskTemplateMapper;
	@Autowired
	private UserDefinedTaskTemplateStubMapper userDefinedTaskTemplateStubMapper;
	@Autowired
	private PropertyMapper propertyMapper;
	
	@Autowired
	private UserDefinedTaskTemplateRepository userDefinedTaskTemplateRepository;
	@Autowired
	private PropertyParentItemMapper propertyParentItemMapper;
	@Autowired
	private PropertyRepository propertyRepository;
		

	@GetMapping("/tasktemplate/user")
	public List<?> findAll(@RequestParam(value = "stub", required = true) boolean stub) {
		List<UserDefinedTaskTemplate> t = userDefinedTaskTemplateRepository.findAll();	
		if (stub) {
			return userDefinedTaskTemplateStubMapper.toDTOs(t);
		} else {
			return userDefinedTaskTemplateMapper.toDTOs(t);
		}	
	}

	@GetMapping("/tasktemplate/user/{templateId}")
	public UserDefinedTaskTemplateDTO findTemplateById(@PathVariable("templateId") String templateId) {		
		return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.findOne(templateId));
	}
	
	@GetMapping("/tasktemplate/user/{templateId}/{subtemplateId}")
	public UserDefinedTaskTemplateDTO findSubTemplateById(@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId) {	
		return userDefinedTaskTemplateMapper.toDTO(((MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId)).getTemplates()
				.stream().filter(t -> t.getId().equals(subtemplateId)).findFirst().get());
	}
	
	@GetMapping("/tasktemplate/user/names")
	public List<String> getAllTemplateNames() {
		List<String> templateNames = userDefinedTaskTemplateRepository.findAll().parallelStream().map(UserDefinedTaskTemplate::getName).collect(Collectors.toList());
		return templateNames;
	}
	
	
	@GetMapping("/properties/{propId}/parents")
	public List<PropertyParentItemDTO> getPropertyParents(@PathVariable("propId") String propId, 
			@RequestParam(value = "templateId", required = true) String templateId, @RequestParam(value = "subtemplateId", required = false) String subtemplateId) {
				
		if (subtemplateId != null) {
			MultiUserDefinedTaskTemplate root = (MultiUserDefinedTaskTemplate) this.userDefinedTaskTemplateRepository.findOne(templateId);
			SingleUserDefinedTaskTemplate nested = root.getTemplates().stream().filter(t -> t.getId().equals(subtemplateId)).findFirst().get();
			
			List<UserDefinedTaskTemplate> items = new ArrayList<>();
			items.add(root);
			items.add(nested);
			
			return propertyParentItemMapper.toDTOs(items);
			
		} else {
			throw new NotAcceptableException("No subtemplate id specified");
		}	
	}

	@PostMapping("/tasktemplate/user/new")
	public UserDefinedTaskTemplateDTO createRootTemplate(@RequestBody String[] params, @RequestParam(value = "type", required = true) String type) {		
		
		if (type.equals("single")) {
			SingleUserDefinedTaskTemplate taskTemplate = (SingleUserDefinedTaskTemplate) createTemplate(params[0], params[1], "single");
			taskTemplate.setProperties(new ArrayList<Property>());
			
			return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(taskTemplate));
		
		} else if (type.equals("multi") ) {	
			MultiUserDefinedTaskTemplate taskTemplate = (MultiUserDefinedTaskTemplate) createTemplate(params[0], params[1], "multi");
			taskTemplate.setTemplates(new ArrayList<SingleUserDefinedTaskTemplate>());
			
			return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(taskTemplate));
		
		} else {
			throw new NotAcceptableException("type has to be either 'single' or 'multi'");
		}
	}
	
	@PostMapping("/tasktemplate/user/{templateId}/new")
	public UserDefinedTaskTemplateDTO createSubTemplate(@PathVariable("templateId") String templateId, @RequestBody String[] params) {
		
		SingleUserDefinedTaskTemplate newTaskTemplate = (SingleUserDefinedTaskTemplate) createTemplate(params[0], params[1], "single");
		newTaskTemplate.setId(ObjectId.get().toHexString());
		newTaskTemplate.setProperties(new ArrayList<Property>());
		
		MultiUserDefinedTaskTemplate t = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		
		List<SingleUserDefinedTaskTemplate> subTemplates;
		if (t.getTemplates() == null) {
			subTemplates = new ArrayList<>();
		} else {
			subTemplates = t.getTemplates();
		}
		
		subTemplates.add(newTaskTemplate);
		
		t.setTemplates(subTemplates);
				
		return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(t));
	}
	
	@PostMapping("/tasktemplate/user/{templateId}/new-copy")
	public UserDefinedTaskTemplateDTO createTemplateFromExisting(@PathVariable("templateId") String templateId, @RequestBody String[] params) {

		UserDefinedTaskTemplate existingTemplate = userDefinedTaskTemplateRepository.findOne(templateId);
		
		if (existingTemplate == null) {throw new NotAcceptableException("no template with id " + templateId + " in database - should not happen");}

		existingTemplate.setId(null);
		existingTemplate.setName(params[0]);
		existingTemplate.setDescription(params[1]);
		
		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(existingTemplate);
		return userDefinedTaskTemplateMapper.toDTO(ret);
	
	}
	
	private UserDefinedTaskTemplate createTemplate(String name, String description, String type) {
		UserDefinedTaskTemplate taskTemplate;
		
		if (type.equals("single")) {
			taskTemplate = new SingleUserDefinedTaskTemplate();
		} else if (type.equals("multi")){
			taskTemplate = new MultiUserDefinedTaskTemplate();
		} else {
			throw new NotAcceptableException("type has to be either 'single' or 'multi'");
		}
		
		taskTemplate.setName(name);
		taskTemplate.setDescription(description);
		return taskTemplate;
	}
	
	@PutMapping("/tasktemplate/user/{templateId}/update")
	public UserDefinedTaskTemplateDTO updateRootTemplate(@PathVariable("templateId") String templateId, @RequestBody String[] params) {
		UserDefinedTaskTemplate taskTemplate = userDefinedTaskTemplateRepository.findOne(templateId);
				
		if (params[0] != null) {
			taskTemplate.setName(params[0]);
		}
		
		if (params[1] != null) {
			taskTemplate.setDescription(params[1]);
		}

		return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(taskTemplate));
	}
	
	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/update")
	public UserDefinedTaskTemplateDTO updateNestedTemplate (@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId, @RequestBody String[] params) {
		
		MultiUserDefinedTaskTemplate rootTemplate = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		
		int index = rootTemplate.getTemplates().indexOf(new SingleUserDefinedTaskTemplate(subtemplateId));
		
		if (index < 0) {
			throw new NotAcceptableException("no subtemplate with id \" + subtemplateId + \" in database - should not happen");
		}
		
		if (params[0] != null) {
			rootTemplate.getTemplates().get(index).setName(params[0]);
		}		
		if (params[1] != null) {
			rootTemplate.getTemplates().get(index).setDescription(params[1]);
		}
	
		return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(rootTemplate));	
	}
	
	@DeleteMapping("/tasktemplate/user/{templateId}")
	public boolean deleteTemplate(@PathVariable("templateId") String templateId) {
						
		userDefinedTaskTemplateRepository.delete(templateId);
		boolean ret = !userDefinedTaskTemplateRepository.exists(templateId);			
		return ret;
		
	}
	
	@DeleteMapping("/tasktemplate/user/{templateId}/{subtemplateId}")
	public boolean deleteTemplate(@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId) {

		MultiUserDefinedTaskTemplate root = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		List<SingleUserDefinedTaskTemplate> remainingSubTemplates = root.getTemplates().stream()
				.filter((t -> !t.getId().equals(subtemplateId))).collect(Collectors.toList());
			
		root.setTemplates(remainingSubTemplates);
		
		boolean  ret = !remainingSubTemplates.contains(new SingleUserDefinedTaskTemplate(subtemplateId));
		userDefinedTaskTemplateRepository.save(root);
		return ret;
	}
	
	
	///////////////////////////////////////////////////////
	//////////Property-Manipulation
	///////////////////////////////////////////////////////
	
	
	@GetMapping("/tasktemplate/user/{templateId}/{subtemplateId}/{propId}")
	public PropertyDTO<Object> getPropertyFromSubtemplate(@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId, 
			@PathVariable("propId") String propId) {
		
		Property ret = ((MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId))
		.getTemplates().stream()
			.filter(t -> t.getId().equals(subtemplateId)).findFirst().get()
			.getProperties().stream()
			.filter(p -> p.getId().equals(propId)).findFirst().get();

		return propertyMapper.toDTO(ret);
	}
	
	
	//Add Properties to Single Template
	@PutMapping("/tasktemplate/user/{templateId}/addproperties")
	public UserDefinedTaskTemplateDTO addProperties(@PathVariable("templateId") String templateId,   @RequestBody String[] propIds) {

		SingleUserDefinedTaskTemplate t = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);	
		
		for (String propId : propIds) {
			Property p = propertyRepository.findOne(propId);
			if (!t.getProperties().contains(p)) {
				t.getProperties().add(p);
			}
		}	
		
		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(t);
		return userDefinedTaskTemplateMapper.toDTO(ret);

	}
	
	//Add Properties to SubTemplate
	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/addproperties")
	public UserDefinedTaskTemplateDTO addProperties(@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId, @RequestBody String[] propIds) {

		MultiUserDefinedTaskTemplate rootTemplate = ((MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId));
		
		if (rootTemplate == null) { throw new NotAcceptableException("no template with id " + templateId + " in database - should not happen");}
		
		int[] indexArr = {0}; //we want the index of the found subtemplate to get quick access to in order to replace it
		SingleUserDefinedTaskTemplate subTemplate;	
		
		try {	
			subTemplate = rootTemplate.getTemplates()
					.stream().filter(sub -> {
						
						boolean b = sub.getId().equals(subtemplateId);
						if (!b) {
							int i = indexArr[0];
							i++;
							indexArr[0] = i;
						}	
						return b;	
					}).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new NotAcceptableException("no subtemplate with id " + subtemplateId + " in database - should not happen");
		}
						
		for (String propId : propIds) {
			Property p = propertyRepository.findOne(propId);
			
			if (p != null && !subTemplate.getProperties().contains(p)) {
				subTemplate.getProperties().add(p);
			}
		}
		rootTemplate.getTemplates().set(indexArr[0], subTemplate);
			
		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(rootTemplate);
		return userDefinedTaskTemplateMapper.toDTO(ret);
	}
	
	
	//Update Properties from Single Template
	@PutMapping("/tasktemplate/user/{templateId}/updateproperties")
	public UserDefinedTaskTemplateDTO updateProperties(@PathVariable("templateId") String templateId, @RequestBody PropertyDTO<Object>[] properties) {

		SingleUserDefinedTaskTemplate t = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		
		List<PropertyDTO<Object>> propertyList = Arrays.asList(properties);
		
		//Recursively set properties		
		List<Property> returnProperties = setPropertiesRec(propertyList, t.getProperties());	
		t.setProperties(returnProperties);

		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(t);
		
		return userDefinedTaskTemplateMapper.toDTO(ret);
	}
	
	//Update Properties from Subtemplate
	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/updateproperties")
	public UserDefinedTaskTemplateDTO updateProperties(@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId, @RequestBody PropertyDTO<Object>[] properties) {
		
		MultiUserDefinedTaskTemplate rootTemplate = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		
		if (rootTemplate == null) { throw new NotAcceptableException("no template with id " + templateId + " in database - should not happen");}
		
		int[] indexArr = {0}; //we want the index of the found subtemplate to get quick access to in order to replace it
		SingleUserDefinedTaskTemplate subTemplate;	
		
		try {	
			subTemplate = rootTemplate.getTemplates()
					.stream().filter(sub -> {
						
						boolean b = sub.getId().equals(subtemplateId);
						if (!b) {
							int i = indexArr[0];
							i++;
							indexArr[0] = i;
						}	
						return b;	
					}).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new NotAcceptableException("no subtemplate with id " + subtemplateId + " in database - should not happen");
		}
		
		// replace entry in arraylist
		ArrayList<PropertyDTO<Object>> propertyList = new ArrayList<>(Arrays.asList(properties));		
		List<Property> returnProperties= setPropertiesRec(propertyList, subTemplate.getProperties());
		
		rootTemplate.getTemplates().get(indexArr[0]).setProperties(returnProperties);

		UserDefinedTaskTemplate ret = this.userDefinedTaskTemplateRepository.save(rootTemplate);
		return userDefinedTaskTemplateMapper.toDTO(ret);
	}
	
	private List<Property> setPropertiesRec(List<PropertyDTO<Object>> updateProperties, List<Property> currentProperties) {
		List<Property> returnProperties = new ArrayList<>();
		
		for(PropertyDTO<Object> dto : updateProperties) {
			Property p = propertyMapper.toEntity(dto);

			if (!p.getKind().equals(PropertyKind.MULTIPLE) && currentProperties.stream().anyMatch(cur -> p.getId().equals(cur.getId()))) {

				SingleProperty<Object> updateProperty = (SingleProperty<Object>) p;
				SingleProperty<Object> currentProperty = (SingleProperty<Object>) currentProperties.stream().filter(cur -> p.getId().equals(cur.getId())).findFirst().get();
				
				currentProperty.setValues(updateProperty.getValues());
				currentProperty.setDefaultValues(updateProperty.getDefaultValues());				
				returnProperties.add(currentProperty);
				
			} else if (p.getKind().equals(PropertyKind.MULTIPLE)) {
				
				MultipleProperty currentProperty = (MultipleProperty) currentProperties.stream().filter(cur -> p.getId().equals(cur.getId())).findFirst().get();
				
				List<Property> nestedList= setPropertiesRec(dto.getProperties(), currentProperty.getProperties() );
				
				currentProperty.setProperties(nestedList);
				returnProperties.add(currentProperty);
			}
		}
		
		return returnProperties;
	}
	
	@PutMapping("/tasktemplate/user/{templateId}/deleteproperties")
	public UserDefinedTaskTemplateDTO deleteProperties(@PathVariable("templateId") String templateId, @RequestBody List<String> propIds ) {
		
		SingleUserDefinedTaskTemplate t = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);		
		ArrayList<Property> remainingProperties = t.getProperties().stream()
				.filter(prop -> propIds.stream().noneMatch(id -> prop.getId().equals(id))).collect(Collectors.toCollection(ArrayList::new));		
		
		t.setProperties(remainingProperties);

		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(t);
		
		return userDefinedTaskTemplateMapper.toDTO(ret);
	}
	
	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/deleteproperties")
	public UserDefinedTaskTemplateDTO deleteProperties(@PathVariable("templateId") String templateId, @PathVariable(value = "subtemplateId") String subtemplateId, 
			@RequestBody String[] propIds ) {
				
		MultiUserDefinedTaskTemplate rootTemplate = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		
		int[] indexArr = {0}; //we want the index of the found subtemplate to get quick access to in order to replace it
		SingleUserDefinedTaskTemplate subTemplate;	
		
		try {	
			subTemplate = rootTemplate.getTemplates()
					.stream().filter(sub -> {
						
						boolean b = sub.getId().equals(subtemplateId);
						if (!b) {
							int i = indexArr[0];
							i++;
							indexArr[0] = i;
						}	
						return b;	
					}).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new NotAcceptableException("no subtemplate with id " + subtemplateId + " in database - should not happen");
		}
		
		ArrayList<Property> remainingProperties = subTemplate.getProperties().stream()
				.filter(prop -> Arrays.stream(propIds).noneMatch(id -> prop.getId().equals(id))).collect(Collectors.toCollection(ArrayList::new));

		rootTemplate.getTemplates().get(indexArr[0]).setProperties(remainingProperties);
		
		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(rootTemplate);
		return userDefinedTaskTemplateMapper.toDTO(ret);	
	}
}
