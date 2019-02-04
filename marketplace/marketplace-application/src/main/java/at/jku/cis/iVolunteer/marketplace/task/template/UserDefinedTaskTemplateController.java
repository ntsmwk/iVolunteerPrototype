package at.jku.cis.iVolunteer.marketplace.task.template;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.StandardProperties;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.properties.PropertyToBooleanPropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.properties.PropertyToDatePropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.properties.PropertyToDoublePropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.properties.PropertyToNumberPropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.properties.PropertyToTextPropertyMapper;
import at.jku.cis.iVolunteer.mapper.task.template.UserDefinedTaskTemplateMapper;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.model.property.BooleanProperty;
import at.jku.cis.iVolunteer.model.property.DateProperty;
import at.jku.cis.iVolunteer.model.property.DoubleProperty;
import at.jku.cis.iVolunteer.model.property.NumberProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.TextProperty;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;

import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.TaskTemplateDTO;
import at.jku.cis.iVolunteer.model.task.template.dto.UserDefinedTaskTemplateDTO;


@RestController
public class UserDefinedTaskTemplateController {

	@Autowired
	private UserDefinedTaskTemplateMapper userDefinedTaskTemplateMapper;
	@Autowired
	private PropertyMapper propertyMapper;
	@Autowired
	private PropertyToBooleanPropertyMapper booleanMapper;
	@Autowired
	private PropertyToTextPropertyMapper textMapper;
	@Autowired
	private PropertyToNumberPropertyMapper numberMapper;
	@Autowired
	private PropertyToDoublePropertyMapper doubleMapper;
	@Autowired
	private PropertyToDatePropertyMapper dateMapper;
	
	@Autowired
	private UserDefinedTaskTemplateRepository userDefinedTaskTemplateRepository;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired private StandardProperties sp;
	
	List<UserDefinedTaskTemplate> templates;
	boolean testValuesSet = false;
	
	
	
	
	private void setupTestValues() {
		templates = new LinkedList<UserDefinedTaskTemplate>();
		UserDefinedTaskTemplate t1 = new UserDefinedTaskTemplate("0");
		UserDefinedTaskTemplate t2 = new UserDefinedTaskTemplate("1");
		
//		t1.setId("1");
		t1.setName("My Template 1");
		List<Property<?>> p1 = sp.getAll();
		//StandardProperties.setIds(p1);
		
		t1.setProperties(p1);
		templates.add(t1);
		
//		t2.setId("2");
		t2.setName("My Template 2");
		List<Property<?>> p2 = sp.getAll();
		//StandardProperties.setIds(p2);
		t2.setProperties(p2);
		templates.add(t2);
		
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
	}
	

	@GetMapping("/tasktemplate/user")
	public List<UserDefinedTaskTemplateDTO> findAll() {
		//return taskTemplateMapper.toDTOs(taskTemplateRepository.findAll());
		if (!testValuesSet) {
			this.setupTestValues();
			this.testValuesSet = true;
		}
		
		List<UserDefinedTaskTemplate> t = userDefinedTaskTemplateRepository.findAll();
		
		
		return userDefinedTaskTemplateMapper.toDTOs(t);
		
	}

	@GetMapping("/tasktemplate/user/{id}")
	public UserDefinedTaskTemplateDTO findById(@PathVariable("id") String id) {
		//return taskTemplateMapper.toDTO(taskTemplateRepository.findOne(id));
		
//		for (UserDefinedTaskTemplate t : templates) {
//			if (t.getId().equals(id)) {
//				return userDefinedTaskTemplateMapper.toDTO(t);
//			}
//		}
		
		return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.findOne(id));
	}

	@PostMapping("/tasktemplate/user/new")
	public UserDefinedTaskTemplateDTO create(@RequestBody String name) {
//		TaskTemplate taskTemplate = taskTemplateMapper.toEntity(taskTemplateDto);
//		return taskTemplateMapper.toDTO(taskTemplateRepository.insert(taskTemplate));
		UserDefinedTaskTemplate taskTemplate = new UserDefinedTaskTemplate();
		//taskTemplate.setId(String.valueOf(templates.size()));
		taskTemplate.setName(name);
		
		taskTemplate.setProperties(new LinkedList<Property<?>>());
		//templates.add(taskTemplate);
		//return userDefinedTaskTemplateMapper.toDTO(taskTemplate);
		
		return userDefinedTaskTemplateMapper.toDTO(userDefinedTaskTemplateRepository.save(taskTemplate));
	}
	

	@PutMapping("/tasktemplate/user/{id}")
	public TaskTemplateDTO update(@PathVariable("id") String id, @RequestBody TaskTemplateDTO taskTemplateDto) {
//		if (!taskTemplateRepository.exists(id)) {
//			throw new NotAcceptableException();
//		}
//
//		TaskTemplate taskTemplate = taskTemplateMapper.toEntity(taskTemplateDto);
//		return taskTemplateMapper.toDTO(taskTemplateRepository.save(taskTemplate));
		
		
		
		return null;
	}
	
	@DeleteMapping("/tasktemplate/user/{templateId}")
	public void deleteTemplate(@PathVariable("templateId") String id) {
//		taskTemplateRepository.delete(id);+
		System.out.println("called remove");
		UserDefinedTaskTemplate rem = userDefinedTaskTemplateRepository.findOne(id);
		
		System.out.println("Removing TASKTEMPLATE: " + rem.getId() + ": " + rem.getName() + " - Properties: " + rem.getProperties().size());

		
		userDefinedTaskTemplateRepository.delete(id);
		
		
		if (!userDefinedTaskTemplateRepository.exists(id)) {
			System.out.println("Deletion successful");
		} else {
			System.out.println("Deletion failed");
		}
		
	}
	
	
	///////////////////////////////////////////////////////
	//////////Property-Manipulation
	///////////////////////////////////////////////////////
	

	@PutMapping("/tasktemplate/user/{templateId}/addproperties")
	public UserDefinedTaskTemplateDTO addProperties(@PathVariable("templateId") String templateId, @RequestBody String[] propIds) {
		System.out.println("called add properties");
		//UserDefinedTaskTemplate t = templates.get(templates.indexOf(new UserDefinedTaskTemplate(templateId)));
		UserDefinedTaskTemplate t = userDefinedTaskTemplateRepository.findOne(templateId);
		
		for (String propId : propIds) {
			Property p = sp.getAllMap().get(propId);
			if (!t.getProperties().contains(p)) {
				t.getProperties().add(p);
				System.out.println("added " + p.getId() );
			}
		}
		
//		List<String> ids = Collections.arrayToList(propIds);
//		
//		for (String s : ids) {
//			System.out.println(s);
//			Property<?> prop = propertyRepository.findOne(s);
//			t.getProperties().add(prop);
//		}
		
		System.out.println(t.getProperties().size());
		
		UserDefinedTaskTemplate ret = userDefinedTaskTemplateRepository.save(t);
		
		
		
		return userDefinedTaskTemplateMapper.toDTO(ret);
		
	}
	
	@PutMapping("/tasktemplate/user/{templateId}/updateproperties")
	public UserDefinedTaskTemplateDTO updateProperties(@PathVariable("templateId") String templateId, @RequestBody PropertyDTO[] properties) {
		System.out.println("called updated properties");
		UserDefinedTaskTemplate t = templates.get(templates.indexOf(new UserDefinedTaskTemplate(templateId)));
		
		Map<String,Property<?>> map = this.toMap(t.getProperties());
		System.out.println("\n");
		for(PropertyDTO dto : properties) {
			Property p = propertyMapper.toEntity(dto);

			
			System.out.println("===Property to Update===");
			switch (p.getKind()) {
			case BOOL: {				
					BooleanProperty bp = booleanMapper.toTypeProperty(p);
					System.out.println("BooleanProperty: "  + bp.getName() + ": " + bp.getValue() + " Kind: " + bp.getKind());
					if (bp.getValues() != null) {
						System.out.println("===Multiple Values:");
	
						for (ListEntry<Boolean> itm : bp.getValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					if (bp.getLegalValues() != null) {
						System.out.println("===Legal Values:");
	
						for (ListEntry<Boolean> itm : bp.getLegalValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					
					if (map.containsKey(bp.getId())) {
						((BooleanProperty)map.get(bp.getId())).setValue(bp.getValue());
						((BooleanProperty)map.get(bp.getId())).setValues(bp.getValues());
						System.out.println("updated Booleanproperty");
					} else {
						map.put(bp.getId(), bp);
						System.out.println("Added?? Should not happen");
					}
					
					break;
				}
			case TEXT: case LONG_TEXT: {
					TextProperty tp = textMapper.toTypeProperty(p);
					System.out.println("TextProperty: " + tp.getName() + ": "  + tp.getValue() + " Kind: " + tp.getKind());
					if (tp.getValues() != null) {
						System.out.println("===Multiple Values:");
	
						for (ListEntry<String> itm : tp.getValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					if (tp.getLegalValues() != null) {
						System.out.println("===Legal Values:");
	
						for (ListEntry<String> itm : tp.getLegalValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					
					if (map.containsKey(tp.getId())) {
						((TextProperty)map.get(tp.getId())).setValue(tp.getValue());
						((TextProperty)map.get(tp.getId())).setValues(tp.getValues());
						System.out.println("updated Textproperty");
					} else {
						map.put(tp.getId(), tp);
						System.out.println("Added?? Should not happen");
					}
					break;
				}
			case WHOLE_NUMBER: {
					NumberProperty np = numberMapper.toTypeProperty(p);
					System.out.println("NumberProperty: " + np.getName() + ": "  + np.getValue() + " Kind: " + np.getKind());
					if (np.getValues() != null) {
						System.out.println("===Multiple Values:");
	
						for (ListEntry<Integer> itm : np.getValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					if (np.getLegalValues() != null) {
						System.out.println("===Legal Values:");
	
						for (ListEntry<Integer> itm : np.getLegalValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					
					if (map.containsKey(np.getId())) {
						((NumberProperty)map.get(np.getId())).setValue(np.getValue());
						((NumberProperty)map.get(np.getId())).setValues(np.getValues());
						System.out.println("updated Numberproperty");
					} else {
						map.put(np.getId(), np);
						System.out.println("Added?? Should not happen");
					}
					
					
					break;
				}
			case FLOAT_NUMBER: {
					DoubleProperty dp = doubleMapper.toTypeProperty(p);
					System.out.println("DoubleProperty: " + dp.getName() + ": " + dp.getValue() + " Kind: " + dp.getKind());
					if (dp.getValues() != null) {
						System.out.println("===Multiple Values:");
	
						for (ListEntry<Double> itm : dp.getValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					if (dp.getLegalValues() != null) {
						System.out.println("===Legal Values:");
	
						for (ListEntry<Double> itm : dp.getLegalValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					
					if (map.containsKey(dp.getId())) {
						((DoubleProperty)map.get(dp.getId())).setValue(dp.getValue());
						((DoubleProperty)map.get(dp.getId())).setValues(dp.getValues());
						System.out.println("updated Doubleproperty");
					} else {
						map.put(dp.getId(), dp);
						System.out.println("Added?? Should not happen");
					}
					
					break;
				}
			case DATE: {
					DateProperty dp = dateMapper.toTypeProperty(p);
					System.out.println("DateProperty: "  + dp.getName() + ": " + dp.getValue() + " Kind: " + dp.getKind());
					if (dp.getValues() != null) {
						System.out.println("===Multiple Values:");
	
						for (ListEntry<Date> itm : dp.getValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					if (dp.getLegalValues() != null) {
						System.out.println("===Legal Values:");
	
						for (ListEntry<Date> itm : dp.getLegalValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					
					if (map.containsKey(dp.getId())) {
						((DateProperty)map.get(dp.getId())).setValue(dp.getValue());
						((DateProperty)map.get(dp.getId())).setValues(dp.getValues());
						System.out.println("updated Dateproperty");
					} else {
						map.put(dp.getId(), dp);
						System.out.println("Added?? Should not happen");
					}
					
					break;
				}
			case LIST: {
					TextProperty tp = textMapper.toTypeProperty(p);
					System.out.println("ListProperty: "  + tp.getName() + ": " + tp.getValue() + " Kind: " + tp.getKind());
					if (tp.getValues() != null) {
						System.out.println("===Multiple Values:");
	
						for (ListEntry<String> itm : tp.getValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					if (tp.getLegalValues() != null) {
						System.out.println("===Legal Values:");
	
						for (ListEntry<String> itm : tp.getLegalValues()) {
							System.out.println(itm.getId() + " " + itm.getValue());
						}
					}
					
					if (map.containsKey(tp.getId())) {
						((TextProperty)map.get(tp.getId())).setValue(tp.getValue());
						((TextProperty)map.get(tp.getId())).setValues(tp.getValues());
						System.out.println("updated Textproperty");
					} else {
						map.put(tp.getId(), tp);
						System.out.println("Added?? Should not happen");
					}
					
					break;
				}
				
			}

			System.out.println("========================");

		}
		
		//t.setProperties(new LinkedList(map.values()));
		
		return userDefinedTaskTemplateMapper.toDTO(t);
	}
	
	@PutMapping("/tasktemplate/user/{templateId}/deleteproperties")
	public void deleteProperties(@PathVariable("templateId") String templateId, @RequestBody String[] propIds ) {
		System.out.println("called remove properties");
		UserDefinedTaskTemplate t = templates.get(templates.indexOf(new UserDefinedTaskTemplate(templateId)));
		
		//List<Property<?>> propsToRemove = new LinkedList<Property<?>>();
		
		Map<String,Property<?>> map = this.toMap(t.getProperties());
		
		for (String propId : propIds) {
			map.remove(propId);
			System.out.println("REMOVE: "  + " - " + propId);
			//System.out.println("ITEM: " + t.getProperties().size() + t.getProperties());
			
			
			
			//t.getProperties().remove(index);
		}
		
		t.setProperties(new LinkedList(map.values()));
		
		System.out.println("\n\nPROPERTIES after remove");
		for (Property<?> p : t.getProperties()) {
			System.out.println(p.getId());
		}
		
		
		
	}
	
	private Map<String, Property<?>> toMap(List<Property<?>> list) {
			
		Map<String,Property<?>> map = new HashMap<String,Property<?>>();
		for (Property<?> p : list) {
			map.put(p.getId(),(Property<?>) p);
		}
		
		return map;
	}
	
	
		
	
	
	

}
