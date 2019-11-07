//package at.jku.cis.iVolunteer.marketplace.property;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
//
//
//
//@RestController
//public class PropertyController {
//	
//	@Autowired private PropertyDefinitionMapper propertyDefinitionMapper;
//	
//	@Autowired private PropertyRepository propertyRepository;
//		
//	@Autowired StandardProperties sp;
//	
//	@GetMapping("/properties/all")
//	public List<PropertyDTO<Object>> getPropertiesFull() {
//
//		List<PropertyDTO<Object>> retVal = propertyMapper.toDTOs(propertyRepository.findAll());
//
//		return retVal;
//	}
//	
//	@GetMapping("/properties/{id}") 
//	public PropertyDTO<Object> getPropertyByID(@PathVariable("id") String id) {
//		return propertyMapper.toDTO(propertyRepository.findOne(id));
//	}
//	
//	@PostMapping("/properties/new/single")
//	public void addSingleProperty(@RequestBody PropertyDTO<Object> dto) {
//		System.out.println("Adding Single Property");
//		
//		System.out.println("DTO");
//		System.out.println(dto.getLegalValues().size());
//		System.out.println(dto.getDefaultValues().size());
//		
//		SingleProperty<Object> p = (SingleProperty<Object>) propertyMapper.toEntity(dto);
//		
//		
//		//fix the ids for Default Values	
//		if (p.getLegalValues() != null && p.getDefaultValues() != null) {
//			for (ListEntry<Object> val : p.getLegalValues()) {
//				
//				try {
//					ListEntry<Object> defaultValue = p.getDefaultValues().stream().filter(entry -> entry.value.equals(val.value)).findFirst().get();
//					defaultValue.id = val.id;
//				} catch (NoSuchElementException e) {
//					System.out.println("No such Element - " + val.getId() +": " + val.getValue());
//					System.out.println("Continue");
//					continue;
//				}
//			}	
//		}
//
//		p.setCustom(true);
//		this.propertyRepository.save(p);
//					
//	}
//	
//	@PostMapping("/properties/new/multiple")
//	public void addMultipleProperty(@RequestBody PropertyDTO<Object> dto) {
//		System.out.println("Adding Multiple Property");
//		System.out.println("custom: " + dto.isCustom());
//		
//		MultiProperty mp = (MultiProperty) propertyMapper.toEntity(dto);
//				
//		System.out.println("custom: " + mp.isCustom());
//
////		for (String id : dto.getPropertyIDs()) {		
////			Property p = propertyRepository.findOne(id);			
////			mp.getProperties().add(p);
////		}
//		
//		this.propertyRepository.save(mp);
//		
//	}
//	
//	@DeleteMapping("/properties/{id}")
//	public void deleteProperty(@PathVariable("id") String id) {
//		this.propertyRepository.delete(id);
//	}
//	
////	@PutMapping("/properties/{id}/update/single")
////	public void updatePropertySingle(@PathVariable("id") String id, @RequestBody PropertyDTO<Object> dto) {
////		//TODO Test if working (after frontend has been implemented)
////		Property current = propertyRepository.findOne(id);
////		
////		if (current == null) {
////			return;
////		} else {
////			propertyRepository.save(propertyMapper.toEntity(dto));
////		}
////		
////		
////		
////	}
////	
////	@PutMapping("/properties/{id}/update/multiple")
////	public void updateMultipleProperty(@PathVariable("id") String id, @RequestBody MultiplePropertyRetDTO dto) {
////		
////	}
//	
//	/* Old Test Properties		
//	 
//	String[] arr = new String[] {"BERT", "BERTI", "BERTIBERT"};
//	
//	System.out.println("CREATING TEST TextPropertyDTOs");
//	props = new LinkedList<>();
//	TextProperty t1 = new TextProperty();
//	TextProperty t2 = new TextProperty();
//	TextProperty t3 = new TextProperty();
//	TextProperty t4 = new TextProperty();
//	TextProperty t6 = new TextProperty();
//	t1.setId("1"); t1.setName("ID"); t1.setValue("0000000001"); t1.setDefaultValue("BERT");
//	t1.setLegalValues(Arrays.asList(arr));
//	
//	Rule r1 = new Rule();
//	r1.setId("1");
//	r1.setKind(RuleKind.EQUAL);
//	
//	
//	Rule r2 = new Rule();
//	r2.setId("2");
//	r2.setKind(RuleKind.MAX);
//	Rule[] r = new Rule[] {r1, r2};
//	
//	t1.setRules(Arrays.asList(new Rule[] {r1, r2}));
//	t1.setKind(PropertyKind.TEXT);
//	
//	NumberProperty p1 = new NumberProperty();
//	NumberProperty p2 = new NumberProperty();
//	
//	t2.setId("2"); t2.setName("First Name"); t2.setValue("Schorsch"); t2.setKind(PropertyKind.TEXT);
//	t3.setId("3"); t3.setName("Last Name"); t3.setValue("Burli"); t3.setKind(PropertyKind.TEXT);
//	t4.setId("4"); t4.setName("Address"); t4.setValue("Sonnenstraße 232"); t4.setKind(PropertyKind.TEXT);
//	p1.setId("5"); p1.setName("Postcode"); p1.setValue(6666); p1.setKind(PropertyKind.WHOLE_NUMBER);
//	t6.setId("6"); t6.setName("City"); t6.setValue("Hell City"); t6.setKind(PropertyKind.TEXT);
//	
//	
//	
//	p2.setId("7"); p2.setName("Telephone Number"); p2.setValue(123456789); p2.setKind(PropertyKind.WHOLE_NUMBER);
//*/
//	
////	private void setTestValues() {
////	if (setUpFlag) {
////		
////		props = sp.getAllSingleMap();
////		
////		setUpFlag = false;
////		
////		for (Property p : sp.getAllSingle()) {
////			System.out.println("Setup Property:  "+ p.getId() + " -- " + p.getKind() + ": " + p.getName() + ": " + " "
////					);
////			if (propertyRepository.findByName(p.getName()) == null) {
////				System.out.println("adding prop to db");
////				propertyRepository.save(p);
////			} else {
////				System.out.println("prop already in db");
////			}	
////		}
////		
////		System.out.println("Number Of Properties pushed: " + props.size());
////		System.out.println("Number Of Properties in DB : " + propertyRepository.count());
////		System.out.println("=> done Single\n\n");
////		System.out.println("Getting Multi Properties");
////		
////		for (Property p : sp.getAllMulti()) {
////			System.out.println("Setup Property: " + p.getId() + "--" + p.getKind() + " - Properties: " );
////			for (Property mp : ((MultipleProperty)p).getProperties()) {
////				System.out.println(mp.getId() + ": " + mp.getName() + " = " );
////			}
////			
//////			if (p.getId() == null || !propertyRepository.exists(p.getId())) {
////			if (propertyRepository.findByName(p.getName()) == null) {
////				propertyRepository.save(p);
////				System.out.println("prop added");
////			} else {
////				System.out.println("prop already in db");
////			}
////		}
////		
////	}
////	
////	System.out.println("PropertyController: DONE\n\n");
////	
////	
////}
//}
