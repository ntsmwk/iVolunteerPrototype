package at.jku.cis.iVolunteer.marketplace.task.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ws.rs.NotAcceptableException;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.StandardProperties;
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
	
	@Autowired private StandardProperties sp;
	
	List<UserDefinedTaskTemplate> templates;
	boolean testValuesSet = false;
	
	
	
	
	private void setupTestValues() {
		templates = new ArrayList<UserDefinedTaskTemplate>();
		SingleUserDefinedTaskTemplate t1 = new SingleUserDefinedTaskTemplate("0");
		SingleUserDefinedTaskTemplate t2 = new SingleUserDefinedTaskTemplate("1");
		MultiUserDefinedTaskTemplate nested = new MultiUserDefinedTaskTemplate("SmallExampleTasks");

		t1.setName("My Template 1");
		List<Property> p1 = propertyRepository.findAll();
		t1.setProperties(p1);
		templates.add(t1);

		t2.setName("My Template 2");
		List<Property> p2 = propertyRepository.findAll();
		t2.setProperties(p2);
		templates.add(t2);
		
		nested.setName("Test nested template");
		nested.setDescription("This is a description");
		List<SingleUserDefinedTaskTemplate> nestedTemplates = new ArrayList<>();
		
		SingleUserDefinedTaskTemplate s1 = new SingleUserDefinedTaskTemplate("CleanFloor");
		s1.setName("Clean Garage Floor");
		s1.setDescription("Clean garage floor at the end of the day");
		List<Property> nsp1 = new ArrayList<>();
		nsp1.add(propertyRepository.findOne("location"));
		nsp1.add(propertyRepository.findOne("postcode"));
		nsp1.add(propertyRepository.findOne("number_of_volunteers"));
		nsp1.add(propertyRepository.findOne("period_type"));
		nsp1.add(propertyRepository.findOne("priority"));
		nsp1.add(propertyRepository.findOne("urgent"));
		nsp1.add(propertyRepository.findOne("workshift"));
		
		s1.setProperties(nsp1);
		nestedTemplates.add(s1);
		
		SingleUserDefinedTaskTemplate s2 = new SingleUserDefinedTaskTemplate("drivecartoservice");
		s2.setName("Drive car to service");
		s2.setDescription("A car is has to be serviced soon, someone has to drive the car described to the service partner");
		List<Property> nsp2 = new ArrayList<>();
		nsp2.add(propertyRepository.findOne("location"));
		nsp2.add(propertyRepository.findOne("postcode"));
		nsp2.add(propertyRepository.findOne("number_of_volunteers"));
		nsp2.add(propertyRepository.findOne("urgent"));
		nsp2.add(propertyRepository.findOne("priority"));
		nsp2.add(propertyRepository.findOne("description"));
		s2.setProperties(nsp2);
		nestedTemplates.add(s2);

		SingleUserDefinedTaskTemplate s3 = new SingleUserDefinedTaskTemplate("PickupPerson");
		s3.setName("Person");
		s3.setDescription("This is a description for the 3rd nested template");
		List<Property> nsp3 = new ArrayList<>();
		nsp3.add(propertyRepository.findOne("5c9d1b251704ee0f8972b313"));
		nsp3.add(propertyRepository.findOne("postcode"));
		nsp3.add(propertyRepository.findOne("location"));
		nsp3.add(propertyRepository.findOne("description"));
		nsp3.add(propertyRepository.findOne("starting_date"));
		nsp3.add(propertyRepository.findOne("latitude"));
		nsp3.add(propertyRepository.findOne("longitude"));
		nsp3.add(propertyRepository.findOne("priority"));
		nsp3.add(propertyRepository.findOne("importancy"));
		
		
		s3.setProperties(nsp3);
		nestedTemplates.add(s3);
		
		SingleUserDefinedTaskTemplate s4 = new SingleUserDefinedTaskTemplate("genericworktask");
		s4.setName("Generic Worktask");
		s4.setDescription("This is a description for the fourth nested template");
		List<Property> nsp4 = new ArrayList<>();
		nsp4.add(propertyRepository.findOne("5c9d1b251704ee0f8972b313"));
		nsp4.add(propertyRepository.findOne("workshift"));
		nsp4.add(propertyRepository.findOne("offered_rewards"));
		nsp4.add(propertyRepository.findOne("number_of_volunteers"));
		nsp4.add(propertyRepository.findOne("location"));
		nsp4.add(propertyRepository.findOne("starting_date"));
		nsp4.add(propertyRepository.findOne("end_date"));
		s4.setProperties(nsp4);
		nestedTemplates.add(s4);
		
		nested.setTemplates(nestedTemplates);
		
		
		if (!userDefinedTaskTemplateRepository.exists(t1.getId())) {
			userDefinedTaskTemplateRepository.save(t1);
			System.out.println("Testtemplate " + 0 + "added");
		} else {
			System.out.println("Template 0 already in db");
		}
		
		if (!userDefinedTaskTemplateRepository.exists(t2.getId())) {
			userDefinedTaskTemplateRepository.save(t2);
			System.out.println("Testtemplate " + 1 + "added");
		} else {
			System.out.println("Template 1 already in db");
		}
		
		if (!userDefinedTaskTemplateRepository.exists(nested.getId())) {
			userDefinedTaskTemplateRepository.save(nested);
			System.out.println("TestTemplate nested added");
		} else {
			System.out.println("TestTemplate nested already in db");
		}
	}

	@GetMapping("/tasktemplate/user")
	public List<?> findAll(@RequestParam(value = "stub", required = true) boolean stub) {
		if (!testValuesSet) {
			this.setupTestValues();
			this.testValuesSet = true;
		}

		List<UserDefinedTaskTemplate> t = userDefinedTaskTemplateRepository.findAll();
		
		if (stub) {
			return userDefinedTaskTemplateStubMapper.toDTOs(t);
		} else {
			return userDefinedTaskTemplateMapper.toDTOs(t);
		}	
	}

	@GetMapping("/tasktemplate/user/{templateId}")
	public UserDefinedTaskTemplateDTO findTemplateById(@PathVariable("templateId") String templateId) {		
		UserDefinedTaskTemplate t = userDefinedTaskTemplateRepository.findOne(templateId);

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
		
		System.out.println(templateNames.size());
		for (String s : templateNames) {
			System.out.println("Name: " + s);
		}
		
		return templateNames;
		
	}
	
	
	@GetMapping("/properties/{propId}/parents")
	public List<PropertyParentItemDTO> getPropertyParents(@PathVariable("propId") String propId, 
			@RequestParam(value = "templateId", required = true) String templateId, @RequestParam(value = "subtemplateId", required = false) String subtemplateId) {
		
		System.out.println("called getPropetyParents");
		
		if (subtemplateId != null) {
			MultiUserDefinedTaskTemplate root = (MultiUserDefinedTaskTemplate) this.userDefinedTaskTemplateRepository.findOne(templateId);
			SingleUserDefinedTaskTemplate nested = root.getTemplates().stream().filter(t -> t.getId().equals(subtemplateId)).findFirst().get();
			
			List<UserDefinedTaskTemplate> items = new ArrayList<>();
			items.add(root);
			items.add(nested);
			
			return propertyParentItemMapper.toDTOs(items);
			
		} else {
			System.out.println("isNull");
		}
		
		
		
		return null;
	}

	@PostMapping("/tasktemplate/user/new")
	public UserDefinedTaskTemplateDTO createRootTemplate(@RequestBody String[] params, @RequestParam(value = "type", required = true) String type) {
		System.out.println("create task template " + type);
		
		if (type.equals("single")) {
			
			SingleUserDefinedTaskTemplate taskTemplate = (SingleUserDefinedTaskTemplate) createTemplate(params[0], params[1], "single");
			
			taskTemplate.setProperties(new ArrayList<Property>());
			
			return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(taskTemplate));
		
		} else if (type.equals("multi") ) {
			
			MultiUserDefinedTaskTemplate taskTemplate = (MultiUserDefinedTaskTemplate) createTemplate(params[0], params[1], "multi");
			taskTemplate.setTemplates(new ArrayList<SingleUserDefinedTaskTemplate>());
			
			return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(taskTemplate));
		
		} else {
			System.out.println("should not happen");
			return null;
		}
	}
	
	@PostMapping("/tasktemplate/user/{templateId}/new")
	public UserDefinedTaskTemplateDTO createSubTemplate(@PathVariable("templateId") String templateId, @RequestBody String[] params) {
		System.out.println("Create sub template called");
		
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
		
		System.out.println(newTaskTemplate.getId() + " " + newTaskTemplate.getName() + " " + newTaskTemplate.getDescription());
		
		return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(t));
	}
	
	@PostMapping("/tasktemplate/user/{templateId}/new-copy")
	public UserDefinedTaskTemplateDTO createTemplateFromExisting(@PathVariable("templateId") String templateId, @RequestBody String[] params) {
		System.out.println("Create template from Copy called");
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
			return null;
		}
		
		taskTemplate.setName(name);
		taskTemplate.setDescription(description);
		return taskTemplate;
	}
	
	@PutMapping("/tasktemplate/user/{templateId}/update")
	public UserDefinedTaskTemplateDTO updateRootTemplate(@PathVariable("templateId") String templateId, @RequestBody String[] params) {
		UserDefinedTaskTemplate taskTemplate = userDefinedTaskTemplateRepository.findOne(templateId);
		
		System.out.println("called update task Template");
		
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
		System.out.println("called remove single");
		
		UserDefinedTaskTemplate rem = userDefinedTaskTemplateRepository.findOne(templateId);
		
		System.out.println("Removing TASKTEMPLATE: " + rem.getId() + ": " + rem.getName());// + " - Properties: " + rem.getProperties().size());
		
		userDefinedTaskTemplateRepository.delete(templateId);
		
		boolean ret = !userDefinedTaskTemplateRepository.exists(templateId);	


		if (ret) System.out.println("Deletion successful"); else System.out.println("Deletion failed");
		
		return ret;
		
	}
	
	@DeleteMapping("/tasktemplate/user/{templateId}/{subtemplateId}")
	public boolean deleteTemplate(@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId) {
		System.out.println("called remove nested");

		MultiUserDefinedTaskTemplate root = (MultiUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		
		List<SingleUserDefinedTaskTemplate> remainingSubTemplates = root.getTemplates().stream()
				.filter((t -> !t.getId().equals(subtemplateId))).collect(Collectors.toList());
			
		root.setTemplates(remainingSubTemplates);
		
		boolean  ret = !remainingSubTemplates.contains(new SingleUserDefinedTaskTemplate(subtemplateId));
		userDefinedTaskTemplateRepository.save(root);
		
		if (ret) System.out.println("Deletion successful"); else System.out.println("Deletion failed");
		
		return ret;
	}
	
	
	///////////////////////////////////////////////////////
	//////////Property-Manipulation
	///////////////////////////////////////////////////////
	
	
	@GetMapping("/tasktemplate/user/{templateId}/{subtemplateId}/{propId}")
	public PropertyDTO<Object> getPropertyFromSubtemplate(@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId, 
			@PathVariable("propId") String propId) {
		
		System.out.println("called get property from subtemplate");
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
		System.out.println("called add properties single");

		SingleUserDefinedTaskTemplate t = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		
		for (String propId : propIds) {
			Property p = propertyRepository.findOne(propId);
			if (!t.getProperties().contains(p)) {
				t.getProperties().add(p);
				System.out.println("added " + p.getId() );
			}
		}
					
		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(t);
		return userDefinedTaskTemplateMapper.toDTO(ret);

	}
	
	//Add Properties to SubTemplate
	@PutMapping("/tasktemplate/user/{templateId}/{subtemplateId}/addproperties")
	public UserDefinedTaskTemplateDTO addProperties(@PathVariable("templateId") String templateId, @PathVariable("subtemplateId") String subtemplateId, @RequestBody String[] propIds) {
		System.out.println("called add properties single");

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
				System.out.println("Added " + p.getId() + " to nested template " + templateId + " > " + subtemplateId + "(index " + indexArr[0] + ")");
			}
		}
		rootTemplate.getTemplates().set(indexArr[0], subTemplate);
			
		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(rootTemplate);
		return userDefinedTaskTemplateMapper.toDTO(ret);
	}
	
	
	//Update Properties from Single Template
	@PutMapping("/tasktemplate/user/{templateId}/updateproperties")
	public UserDefinedTaskTemplateDTO updateProperties(@PathVariable("templateId") String templateId, @RequestBody PropertyDTO<Object>[] properties) {
		System.out.println("called updated properties");

		SingleUserDefinedTaskTemplate t = (SingleUserDefinedTaskTemplate) userDefinedTaskTemplateRepository.findOne(templateId);
		
		System.out.println();
		System.out.println("INCOMING PROPERTIES: ");
		
		List<PropertyDTO<Object>> propertyList = Arrays.asList(properties);
		
		//Recursively set properties		
		List<Property> returnProperties = setPropertiesRec(propertyList,  t.getProperties());
		
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
			System.out.println("===Property to Update===");

			if (!p.getKind().equals(PropertyKind.MULTIPLE) && currentProperties.stream().anyMatch(cur -> p.getId().equals(cur.getId()))) {

				
				SingleProperty<Object> updateProperty = (SingleProperty<Object>) p;
				SingleProperty<Object> currentProperty = (SingleProperty<Object>) currentProperties.stream().filter(cur -> p.getId().equals(cur.getId())).findFirst().get();
				
				currentProperty.setValues(updateProperty.getValues());
				currentProperty.setDefaultValues(updateProperty.getDefaultValues());
				
				System.out.println("updated Property: " + updateProperty.getName()); // + " = " + update.getValues()!=null && update.getValues().get(0) != null ? update.getValues().get(0).value : "null");
				System.out.println("Default: " + updateProperty.getName()); // + " = " + update.getDefaultValues()!=null && update.getDefaultValues().get(0) != null ? update.getDefaultValues().get(0).value : "null");
				
				returnProperties.add(currentProperty);
				
			} else if (p.getKind().equals(PropertyKind.MULTIPLE)) {
				System.out.println("\nMULTIPLE--> " + p.getId());
				
				MultipleProperty currentProperty = (MultipleProperty) currentProperties.stream().filter(cur -> p.getId().equals(cur.getId())).findFirst().get();
				
				List<Property> nestedList= setPropertiesRec(dto.getProperties(), currentProperty.getProperties() );
				
				currentProperty.setProperties(nestedList);
				
				returnProperties.add(currentProperty);
				System.out.println();
			}
			
			System.out.println("========================");

		}
		
		return returnProperties;
	}
	
	@PutMapping("/tasktemplate/user/{templateId}/deleteproperties")
	public UserDefinedTaskTemplateDTO deleteProperties(@PathVariable("templateId") String templateId, @RequestBody List<String> propIds ) {
		
		System.out.println("called remove properties single");
	
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
		
		System.out.println("called remove properties nested");
		
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





//switch (p.getKind()) {
//case BOOL: {				
//		BooleanProperty bp = booleanMapper.toTypeProperty(p);
//		System.out.println("BooleanProperty: "  + bp.getName() + ": " + bp.getValue() + " Kind: " + bp.getKind());
//		if (bp.getValues() != null) {
//			System.out.println("===Multiple Values:");
//
//			for (ListEntry<Boolean> itm : bp.getValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		if (bp.getLegalValues() != null) {
//			System.out.println("===Legal Values:");
//
//			for (ListEntry<Boolean> itm : bp.getLegalValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		
//		if (map.containsKey(bp.getId())) {
//			((BooleanProperty)map.get(bp.getId())).setValue(bp.getValue());
//			((BooleanProperty)map.get(bp.getId())).setValues(bp.getValues());
//			System.out.println("updated Booleanproperty");
//		} else {
//			map.put(bp.getId(), bp);
//			System.out.println("Added?? Should not happen");
//		}
//		
//		break;
//	}
//case TEXT: case LONG_TEXT: {
//		TextProperty tp = textMapper.toTypeProperty(p);
//		System.out.println("TextProperty: " + tp.getName() + ": "  + tp.getValue() + " Kind: " + tp.getKind());
//		if (tp.getValues() != null) {
//			System.out.println("===Multiple Values:");
//
//			for (ListEntry<String> itm : tp.getValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		if (tp.getLegalValues() != null) {
//			System.out.println("===Legal Values:");
//
//			for (ListEntry<String> itm : tp.getLegalValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		
//		if (map.containsKey(tp.getId())) {
//			((TextProperty)map.get(tp.getId())).setValue(tp.getValue());
//			((TextProperty)map.get(tp.getId())).setValues(tp.getValues());
//			System.out.println("updated Textproperty");
//		} else {
//			map.put(tp.getId(), tp);
//			System.out.println("Added?? Should not happen");
//		}
//		break;
//	}
//case WHOLE_NUMBER: {
//		NumberProperty np = numberMapper.toTypeProperty(p);
//		System.out.println("NumberProperty: " + np.getName() + ": "  + np.getValue() + " Kind: " + np.getKind());
//		if (np.getValues() != null) {
//			System.out.println("===Multiple Values:");
//
//			for (ListEntry<Integer> itm : np.getValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		if (np.getLegalValues() != null) {
//			System.out.println("===Legal Values:");
//
//			for (ListEntry<Integer> itm : np.getLegalValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		
//		if (map.containsKey(np.getId())) {
//			((NumberProperty)map.get(np.getId())).setValue(np.getValue());
//			((NumberProperty)map.get(np.getId())).setValues(np.getValues());
//			System.out.println("updated Numberproperty");
//		} else {
//			map.put(np.getId(), np);
//			System.out.println("Added?? Should not happen");
//		}
//		
//		
//		break;
//	}
//case FLOAT_NUMBER: {
//		DoubleProperty dp = doubleMapper.toTypeProperty(p);
//		System.out.println("DoubleProperty: " + dp.getName() + ": " + dp.getValue() + " Kind: " + dp.getKind());
//		if (dp.getValues() != null) {
//			System.out.println("===Multiple Values:");
//
//			for (ListEntry<Double> itm : dp.getValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		if (dp.getLegalValues() != null) {
//			System.out.println("===Legal Values:");
//
//			for (ListEntry<Double> itm : dp.getLegalValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		
//		if (map.containsKey(dp.getId())) {
//			((DoubleProperty)map.get(dp.getId())).setValue(dp.getValue());
//			((DoubleProperty)map.get(dp.getId())).setValues(dp.getValues());
//			System.out.println("updated Doubleproperty");
//		} else {
//			map.put(dp.getId(), dp);
//			System.out.println("Added?? Should not happen");
//		}
//		
//		break;
//	}
//case DATE: {
//		DateProperty dp = dateMapper.toTypeProperty(p);
//		System.out.println("DateProperty: "  + dp.getName() + ": " + dp.getValue() + " Kind: " + dp.getKind());
//		if (dp.getValues() != null) {
//			System.out.println("===Multiple Values:");
//
//			for (ListEntry<Date> itm : dp.getValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		if (dp.getLegalValues() != null) {
//			System.out.println("===Legal Values:");
//
//			for (ListEntry<Date> itm : dp.getLegalValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		
//		if (map.containsKey(dp.getId())) {
//			((DateProperty)map.get(dp.getId())).setValue(dp.getValue());
//			((DateProperty)map.get(dp.getId())).setValues(dp.getValues());
//			System.out.println("updated Dateproperty");
//		} else {
//			map.put(dp.getId(), dp);
//			System.out.println("Added?? Should not happen");
//		}
//		
//		break;
//	}
//case LIST: {
//		TextProperty tp = textMapper.toTypeProperty(p);
//		System.out.println("ListProperty: "  + tp.getName() + ": " + tp.getValue() + " Kind: " + tp.getKind());
//		if (tp.getValues() != null) {
//			System.out.println("===Multiple Values:");
//
//			for (ListEntry<String> itm : tp.getValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		if (tp.getLegalValues() != null) {
//			System.out.println("===Legal Values:");
//
//			for (ListEntry<String> itm : tp.getLegalValues()) {
//				System.out.println(itm.getId() + " " + itm.getValue());
//			}
//		}
//		
//		if (map.containsKey(tp.getId())) {
//			((TextProperty)map.get(tp.getId())).setValue(tp.getValue());
//			((TextProperty)map.get(tp.getId())).setValues(tp.getValues());
//			System.out.println("updated Textproperty");
//		} else {
//			map.put(tp.getId(), tp);
//			System.out.println("Added?? Should not happen");
//		}
//		
//		break;
//	}
//}

//private void printPropertyDTOs(List<PropertyDTO<Object>> properties) {
//
//
//for (PropertyDTO<Object> p : properties) {
//	System.out.println(p.getId() + ": " + p.getName() + " - " + p.getValues().size());
//	if (p.getKind().equals(PropertyKind.MULTIPLE)) {
//		System.out.println("===>");
//		printPropertyDTOs(p.getProperties());
//		System.out.println("<===");
//	}
//}
//}




//private Map<String,Property> setPropertiesRec(List<PropertyDTO<Object>> updateProperties, List<Property> currentProperties) {
//Map<String,Property> map = this.toMap(currentProperties);
//
//for(PropertyDTO<Object> dto : updateProperties) {
//	Property p = propertyMapper.toEntity(dto);
//	System.out.println("===Property to Update===");
//
//	if (!p.getKind().equals(PropertyKind.MULTIPLE) && map.containsKey(p.getId())) {
//		
//		SingleProperty<Object> update = (SingleProperty<Object>) p;
//		SingleProperty<Object> current = (SingleProperty<Object>) (map.get(p.getId()));
//		
//		current.setValues(update.getValues());
//		current.setDefaultValues(update.getDefaultValues());
//		
//		System.out.println("updated Property: " + update.getName()); // + " = " + update.getValues()!=null && update.getValues().get(0) != null ? update.getValues().get(0).value : "null");
//		System.out.println("Default: " + update.getName()); // + " = " + update.getDefaultValues()!=null && update.getDefaultValues().get(0) != null ? update.getDefaultValues().get(0).value : "null");
//
//	} else if (p.getKind().equals(PropertyKind.MULTIPLE)) {
//		System.out.println("\nMULTIPLE--> " + p.getId());
//		
//		MultipleProperty current = (MultipleProperty) (map.get(p.getId()));
//		
//		Map<String,Property> nestedMap = setPropertiesRec(dto.getProperties(), current.getProperties() );
//		
//		current.setProperties(new ArrayList<>(nestedMap.values()));
//		System.out.println();
//	}
//	
//	System.out.println("========================");
//
//}
//
//return map;
//}

//private Map<String, Property> toMap(List<Property> list) {
//
//Map<String,Property> map = new LinkedHashMap<>();
//for (Property p : list) {
//map.put(p.getId(),(Property) p);
//}
//
//return map;
//}


