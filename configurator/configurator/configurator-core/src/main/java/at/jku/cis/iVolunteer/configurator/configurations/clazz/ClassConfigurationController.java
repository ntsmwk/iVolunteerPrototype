package at.jku.cis.iVolunteer.configurator.configurations.clazz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.configurator._mapper.property.TreePropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.configurator.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipController;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.configurator.model._httprequests.SaveClassConfigurationRequest;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfigurationDTO;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.collector.MatchingEntityMappingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.matching.MatchingEntityMappings;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipType;

@RestController
public class ClassConfigurationController {

	@Autowired private ClassConfigurationRepository classConfigurationRepository;
	@Autowired private CollectionService collectionService;

	@Autowired private MatchingEntityMappingConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private RelationshipController relationshipController;
	@Autowired private FlatPropertyDefinitionRepository flatPropertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper flatPropertyDefinitionToClassPropertyMapper;
	@Autowired private TreePropertyDefinitionRepository treePropertyDefinitionRepository;
	@Autowired private TreePropertyDefinitionToClassPropertyMapper treePropertyDefinitionToClassPropertyMapper;

	@GetMapping("class-configuration/all")
	List<ClassConfiguration> getAllClassConfigurations() {
		return classConfigurationRepository.findAll();
	}

	@GetMapping("class-configuration/all/tenant/{tenantId}")
	List<ClassConfiguration> getAllClassConfigurations(@PathVariable("tenantId") String tenantId) {
		return classConfigurationRepository.findByTenantId(tenantId);
	}

	@GetMapping("class-configuration/{id}")
	public ClassConfiguration getClassConfigurationById(@PathVariable("id") String id) {
		if (id == null) {
			return null;
		}
		return classConfigurationRepository.findOne(id);
	}

	@GetMapping("class-configuration/by-name/{name}")
	public List<ClassConfiguration> getClassConfigurationByName(@PathVariable("name") String name) {
		return classConfigurationRepository.findByName(name);
	}

	@GetMapping("class-configuration/all-in-one/{id}")
	public ClassConfigurationDTO getAllForClassConfigurationInOne(@PathVariable("id") String id) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(id);

		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds()).forEach(classDefinitions::add);
		;

		List<Relationship> relationships = new ArrayList<>();
		relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);

		ClassConfigurationDTO dto = new ClassConfigurationDTO(classConfiguration, classDefinitions, relationships);
		return dto;
	}

	@PostMapping("class-configuration/new-empty")
	public ClassConfiguration createNewEmptyClassConfiguration(@RequestBody String[] params) {
		if (params.length != 2) {
			return null;
		}

		ClassConfiguration classConfiguration = new ClassConfiguration();
		classConfiguration.setName(params[0]);
		classConfiguration.setDescription(params[1]);
		classConfiguration.setTimestamp(new Date());

		return saveClassConfiguration(classConfiguration);
	}

	@PostMapping("class-configuration/new")
	public ClassConfiguration createNewClassConfiguration(@RequestBody String[] params) {
		if (params.length != 3) {
			return null;
		}

		ClassConfiguration newClassConfiguration = createAndSaveNewClassConfiguration(params[0], params[1], params[2],
				null);
		return newClassConfiguration;
	}

	@PutMapping("class-configuration/save-all-in-one")
	public ClassConfiguration saveEverything(@RequestBody SaveClassConfigurationRequest req) {
		this.relationshipController.addOrUpdateRelationships(req.getRelationships());
		this.classDefinitionRepository.save(req.getClassDefinitions());
		this.classDefinitionRepository.deleteByIdIn(req.getDeletedClassDefinitionIds());
		this.relationshipRepository.deleteByIdIn(req.getDeletedRelationshipIds());
		ClassConfiguration ret = this.saveClassConfiguration(req.getClassConfiguration());

		return ret;
	}

	@PutMapping("class-configuration/save")
	public ClassConfiguration saveClassConfiguration(@RequestBody ClassConfiguration updatedClassConfiguration) {

		updatedClassConfiguration.setTimestamp(new Date());

		ClassConfiguration classConfiguration = classConfigurationRepository.save(updatedClassConfiguration);

		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(updatedClassConfiguration.getClassDefinitionIds())
				.forEach(classDefinitions::add);

		if (classDefinitions != null) {
			classDefinitions = updateClassDefinitions(classDefinitions, classConfiguration);
		}

		classDefinitionRepository.save(classDefinitions);

		// Build MatchingCollector
		MatchingEntityMappings mappings = collectionService
				.collectAllClassDefinitionsWithPropertiesAsMatchingEntityMappings(classConfiguration.getId());

		MatchingEntityMappingConfiguration matchingCollectorConfiguration = new MatchingEntityMappingConfiguration();
		matchingCollectorConfiguration.setId(classConfiguration.getId());
		matchingCollectorConfiguration.setClassConfigurationId(classConfiguration.getId());
		matchingCollectorConfiguration.setMappings(mappings);

		matchingCollectorConfigurationRepository.save(matchingCollectorConfiguration);
		return classConfiguration;
	}

	private List<ClassDefinition> updateClassDefinitions(List<ClassDefinition> classDefinitions,
			ClassConfiguration classConfiguration) {
		for (ClassDefinition cd : classDefinitions) {
			cd.setConfigurationId(classConfiguration.getId());
		}

		List<Relationship> relationships = new ArrayList<>();
		relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);

		collectionService.assignLevelsToClassDefinitions(classDefinitions, relationships);

		return classDefinitions;
	}

	@PutMapping("class-configuration/{id}/save-meta")
	public ClassConfiguration saveClassConfigurationMeta(@RequestBody String[] params, @PathVariable String id) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(id);

		if (params.length != 2) {
			return null;
		}

		classConfiguration.setName(params[0]);
		classConfiguration.setDescription(params[1]);

		return classConfigurationRepository.save(classConfiguration);
	}

	@DeleteMapping("class-configuration/{id}/delete")
	public void deleteClassConfiguration(@PathVariable("id") String id) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(id);

		classConfiguration.getClassDefinitionIds().forEach(classDefinitionRepository::delete);
		classConfiguration.getRelationshipIds().forEach(relationshipRepository::delete);

		classConfigurationRepository.delete(id);
	}

	@PutMapping("class-configuration/delete-multiple")
	public List<ClassConfiguration> deleteMultipleClassConfigurations(@RequestBody List<String> ids) {
		ids.forEach(this::deleteClassConfiguration);
		return this.classConfigurationRepository.findAll();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ClassConfiguration createAndSaveNewClassConfiguration(String tenantId, String configuratorName,
			String description, String configuratorId) {

		List<ClassDefinition> classDefinitions = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();

		List<FlatPropertyDefinition<Object>> flatProperties = this.flatPropertyDefinitionRepository
				.getByTenantId(tenantId);
		List<TreePropertyDefinition> treeProperties = this.treePropertyDefinitionRepository.findByTenantId(tenantId);

		ClassDefinition fwPassEintrag = new ClassDefinition();
		fwPassEintrag.setId(new ObjectId().toHexString());
		fwPassEintrag.setTenantId(tenantId);
		fwPassEintrag.setName("Freiwilligenpass-\nEintrag");
		fwPassEintrag.setRoot(true);
		fwPassEintrag.setClassArchetype(ClassArchetype.ROOT);
		fwPassEintrag.setWriteProtected(true);
		fwPassEintrag.setCollector(true);
		fwPassEintrag.setProperties(new ArrayList<ClassProperty<Object>>());
		fwPassEintrag.setLevel(0);

		FlatPropertyDefinition idProperty = flatProperties.stream().filter(p -> p.getName().equals("ID")).findFirst()
				.get();
		fwPassEintrag.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(idProperty));

		FlatPropertyDefinition nameProperty = flatProperties.stream().filter(p -> p.getName().equals("Name"))
				.findFirst().get();
		fwPassEintrag.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(nameProperty));

		// FlatPropertyDefinition evidenzProperty = properties.stream().filter(p ->
		// p.getName().equals("evidenz")).findFirst()
		// .get();
		// fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(evidenzProperty));

		FlatPropertyDefinition imageLinkProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Description")).findFirst().get();
		fwPassEintrag.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(imageLinkProperty));
		//
		// FlatPropertyDefinition descriptionProperty = properties.stream().filter(p ->
		// p.getName().equals("Image Link")).findFirst().get();
		// fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(descriptionProperty));
		//
		// FlatPropertyDefinition expiredProperty = properties.stream().filter(p ->
		// p.getName().equals("Expired")).findFirst().get();
		// fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(expiredProperty));

		classDefinitions.add(fwPassEintrag);

		ClassDefinition task = new ClassDefinition();
		task.setId(new ObjectId().toHexString());
		task.setTenantId(tenantId);
		task.setName("Tätigkeit");
		task.setClassArchetype(ClassArchetype.TASK);
		task.setWriteProtected(true);
		task.setProperties(new ArrayList<>());
		task.setLevel(0);

		FlatPropertyDefinition dateFromProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Starting Date")).findFirst().get();
		task.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(dateFromProperty));

		FlatPropertyDefinition dateToProperty = flatProperties.stream().filter(p -> p.getName().equals("End Date"))
				.findFirst().get();
		task.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(dateToProperty));

		FlatPropertyDefinition locationProperty = flatProperties.stream().filter(p -> p.getName().equals("Location"))
				.findFirst().get();
		task.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(locationProperty));

		FlatPropertyDefinition bereichProperty = flatProperties.stream().filter(p -> p.getName().equals("Bereich"))
				.findFirst().get();
		task.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(bereichProperty));

		classDefinitions.add(task);

		Inheritance r1 = new Inheritance();
		r1.setRelationshipType(RelationshipType.INHERITANCE);
		r1.setTarget(task.getId());
		r1.setSource(fwPassEintrag.getId());

		relationships.add(r1);

		ClassDefinition competence = new ClassDefinition();
		competence.setId(new ObjectId().toHexString());
		competence.setTenantId(tenantId);
		competence.setName("Kompetenz");
		competence.setClassArchetype(ClassArchetype.COMPETENCE);
		competence.setWriteProtected(true);
		competence.setProperties(new ArrayList<>());
		competence.setLevel(0);

		classDefinitions.add(competence);

		Inheritance r2 = new Inheritance();
		r2.setRelationshipType(RelationshipType.INHERITANCE);
		r2.setTarget(competence.getId());
		r2.setSource(fwPassEintrag.getId());

		relationships.add(r2);

		ClassDefinition achievement = new ClassDefinition();
		achievement.setId(new ObjectId().toHexString());
		achievement.setTenantId(tenantId);
		achievement.setName("Verdienst");
		achievement.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievement.setWriteProtected(true);
		achievement.setProperties(new ArrayList<>());
		achievement.setLevel(0);

		classDefinitions.add(achievement);

		Inheritance r3 = new Inheritance();
		r3.setRelationshipType(RelationshipType.INHERITANCE);
		r3.setTarget(achievement.getId());
		r3.setSource(fwPassEintrag.getId());

		relationships.add(r3);

		ClassDefinition function = new ClassDefinition();
		function.setId(new ObjectId().toHexString());
		function.setTenantId(tenantId);
		function.setName("Funktion");
		function.setClassArchetype(ClassArchetype.FUNCTION);
		function.setWriteProtected(true);
		function.setProperties(new ArrayList<>());
		function.setLevel(0);

		classDefinitions.add(function);

		Inheritance r4 = new Inheritance();
		r4.setRelationshipType(RelationshipType.INHERITANCE);
		r4.setTarget(function.getId());
		r4.setSource(fwPassEintrag.getId());

		relationships.add(r4);

		///////////////// Philipp Zeug//////////////////////////
		ClassDefinition myTask = new ClassDefinition();
		myTask.setId(new ObjectId().toHexString());
		myTask.setTenantId(tenantId);
		myTask.setName("myTask");
		myTask.setClassArchetype(ClassArchetype.TASK);
		myTask.setProperties(new ArrayList<>());

		TreePropertyDefinition taskType = treeProperties.stream().filter(p -> p.getName().equals("TaskType"))
				.findFirst().orElse(null);
		if (taskType != null) {
			myTask.getProperties().add(treePropertyDefinitionToClassPropertyMapper.toTarget(taskType));
		}

		FlatPropertyDefinition rank = flatProperties.stream().filter(p -> p.getName().equals("Rank")).findFirst().get();
		myTask.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(rank));

		FlatPropertyDefinition duration = flatProperties.stream().filter(p -> p.getName().equals("Duration"))
				.findFirst().get();
		myTask.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(duration));

		classDefinitions.add(myTask);

		Inheritance r5 = new Inheritance();
		r5.setRelationshipType(RelationshipType.INHERITANCE);
		r5.setTarget(myTask.getId());
		r5.setSource(task.getId());

		relationships.add(r5);

		// Test Task for Micha

		ClassDefinition testTask = new ClassDefinition();
		testTask.setId(new ObjectId().toHexString());
		testTask.setTenantId(tenantId);
		testTask.setName("xNet Test Task");
		testTask.setClassArchetype(ClassArchetype.TASK);
		testTask.setWriteProtected(true);
		testTask.setProperties(new ArrayList<>());
		testTask.setLevel(0);

		FlatPropertyDefinition testTextProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Test TextProperty")).findFirst().get();
		testTask.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(testTextProperty));

		FlatPropertyDefinition testLongTextProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Test LongTextProperty")).findFirst().get();
		testTask.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(testLongTextProperty));

		FlatPropertyDefinition testWholeNumberProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Test WholeNumberProperty")).findFirst().get();
		testTask.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(testWholeNumberProperty));

		FlatPropertyDefinition testFloatNumberProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Test FloatNumberProperty")).findFirst().get();
		testTask.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(testFloatNumberProperty));

		FlatPropertyDefinition testBooleanProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Test BooleanProperty")).findFirst().get();
		testTask.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(testBooleanProperty));

		FlatPropertyDefinition testDateProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Test DateProperty")).findFirst().get();
		testTask.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(testDateProperty));

		classDefinitions.add(testTask);

		Inheritance r6 = new Inheritance();
		r6.setRelationshipType(RelationshipType.INHERITANCE);
		r6.setTarget(testTask.getId());
		r6.setSource(myTask.getId());

		relationships.add(r6);

		// ------------------------------ //

		ClassConfiguration configurator = new ClassConfiguration();
		configurator.setTimestamp(new Date());
		configurator.setTenantId(tenantId);
		configurator.setId(configuratorId);
		configurator.setName(configuratorName);
		configurator.setDescription(description);
		configurator.setClassDefinitionIds(new ArrayList<>());
		configurator.setRelationshipIds(new ArrayList<>());

		for (ClassDefinition cd : classDefinitions) {
			cd.setConfigurationId(configurator.getId());
			this.classDefinitionRepository.save(cd);
			configurator.getClassDefinitionIds().add(cd.getId());
		}

		for (Relationship r : relationships) {
			this.relationshipRepository.save(r);
			configurator.getRelationshipIds().add(r.getId());
		}

		return saveClassConfiguration(configurator);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ClassConfiguration createAndSaveHaubenofen(String tenantId) {

		List<FlatPropertyDefinition<Object>> flatProperties = this.flatPropertyDefinitionRepository
				.getByTenantId(tenantId);

		List<ClassDefinition> classDefinitionsOfen = new ArrayList<>();
		List<Relationship> relationshipsOfen = new ArrayList<>();

		// Maschine
		ClassDefinition machine = new ClassDefinition();
		machine.setId(new ObjectId().toHexString());
		machine.setTenantId(tenantId);
		machine.setName("Machine");
		machine.setClassArchetype(ClassArchetype.ROOT);
		machine.setProperties(new ArrayList<>());
		machine.setLevel(0);
		classDefinitionsOfen.add(machine);

		//
		// Haubenofen
		ClassDefinition haubenofen = new ClassDefinition();
		haubenofen.setId(new ObjectId().toHexString());
		haubenofen.setTenantId(tenantId);
		haubenofen.setName("Haubenofen");
		haubenofen.setClassArchetype(ClassArchetype.ROOT);
		haubenofen.setProperties(new ArrayList<>());
		haubenofen.setLevel(0); // TODO

		FlatPropertyDefinition produkttypeProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Produkttyp")).findFirst().get();
		haubenofen.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(produkttypeProperty));
		FlatPropertyDefinition ofenhoeheProperty = flatProperties.stream().filter(p -> p.getName().equals("Ofenhöhe"))
				.findFirst().get();
		haubenofen.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(ofenhoeheProperty));
		FlatPropertyDefinition kapazitaetProperty = flatProperties.stream().filter(p -> p.getName().equals("Kapazität"))
				.findFirst().get();
		haubenofen.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(kapazitaetProperty));
		classDefinitionsOfen.add(haubenofen);

		Inheritance assoz1 = new Inheritance();
		assoz1.setRelationshipType(RelationshipType.INHERITANCE);
		assoz1.setTarget(haubenofen.getId());
		assoz1.setSource(machine.getId());
		relationshipsOfen.add(assoz1);

		ClassDefinition band = new ClassDefinition();
		band.setId(new ObjectId().toHexString());
		band.setTenantId(tenantId);
		band.setName("Band");
		band.setClassArchetype(ClassArchetype.FLEXPROD);
		band.setProperties(new ArrayList<>());
		band.setLevel(0); // TODO

		FlatPropertyDefinition durchmesserDornProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Durchmesser Dorn")).findFirst().get();
		band.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(durchmesserDornProperty));
		FlatPropertyDefinition innendurchmesserOfenProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Innendurchmesser Ofen")).findFirst().get();
		band.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(innendurchmesserOfenProperty));
		classDefinitionsOfen.add(band);

		Association assoz2 = new Association();
		assoz2.setRelationshipType(RelationshipType.ASSOCIATION);
		assoz2.setTarget(band.getId());
		assoz2.setSource(haubenofen.getId());
		relationshipsOfen.add(assoz2);

		ClassDefinition draht = new ClassDefinition();
		draht.setId(new ObjectId().toHexString());
		draht.setTenantId(tenantId);
		draht.setName("Draht");
		draht.setClassArchetype(ClassArchetype.FLEXPROD);
		draht.setProperties(new ArrayList<>());
		draht.setLevel(0); // TODO

		FlatPropertyDefinition durchmesserKronenstockProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Durchmesser Kronenstock")).findFirst().get();
		draht.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(durchmesserKronenstockProperty));
		FlatPropertyDefinition maxDurchmesserBundProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Maximaldurchmesser Bund")).findFirst().get();
		draht.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(maxDurchmesserBundProperty));
		classDefinitionsOfen.add(draht);

		Association assoz3 = new Association();
		assoz3.setRelationshipType(RelationshipType.ASSOCIATION);
		assoz3.setTarget(draht.getId());
		assoz3.setSource(haubenofen.getId());
		relationshipsOfen.add(assoz3);

		ClassDefinition gluehreise = new ClassDefinition();
		gluehreise.setId(new ObjectId().toHexString());
		gluehreise.setTenantId(tenantId);
		gluehreise.setName("Glühreise");
		gluehreise.setClassArchetype(ClassArchetype.FLEXPROD);
		gluehreise.setProperties(new ArrayList<>());
		gluehreise.setLevel(0); // TODO

		FlatPropertyDefinition maxGluehtempProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Max. Glühtemperatur")).findFirst().get();
		gluehreise.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(maxGluehtempProperty));
		FlatPropertyDefinition tempHomogenitaetProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Temperaturhomogenität")).findFirst().get();
		gluehreise.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(tempHomogenitaetProperty));
		FlatPropertyDefinition aufheizrateProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Aufheizrate")).findFirst().get();
		gluehreise.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(aufheizrateProperty));
		FlatPropertyDefinition abkuehlrateProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Abkühlrate")).findFirst().get();
		gluehreise.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(abkuehlrateProperty));
		FlatPropertyDefinition gluehprogrammProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Glühprogramm /-reise verfügbar?")).findFirst().get();
		gluehreise.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(gluehprogrammProperty));
		classDefinitionsOfen.add(gluehreise);

		Association assoz4 = new Association();
		assoz4.setRelationshipType(RelationshipType.ASSOCIATION);
		assoz4.setTarget(gluehreise.getId());
		assoz4.setSource(haubenofen.getId());
		relationshipsOfen.add(assoz4);

		ClassDefinition schutzgas = new ClassDefinition();
		schutzgas.setId(new ObjectId().toHexString());
		schutzgas.setTenantId(tenantId);
		schutzgas.setName("Schutzgas");
		schutzgas.setClassArchetype(ClassArchetype.FLEXPROD);
		schutzgas.setProperties(new ArrayList<>());
		schutzgas.setLevel(0); // TODO

		FlatPropertyDefinition maxAnteilH2Property = flatProperties.stream()
				.filter(p -> p.getName().equals("Maximaler Anteil H2")).findFirst().get();
		schutzgas.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(maxAnteilH2Property));
		classDefinitionsOfen.add(schutzgas);

		Association assoz5 = new Association();
		assoz5.setRelationshipType(RelationshipType.ASSOCIATION);
		assoz5.setTarget(schutzgas.getId());
		assoz5.setSource(haubenofen.getId());
		relationshipsOfen.add(assoz5);

		ClassConfiguration configuratorOfen = new ClassConfiguration();
		configuratorOfen.setTimestamp(new Date());
		configuratorOfen.setTenantId(tenantId);
		configuratorOfen.setId(null);
		configuratorOfen.setName("Haubenofen");
		configuratorOfen.setDescription("");
		configuratorOfen.setClassDefinitionIds(new ArrayList<>());
		configuratorOfen.setRelationshipIds(new ArrayList<>());

		for (ClassDefinition cd : classDefinitionsOfen) {
			cd.setConfigurationId(configuratorOfen.getId());
			this.classDefinitionRepository.save(cd);
			configuratorOfen.getClassDefinitionIds().add(cd.getId());
		}

		for (Relationship r : relationshipsOfen) {
			this.relationshipRepository.save(r);
			configuratorOfen.getRelationshipIds().add(r.getId());
		}
		ClassConfiguration cc = saveClassConfiguration(configuratorOfen);
		return cc;
	}

	// ------------

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ClassConfiguration createAndSaveRFQ(String tenantId) {
		List<FlatPropertyDefinition<Object>> flatProperties = this.flatPropertyDefinitionRepository
				.getByTenantId(tenantId);

		List<ClassDefinition> classDefinitionsRfq = new ArrayList<>();
		List<Relationship> relationshipsRfq = new ArrayList<>();

		// -----------------------------------
		// Draht-Auftrag
		ClassDefinition rfq = new ClassDefinition();
		rfq.setId(new ObjectId().toHexString());
		rfq.setTenantId(tenantId);
		rfq.setName("RFQ");
		rfq.setClassArchetype(ClassArchetype.ROOT);
		rfq.setProperties(new ArrayList<>());
		rfq.setLevel(0);
		classDefinitionsRfq.add(rfq);

		ClassDefinition drahtAuftrag = new ClassDefinition();
		drahtAuftrag.setId(new ObjectId().toHexString());
		drahtAuftrag.setTenantId(tenantId);
		drahtAuftrag.setName("Draht-Auftrag");
		drahtAuftrag.setClassArchetype(ClassArchetype.ROOT);
		drahtAuftrag.setProperties(new ArrayList<>());
		drahtAuftrag.setLevel(0); // TODO
		classDefinitionsRfq.add(drahtAuftrag);

		Inheritance assoz6 = new Inheritance();
		assoz6.setRelationshipType(RelationshipType.INHERITANCE);
		assoz6.setTarget(drahtAuftrag.getId());
		assoz6.setSource(rfq.getId());
		relationshipsRfq.add(assoz6);

		ClassDefinition auftragsdaten = new ClassDefinition();
		auftragsdaten.setId(new ObjectId().toHexString());
		auftragsdaten.setTenantId(tenantId);
		auftragsdaten.setName("Auftragsdaten");
		auftragsdaten.setClassArchetype(ClassArchetype.FLEXPROD);
		auftragsdaten.setProperties(new ArrayList<>());
		auftragsdaten.setLevel(0); // TODO

		FlatPropertyDefinition titelProperty = flatProperties.stream().filter(p -> p.getName().equals("Titel"))
				.findFirst().get();
		auftragsdaten.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(titelProperty));
		FlatPropertyDefinition produkttypProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Produkttyp")).findFirst().get();
		auftragsdaten.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(produkttypProperty));
		FlatPropertyDefinition mengeProperty = flatProperties.stream().filter(p -> p.getName().equals("Menge"))
				.findFirst().get();
		auftragsdaten.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(mengeProperty));
		FlatPropertyDefinition minimaleMengeProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("minimale Menge")).findFirst().get();
		auftragsdaten.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(minimaleMengeProperty));
		FlatPropertyDefinition lieferdatumProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Lieferdatum (spätestens)")).findFirst().get();
		auftragsdaten.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(lieferdatumProperty));
		FlatPropertyDefinition werkstoffProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Werkstoff bereitgestellt")).findFirst().get();
		auftragsdaten.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(werkstoffProperty));
		FlatPropertyDefinition allgBeschreibungProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("allgemeine Beschreibung / Zusatzinformationen")).findFirst().get();
		auftragsdaten.getProperties()
				.add(flatPropertyDefinitionToClassPropertyMapper.toTarget(allgBeschreibungProperty));
		classDefinitionsRfq.add(auftragsdaten);

		Association assoz7 = new Association();
		assoz7.setRelationshipType(RelationshipType.ASSOCIATION);
		assoz7.setTarget(auftragsdaten.getId());
		assoz7.setSource(drahtAuftrag.getId());
		relationshipsRfq.add(assoz7);

		ClassDefinition produktdaten = new ClassDefinition();
		produktdaten.setId(new ObjectId().toHexString());
		produktdaten.setTenantId(tenantId);
		produktdaten.setName("Produktdaten");
		produktdaten.setClassArchetype(ClassArchetype.FLEXPROD);
		produktdaten.setProperties(new ArrayList<>());
		produktdaten.setLevel(0); // TODO

		FlatPropertyDefinition oberflaechenQalitaetProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Oberflächenqualität")).findFirst().get();
		produktdaten.getProperties()
				.add(flatPropertyDefinitionToClassPropertyMapper.toTarget(oberflaechenQalitaetProperty));
		FlatPropertyDefinition zusaetzlicheProduktinfosProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Zusätzliche Produkt- und Bearbeitungsinformationen")).findFirst()
				.get();
		produktdaten.getProperties()
				.add(flatPropertyDefinitionToClassPropertyMapper.toTarget(zusaetzlicheProduktinfosProperty));
		classDefinitionsRfq.add(produktdaten);

		Association assoz8 = new Association();
		assoz8.setRelationshipType(RelationshipType.ASSOCIATION);
		assoz8.setTarget(produktdaten.getId());
		assoz8.setSource(drahtAuftrag.getId());
		relationshipsRfq.add(assoz8);

		ClassDefinition logistik = new ClassDefinition();
		logistik.setId(new ObjectId().toHexString());
		logistik.setTenantId(tenantId);
		logistik.setName("Logistik");
		logistik.setClassArchetype(ClassArchetype.FLEXPROD);
		logistik.setProperties(new ArrayList<>());
		logistik.setLevel(0); // TODO

		FlatPropertyDefinition incotermsProperty = flatProperties.stream().filter(p -> p.getName().equals("Incoterms"))
				.findFirst().get();
		logistik.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(incotermsProperty));
		FlatPropertyDefinition lieferortProperty = flatProperties.stream().filter(p -> p.getName().equals("Lieferort"))
				.findFirst().get();
		logistik.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(lieferortProperty));
		FlatPropertyDefinition abholortProperty = flatProperties.stream().filter(p -> p.getName().equals("Abholort"))
				.findFirst().get();
		logistik.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(abholortProperty));
		FlatPropertyDefinition verpackungsvorgabenProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Verpackungsvorgaben")).findFirst().get();
		logistik.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(verpackungsvorgabenProperty));
		classDefinitionsRfq.add(logistik);

		Association assoz9 = new Association();
		assoz9.setRelationshipType(RelationshipType.ASSOCIATION);
		assoz9.setTarget(logistik.getId());
		assoz9.setSource(drahtAuftrag.getId());
		relationshipsRfq.add(assoz9);

		ClassDefinition bund = new ClassDefinition();
		bund.setId(new ObjectId().toHexString());
		bund.setTenantId(tenantId);
		bund.setName("Bund");
		bund.setClassArchetype(ClassArchetype.FLEXPROD);
		bund.setProperties(new ArrayList<>());
		bund.setLevel(0); // TODO

		FlatPropertyDefinition durchmesserInnenProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Durchmesser (innen)")).findFirst().get();
		bund.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(durchmesserInnenProperty));
		FlatPropertyDefinition durchmesserAussenProperty = flatProperties.stream()
				.filter(p -> p.getName().equals("Durchmesser (außen)")).findFirst().get();
		bund.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(durchmesserAussenProperty));
		FlatPropertyDefinition hoeheProperty = flatProperties.stream().filter(p -> p.getName().equals("Höhe"))
				.findFirst().get();
		bund.getProperties().add(flatPropertyDefinitionToClassPropertyMapper.toTarget(hoeheProperty));
		classDefinitionsRfq.add(bund);

		Association assoz10 = new Association();
		assoz10.setRelationshipType(RelationshipType.ASSOCIATION);
		assoz10.setTarget(bund.getId());
		assoz10.setSource(produktdaten.getId());
		relationshipsRfq.add(assoz10);

		ClassConfiguration configuratorRfq = new ClassConfiguration();
		configuratorRfq.setTimestamp(new Date());
		configuratorRfq.setTenantId(tenantId);
		configuratorRfq.setId(null);
		configuratorRfq.setName("RFQ");
		configuratorRfq.setDescription("");
		configuratorRfq.setClassDefinitionIds(new ArrayList<>());
		configuratorRfq.setRelationshipIds(new ArrayList<>());

		for (ClassDefinition cd : classDefinitionsRfq) {
			cd.setConfigurationId(configuratorRfq.getId());
			this.classDefinitionRepository.save(cd);
			configuratorRfq.getClassDefinitionIds().add(cd.getId());
		}

		for (Relationship r : relationshipsRfq) {
			this.relationshipRepository.save(r);
			configuratorRfq.getRelationshipIds().add(r.getId());
		}
		ClassConfiguration cc = saveClassConfiguration(configuratorRfq);
		return cc;

	}

//	private Tenant getFlexProdTenant() {
//		List<Tenant> tenants = new ArrayList<>();
//		tenants = coreTenantRestClient.getAllTenants();
//
//		return tenants.stream().filter(t -> t.getName().equals("FlexProd")).findFirst().orElse(null);
//	}

}
