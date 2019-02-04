package at.jku.cis.iVolunteer.marketplace.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.StandardProperties;
import at.jku.cis.iVolunteer.StandardProperties.*;
import at.jku.cis.iVolunteer.mapper.property.PropertyListItemMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.TransferPropertyMapper;
//import at.jku.cis.iVolunteer.mapper.property.properties.TransferPropertyBooleanMapper;
//import at.jku.cis.iVolunteer.mapper.property.properties.TransferPropertyDateMapper;
//import at.jku.cis.iVolunteer.mapper.property.properties.TransferPropertyDoubleMapper;
//import at.jku.cis.iVolunteer.mapper.property.properties.TransferPropertyNumberMapper;
//import at.jku.cis.iVolunteer.mapper.property.properties.TransferPropertyTextMapper;

import at.jku.cis.iVolunteer.model.property.DateProperty;
import at.jku.cis.iVolunteer.model.property.DoubleProperty;
import at.jku.cis.iVolunteer.model.property.NumberProperty;
import at.jku.cis.iVolunteer.model.property.BooleanProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.TextProperty;
import at.jku.cis.iVolunteer.model.property.TransferProperty;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.dto.PropertyListItemDTO;
import at.jku.cis.iVolunteer.model.property.dto.TransferPropertyDTO;
import at.jku.cis.iVolunteer.model.property.rule.Rule;
import at.jku.cis.iVolunteer.model.property.rule.RuleKind;
import at.jku.cis.iVolunteer.model.task.template.TaskTemplateA;
import at.jku.cis.iVolunteer.model.task.template.dto.TaskTemplateADTO;



@RestController
public class PropertyController {
	
	
	
//	@Autowired private TransferPropertyMapper tpMapper;
	//@Autowired private CompetencePropertyMapper competenceMapper;
	
//	@Autowired private TransferPropertyTextMapper tpToTxtMapper;
//	@Autowired private TransferPropertyNumberMapper tpToNumMapper;
//	@Autowired private TransferPropertyBooleanMapper tpToBoolMapper;
//	@Autowired private TransferPropertyDoubleMapper tpToDoubleMapper;
//	@Autowired private TransferPropertyDateMapper tpToDateMapper;
	
	@Autowired private PropertyListItemMapper propertyListItemMapper;
	@Autowired private PropertyMapper propertyMapper;
	@Autowired private PropertyRepository propertyRepository;

	
	
	private boolean setUpFlag = true;

	
	@Autowired StandardProperties sp;
	
	
	private Map<String, Property<?>> props;
	List<TransferProperty> transfer;
	
	
	@GetMapping("/properties/list") 
	public List<PropertyListItemDTO<?>> getPropertiesList() {
		
/*		String[] arr = new String[] {"BERT", "BERTI", "BERTIBERT"};
		
		System.out.println("CREATING TEST TextPropertyDTOs");
		props = new LinkedList<>();
		TextProperty t1 = new TextProperty();
		TextProperty t2 = new TextProperty();
		TextProperty t3 = new TextProperty();
		TextProperty t4 = new TextProperty();
		TextProperty t6 = new TextProperty();
		t1.setId("1"); t1.setName("ID"); t1.setValue("0000000001"); t1.setDefaultValue("BERT");
		t1.setLegalValues(Arrays.asList(arr));
		
		Rule r1 = new Rule();
		r1.setId("1");
		r1.setKind(RuleKind.EQUAL);
		
		
		Rule r2 = new Rule();
		r2.setId("2");
		r2.setKind(RuleKind.MAX);
		Rule[] r = new Rule[] {r1, r2};
		
		t1.setRules(Arrays.asList(new Rule[] {r1, r2}));
		t1.setKind(PropertyKind.TEXT);
		
		NumberProperty p1 = new NumberProperty();
		NumberProperty p2 = new NumberProperty();
		
		t2.setId("2"); t2.setName("First Name"); t2.setValue("Schorsch"); t2.setKind(PropertyKind.TEXT);
		t3.setId("3"); t3.setName("Last Name"); t3.setValue("Burli"); t3.setKind(PropertyKind.TEXT);
		t4.setId("4"); t4.setName("Address"); t4.setValue("Sonnenstraße 232"); t4.setKind(PropertyKind.TEXT);
		p1.setId("5"); p1.setName("Postcode"); p1.setValue(6666); p1.setKind(PropertyKind.WHOLE_NUMBER);
		t6.setId("6"); t6.setName("City"); t6.setValue("Hell City"); t6.setKind(PropertyKind.TEXT);
		
		
		
		p2.setId("7"); p2.setName("Telephone Number"); p2.setValue(123456789); p2.setKind(PropertyKind.WHOLE_NUMBER);
	*/	
		
		
		
		setTestValues();

		List<PropertyListItemDTO<?>> retVal = propertyListItemMapper.toDTOs(new LinkedList<>(props.values()));

		return retVal;
		
	}
	
	@GetMapping("/properties/full")
	public List<PropertyDTO<?>> getPropertiesFull() {
		
		//System.out.println("Props: " + props.size());
		setTestValues();
		
		List<PropertyDTO<?>> retVal = propertyMapper.toDTOs(new LinkedList<>(props.values()));
		
		
		return retVal;
	}
	
	private void setTestValues() {
		if (setUpFlag) {
			
			props = sp.getAllMap();
			
			setUpFlag = false;
			
			for (Property p : sp.getAll()) {
				System.out.println("Setup Property:  "+ p.getId() + " -- " + p.getKind() + ": " + p.getName() + ": " + p.getValue());
				if (!propertyRepository.exists(p.getId())) {
					System.out.println("adding prop to db");
					propertyRepository.save(p);
				} else {
					System.out.println("prop already in db");
				}
				
			}
			
			//propertyRepository.save(props.values());
			System.out.println("Number Of Properties pushed: " + props.size());
			System.out.println("Number Of Properties in DB : " + propertyRepository.count());
			
			
		}
		
		
	}
	
	@GetMapping("/properties/{id}") 
	public PropertyDTO<?> getPropertyByID(@PathVariable("id") String id) {
		
		

		return propertyMapper.toDTO(propertyRepository.findOne(id));
	}
	
	@PostMapping("/properties/new")
	public void addProperty(@RequestBody TransferPropertyDTO tpDto) {
		//TODO
	}
	
	@PutMapping("/properties/{id}/update")
	public void updateProperty(@PathVariable("id") String id, @RequestBody TransferPropertyDTO tpDto) {
		//TODO
		
	}
}
