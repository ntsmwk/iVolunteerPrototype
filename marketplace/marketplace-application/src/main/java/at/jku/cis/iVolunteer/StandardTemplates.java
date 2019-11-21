package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.task.template.UserDefinedTaskTemplateRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.task.template.MultiUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.SingleUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;


@Component
public class StandardTemplates {
	
	
	@Autowired public ClassDefinitionRepository competenceRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired private UserDefinedTaskTemplateRepository userDefinedTaskTemplateRepository;
	
	public StandardTemplates() {
		
	}
	
	@PostConstruct
	public void addTestTemplates() {
		for (UserDefinedTaskTemplate t : this.createAll()) {
			if (!userDefinedTaskTemplateRepository.exists(t.getId())) {
				userDefinedTaskTemplateRepository.save(t);
			}
		}
	}

	
	public List<UserDefinedTaskTemplate> createAll() {
		List<UserDefinedTaskTemplate> list = new ArrayList<>();
		
		list.add(createTemplateWithAllProperties());
		list.add(createNestedTemplateWithExamples());
//		list.add(createGenericDienstTemplate());
		list.add(createGenericTaskTemplate());
//		list.add(createGenericEventTemplate());
//		list.add(createGenericActivityTemplate());
//		list.add(createGenericTrainingTemplate());
//		list.add(createTrainingTemplate());
//		list.add(createVehicleTemplate());
//		list.add(createVolunteerCheckTemplate());
//		
		return list;
	}


	public UserDefinedTaskTemplate createTemplateWithAllProperties() {
		SingleUserDefinedTaskTemplate t = new SingleUserDefinedTaskTemplate("all");
		
		t.setName("All Properties");
		List<PropertyDefinition<Object>> p = propertyDefinitionRepository.findAll();
		System.out.println(p.size());
		List<ClassProperty<Object>> a = propertyDefinitionToClassPropertyMapper.toTargets(p);
		
		t.setTemplateProperties(a);
		
		int i = 0;
		for (ClassProperty<Object> classProperty : t.getTemplateProperties()) {
			classProperty.setPosition(i);
			i++;
		}
		return t;
		
	}
	
	public UserDefinedTaskTemplate createNestedTemplateWithExamples() {
		MultiUserDefinedTaskTemplate nested = new MultiUserDefinedTaskTemplate("SmallExampleTasks");
		nested.setName("Test nested template");
		nested.setDescription("This is a description");
		List<SingleUserDefinedTaskTemplate> nestedTemplates = new ArrayList<>();
		
		SingleUserDefinedTaskTemplate s1 = new SingleUserDefinedTaskTemplate("CleanFloor");
		s1.setName("Clean Garage Floor");
		s1.setDescription("Clean garage floor at the end of the day");
		List<ClassProperty<Object>> nsp1 = new ArrayList<>();
		nsp1.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("location")));
		nsp1.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("postcode")));
		nsp1.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("number_of_volunteers")));
		nsp1.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("period_type")));
		nsp1.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("priority")));
		nsp1.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("urgent")));
		nsp1.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("workshift")));
		
		int i = 0;
		for (ClassProperty<Object> prop : nsp1) {
			prop.setPosition(i);
			i++;
		}
		
		
		s1.setTemplateProperties(nsp1);
		nestedTemplates.add(s1);
		
		SingleUserDefinedTaskTemplate s2 = new SingleUserDefinedTaskTemplate("drivecartoservice");
		s2.setName("Drive car to service");
		s2.setDescription("A car is has to be serviced soon, someone has to drive the car described to the service partner");
		List<ClassProperty<Object>> nsp2 = new ArrayList<>();
		nsp2.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("location")));
		nsp2.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("postcode")));
		nsp2.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("number_of_volunteers")));
		nsp2.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("urgent")));
		nsp2.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("priority")));
		nsp2.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("description")));
		
		i = 0;
		for (ClassProperty<Object> prop : nsp2) {
			prop.setPosition(i);
			i++;
		}
		
		s2.setTemplateProperties(nsp2);
		nestedTemplates.add(s2);

		SingleUserDefinedTaskTemplate s3 = new SingleUserDefinedTaskTemplate("PickupPerson");
		s3.setName("Person");
		s3.setDescription("This is a description for the 3rd nested template");
		List<ClassProperty<Object>> nsp3 = new ArrayList<>();
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("name")));
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("postcode")));
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("location")));
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("description")));
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("starting_date")));
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("latitude")));
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("longitude")));
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("priority")));
		nsp3.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("importancy")));
		
		i = 0;
		for (ClassProperty<Object> prop : nsp3) {
			prop.setPosition(i);
			i++;
		}
		
		s3.setTemplateProperties(nsp3);
		nestedTemplates.add(s3);
		
		SingleUserDefinedTaskTemplate s4 = new SingleUserDefinedTaskTemplate("genericworktask");
		s4.setName("Generic Worktask");
		s4.setDescription("This is a description for the fourth nested template");
		List<ClassProperty<Object>> nsp4 = new ArrayList<>();
		nsp4.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("name")));
		nsp4.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("workshift")));
		nsp4.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("offered_rewards")));
		nsp4.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("number_of_volunteers")));
		nsp4.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("location")));
		nsp4.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("starting_date")));
		nsp4.add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("end_date")));
		
		i = 0;
		for (ClassProperty<Object> prop : nsp4) {
			prop.setPosition(i);
			i++;
		}
		
		s4.setTemplateProperties(nsp4);
		nestedTemplates.add(s4);
		
		i = 0;
		for (UserDefinedTaskTemplate t : nestedTemplates) {
			t.setOrder(i);
			i++;
		}
		
		nested.setTemplates(nestedTemplates);
		
		return nested;
	}
	
//	public SingleUserDefinedTaskTemplate createGenericDienstTemplate() {
//		SingleUserDefinedTaskTemplate t = new SingleUserDefinedTaskTemplate("generic_dienst");
//		t.setName("Generic 'Dienst'");
//		t.setDescription("According to SyBOS (Portal > Administration > Leistungen > Dienstvorlagen -> New");
//		
//		t.setProperties(new ArrayList<>());
//		t.getProperties().add(propertyRepository.findOne("planning_type"));
//		t.getProperties().add(propertyRepository.findOne("description"));
//		t.getProperties().add(propertyRepository.findOne("department"));
//		t.getProperties().add(propertyRepository.findOne("area_of_expertise"));
//		t.getProperties().add(propertyRepository.findOne("points"));
//		t.getProperties().add(propertyRepository.findOne("address"));
//		t.getProperties().add(propertyRepository.findOne("paid"));
//		t.getProperties().add(propertyRepository.findOne("comments"));
//		
//		int i = 0;
//		for (Property prop : t.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		return t;
//	
//	}
	
	public SingleUserDefinedTaskTemplate createGenericTaskTemplate() {
		SingleUserDefinedTaskTemplate t = new SingleUserDefinedTaskTemplate("generic_task");
		t.setName("Simple Generic Task");
		t.setDescription("How a generic Task could look like");
		
		t.setTemplateProperties(new ArrayList<ClassProperty<Object>>());
		t.getTemplateProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("name")));
		t.getTemplateProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("description")));
		t.getTemplateProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aquireable_competences")));
//		t.getProperties().add(propertyRepository.findOne("comments"));
		
		int i = 0;
		for (ClassProperty<Object> prop : t.getTemplateProperties()) {
			prop.setPosition(i);
			i++;
		}
		
		return t;
	
	}
	
//	public SingleUserDefinedTaskTemplate createGenericEventTemplate() {
//		SingleUserDefinedTaskTemplate t = new SingleUserDefinedTaskTemplate("generic_event");
//		t.setName("Generic Event");
//		t.setDescription("According to SyBOS (Leistungen > Veranstaltungen --> select one)");
//		
//		t.setProperties(new ArrayList<>());
//		t.getProperties().add(propertyRepository.findOne("duration_time"));
//		t.getProperties().add(propertyRepository.findOne("area_of_expertise"));
//		t.getProperties().add(propertyRepository.findOne("description"));
//		t.getProperties().add(propertyRepository.findOne("department"));
//		t.getProperties().add(propertyRepository.findOne("address"));
//		t.getProperties().add(propertyRepository.findOne("content"));
//		t.getProperties().add(propertyRepository.findOne("prerequisites"));
//		t.getProperties().add(propertyRepository.findOne("comments"));
//		t.getProperties().add(propertyRepository.findOne("event_type"));
//		t.getProperties().add(propertyRepository.findOne("required_competences"));
//		t.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		
//		int i = 0;
//		for (Property prop : t.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		return t;
//	}
//	
//	public SingleUserDefinedTaskTemplate createGenericActivityTemplate() {
//		SingleUserDefinedTaskTemplate t = new SingleUserDefinedTaskTemplate("generic_activity");
//		t.setName("Generic Activity");
//		t.setDescription("According to SyBOS (Leistungen > Tätigkeiten --> select one)");
//		
//		
//		t.setProperties(new ArrayList<>());
//		t.getProperties().add(propertyRepository.findOne("address"));
//		t.getProperties().add(propertyRepository.findOne("department"));
//		t.getProperties().add(propertyRepository.findOne("activity_group"));
//		t.getProperties().add(propertyRepository.findOne("duration_time"));
//		t.getProperties().add(propertyRepository.findOne("vehicle_selection"));
//		t.getProperties().add(propertyRepository.findOne("comments"));
//		
//		int i = 0;
//		for (Property prop : t.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		return t;
//
//	}
//	
//	public SingleUserDefinedTaskTemplate createGenericTrainingTemplate() {
//		SingleUserDefinedTaskTemplate t = new SingleUserDefinedTaskTemplate("generic_training");
//		t.setName("Generic Training");
//		t.setDescription("According to SyBOS (Lehrgänge > Teilnahmen --> select one)");
//		
//		
//		t.setProperties(new ArrayList<>());
//		t.getProperties().add(propertyRepository.findOne("register_date"));
//		t.getProperties().add(propertyRepository.findOne("department"));
//		t.getProperties().add(propertyRepository.findOne("education_class"));
//		t.getProperties().add(propertyRepository.findOne("priority"));
//		t.getProperties().add(propertyRepository.findOne("required_competences"));
//		t.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		t.getProperties().add(propertyRepository.findOne("grade"));
//		t.getProperties().add(propertyRepository.findOne("comments"));
//		
//		int i = 0;
//		for (Property prop : t.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		return t;
//	
//	
//	}
//	
//	/**
//	 * Selected From Document Tasks_SYBOS_FireBrigade.docx
//	 * Stichworte / Tags aus dem Feuerwehrverwaltungssystem (syBOS)
//	 */
//	
//	public MultiUserDefinedTaskTemplate createTrainingTemplate() {
//		MultiUserDefinedTaskTemplate root = new MultiUserDefinedTaskTemplate("training");
//		root.setName("Training");
//		root.setDescription("Tasks for Trainer for a specific training session");
//		
//		root.setTemplates(new ArrayList<>());
//		SingleUserDefinedTaskTemplate t1 = new SingleUserDefinedTaskTemplate("preparation");
//		t1.setName("Preparation");
//		t1.setDescription("(Ausbildung: Vorbereitung) Prepare Training");
//
//		
//		t1.setProperties(new ArrayList<>());
//		t1.getProperties().add(propertyRepository.findOne("description"));
//		t1.getProperties().add(propertyRepository.findOne("education_class"));
//		t1.getProperties().add(propertyRepository.findOne("department"));
//		t1.getProperties().add(propertyRepository.findOne("required_competences"));
//		t1.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//
//		
//		int i = 0;
//		for (Property prop : t1.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t1);
//		
//		SingleUserDefinedTaskTemplate t2 = new SingleUserDefinedTaskTemplate("material");
//		t2.setName("Material");
//		t2.setDescription("(Ausbildung: Unterlagen) Prepare Material for training session");
//		
//		t2.setProperties(new ArrayList<>());
//		t2.getProperties().add(propertyRepository.findOne("education_class"));
//		t2.getProperties().add(propertyRepository.findOne("department"));
//		t2.getProperties().add(propertyRepository.findOne("required_competences"));
//		t2.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		t2.getProperties().add(propertyRepository.findOne("comments"));
//		
//		
//		i = 0;
//		for (Property prop : t2.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t2);
//		
//		SingleUserDefinedTaskTemplate t3 = new SingleUserDefinedTaskTemplate("conduct");
//		t3.setName("Conduct Training");
//		t3.setDescription("Conduct a training session");
//		
//		t3.setProperties(new ArrayList<>());
//		t3.getProperties().add(propertyRepository.findOne("duration_time"));
//		t3.getProperties().add(propertyRepository.findOne("address"));
//		t3.getProperties().add(propertyRepository.findOne("education_class"));
//		t3.getProperties().add(propertyRepository.findOne("department"));
//		t3.getProperties().add(propertyRepository.findOne("required_competences"));
//		t3.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		t3.getProperties().add(propertyRepository.findOne("comments"));
//		t3.getProperties().add(propertyRepository.findOne("points"));
//		t3.getProperties().add(propertyRepository.findOne("offered_rewards"));
//		
//		i = 0;
//		for (Property prop : t3.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t3);
//		
//		SingleUserDefinedTaskTemplate t4 = new SingleUserDefinedTaskTemplate("follow_up");
//		t4.setName("Training follow-up");
//		t4.setDescription("(Ausbildung: Nachbereitung) Perform post training follow-up stuff");
//		
//		t4.setProperties(new ArrayList<>());
//		t4.getProperties().add(propertyRepository.findOne("education_class"));
//		t4.getProperties().add(propertyRepository.findOne("required_competences"));
//		t4.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		t4.getProperties().add(propertyRepository.findOne("comments"));
//		
//		i = 0;
//		for (Property prop : t4.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t4);
//		
//		i = 0;
//		for (UserDefinedTaskTemplate template : root.getTemplates()) {
//			template.setOrder(i);
//			i++;
//		}
//		
//		return root;
//	}
//	
//	public MultiUserDefinedTaskTemplate createVehicleTemplate() {
//		MultiUserDefinedTaskTemplate root = new MultiUserDefinedTaskTemplate("vehicle_tasks");
//		root.setName("Vehicle Tasks");
//		root.setDescription("Regular Tasks involving a vehicle");
//		
//		root.setTemplates(new ArrayList<>());
//		SingleUserDefinedTaskTemplate t1 = new SingleUserDefinedTaskTemplate("maintenance");
//		t1.setName("Vehicle maintenance");
//		t1.setDescription("(Fahrzeug: Instandhaltung / Wartung) - perform maintenance");
//		
//		t1.setProperties(new ArrayList<>());
//		t1.getProperties().add(propertyRepository.findOne("description"));
//		t1.getProperties().add(propertyRepository.findOne("end_date"));
//		t1.getProperties().add(propertyRepository.findOne("urgent"));
//		t1.getProperties().add(propertyRepository.findOne("department"));
//		t1.getProperties().add(propertyRepository.findOne("number_of_volunteers"));
//		t1.getProperties().add(propertyRepository.findOne("vehicle_selection"));
//		t1.getProperties().add(propertyRepository.findOne("required_competences"));
//		t1.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		t1.getProperties().add(propertyRepository.findOne("comments"));
//		t1.getProperties().add(propertyRepository.findOne("points"));
//		t1.getProperties().add(propertyRepository.findOne("offered_rewards"));
//		
//		int i = 0;
//		for (Property prop : t1.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t1);
//		
//		SingleUserDefinedTaskTemplate t2 = new SingleUserDefinedTaskTemplate("move_the_vehicle");
//		t2.setName("Move the vehicle");
//		t2.setDescription("(Fahrzeug: Bewegungsfahrt) - perform a test drive");
//		
//		t2.setProperties(new ArrayList<>());
//		t2.getProperties().add(propertyRepository.findOne("description"));
//		t2.getProperties().add(propertyRepository.findOne("department"));
//		t2.getProperties().add(propertyRepository.findOne("vehicle_selection"));
//		t2.getProperties().add(propertyRepository.findOne("required_competences"));
//		t2.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		t2.getProperties().add(propertyRepository.findOne("points"));
//		t2.getProperties().add(propertyRepository.findOne("offered_rewards"));
//		
//		i = 0;
//		for (Property prop : t2.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t2);
//		
//		SingleUserDefinedTaskTemplate t3 = new SingleUserDefinedTaskTemplate("clean_vehicle");
//		t3.setName("Clean the vehicle");
//		t3.setDescription("(Fahrzeug: Reinigung)");
//		
//		t3.setProperties(new ArrayList<>());
//		t3.getProperties().add(propertyRepository.findOne("description"));
//		t3.getProperties().add(propertyRepository.findOne("department"));
//		t3.getProperties().add(propertyRepository.findOne("vehicle_selection"));
//		t3.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		t3.getProperties().add(propertyRepository.findOne("points"));
//		t3.getProperties().add(propertyRepository.findOne("offered_rewards"));
//
//		i = 0;
//		for (Property prop : t3.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t3);
//		
//		i = 0;
//		for (UserDefinedTaskTemplate template : root.getTemplates()) {
//			template.setOrder(i);
//			i++;
//		}
//		
//		return root;
//	}
//	
//	public MultiUserDefinedTaskTemplate createVolunteerCheckTemplate() {
//		MultiUserDefinedTaskTemplate root = new MultiUserDefinedTaskTemplate("volunteer_check");
//		root.setName("Volunteer-Checks");
//		root.setDescription("regular checks which have to be performed by every volunteer");
//		
//		root.setTemplates(new ArrayList<>());
//		SingleUserDefinedTaskTemplate t1 = new SingleUserDefinedTaskTemplate("breathing_performance");
//		t1.setName("Breathing-Protection Performance Test");
//		t1.setDescription("(Atemschutz-Leistungstest)");
//		
//		t1.setProperties(new ArrayList<>());
//		t1.getProperties().add(propertyRepository.findOne("description"));
//		t1.getProperties().add(propertyRepository.findOne("duration_time"));
//		t1.getProperties().add(propertyRepository.findOne("importancy"));
//		t1.getProperties().add(propertyRepository.findOne("priority"));
//		t1.getProperties().add(propertyRepository.findOne("promotion"));
//		t1.getProperties().add(propertyRepository.findOne("address"));
//		t1.getProperties().add(propertyRepository.findOne("department"));
//		t1.getProperties().add(propertyRepository.findOne("comments"));
//		t1.getProperties().add(propertyRepository.findOne("points"));
//		t1.getProperties().add(propertyRepository.findOne("offered_rewards"));
//		
//		int i = 0;
//		for (Property prop : t1.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t1);
//		
//		SingleUserDefinedTaskTemplate t2 = new SingleUserDefinedTaskTemplate("breathing_examination");
//		t2.setName("Breathing-Protection Examination");
//		t2.setDescription("(Atemschutz-Untersuchung)");
//		
//		t2.setProperties(new ArrayList<>());
//		t2.getProperties().add(propertyRepository.findOne("description"));
//		t2.getProperties().add(propertyRepository.findOne("duration_time"));
//		t2.getProperties().add(propertyRepository.findOne("importancy"));
//		t2.getProperties().add(propertyRepository.findOne("priority"));
//		t2.getProperties().add(propertyRepository.findOne("promotion"));
//		t2.getProperties().add(propertyRepository.findOne("address"));
//		t2.getProperties().add(propertyRepository.findOne("department"));
//		t2.getProperties().add(propertyRepository.findOne("number_of_volunteers"));
//		t2.getProperties().add(propertyRepository.findOne("comments"));
//		t2.getProperties().add(propertyRepository.findOne("points"));
//		t2.getProperties().add(propertyRepository.findOne("offered_rewards"));
//		
//		i = 0;
//		for (Property prop : t2.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//		
//		root.getTemplates().add(t2);
//		
//		SingleUserDefinedTaskTemplate t3 = new SingleUserDefinedTaskTemplate("first_aid_course");
//		t3.setName("First Aid Course");
//		t3.setDescription("(Erste-Hilfe-Kurs)");
//		
//		t3.setProperties(new ArrayList<>());
//		t3.getProperties().add(propertyRepository.findOne("description"));
//		t3.getProperties().add(propertyRepository.findOne("duration_time"));
//		t3.getProperties().add(propertyRepository.findOne("promotion"));
//		t3.getProperties().add(propertyRepository.findOne("address"));
//		t3.getProperties().add(propertyRepository.findOne("department"));
//		t3.getProperties().add(propertyRepository.findOne("number_of_volunteers"));
//		t3.getProperties().add(propertyRepository.findOne("comments"));
//		t3.getProperties().add(propertyRepository.findOne("points"));
//		t3.getProperties().add(propertyRepository.findOne("offered_rewards"));
//		t3.getProperties().add(propertyRepository.findOne("aquireable_competences"));
//		
//		i = 0;
//		for (Property prop : t3.getProperties()) {
//			prop.setOrder(i);
//			i++;
//		}
//
//		root.getTemplates().add(t3);
//		
//		SingleUserDefinedTaskTemplate t4 = new SingleUserDefinedTaskTemplate("vaccination");
//		t4.setName("Vaccination");
//		t4.setDescription("(Impfung)");
//
//		root.getTemplates().add(t4);
//		
//		SingleUserDefinedTaskTemplate t5 = new SingleUserDefinedTaskTemplate("compressor");
//		t5.setName("Yearly Instruction: Air-Compressor");
//		t5.setDescription("(Jährliche Unterweisung: Atemluft-Kompressor)");
//
//		root.getTemplates().add(t5);
//		
//		SingleUserDefinedTaskTemplate t6 = new SingleUserDefinedTaskTemplate("crane");
//		t6.setName("Yearly Instruction: Crane");
//		t6.setDescription("(Jährliche Unterweisung: Kran)");
//		root.getTemplates().add(t6);
//		
//		SingleUserDefinedTaskTemplate t7 = new SingleUserDefinedTaskTemplate("stapler");
//		t7.setName("Yearly Instruction: Stapler");
//		t7.setDescription("(Jährliche Unterweisung: Stapler)");
//		root.getTemplates().add(t7);
//		
//		i = 0;
//		for (UserDefinedTaskTemplate template : root.getTemplates()) {
//			template.setOrder(i);
//			i++;
//		}
//		
//		return root;
//
//	}
//	

	
	
	
	
	
}
