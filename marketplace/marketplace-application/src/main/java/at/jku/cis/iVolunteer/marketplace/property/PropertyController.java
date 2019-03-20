package at.jku.cis.iVolunteer.marketplace.property;

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
import at.jku.cis.iVolunteer.mapper.property.MultiplePropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyListItemMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.SinglePropertyMapper;
import at.jku.cis.iVolunteer.model.property.MultipleProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.dto.PropertyListItemDTO;
import at.jku.cis.iVolunteer.model.property.dto.SinglePropertyDTO;



@RestController
public class PropertyController {
	
	@Autowired private PropertyListItemMapper propertyListItemMapper;
//	@Autowired private SinglePropertyMapper singlePropertyMapper;
//	@Autowired private MultiplePropertyMapper multiplePropertyMapper;
	@Autowired private PropertyMapper propertyMapper;
	
//	@Autowired private SinglePropertyRepository singlePropertyRepository;
	@Autowired private PropertyRepository propertyRepository;
	

	private boolean setUpFlag = true;

	@Autowired StandardProperties sp;
	
	private Map<String, Property> props;
	
	
	@GetMapping("/properties/list") 
	public List<PropertyListItemDTO<Object>> getPropertiesList() {

		setTestValues();

		List<PropertyListItemDTO<Object>> retVal = propertyListItemMapper.toDTOs(propertyRepository.findAll());
		
		return retVal;
		
	}
	
	@GetMapping("/properties/full")
	public List<PropertyDTO<Object>> getPropertiesFull() {
		
		//System.out.println("Props: " + props.size());
		setTestValues();
		
		List<PropertyDTO<Object>> retVal = propertyMapper.toDTOs(propertyRepository.findAll());
		
		
		return retVal;
	}
	
	private void setTestValues() {
		if (setUpFlag) {
			
			props = sp.getAllSingleMap();
			
			setUpFlag = false;
			
			for (Property p : sp.getAllSingle()) {
				System.out.println("Setup Property:  "+ p.getId() + " -- " + p.getKind() + ": " + p.getName() + ": " + " "
						);
				if (propertyRepository.findByName(p.getName()) == null) {
					System.out.println("adding prop to db");
					propertyRepository.save(p);
				} else {
					System.out.println("prop already in db");
				}	
			}
			
			System.out.println("Number Of Properties pushed: " + props.size());
			System.out.println("Number Of Properties in DB : " + propertyRepository.count());
			System.out.println("=> done Single\n\n");
			System.out.println("Getting Multi Properties");
			
			for (Property p : sp.getAllMulti()) {
				System.out.println("Setup Property: " + p.getId() + "--" + p.getKind() + " - Properties: " );
				for (Property mp : ((MultipleProperty)p).getProperties()) {
					System.out.println(mp.getId() + ": " + mp.getName() + " = " );
				}
				
				if (p.getId() == null || !propertyRepository.exists(p.getId())) {
					propertyRepository.save(p);
					System.out.println("prop added");
				} else {
					System.out.println("prop already in db");
				}
			}
			
		}
		
		System.out.println("PropertyController: DONE\n\n");
		
		
	}
	
	@GetMapping("/properties/{id}") 
	public PropertyDTO<Object> getPropertyByID(@PathVariable("id") String id) {
		
		

		return propertyMapper.toDTO(propertyRepository.findOne(id));
	}
	
	@PostMapping("/properties/new")
	public void addProperty(@RequestBody PropertyDTO<?> dto) {
		//TODO
	}
	
	@PutMapping("/properties/{id}/update")
	public void updateProperty(@PathVariable("id") String id, @RequestBody PropertyDTO<?> dto) {
		//TODO

	}
	
	/* Old Test Properties		
	 
	String[] arr = new String[] {"BERT", "BERTI", "BERTIBERT"};
	
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
}
