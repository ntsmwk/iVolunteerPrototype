package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.task.template.UserDefinedTaskTemplateRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.AssociationCardinality;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	@Autowired private CompetenceRepository competenceRepository;
	
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;

	@Autowired private UserDefinedTaskTemplateRepository userDefinedTaskTemplateRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired private ConfiguratorRepository configuratorRepository;

	@Bean
	@Primary
	public RestTemplate produceRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		createCompetence("Planning");
		createCompetence("Leadership");
		createCompetence("Creativity");
		createCompetence("Flexability");
		createCompetence("Motivation");
		
//		addStandardProperties();
		addStandardPropertyDefinitions();
		addTestTemplates();
		
		addTestConfigClasses();
		addFlexProdConfigClasses();
		
		addConfigurators();
	}

	private Competence createCompetence(String competenceName) {
		Competence competence = competenceRepository.findByName(competenceName);
		if (competence == null) {
			competence = new Competence();
			competence.setName(competenceName);
			competence = competenceRepository.insert(competence);
		}
		return competence;
	}
	
//	private void addStandardProperties() {
//		StandardProperties sp = new StandardProperties(competenceRepository, propertyRepository);
//		List<Property> props = sp.getAll();
//		
//		for (Property p : props) {
//			if (!propertyRepository.exists(p.getId())) {
//				propertyRepository.save(p);
//			}
//		}
//	}
	
	private void addStandardPropertyDefinitions() {
		StandardPropertyDefinitions spd = new StandardPropertyDefinitions(competenceRepository, propertyDefinitionRepository);
		List<PropertyDefinition<Object>> props = spd.getAll();
		
		
		for (PropertyDefinition<Object> pd : props) {
			if (!propertyDefinitionRepository.exists(pd.getId())) {
				propertyDefinitionRepository.save(pd);
			}
		}
	}
	
	private void addTestTemplates() {
		
		StandardTemplates st = new StandardTemplates(competenceRepository, propertyDefinitionRepository, propertyDefinitionToClassPropertyMapper);
		
		for (UserDefinedTaskTemplate t : st.createAll()) {
			if (!userDefinedTaskTemplateRepository.exists(t.getId())) {
				userDefinedTaskTemplateRepository.save(t);
			}
		}
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	private void addTestConfigClasses() {
				
		ClassDefinition c1 = new ClassDefinition();
		c1.setId("test1");
		c1.setName("Class 1");
		c1.setProperties(new LinkedList<ClassProperty<Object>>());
		c1.setRoot(true);
		
		PropertyDefinition npd = new StandardPropertyDefinitions.NameProperty();
		ClassProperty<Object> ncp = propertyDefinitionToClassPropertyMapper.toTarget(npd);
		c1.getProperties().add(ncp);
		
		PropertyDefinition sdpd = new StandardPropertyDefinitions.StartDateProperty();	
		ClassProperty<Object> sdcp = propertyDefinitionToClassPropertyMapper.toTarget(sdpd);
		c1.getProperties().add(sdcp);
		
		PropertyDefinition dpd = new StandardPropertyDefinitions.DescriptionProperty(); 
		ClassProperty<Object> dcp = propertyDefinitionToClassPropertyMapper.toTarget(dpd);
		c1.getProperties().add(dcp);
		
		
		ClassDefinition c2 = new ClassDefinition();
		c2.setId("test2");
		c2.setName("Class 2");
		
		ClassDefinition c3 = new ClassDefinition();
		c3.setId("test3");
		c3.setName("Class 3");
		
		
		ClassDefinition c4 = new ClassDefinition();
		c4.setId("test4");
		c4.setName("Class 4");
		
		
		ClassDefinition c5 = new ClassDefinition();
		c5.setId("test5");
		c5.setName("Class 5");
		
		
		ClassDefinition c6 = new ClassDefinition();
		c6.setId("test6");
		c6.setName("Class 6");
		
		ClassDefinition c7 = new ClassDefinition();
		c7.setId("test7");
		c7.setName("Class 7");
		
		ClassDefinition c8 = new ClassDefinition();
		c8.setId("test8");
		c8.setName("Class 8");
		
		ClassDefinition c9 = new ClassDefinition();
		c9.setId("test9");
		c9.setName("Class 9");
		
		
		
//		{from: 1, to: 3},
//		  {from: 1, to: 2},
//		  {from: 2, to: 4},
//		  {from: 2, to: 5},
//		  {from: 3, to: 3},
//		  {from: 6, to: 6},

		
		Inheritance i1 = new Inheritance(c1.getId(), c3.getId(), c1.getId());
		i1.setId("test_i1");
		Inheritance i2 = new Inheritance(c1.getId(), c2.getId(), c1.getId());
		i2.setId("test_i2");
		Inheritance i3 = new Inheritance(c2.getId(), c4.getId(), c2.getId());
		i3.setId("test_i3");
		Inheritance i4 = new Inheritance(c2.getId(), c5.getId(), c2.getId());
		i4.setId("test_i4");
		Inheritance i5 = new Inheritance(c3.getId(), c3.getId(), c3.getId());
		i5.setId("test_i5");
//		Inheritance i6 = new Inheritance(c6.getId(), c6.getId(), c6.getId());
//		i6.setId("test_i6");
		
		Inheritance i7 = new Inheritance(c5.getId(), c7.getId(),c5.getId());
		i7.setId("test_i7");
		Inheritance i8 = new Inheritance(c5.getId(), c8.getId(), c5.getId());
		i8.setId("test_i8");
		Inheritance i9 = new Inheritance(c4.getId(), c9.getId(), c4.getId());
		i9.setId("test_i9");
		
		
		
		if (!relationshipRepository.exists(i1.getId())) {
			relationshipRepository.save(i1);
		}
		if (!relationshipRepository.exists(i2.getId())) {
			relationshipRepository.save(i2);
		}
		if (!relationshipRepository.exists(i3.getId())) {
			relationshipRepository.save(i3);
		}
		if (!relationshipRepository.exists(i4.getId())) {
			relationshipRepository.save(i4);
		}
		if (!relationshipRepository.exists(i5.getId())) {
			relationshipRepository.save(i5);
		}

		if (!relationshipRepository.exists(i7.getId())) {
			relationshipRepository.save(i7);
		}
		if (!relationshipRepository.exists(i8.getId())) {
			relationshipRepository.save(i8);
		}
		if (!relationshipRepository.exists(i9.getId())) {
			relationshipRepository.save(i9);
		}
		  
		  
		if (!classDefinitionRepository.exists(c1.getId())) {
			classDefinitionRepository.save(c1);
		}
		
		if (!classDefinitionRepository.exists(c2.getId())) {
			classDefinitionRepository.save(c2);
		}
		
		if (!classDefinitionRepository.exists(c3.getId())) {
			classDefinitionRepository.save(c3);
		}
		
		if (!classDefinitionRepository.exists(c4.getId())) {
			classDefinitionRepository.save(c4);
		}
		
		if (!classDefinitionRepository.exists(c5.getId())) {
			classDefinitionRepository.save(c5);
		}
		
		if (!classDefinitionRepository.exists(c6.getId())) {
			classDefinitionRepository.save(c6);
		}
		
		if (!classDefinitionRepository.exists(c7.getId())) {
			classDefinitionRepository.save(c7);
		}
		
		if (!classDefinitionRepository.exists(c8.getId())) {
			classDefinitionRepository.save(c8);
		}
		
		if (!classDefinitionRepository.exists(c9.getId())) {
			classDefinitionRepository.save(c9);
		}
	}
	
	
	private void addFlexProdConfigClasses() {
		
		List<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
		List<Relationship> relationships = new ArrayList<Relationship>();
		
		ClassDefinition technischeBeschreibung = new ClassDefinition();
		technischeBeschreibung.setId("technische_beschreibung");
		technischeBeschreibung.setName("Technische\nBeschreibung");
		technischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		classDefinitions.add(technischeBeschreibung);
		
			ClassDefinition ofen = new ClassDefinition();
			ofen.setId("ofen");
			ofen.setName("Ofen");
			ofen.setProperties(new ArrayList<ClassProperty<Object>>());
			classDefinitions.add(ofen);
			
			Inheritance i1 = new Inheritance(technischeBeschreibung.getId(), ofen.getId(), technischeBeschreibung.getId());
			i1.setId("i1");
			relationships.add(i1);
			
			
				ClassDefinition ofenTechnischeEigenschaften = new ClassDefinition();
				ofenTechnischeEigenschaften.setId("ofenTechnischeEigenschaften");
				ofenTechnischeEigenschaften.setName("Technische\nEigenschaften");
				ofenTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				classDefinitions.add(ofenTechnischeEigenschaften);
				
				Inheritance i11 = new Inheritance(ofen.getId(), ofenTechnischeEigenschaften.getId(), ofen.getId());
				i11.setId("i11");

				relationships.add(i11);


				
					ClassDefinition oteAllgemein = new ClassDefinition();
					oteAllgemein.setId("oteAllgemein");
					oteAllgemein.setName("Allgemein");
					oteAllgemein.setProperties(new ArrayList<ClassProperty<Object>>());
					oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxgluehtemperatur")));
					oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verfuegbaresschutzgas")));
					oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bauart")));
					oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("temperaturhomogenitaet")));
					oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalztesmaterialzulaessig")));
					oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalztesmaterialzulaessig")));

					
					classDefinitions.add(oteAllgemein);
					
					Inheritance i111 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteAllgemein.getId(), ofenTechnischeEigenschaften.getId());
					i111.setId("i111");
					relationships.add(i111);
					

					ClassDefinition oteMoeglicheVorbehandlung = new ClassDefinition();
					oteMoeglicheVorbehandlung.setId("oteMoeglicheVorbehandlung");
					oteMoeglicheVorbehandlung.setName("Mögliche\nVorbehandlung");
					oteMoeglicheVorbehandlung.setProperties(new ArrayList<ClassProperty<Object>>());
					oteMoeglicheVorbehandlung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bundentfetten")));

					classDefinitions.add(oteMoeglicheVorbehandlung);

					Inheritance i112 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteMoeglicheVorbehandlung.getId(), ofenTechnischeEigenschaften.getId());
					i112.setId("i112");
					relationships.add(i112);

					
					ClassDefinition oteChargierhilfe = new ClassDefinition();
					oteChargierhilfe.setId("oteChargierhilfe");
					oteChargierhilfe.setName("Chargierhilfe");
					oteChargierhilfe.setProperties(new ArrayList<ClassProperty<Object>>());
					classDefinitions.add(oteChargierhilfe);
					
					Inheritance i113 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteChargierhilfe.getId(), ofenTechnischeEigenschaften.getId());
					i113.setId("i113");
					relationships.add(i113);


						ClassDefinition otecKonvektoren = new ClassDefinition();
						otecKonvektoren.setId("otecKonvektoren");
						otecKonvektoren.setName("Konvektoren");
						otecKonvektoren.setProperties(new ArrayList<ClassProperty<Object>>());
						otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
						otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
						otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));


						
						classDefinitions.add(otecKonvektoren);
						
						Inheritance i1131 = new Inheritance(oteChargierhilfe.getId(), otecKonvektoren.getId(), oteChargierhilfe.getId());
						i1131.setId("i1131");
						relationships.add(i1131);

						
						ClassDefinition otecTragerahmen = new ClassDefinition();
						otecTragerahmen.setId("otecTragerahmen");
						otecTragerahmen.setName("Tragerahmen");
						otecTragerahmen.setProperties(new ArrayList<ClassProperty<Object>>());
						otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
						otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
						otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));

						classDefinitions.add(otecTragerahmen);

						Inheritance i1132 = new Inheritance(oteChargierhilfe.getId(), otecTragerahmen.getId(), oteChargierhilfe.getId());
						i1132.setId("i1132");						
						relationships.add(i1132);

						
						ClassDefinition otecZwischenrahmen = new ClassDefinition();
						otecZwischenrahmen.setId("otecZwischenrahmen");
						otecZwischenrahmen.setName("Zwischenrahmen");
						otecZwischenrahmen.setProperties(new ArrayList<ClassProperty<Object>>());
						otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
						otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
						otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
						
						classDefinitions.add(otecZwischenrahmen);
						
						Inheritance i1133 = new Inheritance(oteChargierhilfe.getId(), otecZwischenrahmen.getId(), oteChargierhilfe.getId());
						i1133.setId("i1133");
						relationships.add(i1133);


						ClassDefinition otecKronenstoecke = new ClassDefinition();
						otecKronenstoecke.setId("otecKronenstoecke");
						otecKronenstoecke.setName("Kronenstöcke");
						otecKronenstoecke.setProperties(new ArrayList<ClassProperty<Object>>());
						otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
						otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
						otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
					
						classDefinitions.add(otecKronenstoecke);
						
						Inheritance i1134 = new Inheritance(oteChargierhilfe.getId(), otecKronenstoecke.getId(), oteChargierhilfe.getId());
						i1134.setId("i1134");
						relationships.add(i1134);


						ClassDefinition otecChargierkoerbe = new ClassDefinition();
						otecChargierkoerbe.setId("otecChargierkoerbe");
						otecChargierkoerbe.setName("Chargierkörbe");
						otecChargierkoerbe.setProperties(new ArrayList<ClassProperty<Object>>());
						
						otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
						otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
						otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
					
						classDefinitions.add(otecChargierkoerbe);

						Inheritance i1135 = new Inheritance(oteChargierhilfe.getId(), otecChargierkoerbe.getId(), oteChargierhilfe.getId());
						i1135.setId("i1135");
						relationships.add(i1135);

						
				
				ClassDefinition ofenBetrieblicheEigenschaften = new ClassDefinition();
				ofenBetrieblicheEigenschaften.setId("ofenBetrieblicheEigenschaften");
				ofenBetrieblicheEigenschaften.setName("Betriebliche\nEigenschaften");
				ofenBetrieblicheEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gluehzeit")));
				ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("durchsatz")));
		
				classDefinitions.add(ofenBetrieblicheEigenschaften);
				
				Inheritance i12 = new Inheritance(ofen.getId(), ofenBetrieblicheEigenschaften.getId(), ofen.getId());
				i12.setId("i12");
				relationships.add(i12);

				
				ClassDefinition ofenGeometrischeEigenschaften = new ClassDefinition();
				ofenGeometrischeEigenschaften.setId("ofenGeometrischeEigenschaften");
				ofenGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
				ofenGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				classDefinitions.add(ofenGeometrischeEigenschaften);

				
				Inheritance i13 = new Inheritance(ofen.getId(), ofenGeometrischeEigenschaften.getId(), ofen.getId());
				i13.setId("i13");
				relationships.add(i13);

				
					ClassDefinition ogeBaugroesse = new ClassDefinition();
					ogeBaugroesse.setId("ogeBaugroesse");
					ogeBaugroesse.setName("Baugröße");
					ogeBaugroesse.setProperties(new ArrayList<ClassProperty<Object>>());
					ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("moeglicheinnendurchmesser")));
					ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxaussendurchmesser")));
					ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxchargierhoehe")));

					classDefinitions.add(ogeBaugroesse);
					
					Inheritance i131 = new Inheritance(ofenGeometrischeEigenschaften.getId(), ogeBaugroesse.getId(), ofenGeometrischeEigenschaften.getId());
					i131.setId("i131");
					relationships.add(i131);

				
				ClassDefinition ofenQualitativeEigenschaften = new ClassDefinition();
				ofenQualitativeEigenschaften.setId("ofenQualitativeEigenschaften");
				ofenQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
				ofenQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				
				classDefinitions.add(ofenQualitativeEigenschaften);
				
				Inheritance i14 = new Inheritance(ofen.getId(), ofenQualitativeEigenschaften.getId(), ofen.getId());
				i14.setId("i14");
				relationships.add(i14);

				
					ClassDefinition oqeQualitätsnormen = new ClassDefinition();
					oqeQualitätsnormen.setId("oqeQualitätsnormen");
					oqeQualitätsnormen.setName("Qualitätsnormen");
					oqeQualitätsnormen.setProperties(new ArrayList<ClassProperty<Object>>());
					oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("cqi9")));
					oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("tus")));

					classDefinitions.add(oqeQualitätsnormen);

					Inheritance i141 = new Inheritance(ofenQualitativeEigenschaften.getId(), oqeQualitätsnormen.getId(), ofenQualitativeEigenschaften.getId());
					i141.setId("i141");
					relationships.add(i141);

					
					ClassDefinition oqeWartungen = new ClassDefinition();
					oqeWartungen.setId("oqeWartungen");
					oqeWartungen.setName("Wartungen");
					oqeWartungen.setProperties(new ArrayList<ClassProperty<Object>>());
					oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("letztewartung")));
					oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("wartungsintervall")));

					classDefinitions.add(oqeWartungen);
					
					Inheritance i142 = new Inheritance(ofenQualitativeEigenschaften.getId(), oqeWartungen.getId(), ofenQualitativeEigenschaften.getId());
					i142.setId("i142");
					relationships.add(i142);


				
	//=================================================================================
			
			ClassDefinition input = new ClassDefinition();
			input.setId("input");
			input.setName("Input");
			input.setProperties(new ArrayList<ClassProperty<Object>>());
			classDefinitions.add(input);
			
			Inheritance i2 = new Inheritance(technischeBeschreibung.getId(), input.getId(), technischeBeschreibung.getId());
			i2.setId("i2");
			relationships.add(i2);

			
				ClassDefinition inGeometrischeEigenschaften = new ClassDefinition();
				inGeometrischeEigenschaften.setId("inGeometrischeEigenschaften");
				inGeometrischeEigenschaften.setName("Geometrsiche\nEigenschaften");
				inGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				classDefinitions.add(inGeometrischeEigenschaften);
				
				Inheritance i21 = new Inheritance(input.getId(), inGeometrischeEigenschaften.getId(), input.getId());
				i21.setId("i21");
				relationships.add(i21);

				
					ClassDefinition ingeBundabmessungen = new ClassDefinition();
					ingeBundabmessungen.setId("ingeBundabmessungen");
					ingeBundabmessungen.setName("Bundabmessungen");
					ingeBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
					ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
					ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
					ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
					ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));

					classDefinitions.add(ingeBundabmessungen);

					Inheritance i211 = new Inheritance(inGeometrischeEigenschaften.getId(), ingeBundabmessungen.getId(), inGeometrischeEigenschaften.getId());
					i211.setId("i211");
					relationships.add(i211);

				
				ClassDefinition inQualitativeEigenschaften = new ClassDefinition();
				inQualitativeEigenschaften.setId("inQualitativeEigenschaften");
				inQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
				inQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				classDefinitions.add(inQualitativeEigenschaften);
				
				Inheritance i22 = new Inheritance(input.getId(), inQualitativeEigenschaften.getId(), input.getId());
				i22.setId("i22");
				relationships.add(i22);

	
					ClassDefinition inqeMaterialart = new ClassDefinition();
					inqeMaterialart.setId("inqeMaterialart");
					inqeMaterialart.setName("Materialart");
					inqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
					inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
					inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));

					classDefinitions.add(inqeMaterialart);
					
					Inheritance i221 = new Inheritance(inQualitativeEigenschaften.getId(), inqeMaterialart.getId(), inQualitativeEigenschaften.getId());
					i221.setId("i221");
					relationships.add(i221);


					
				
	//=================================================================================
			
			
			ClassDefinition output = new ClassDefinition();
			output.setId("output");
			output.setName("Output");
			output.setProperties(new ArrayList<ClassProperty<Object>>());
			classDefinitions.add(output);
			
			Inheritance i3 = new Inheritance(technischeBeschreibung.getId(), output.getId(), technischeBeschreibung.getId());
			i3.setId("i3");
			relationships.add(i3);


				ClassDefinition outTechnischeEigenschaften = new ClassDefinition();
				outTechnischeEigenschaften.setId("outTechnischeEigenschaften");
				outTechnischeEigenschaften.setName("Technische\nEigenschaften");
				outTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				classDefinitions.add(outTechnischeEigenschaften);
				
				Inheritance i31 = new Inheritance(output.getId(), outTechnischeEigenschaften.getId(), output.getId());
				i31.setId("i31");
				relationships.add(i31);
	
				
					ClassDefinition outteMechanischeEigenschaften = new ClassDefinition();
					outteMechanischeEigenschaften.setId("outteMechanischeEigenschaften");
					outteMechanischeEigenschaften.setName("Mechanische\nEigenschaften");
					outteMechanischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
					outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("streckgrenze")));
					outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zugfestigkeit")));
					outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("dehnung")));

					classDefinitions.add(outteMechanischeEigenschaften);
					
					Inheritance i311 = new Inheritance(outTechnischeEigenschaften.getId(), outteMechanischeEigenschaften.getId(), outTechnischeEigenschaften.getId());
					i311.setId("i311");
					relationships.add(i311);

					
					ClassDefinition outteGefuege = new ClassDefinition();
					outteGefuege.setId("outteGefuege");
					outteGefuege.setName("Gefüge");
					outteGefuege.setProperties(new ArrayList<ClassProperty<Object>>());
					outteGefuege.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gefuege")));
					classDefinitions.add(outteGefuege);
					
					Inheritance i312 = new Inheritance(outTechnischeEigenschaften.getId(), outteGefuege.getId(), outTechnischeEigenschaften.getId());
					i312.setId("i312");
					relationships.add(i312);


					
					
				ClassDefinition outGeometrischeEigenschaften = new ClassDefinition();
				outGeometrischeEigenschaften.setId("outGeometrischeEigenschaften");
				outGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
				outGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				classDefinitions.add(outGeometrischeEigenschaften);
				
				Inheritance i32 = new Inheritance(output.getId(), outGeometrischeEigenschaften.getId(), output.getId());
				i32.setId("i32");
				relationships.add(i32);

					
					ClassDefinition outgeMoeglicheBundabmessungen = new ClassDefinition();
					outgeMoeglicheBundabmessungen.setId("outgeMoeglicheBundabmessungen");
					outgeMoeglicheBundabmessungen.setName("Mögliche\nBundabmessungen");
					outgeMoeglicheBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
					outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
					outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
					outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
					outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));

					classDefinitions.add(outgeMoeglicheBundabmessungen);

					Inheritance i321 = new Inheritance(outGeometrischeEigenschaften.getId(), outgeMoeglicheBundabmessungen.getId(), outGeometrischeEigenschaften.getId());
					i321.setId("i321");
					relationships.add(i321);

					
				
				ClassDefinition outQualitativeEigenschaften = new ClassDefinition();
				outQualitativeEigenschaften.setId("outQualitativeEigenschaften");
				outQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
				outQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
				classDefinitions.add(outQualitativeEigenschaften);
				
				Inheritance i33 = new Inheritance(output.getId(), outQualitativeEigenschaften.getId(), output.getId());
				i33.setId("i33");
				relationships.add(i33);

				
					ClassDefinition outqeMaterialart = new ClassDefinition();
					outqeMaterialart.setId("outqeMaterialart");
					outqeMaterialart.setName("Materialart");
					outqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
					outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
					outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));

					classDefinitions.add(outqeMaterialart);
					
					Inheritance i331 = new Inheritance(outQualitativeEigenschaften.getId(), outqeMaterialart.getId(), outQualitativeEigenschaften.getId());
					i331.setId("i331");
					relationships.add(i331);


		//=========================================================	
		
		ClassDefinition logistischeBeschreibung = new ClassDefinition();
		logistischeBeschreibung.setId("logistischeBeschreibung");
		logistischeBeschreibung.setName("Logistische\nBeschreibung");
		logistischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("materialbereitgestellt")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferort")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verpackung")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("transportart")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("menge")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferdatum")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("incoterms")));
		classDefinitions.add(logistischeBeschreibung);
		
		ClassDefinition preislicheBeschreibung = new ClassDefinition();
		preislicheBeschreibung.setId("preislicheBeschreibung");
		preislicheBeschreibung.setName("Preisliche\nBeschreibung");
		preislicheBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		preislicheBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zahlungsbedingungen")));
		classDefinitions.add(preislicheBeschreibung); 
		
		ClassDefinition root = new ClassDefinition();
		root.setId("root");
		root.setName("/");
		root.setProperties(new ArrayList<ClassProperty<Object>>());
		root.setRoot(true);
		classDefinitions.add(root);
		
		Inheritance r1 = new Inheritance(root.getId(), technischeBeschreibung.getId(), root.getId());
		r1.setId("r1");
		Inheritance r2 = new Inheritance(root.getId(), logistischeBeschreibung.getId(), root.getId());
		r2.setId("r2");
		Inheritance r3 = new Inheritance(root.getId(), preislicheBeschreibung.getId(), root.getId());
		r3.setId("r3");
		
		relationships.add(r1);
		relationships.add(r2);
		relationships.add(r3);
		
		
		Configurator c5 = new Configurator();
		c5.setName("Haubenofen");
		c5.setId("haubenofen");

		c5.setDate(new Date());
		
		c5.setRelationshipIds(new ArrayList<>());
		c5.setClassDefinitionIds(new ArrayList<>());

					
		for (Relationship r : relationships) {
			if (!relationshipRepository.exists(r.getId())) {
				relationshipRepository.save(r);
				c5.getRelationshipIds().add(r.getId());
			}
		}
		
		for (ClassDefinition cd : classDefinitions) {
			if (!classDefinitionRepository.exists(cd.getId())) {
				classDefinitionRepository.save(cd);
				c5.getClassDefinitionIds().add(cd.getId());
			}
		}
	
		if (!configuratorRepository.exists(c5.getId())) {
			configuratorRepository.save(c5);
		}
	}
	
	private void addConfigurators() {
		Configurator c1 = new Configurator();
		c1.setName("test1");
		c1.setId("test1");
		c1.setDate(new Date());
		
		c1.setRelationshipIds(new ArrayList<>());
		c1.getRelationshipIds().add("test_i1");
		c1.getRelationshipIds().add("test_i2");
		c1.getRelationshipIds().add("test_i3");
		c1.getRelationshipIds().add("test_i4");
		c1.getRelationshipIds().add("test_i5");
		c1.getRelationshipIds().add("test_i6");
		c1.getRelationshipIds().add("test_i7");
		c1.getRelationshipIds().add("test_i8");
		c1.getRelationshipIds().add("test_i9");

		c1.setClassDefinitionIds(new ArrayList<>());
		c1.getClassDefinitionIds().add("test1");
		c1.getClassDefinitionIds().add("test2");
		c1.getClassDefinitionIds().add("test3");
		c1.getClassDefinitionIds().add("test4");
		c1.getClassDefinitionIds().add("test5");
		c1.getClassDefinitionIds().add("test6");
		c1.getClassDefinitionIds().add("test7");
		c1.getClassDefinitionIds().add("test8");
		c1.getClassDefinitionIds().add("test9");
		
		Configurator c2 = new Configurator();
		c2.setName("test2");
		c2.setId("test2");
		c2.setDate(new Date(1289516400000L));
		
		Configurator c3 = new Configurator();
		c3.setName("test3");
		c3.setId("test3");

		c3.setDate(new Date());
		
		Configurator c4 = new Configurator();
		c4.setName("test4");
		c4.setId("test4");

		c4.setDate(new Date());

		
		
		
		if (!configuratorRepository.exists(c1.getId())) {
			configuratorRepository.save(c1);
		}
		
		if (!configuratorRepository.exists(c2.getId())) {
			configuratorRepository.save(c2);
		}
		
		if (!configuratorRepository.exists(c3.getId())) {
			configuratorRepository.save(c3);
		}
		
		if (!configuratorRepository.exists(c3.getId())) {
			configuratorRepository.save(c3);
		}
		
		if (!configuratorRepository.exists(c4.getId())) {
			configuratorRepository.save(c4);
		}
		

		

	}
	
}
