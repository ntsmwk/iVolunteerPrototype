package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.fake.configuratorReset.ClassesAndRelationshipsToReset;
import at.jku.cis.iVolunteer.marketplace.fake.configuratorReset.ClassesAndRelationshipsToResetRepository;
import at.jku.cis.iVolunteer.marketplace.feedback.FeedbackRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;
import at.jku.cis.iVolunteer.marketplace.user.HelpSeekerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.feedback.Feedback;
import at.jku.cis.iVolunteer.model.feedback.FeedbackType;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Aggregation;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.rule.AttributeAggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.AttributeSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.ClassAggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.ClassSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.MappingOperatorType;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class InitializationService {

	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionsRepository;
	@Autowired private ClassConfigurationRepository configuratorRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private FinalizationService finalizationService;
	@Autowired private DerivationRuleRepository derivationRuleRepository;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private HelpSeekerRepository helpSeekerRepository;
	@Autowired private FeedbackRepository feedbackRepository;
	@Autowired private UserMappingRepository userMappingRepository;
	@Autowired private MatchingConfigurationRepository matchingConfiguratorRepository;

	@PostConstruct
	public void init() {
//		finalizationService.destroy(configuratorRepository, classDefinitionRepository, classInstanceRepository,
//				relationshipRepository, propertyDefinitionRepository, derivationRuleRepository);

//		if(environment.acceptsProfiles("dev")) {}
//		addTestConfigClasses();
//		addConfigurators();
//
//		addiVolunteerAPIClassDefinition();
//		addTestDerivationRule();
//		this.addTestClassInstances();

		this.addStandardPropertyDefinitions();
		this.addFlexProdConfigClassesConsumer();
		this.addFlexProdConfigClassesProducer();
		this.addFlexProdMachtingOperatorRelationshipStorage();
		

	}

	private void addStandardPropertyDefinitions() {
		StandardPropertyDefinitions sp = new StandardPropertyDefinitions();
		List<PropertyDefinition<Object>> props = sp.getAll();
//		List<PropertyDefinition<T>> props = sp.getAllFlexProdProperties();

		for (PropertyDefinition<Object> p : props) {
			if (!propertyDefinitionRepository.exists(p.getId())) {
				propertyDefinitionRepository.save(p);
			}
		}

	}

	private void addFlexProdConfigClassesProducer() {
		List<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
		List<Relationship> relationships = new ArrayList<Relationship>();

		ClassDefinition technischeBeschreibung = new ClassDefinition();
		technischeBeschreibung.setId("technische_beschreibung_producer");
		technischeBeschreibung.setName("Technische\nBeschreibung");
		technischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		technischeBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD_COLLECTOR);
		
		classDefinitions.add(technischeBeschreibung);

		ClassDefinition ofen = new ClassDefinition();
		ofen.setId("ofen_producer");
		ofen.setName("Ofen");
		ofen.setProperties(new ArrayList<ClassProperty<Object>>());
		ofen.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(ofen);

		Aggregation i1 = new Aggregation(technischeBeschreibung.getId(), ofen.getId(), technischeBeschreibung.getId());
		i1.setId("i1_producer");
		relationships.add(i1);

		ClassDefinition ofenTechnischeEigenschaften = new ClassDefinition();
		ofenTechnischeEigenschaften.setId("ofenTechnischeEigenschaften_producer");
		ofenTechnischeEigenschaften.setName("Technische\nEigenschaften");
		ofenTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenTechnischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(ofenTechnischeEigenschaften);

		Aggregation i11 = new Aggregation(ofen.getId(), ofenTechnischeEigenschaften.getId(), ofen.getId());
		i11.setId("i11_producer");

		relationships.add(i11);

		ClassDefinition oteAllgemein = new ClassDefinition();
		oteAllgemein.setId("oteAllgemein_producer");
		oteAllgemein.setName("Allgemein");
		oteAllgemein.setProperties(new ArrayList<ClassProperty<Object>>());
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxgluehtemperatur")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verfuegbaresschutzgas")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bauart")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("temperaturhomogenitaet")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalztesmaterialzulaessig")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalztesmaterialzulaessig")));
		oteAllgemein.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteAllgemein);

		Aggregation i111 = new Aggregation(ofenTechnischeEigenschaften.getId(), oteAllgemein.getId(),
				ofenTechnischeEigenschaften.getId());
		i111.setId("i111_producer");
		relationships.add(i111);

		ClassDefinition oteMoeglicheVorbehandlung = new ClassDefinition();
		oteMoeglicheVorbehandlung.setId("oteMoeglicheVorbehandlung_producer");
		oteMoeglicheVorbehandlung.setName("Mögliche\nVorbehandlung");
		oteMoeglicheVorbehandlung.setProperties(new ArrayList<ClassProperty<Object>>());
		oteMoeglicheVorbehandlung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bundentfetten")));
		oteMoeglicheVorbehandlung.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteMoeglicheVorbehandlung);

		Aggregation i112 = new Aggregation(ofenTechnischeEigenschaften.getId(), oteMoeglicheVorbehandlung.getId(),
				ofenTechnischeEigenschaften.getId());
		i112.setId("i112_producer");
		relationships.add(i112);

		ClassDefinition oteChargierhilfe = new ClassDefinition();
		oteChargierhilfe.setId("oteChargierhilfe_producer");
		oteChargierhilfe.setName("Chargierhilfe");
		oteChargierhilfe.setProperties(new ArrayList<ClassProperty<Object>>());
		oteChargierhilfe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("chargierhilfe")));
		
		oteChargierhilfe.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteChargierhilfe);

		Aggregation i113 = new Aggregation(ofenTechnischeEigenschaften.getId(), oteChargierhilfe.getId(),
				ofenTechnischeEigenschaften.getId());
		i113.setId("i113_producer");
		relationships.add(i113);

		ClassDefinition otecKonvektoren = new ClassDefinition();
		otecKonvektoren.setId("otecKonvektoren_producer");
		otecKonvektoren.setName("Konvektoren");
		otecKonvektoren.setProperties(new ArrayList<ClassProperty<Object>>());
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecKonvektoren.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecKonvektoren);

		Aggregation i1131 = new Aggregation(oteChargierhilfe.getId(), otecKonvektoren.getId(),
				oteChargierhilfe.getId());
		i1131.setId("i1131_producer");
		relationships.add(i1131);

		ClassDefinition otecTragerahmen = new ClassDefinition();
		otecTragerahmen.setId("otecTragerahmen_producer");
		otecTragerahmen.setName("Tragerahmen");
		otecTragerahmen.setProperties(new ArrayList<ClassProperty<Object>>());
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecTragerahmen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecTragerahmen);

		Aggregation i1132 = new Aggregation(oteChargierhilfe.getId(), otecTragerahmen.getId(),
				oteChargierhilfe.getId());
		i1132.setId("i1132_producer");
		relationships.add(i1132);

		ClassDefinition otecZwischenrahmen = new ClassDefinition();
		otecZwischenrahmen.setId("otecZwischenrahmen_producer");
		otecZwischenrahmen.setName("Zwischenrahmen");
		otecZwischenrahmen.setProperties(new ArrayList<ClassProperty<Object>>());
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecZwischenrahmen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecZwischenrahmen);

		Aggregation i1133 = new Aggregation(oteChargierhilfe.getId(), otecZwischenrahmen.getId(),
				oteChargierhilfe.getId());
		i1133.setId("i1133_producer");
		relationships.add(i1133);

		ClassDefinition otecKronenstoecke = new ClassDefinition();
		otecKronenstoecke.setId("otecKronenstoecke_producer");
		otecKronenstoecke.setName("Kronenstöcke");
		otecKronenstoecke.setProperties(new ArrayList<ClassProperty<Object>>());
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecKronenstoecke.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecKronenstoecke);

		Aggregation i1134 = new Aggregation(oteChargierhilfe.getId(), otecKronenstoecke.getId(),
				oteChargierhilfe.getId());
		i1134.setId("i1134_producer");
		relationships.add(i1134);

		ClassDefinition otecChargierkoerbe = new ClassDefinition();
		otecChargierkoerbe.setId("otecChargierkoerbe_producer");
		otecChargierkoerbe.setName("Chargierkörbe");
		otecChargierkoerbe.setProperties(new ArrayList<ClassProperty<Object>>());

		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecChargierkoerbe.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecChargierkoerbe);

		Aggregation i1135 = new Aggregation(oteChargierhilfe.getId(), otecChargierkoerbe.getId(),
				oteChargierhilfe.getId());
		i1135.setId("i1135_producer");
		relationships.add(i1135);

		ClassDefinition ofenBetrieblicheEigenschaften = new ClassDefinition();
		ofenBetrieblicheEigenschaften.setId("ofenBetrieblicheEigenschaften_producer");
		ofenBetrieblicheEigenschaften.setName("Betriebliche\nEigenschaften");
		ofenBetrieblicheEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gluehzeit")));
		ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("durchsatz")));
		ofenBetrieblicheEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenBetrieblicheEigenschaften);

		Aggregation i12 = new Aggregation(ofen.getId(), ofenBetrieblicheEigenschaften.getId(), ofen.getId());
		i12.setId("i12_producer");
		relationships.add(i12);

		ClassDefinition ofenGeometrischeEigenschaften = new ClassDefinition();
		ofenGeometrischeEigenschaften.setId("ofenGeometrischeEigenschaften_producer");
		ofenGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
		ofenGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenGeometrischeEigenschaften);

		Aggregation i13 = new Aggregation(ofen.getId(), ofenGeometrischeEigenschaften.getId(), ofen.getId());
		i13.setId("i13_producer");
		relationships.add(i13);

		ClassDefinition ogeBaugroesse = new ClassDefinition();
		ogeBaugroesse.setId("ogeBaugroesse_producer");
		ogeBaugroesse.setName("Baugröße");
		ogeBaugroesse.setProperties(new ArrayList<ClassProperty<Object>>());
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("moeglicheinnendurchmesser")));
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxaussendurchmesser")));
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxchargierhoehe")));
		ogeBaugroesse.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ogeBaugroesse);

		Aggregation i131 = new Aggregation(ofenGeometrischeEigenschaften.getId(), ogeBaugroesse.getId(),
				ofenGeometrischeEigenschaften.getId());
		i131.setId("i131_producer");
		relationships.add(i131);

		ClassDefinition ofenQualitativeEigenschaften = new ClassDefinition();
		ofenQualitativeEigenschaften.setId("ofenQualitativeEigenschaften_producer");
		ofenQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		ofenQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenQualitativeEigenschaften);

		Aggregation i14 = new Aggregation(ofen.getId(), ofenQualitativeEigenschaften.getId(), ofen.getId());
		i14.setId("i14_producer");
		relationships.add(i14);

		ClassDefinition oqeQualitätsnormen = new ClassDefinition();
		oqeQualitätsnormen.setId("oqeQualitätsnormen_producer");
		oqeQualitätsnormen.setName("Qualitätsnormen");
		oqeQualitätsnormen.setProperties(new ArrayList<ClassProperty<Object>>());
		oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("cqi9")));
		oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("tus")));
		oqeQualitätsnormen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oqeQualitätsnormen);

		Aggregation i141 = new Aggregation(ofenQualitativeEigenschaften.getId(), oqeQualitätsnormen.getId(),
				ofenQualitativeEigenschaften.getId());
		i141.setId("i141_producer");
		relationships.add(i141);

		ClassDefinition oqeWartungen = new ClassDefinition();
		oqeWartungen.setId("oqeWartungen_producer");
		oqeWartungen.setName("Wartungen");
		oqeWartungen.setProperties(new ArrayList<ClassProperty<Object>>());
		oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("letztewartung")));
		oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("wartungsintervall")));
		oqeWartungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		
		classDefinitions.add(oqeWartungen);

		Aggregation i142 = new Aggregation(ofenQualitativeEigenschaften.getId(), oqeWartungen.getId(),
				ofenQualitativeEigenschaften.getId());
		i142.setId("i142_producer");
		relationships.add(i142);

		// =================================================================================

		ClassDefinition input = new ClassDefinition();
		input.setId("input_producer");
		input.setName("Input");
		input.setProperties(new ArrayList<ClassProperty<Object>>());
		input.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(input);

		Aggregation i2 = new Aggregation(technischeBeschreibung.getId(), input.getId(), technischeBeschreibung.getId());
		i2.setId("i2_producer");
		relationships.add(i2);

		ClassDefinition inGeometrischeEigenschaften = new ClassDefinition();
		inGeometrischeEigenschaften.setId("inGeometrischeEigenschaften_producer");
		inGeometrischeEigenschaften.setName("Geometrsiche\nEigenschaften");
		inGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		inGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inGeometrischeEigenschaften);

		Aggregation i21 = new Aggregation(input.getId(), inGeometrischeEigenschaften.getId(), input.getId());
		i21.setId("i21_producer");
		relationships.add(i21);

		ClassDefinition ingeBundabmessungen = new ClassDefinition();
		ingeBundabmessungen.setId("ingeBundabmessungen_producer");
		ingeBundabmessungen.setName("Bundabmessungen");
		ingeBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));
		ingeBundabmessungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ingeBundabmessungen);

		Aggregation i211 = new Aggregation(inGeometrischeEigenschaften.getId(), ingeBundabmessungen.getId(),
				inGeometrischeEigenschaften.getId());
		i211.setId("i211_producer");
		relationships.add(i211);

		ClassDefinition inQualitativeEigenschaften = new ClassDefinition();
		inQualitativeEigenschaften.setId("inQualitativeEigenschaften_producer");
		inQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		inQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		inQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inQualitativeEigenschaften);

		Aggregation i22 = new Aggregation(input.getId(), inQualitativeEigenschaften.getId(), input.getId());
		i22.setId("i22_producer");
		relationships.add(i22);

		ClassDefinition inqeMaterialart = new ClassDefinition();
		inqeMaterialart.setId("inqeMaterialart_producer");
		inqeMaterialart.setName("Materialart");
		inqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
		inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
		inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));
		inqeMaterialart.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inqeMaterialart);

		Aggregation i221 = new Aggregation(inQualitativeEigenschaften.getId(), inqeMaterialart.getId(),
				inQualitativeEigenschaften.getId());
		i221.setId("i221_producer");
		relationships.add(i221);

		// =================================================================================

		ClassDefinition output = new ClassDefinition();
		output.setId("output_producer");
		output.setName("Output");
		output.setProperties(new ArrayList<ClassProperty<Object>>());
		output.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(output);

		Aggregation i3 = new Aggregation(technischeBeschreibung.getId(), output.getId(),
				technischeBeschreibung.getId());
		i3.setId("i3_producer");
		relationships.add(i3);

		ClassDefinition outTechnischeEigenschaften = new ClassDefinition();
		outTechnischeEigenschaften.setId("outTechnischeEigenschaften");
		outTechnischeEigenschaften.setName("Technische\nEigenschaften");
		outTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outTechnischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outTechnischeEigenschaften);

		Aggregation i31 = new Aggregation(output.getId(), outTechnischeEigenschaften.getId(), output.getId());
		i31.setId("i31_producer");
		relationships.add(i31);

		ClassDefinition outteMechanischeEigenschaften = new ClassDefinition();
		outteMechanischeEigenschaften.setId("outteMechanischeEigenschaften_producer");
		outteMechanischeEigenschaften.setName("Mechanische\nEigenschaften");
		outteMechanischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("streckgrenze")));
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zugfestigkeit")));
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("dehnung")));
		outteMechanischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outteMechanischeEigenschaften);

		Aggregation i311 = new Aggregation(outTechnischeEigenschaften.getId(), outteMechanischeEigenschaften.getId(),
				outTechnischeEigenschaften.getId());
		i311.setId("i311_producer");
		relationships.add(i311);

		ClassDefinition outteGefuege = new ClassDefinition();
		outteGefuege.setId("outteGefuege_producer");
		outteGefuege.setName("Gefüge");
		outteGefuege.setProperties(new ArrayList<ClassProperty<Object>>());
		outteGefuege.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gefuege")));
		outteGefuege.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outteGefuege);

		Aggregation i312 = new Aggregation(outTechnischeEigenschaften.getId(), outteGefuege.getId(),
				outTechnischeEigenschaften.getId());
		i312.setId("i312_producer");
		relationships.add(i312);

		ClassDefinition outGeometrischeEigenschaften = new ClassDefinition();
		outGeometrischeEigenschaften.setId("outGeometrischeEigenschaften_producer");
		outGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
		outGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outGeometrischeEigenschaften);

		Aggregation i32 = new Aggregation(output.getId(), outGeometrischeEigenschaften.getId(), output.getId());
		i32.setId("i32_producer");
		relationships.add(i32);

		ClassDefinition outgeMoeglicheBundabmessungen = new ClassDefinition();
		outgeMoeglicheBundabmessungen.setId("outgeMoeglicheBundabmessungen_producer");
		outgeMoeglicheBundabmessungen.setName("Mögliche\nBundabmessungen");
		outgeMoeglicheBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));
		outgeMoeglicheBundabmessungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outgeMoeglicheBundabmessungen);

		Aggregation i321 = new Aggregation(outGeometrischeEigenschaften.getId(), outgeMoeglicheBundabmessungen.getId(),
				outGeometrischeEigenschaften.getId());
		i321.setId("i321_producer");
		relationships.add(i321);

		ClassDefinition outQualitativeEigenschaften = new ClassDefinition();
		outQualitativeEigenschaften.setId("outQualitativeEigenschaften_producer");
		outQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		outQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outQualitativeEigenschaften);

		Aggregation i33 = new Aggregation(output.getId(), outQualitativeEigenschaften.getId(), output.getId());
		i33.setId("i33_producer");
		relationships.add(i33);

		ClassDefinition outqeMaterialart = new ClassDefinition();
		outqeMaterialart.setId("outqeMaterialart_producer");
		outqeMaterialart.setName("Materialart");
		outqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
		outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
		outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));
		outqeMaterialart.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outqeMaterialart);

		Aggregation i331 = new Aggregation(outQualitativeEigenschaften.getId(), outqeMaterialart.getId(),
				outQualitativeEigenschaften.getId());
		i331.setId("i331_producer");
		relationships.add(i331);

		// =========================================================

		ClassDefinition logistischeBeschreibung = new ClassDefinition();
		logistischeBeschreibung.setId("logistischeBeschreibung_producer");
		logistischeBeschreibung.setName("Logistische\nBeschreibung");
		logistischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("materialbereitgestellt")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferort")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verpackung")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("transportart")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("menge")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferdatum")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("incoterms")));
		logistischeBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD_COLLECTOR);
		
		classDefinitions.add(logistischeBeschreibung);

		ClassDefinition preislicheBeschreibung = new ClassDefinition();
		preislicheBeschreibung.setId("preislicheBeschreibung_producer");
		preislicheBeschreibung.setName("Preisliche\nBeschreibung");
		preislicheBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		preislicheBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zahlungsbedingungen")));
		preislicheBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD_COLLECTOR);
		classDefinitions.add(preislicheBeschreibung);

		ClassDefinition root = new ClassDefinition();
		root.setId("root_producer");
		root.setName("Haubenofen");
		root.setProperties(new ArrayList<ClassProperty<Object>>());
		root.setRoot(true);
		root.setClassArchetype(ClassArchetype.ROOT);
		classDefinitions.add(root);

		Aggregation r1 = new Aggregation(root.getId(), technischeBeschreibung.getId(), root.getId());
		r1.setId("r1_producer");
		Aggregation r2 = new Aggregation(root.getId(), logistischeBeschreibung.getId(), root.getId());
		r2.setId("r2_producer");
		Aggregation r3 = new Aggregation(root.getId(), preislicheBeschreibung.getId(), root.getId());
		r3.setId("r3_producer");

		relationships.add(r1);
		relationships.add(r2);
		relationships.add(r3);
		
		ClassConfiguration configurator = new ClassConfiguration();
		configurator.setId("slot1");
		configurator.setName("Produzent");
		configurator.setRelationshipIds(new ArrayList<String>());
		configurator.setClassDefinitionIds(new ArrayList<String>());

		for (Relationship r : relationships) {
			if (!relationshipRepository.exists(r.getId())) {
				relationshipRepository.save(r);
			}
			
			configurator.getRelationshipIds().add(r.getId());
		}

		for (ClassDefinition cd : classDefinitions) {
			if (!classDefinitionRepository.exists(cd.getId())) {
				classDefinitionRepository.save(cd);
			}
			
			configurator.getClassDefinitionIds().add(cd.getId());
		}
		
		configuratorRepository.save(configurator);
	}
	
	private void addFlexProdConfigClassesConsumer() {
		List<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
		List<Relationship> relationships = new ArrayList<Relationship>();

		ClassDefinition technischeBeschreibung = new ClassDefinition();
		technischeBeschreibung.setId("technische_beschreibung_consumer");
		technischeBeschreibung.setName("Technische\nBeschreibung");
		technischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		technischeBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD_COLLECTOR);
		
		classDefinitions.add(technischeBeschreibung);

		ClassDefinition ofen = new ClassDefinition();
		ofen.setId("ofen_consumer");
		ofen.setName("Ofen");
		ofen.setProperties(new ArrayList<ClassProperty<Object>>());
		ofen.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(ofen);

		Aggregation i1 = new Aggregation(technischeBeschreibung.getId(), ofen.getId(), technischeBeschreibung.getId());
		i1.setId("i1_consumer");
		relationships.add(i1);

		ClassDefinition ofenTechnischeEigenschaften = new ClassDefinition();
		ofenTechnischeEigenschaften.setId("ofenTechnischeEigenschaften_consumer");
		ofenTechnischeEigenschaften.setName("Technische\nEigenschaften");
		ofenTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenTechnischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(ofenTechnischeEigenschaften);

		Aggregation i11 = new Aggregation(ofen.getId(), ofenTechnischeEigenschaften.getId(), ofen.getId());
		i11.setId("i11_consumer");

		relationships.add(i11);

		ClassDefinition oteAllgemein = new ClassDefinition();
		oteAllgemein.setId("oteAllgemein_consumer");
		oteAllgemein.setName("Allgemein");
		oteAllgemein.setProperties(new ArrayList<ClassProperty<Object>>());
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxgluehtemperatur")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verfuegbaresschutzgas")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bauart")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("temperaturhomogenitaet")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalztesmaterialzulaessig")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalztesmaterialzulaessig")));
		oteAllgemein.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteAllgemein);

		Aggregation i111 = new Aggregation(ofenTechnischeEigenschaften.getId(), oteAllgemein.getId(),
				ofenTechnischeEigenschaften.getId());
		i111.setId("i111_consumer");
		relationships.add(i111);

		ClassDefinition oteMoeglicheVorbehandlung = new ClassDefinition();
		oteMoeglicheVorbehandlung.setId("oteMoeglicheVorbehandlung_consumer");
		oteMoeglicheVorbehandlung.setName("Mögliche\nVorbehandlung");
		oteMoeglicheVorbehandlung.setProperties(new ArrayList<ClassProperty<Object>>());
		oteMoeglicheVorbehandlung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bundentfetten")));
		oteMoeglicheVorbehandlung.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteMoeglicheVorbehandlung);

		Aggregation i112 = new Aggregation(ofenTechnischeEigenschaften.getId(), oteMoeglicheVorbehandlung.getId(),
				ofenTechnischeEigenschaften.getId());
		i112.setId("i112_consumer");
		relationships.add(i112);

		ClassDefinition oteChargierhilfe = new ClassDefinition();
		oteChargierhilfe.setId("oteChargierhilfe_consumer");
		oteChargierhilfe.setName("Chargierhilfe");
		oteChargierhilfe.setProperties(new ArrayList<ClassProperty<Object>>());
		oteChargierhilfe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("chargierhilfe")));

		
		oteChargierhilfe.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteChargierhilfe);

		Aggregation i113 = new Aggregation(ofenTechnischeEigenschaften.getId(), oteChargierhilfe.getId(),
				ofenTechnischeEigenschaften.getId());
		i113.setId("i113_consumer");
		relationships.add(i113);

		ClassDefinition otecKonvektoren = new ClassDefinition();
		otecKonvektoren.setId("otecKonvektoren_consumer");
		otecKonvektoren.setName("Konvektoren");
		otecKonvektoren.setProperties(new ArrayList<ClassProperty<Object>>());
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecKonvektoren.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecKonvektoren);

		Aggregation i1131 = new Aggregation(oteChargierhilfe.getId(), otecKonvektoren.getId(),
				oteChargierhilfe.getId());
		i1131.setId("i1131_consumer");
		relationships.add(i1131);

		ClassDefinition otecTragerahmen = new ClassDefinition();
		otecTragerahmen.setId("otecTragerahmen_consumer");
		otecTragerahmen.setName("Tragerahmen");
		otecTragerahmen.setProperties(new ArrayList<ClassProperty<Object>>());
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecTragerahmen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecTragerahmen);

		Aggregation i1132 = new Aggregation(oteChargierhilfe.getId(), otecTragerahmen.getId(),
				oteChargierhilfe.getId());
		i1132.setId("i1132_consumer");
		relationships.add(i1132);

		ClassDefinition otecZwischenrahmen = new ClassDefinition();
		otecZwischenrahmen.setId("otecZwischenrahmen_consumer");
		otecZwischenrahmen.setName("Zwischenrahmen");
		otecZwischenrahmen.setProperties(new ArrayList<ClassProperty<Object>>());
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecZwischenrahmen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecZwischenrahmen);

		Aggregation i1133 = new Aggregation(oteChargierhilfe.getId(), otecZwischenrahmen.getId(),
				oteChargierhilfe.getId());
		i1133.setId("i1133_consumer");
		relationships.add(i1133);

		ClassDefinition otecKronenstoecke = new ClassDefinition();
		otecKronenstoecke.setId("otecKronenstoecke_consumer");
		otecKronenstoecke.setName("Kronenstöcke");
		otecKronenstoecke.setProperties(new ArrayList<ClassProperty<Object>>());
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecKronenstoecke.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecKronenstoecke);

		Aggregation i1134 = new Aggregation(oteChargierhilfe.getId(), otecKronenstoecke.getId(),
				oteChargierhilfe.getId());
		i1134.setId("i1134_consumer");
		relationships.add(i1134);

		ClassDefinition otecChargierkoerbe = new ClassDefinition();
		otecChargierkoerbe.setId("otecChargierkoerbe_consumer");
		otecChargierkoerbe.setName("Chargierkörbe");
		otecChargierkoerbe.setProperties(new ArrayList<ClassProperty<Object>>());

		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecChargierkoerbe.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecChargierkoerbe);

		Aggregation i1135 = new Aggregation(oteChargierhilfe.getId(), otecChargierkoerbe.getId(),
				oteChargierhilfe.getId());
		i1135.setId("i1135_consumer");
		relationships.add(i1135);

		ClassDefinition ofenBetrieblicheEigenschaften = new ClassDefinition();
		ofenBetrieblicheEigenschaften.setId("ofenBetrieblicheEigenschaften_consumer");
		ofenBetrieblicheEigenschaften.setName("Betriebliche\nEigenschaften");
		ofenBetrieblicheEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gluehzeit")));
		ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("durchsatz")));
		ofenBetrieblicheEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenBetrieblicheEigenschaften);

		Aggregation i12 = new Aggregation(ofen.getId(), ofenBetrieblicheEigenschaften.getId(), ofen.getId());
		i12.setId("i12_consumer");
		relationships.add(i12);

		ClassDefinition ofenGeometrischeEigenschaften = new ClassDefinition();
		ofenGeometrischeEigenschaften.setId("ofenGeometrischeEigenschaften_consumer");
		ofenGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
		ofenGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenGeometrischeEigenschaften);

		Aggregation i13 = new Aggregation(ofen.getId(), ofenGeometrischeEigenschaften.getId(), ofen.getId());
		i13.setId("i13_consumer");
		relationships.add(i13);

		ClassDefinition ogeBaugroesse = new ClassDefinition();
		ogeBaugroesse.setId("ogeBaugroesse_consumer");
		ogeBaugroesse.setName("Baugröße");
		ogeBaugroesse.setProperties(new ArrayList<ClassProperty<Object>>());
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("moeglicheinnendurchmesser")));
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxaussendurchmesser")));
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxchargierhoehe")));
		ogeBaugroesse.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ogeBaugroesse);

		Aggregation i131 = new Aggregation(ofenGeometrischeEigenschaften.getId(), ogeBaugroesse.getId(),
				ofenGeometrischeEigenschaften.getId());
		i131.setId("i131_consumer");
		relationships.add(i131);

		ClassDefinition ofenQualitativeEigenschaften = new ClassDefinition();
		ofenQualitativeEigenschaften.setId("ofenQualitativeEigenschaften_consumer");
		ofenQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		ofenQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenQualitativeEigenschaften);

		Aggregation i14 = new Aggregation(ofen.getId(), ofenQualitativeEigenschaften.getId(), ofen.getId());
		i14.setId("i14_consumer");
		relationships.add(i14);

		ClassDefinition oqeQualitätsnormen = new ClassDefinition();
		oqeQualitätsnormen.setId("oqeQualitätsnormen_consumer");
		oqeQualitätsnormen.setName("Qualitätsnormen");
		oqeQualitätsnormen.setProperties(new ArrayList<ClassProperty<Object>>());
		oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("cqi9")));
		oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("tus")));
		oqeQualitätsnormen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oqeQualitätsnormen);

		Aggregation i141 = new Aggregation(ofenQualitativeEigenschaften.getId(), oqeQualitätsnormen.getId(),
				ofenQualitativeEigenschaften.getId());
		i141.setId("i141_consumer");
		relationships.add(i141);

		ClassDefinition oqeWartungen = new ClassDefinition();
		oqeWartungen.setId("oqeWartungen_consumer");
		oqeWartungen.setName("Wartungen");
		oqeWartungen.setProperties(new ArrayList<ClassProperty<Object>>());
		oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("letztewartung")));
		oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("wartungsintervall")));
		oqeWartungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		
		classDefinitions.add(oqeWartungen);

		Aggregation i142 = new Aggregation(ofenQualitativeEigenschaften.getId(), oqeWartungen.getId(),
				ofenQualitativeEigenschaften.getId());
		i142.setId("i142_consumer");
		relationships.add(i142);

		// =================================================================================

		ClassDefinition input = new ClassDefinition();
		input.setId("input_consumer");
		input.setName("Input");
		input.setProperties(new ArrayList<ClassProperty<Object>>());
		input.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(input);

		Aggregation i2 = new Aggregation(technischeBeschreibung.getId(), input.getId(), technischeBeschreibung.getId());
		i2.setId("i2_consumer");
		relationships.add(i2);

		ClassDefinition inGeometrischeEigenschaften = new ClassDefinition();
		inGeometrischeEigenschaften.setId("inGeometrischeEigenschaften_consumer");
		inGeometrischeEigenschaften.setName("Geometrsiche\nEigenschaften");
		inGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		inGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inGeometrischeEigenschaften);

		Aggregation i21 = new Aggregation(input.getId(), inGeometrischeEigenschaften.getId(), input.getId());
		i21.setId("i21_consumer");
		relationships.add(i21);

		ClassDefinition ingeBundabmessungen = new ClassDefinition();
		ingeBundabmessungen.setId("ingeBundabmessungen_consumer");
		ingeBundabmessungen.setName("Bundabmessungen");
		ingeBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));
		ingeBundabmessungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ingeBundabmessungen);

		Aggregation i211 = new Aggregation(inGeometrischeEigenschaften.getId(), ingeBundabmessungen.getId(),
				inGeometrischeEigenschaften.getId());
		i211.setId("i211_consumer");
		relationships.add(i211);

		ClassDefinition inQualitativeEigenschaften = new ClassDefinition();
		inQualitativeEigenschaften.setId("inQualitativeEigenschaften_consumer");
		inQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		inQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		inQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inQualitativeEigenschaften);

		Aggregation i22 = new Aggregation(input.getId(), inQualitativeEigenschaften.getId(), input.getId());
		i22.setId("i22_consumer");
		relationships.add(i22);

		ClassDefinition inqeMaterialart = new ClassDefinition();
		inqeMaterialart.setId("inqeMaterialart_consumer");
		inqeMaterialart.setName("Materialart");
		inqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
		inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
		inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));
		inqeMaterialart.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inqeMaterialart);

		Aggregation i221 = new Aggregation(inQualitativeEigenschaften.getId(), inqeMaterialart.getId(),
				inQualitativeEigenschaften.getId());
		i221.setId("i221_consumer");
		relationships.add(i221);

		// =================================================================================

		ClassDefinition output = new ClassDefinition();
		output.setId("output_consumer");
		output.setName("Output");
		output.setProperties(new ArrayList<ClassProperty<Object>>());
		output.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(output);

		Aggregation i3 = new Aggregation(technischeBeschreibung.getId(), output.getId(),
				technischeBeschreibung.getId());
		i3.setId("i3_consumer");
		relationships.add(i3);

		ClassDefinition outTechnischeEigenschaften = new ClassDefinition();
		outTechnischeEigenschaften.setId("outTechnischeEigenschaften_consumer");
		outTechnischeEigenschaften.setName("Technische\nEigenschaften");
		outTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outTechnischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outTechnischeEigenschaften);

		Aggregation i31 = new Aggregation(output.getId(), outTechnischeEigenschaften.getId(), output.getId());
		i31.setId("i31_consumer");
		relationships.add(i31);

		ClassDefinition outteMechanischeEigenschaften = new ClassDefinition();
		outteMechanischeEigenschaften.setId("outteMechanischeEigenschaften_consumer");
		outteMechanischeEigenschaften.setName("Mechanische\nEigenschaften");
		outteMechanischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("streckgrenze")));
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zugfestigkeit")));
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("dehnung")));
		outteMechanischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outteMechanischeEigenschaften);

		Aggregation i311 = new Aggregation(outTechnischeEigenschaften.getId(), outteMechanischeEigenschaften.getId(),
				outTechnischeEigenschaften.getId());
		i311.setId("i311_consumer");
		relationships.add(i311);

		ClassDefinition outteGefuege = new ClassDefinition();
		outteGefuege.setId("outteGefuege_consumer");
		outteGefuege.setName("Gefüge");
		outteGefuege.setProperties(new ArrayList<ClassProperty<Object>>());
		outteGefuege.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gefuege")));
		outteGefuege.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outteGefuege);

		Aggregation i312 = new Aggregation(outTechnischeEigenschaften.getId(), outteGefuege.getId(),
				outTechnischeEigenschaften.getId());
		i312.setId("i312_consumer");
		relationships.add(i312);

		ClassDefinition outGeometrischeEigenschaften = new ClassDefinition();
		outGeometrischeEigenschaften.setId("outGeometrischeEigenschaften_consumer");
		outGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
		outGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outGeometrischeEigenschaften);

		Aggregation i32 = new Aggregation(output.getId(), outGeometrischeEigenschaften.getId(), output.getId());
		i32.setId("i32_consumer");
		relationships.add(i32);

		ClassDefinition outgeMoeglicheBundabmessungen = new ClassDefinition();
		outgeMoeglicheBundabmessungen.setId("outgeMoeglicheBundabmessungen_consumer");
		outgeMoeglicheBundabmessungen.setName("Mögliche\nBundabmessungen");
		outgeMoeglicheBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));
		outgeMoeglicheBundabmessungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outgeMoeglicheBundabmessungen);

		Aggregation i321 = new Aggregation(outGeometrischeEigenschaften.getId(), outgeMoeglicheBundabmessungen.getId(),
				outGeometrischeEigenschaften.getId());
		i321.setId("i321_consumer");
		relationships.add(i321);

		ClassDefinition outQualitativeEigenschaften = new ClassDefinition();
		outQualitativeEigenschaften.setId("outQualitativeEigenschaften_consumer");
		outQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		outQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outQualitativeEigenschaften);

		Aggregation i33 = new Aggregation(output.getId(), outQualitativeEigenschaften.getId(), output.getId());
		i33.setId("i33_consumer");
		relationships.add(i33);

		ClassDefinition outqeMaterialart = new ClassDefinition();
		outqeMaterialart.setId("outqeMaterialart_consumer");
		outqeMaterialart.setName("Materialart");
		outqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
		outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
		outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));
		outqeMaterialart.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outqeMaterialart);

		Aggregation i331 = new Aggregation(outQualitativeEigenschaften.getId(), outqeMaterialart.getId(),
				outQualitativeEigenschaften.getId());
		i331.setId("i331_consumer");
		relationships.add(i331);

		// =========================================================

		ClassDefinition logistischeBeschreibung = new ClassDefinition();
		logistischeBeschreibung.setId("logistischeBeschreibung_consumer");
		logistischeBeschreibung.setName("Logistische\nBeschreibung");
		logistischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("materialbereitgestellt")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferort")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verpackung")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("transportart")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("menge")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferdatum")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("incoterms")));
		logistischeBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD_COLLECTOR);
		
		classDefinitions.add(logistischeBeschreibung);

		ClassDefinition preislicheBeschreibung = new ClassDefinition();
		preislicheBeschreibung.setId("preislicheBeschreibung_consumer");
		preislicheBeschreibung.setName("Preisliche\nBeschreibung");
		preislicheBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		preislicheBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zahlungsbedingungen")));
		preislicheBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD_COLLECTOR);
		classDefinitions.add(preislicheBeschreibung);

		ClassDefinition root = new ClassDefinition();
		root.setId("root_consumer");
		root.setName("Haubenofen");
		root.setProperties(new ArrayList<ClassProperty<Object>>());
		root.setRoot(true);
		root.setClassArchetype(ClassArchetype.ROOT);
		classDefinitions.add(root);

		Aggregation r1 = new Aggregation(root.getId(), technischeBeschreibung.getId(), root.getId());
		r1.setId("r1_consumer");
		Aggregation r2 = new Aggregation(root.getId(), logistischeBeschreibung.getId(), root.getId());
		r2.setId("r2_consumer");
		Aggregation r3 = new Aggregation(root.getId(), preislicheBeschreibung.getId(), root.getId());
		r3.setId("r3_consumer");

		relationships.add(r1);
		relationships.add(r2);
		relationships.add(r3);
		
		ClassConfiguration configurator = new ClassConfiguration();
		configurator.setId("slot2");
		configurator.setName("Konsument");
		configurator.setRelationshipIds(new ArrayList<String>());
		configurator.setClassDefinitionIds(new ArrayList<String>());

		for (Relationship r : relationships) {
			if (!relationshipRepository.exists(r.getId())) {
				relationshipRepository.save(r);
			}
			
			configurator.getRelationshipIds().add(r.getId());
		}

		for (ClassDefinition cd : classDefinitions) {
			if (!classDefinitionRepository.exists(cd.getId())) {
				classDefinitionRepository.save(cd);
			}
			
			configurator.getClassDefinitionIds().add(cd.getId());
		}
		
		configuratorRepository.save(configurator);
	}
	
	private void addFlexProdMachtingOperatorRelationshipStorage() {
		MatchingConfiguration storage = new MatchingConfiguration();
		storage.setId("demo");
		storage.setName("FlexProd Demo");
		storage.setProducerClassConfigurationId("slot1");
		storage.setConsumerClassConfigurationId("slot2");
		storage.setRelationships(new ArrayList<MatchingOperatorRelationship>());
		if (!matchingConfiguratorRepository.exists(storage.getId())) {
			matchingConfiguratorRepository.save(storage);
		}

	}
	
	
}