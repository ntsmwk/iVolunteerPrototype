package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.constraint.property.constraints.MaximumTextLength;
import at.jku.cis.iVolunteer.model.meta.constraint.property.constraints.MinimumTextLength;
import at.jku.cis.iVolunteer.model.meta.constraint.property.constraints.TextPattern;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.BooleanPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.DatePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.DoublePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.EnumPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.LongPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.LongTextPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.TextPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class StandardPropertyDefinitions {

	@Autowired public PropertyDefinitionRepository propertyDefinitionRepository;

	// =========================================
	// ========== Text Properties ==============
	// =========================================

	public StandardPropertyDefinitions() {

	}

//	public List<PropertyDefinition<Object>> getAllMulti() {
//		List<PropertyDefinition<Object>> props = new LinkedList<>();
//		
//		MultiProperty mp = new TestMultiProperty();
////		List<Property> allProps = propertyRepository.findAll();
//		List<Property> multiProps = new LinkedList<>();
//		
////		multiProps.add(allProps.get(0));
////		props.getLast().setId(new ObjectId().toString());
//		
//		multiProps.add(new PostcodeProperty());
//		multiProps.add(new LatitudeProperty());
//		multiProps.add(new LongitudeProperty());
//		MultiProperty mp11 = new TestMultiProperty();
//		mp11.setId("nested1");
//		
//		MultiProperty mp111 = new TestMultiProperty();
//		mp111.setProperties(new ArrayList<>(multiProps));
//		mp111.setId("nested2");
//		
//		
//		mp11.setProperties(new ArrayList<>(multiProps));
//		mp11.getProperties().add(mp111);
//		
//		
//		multiProps.add(mp11);
//		
//		
//		
//		mp.setProperties(new ArrayList<>(multiProps));
//		
//		props.add(mp);
//		
//		props.add(new MapProperty());
//		
//		List<Property> ret = new ArrayList<>(props);
//		return ret;
//	}

//	public List<PropertyDefinition<?>> getAllSybos() {
//		List<PropertyDefinition<?>> list = new LinkedList<>();
//		
//		list.add(new DurationTimeProperty());
//		list.add(new AreaOfExpertiseProperty());
//		list.add(new PlanningTypeProperty());
//		list.add(new DurationHoursProperty());
//		list.add(new PointsProperty());
//		list.add(new DepartmentProperty());
//		list.add(new StreetProperty());
//		list.add(new HouseNumberProperty());
//		list.add(new DoorNumberProperty());
//		list.add(new CityProperty());
//		list.add(new AddressProperty());
//		list.add(new CommentsProperty());
//		list.add(new VehicleSelectionProperty());
//		list.add(new EventTypeProperty());
//		list.add(new PublicEventProperty());
//		list.add(new PaidProperty());
//		list.add(new PrerequisitesProperty());
//		list.add(new ActivityGroupProperty());
//		list.add(new RegisterDateProperty());
//		list.add(new EducationClassProperty());
//		list.add(new GradeProperty());
//		
//		return new ArrayList<>(list);
//	}

//	public List<Property> getTestMultiWithRules() {
//		List<Property> list = new LinkedList<>();
//		
//		list.add(new TestMultiWithRules());
//		
//		return new ArrayList<>(list);
//
//	}

//	public List<PropertyDefinition<Object>> getAllFlexProdProperties() {
//		List<PropertyDefinition<?>> list = new LinkedList<>();
//
//		list.add(new MaxGluehtemperaturProperty());
//		list.add(new VerfuegbaresSchutzgasProperty());
//		list.add(new BauartProperty());
//		list.add(new TemperaturhomogenitaetProperty());
//		list.add(new KaltgewalztesMaterialZulaessigProperty());
//		list.add(new WarmgewalztesMaterialZulaessigProperty());
//
//		list.add(new BundEntfettenProperty());
//
//		list.add(new InnendurchmesserProperty());
//		list.add(new AussendurchmesserProperty());
//		list.add(new HoeheProperty());
//
//		list.add(new GluehzeitProperty());
//		list.add(new DurchsatzProperty());
//
//		list.add(new MoeglicheInnendurchmesserProperty());
//		list.add(new MaxAussendurchmesserProperty());
//		list.add(new MaxChargierhoeheProperty());
//
//		list.add(new CQI9Property());
//		list.add(new TUSProperty());
//
//		list.add(new LetzteWartungProperty());
//		list.add(new WartungsintervallProperty());
//
//		list.add(new BandbreiteProperty());
//		list.add(new BandstaerkeProperty());
//
//		list.add(new WarmgewalztProperty());
//		list.add(new KaltgewalztProperty());
//
//		list.add(new StreckgrenzeProperty());
//		list.add(new ZugfestigkeitProperty());
//		list.add(new DehnungProperty());
//
//		list.add(new GefuegeProperty());
//
//		list.add(new MaterialBereitgestelltProperty());
//		list.add(new LieferortProperty());
//		list.add(new VerpackungProperty());
//		list.add(new TransportartProperty());
//		list.add(new MengeProperty());
//		list.add(new LieferdatumProperty());
//		list.add(new IncotermsProperty());
//
//		list.add(new ZahlungsbedingungenProperty());
//
//		return new ArrayList(list);
//
//	}

	public List<PropertyDefinition<Object>> getAll() {
		List<PropertyDefinition<Object>> sps = this.getAllSingle();
//		List<PropertyDefinition<Object>> mps = this.getAllMulti();
//		List<PropertyDefinition<Object>> sbs = this.getAllSybos();
//		List<PropertyDefinition<Object>> tmwr = this.getTestMultiWithRules();
//		List<PropertyDefinition<Object>> flexProd = this.getAllFlexProdProperties();

//		sps.addAll(mps);
//		sps.addAll(sbs);
//		sps.addAll(tmwr);
//		sps.addAll(flexProd);

		return sps;

	}

	public List<PropertyDefinition<Object>> getAllSingle() {
		List<PropertyDefinition<?>> props = new LinkedList<>();

		NameProperty np = new NameProperty();
		np.inst();
		props.add(np);

		DescriptionProperty dp = new DescriptionProperty();
		np.inst();
		props.add(dp);

		props.add(new WorkflowKeyProperty());
		props.add(new ContentProperty());
		props.add(new PriorityProperty());
		props.add(new ImportancyProperty());
		props.add(new RoleProperty());
		props.add(new LocationProperty());
		props.add(new RequiredEquipmentProperty());
		props.add(new WorkshiftProperty());
		props.add(new TaskPeriodTypeProperty());
		props.add(new KeywordsProperty());
		props.add(new RewardsProperty());
		props.add(new PostcodeProperty());
		props.add(new NumberOfVolunteersProperty());
		props.add(new TaskPeriodValueProperty());
		props.add(new StartDateProperty());
		props.add(new EndDateProperty());
		props.add(new UrgentProperty());
		props.add(new HighlightedProperty());
		props.add(new PromotedProperty());
		props.add(new FeedbackRequestedProperty());
		props.add(new RemindParticipantsProperty());
		props.add(new LatitudeProperty());
		props.add(new LongitudeProperty());

		RequiredCompetencesProperty cp1 = new RequiredCompetencesProperty();
		OptionalCompetencesProperty cp2 = new OptionalCompetencesProperty();
		AquireableCompetencesProperty cp3 = new AquireableCompetencesProperty();

		cp1.setAllowedValues(addCompetenceLegalValues());
		props.add(cp1);
		cp2.setAllowedValues(addCompetenceLegalValues());
		props.add(cp2);
		cp3.setAllowedValues(addCompetenceLegalValues());
		props.add(cp3);

		props.add(new TaetigkeitsArtProperty());

		return new ArrayList(props);

	}

	public List<String> addCompetenceLegalValues() {

		List<String> legalValues = new LinkedList<>();

		// @MWE fix for CompetenceClassDefinitions
//		for (Competence c : competenceRepository.findAll()) {
//			legalValues.add(c.getName());
//		}

		return legalValues;
	}

//	public Map<String, Property> getAllSingleMap() {
//		Map<String, Property> props = new HashMap<>();
//		
//		List<Property> list = getAllSingle();
//		for (Property p : list) {
//			props.put(p.getId(), p);
//		}
//			
//		return new HashMap<>(props);
//	}

	/**
	 * 
	 * Standard Properties
	 *
	 */
	public static class NameProperty extends TextPropertyDefinition {

		NameProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setId("name");
			this.setType(PropertyType.TEXT);
			this.setName("name");
			this.setRequired(true);

			List<PropertyConstraint<?>> constraints = new ArrayList<>();
			constraints.add(new MinimumTextLength(3));
			constraints.add(new MaximumTextLength(10));
			constraints.add(new TextPattern("^[A-Za-z][A-Za-zöäüÖÄÜß\\s]*"));
			this.setPropertyConstraints(new ArrayList(constraints));

		}
	}

	public static class DescriptionProperty extends LongTextPropertyDefinition {

		public DescriptionProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setId("description");
			this.setName("Description");
			this.setRequired(true);
		}
	}

	public static class WorkflowKeyProperty extends TextPropertyDefinition {

		public WorkflowKeyProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setId("workflow_key");
			this.setName("Workflow Key");
		}
	}

	public static class ContentProperty extends TextPropertyDefinition {
		public ContentProperty() {
			inst();
		}

		public void inst() {
			this.setId("content");
			this.setName("Content");
		}
	}

	public static class PriorityProperty extends TextPropertyDefinition {
		public PriorityProperty() {
			inst();
		}

		public void inst() {
			this.setId("priority");
			this.setName("Priority");

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Low");
			legalValues.add("Normal");
			legalValues.add("High");
			legalValues.add("Critical");
			this.setAllowedValues(legalValues);
		}
	}

	public static class ImportancyProperty extends TextPropertyDefinition {
		public ImportancyProperty() {
			inst();
		}

		public void inst() {
			this.setId("importancy");
			this.setName("Importancy");

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Not Important");
			legalValues.add("Somewhat Important");
			legalValues.add("Important");
			legalValues.add("Very Important");
			legalValues.add("Critically Important");
			this.setAllowedValues(legalValues);

		}
	}

	public static class RoleProperty extends TextPropertyDefinition {
		public RoleProperty() {
			inst();
		}

		public void inst() {
			this.setId("role");
			this.setName("Role");
		}
	}

	public static class LocationProperty extends TextPropertyDefinition {
		public LocationProperty() {
			inst();
		}

		public void inst() {
			this.setId("location");
			this.setName("Location");
		}
	}

	public static class RequiredEquipmentProperty extends TextPropertyDefinition {
		public RequiredEquipmentProperty() {
			inst();
		}

		public void inst() {
			this.setId("required_equipment");
			this.setName("Required Equipment");
		}
	}

	public static class WorkshiftProperty extends TextPropertyDefinition {
		public WorkshiftProperty() {
			inst();
		}

		public void inst() {
			this.setId("workshift");
			this.setName("Allocated Shift");

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Morning");
			legalValues.add("Day");
			legalValues.add("Evening");
			legalValues.add("Evening-Night");
			legalValues.add("Night");
			legalValues.add("Night-Morning");
			this.setAllowedValues(legalValues);
		}
	}

	public static class TaskPeriodTypeProperty extends TextPropertyDefinition {

		public TaskPeriodTypeProperty() {
			inst();
		}

		public void inst() {
			this.setId("period_type");
			this.setName("Period Type");

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Days");
			legalValues.add("Weeks");
			legalValues.add("Months");
			legalValues.add("Weekly");
			legalValues.add("Daily");
			legalValues.add("Monthly");
			this.setAllowedValues(legalValues);

		}
	}

	public static class KeywordsProperty extends TextPropertyDefinition {
		public KeywordsProperty() {
			inst();
		}

		public void inst() {
			this.setId("keywords");
			this.setName("Keywords");
		}

	}

	//// Rewards ??
	public static class RewardsProperty extends TextPropertyDefinition {
		public RewardsProperty() {
			inst();
		}

		public void inst() {
			this.setId("offered_rewards");
			this.setName("Offered Reward(s)");
		}
	}

	// =========================================
	// ========== Number Properties ============
	// =========================================

	public static class PostcodeProperty extends LongPropertyDefinition {
		public PostcodeProperty() {
			inst();
		}

		public void inst() {
			this.setId("postcode");
			this.setName("Postcode");

		}
	}

	public static class NumberOfVolunteersProperty extends LongPropertyDefinition {
		public NumberOfVolunteersProperty() {
			inst();
		}

		public void inst() {
			this.setId("number_of_volunteers");
			this.setName("Number of Volunteers");

			List<Long> legalValues = new LinkedList<>();
			for (long i = 1; i <= 10; i++) {
				legalValues.add(i);
			}
			this.setAllowedValues(legalValues);
		}
	}

	public static class TaskPeriodValueProperty extends LongPropertyDefinition {
		public TaskPeriodValueProperty() {
			inst();
		}

		public void inst() {
			this.setId("period_length");
			this.setName("Period length");

			List<Long> defaultValues = new ArrayList<>();
			defaultValues.add(1L);
			this.setAllowedValues(defaultValues);
		}
	}

	// =========================================
	// ========== Date Properties ==============
	// =========================================

	public static class StartDateProperty extends DatePropertyDefinition {
		public StartDateProperty() {
			super();
			this.setId("starting_date");
			this.setName("Starting Date");
			setTestValues();
		}

		public void setTestValues() {

			List<Date> defaultValues = new ArrayList<>();
			defaultValues.add(new Date());
			this.setAllowedValues(defaultValues);

		}
	}

	public static class EndDateProperty extends DatePropertyDefinition {
		public EndDateProperty() {
			this.setId("end_date");
			this.setName("End Date");
			setTestValues();
		}

		public void setTestValues() {

		}
	}

	// =========================================
	// ========== Bool Properties ==============
	// =========================================

	public static class UrgentProperty extends BooleanPropertyDefinition {
		public UrgentProperty() {
			inst();
		}

		public void inst() {
			this.setId("urgent");
			this.setName("Urgent");
		}
	}

	public static class HighlightedProperty extends BooleanPropertyDefinition {
		public HighlightedProperty() {
			inst();
		}

		public void inst() {
			this.setId("highlighted");
			this.setName("Highlighted");
		}
	}

	public static class PromotedProperty extends BooleanPropertyDefinition {
		public PromotedProperty() {
			inst();
		}

		public void inst() {
			this.setId("promotion");
			this.setName("Promotion");
		}
	}

	public static class FeedbackRequestedProperty extends BooleanPropertyDefinition {
		public FeedbackRequestedProperty() {
			inst();
		}

		public void inst() {
			this.setId("feedback_requested");
			this.setName("Feedback Requested");
		}
	}

	public static class RemindParticipantsProperty extends BooleanPropertyDefinition {
		public RemindParticipantsProperty() {
			inst();
		}

		public void inst() {
			this.setId("remind_participants");
			this.setName("Remind Participants");
		}
	}

	// =========================================
	// ==== Floating Point Number Properties ===
	// =========================================

	public static class LatitudeProperty extends DoublePropertyDefinition {
		public LatitudeProperty() {
			inst();
			setTestValues();
		}

		public void inst() {
			this.setId("latitude");
			this.setName("Latitude");
		}

		private void setTestValues() {

		}
	}

	public static class LongitudeProperty extends DoublePropertyDefinition {
		public LongitudeProperty() {
			inst();
			setTestValues();
		}

		public void inst() {
			this.setId("longitude");
			this.setName("Longitude");

		}

		private void setTestValues() {

		}
	}

	// =========================================
	// ========= Competence Properties =========
	// =========================================
	public static class RequiredCompetencesProperty extends TextPropertyDefinition {

		public RequiredCompetencesProperty() {
			inst();
		}

		public void inst() {
			this.setName("Required Competences");
			this.setId("required_competences");
			this.setMultiple(true);
		}

	}

	public static class OptionalCompetencesProperty extends TextPropertyDefinition {

		public OptionalCompetencesProperty() {
			inst();
		}

		public void inst() {
			this.setName("Optional Competences");
			this.setId("optional_competences");
			this.setMultiple(true);
		}
	}

	public static class AquireableCompetencesProperty extends TextPropertyDefinition {

		public AquireableCompetencesProperty() {
			inst();
		}

		public void inst() {
			this.setName("Aquirable Competences");
			this.setId("aquireable_competences");
			this.setMultiple(true);
		}
	}

	public static class TaetigkeitsArtProperty extends EnumPropertyDefinition {
		public TaetigkeitsArtProperty() {
			inst();
		}

		public void inst() {
			this.setName("Tätigkeitsart");
			this.setId("taetigkeitsart");
			this.setMultiple(false);
			this.setAllowedValues(new ArrayList<EnumEntry>());
			this.getAllowedValues().add(new EnumEntry(0, "Einsatz", false));
			this.getAllowedValues().add(new EnumEntry(1, "Technischer", true));
			this.getAllowedValues().add(new EnumEntry(1, "Brand", true));
			this.getAllowedValues().add(new EnumEntry(1, "Verkehrsunfall", true));
			this.getAllowedValues().add(new EnumEntry(1, "Tauch", true));
			this.getAllowedValues().add(new EnumEntry(1, "Personen", true));
			this.getAllowedValues().add(new EnumEntry(1, "Höhenrettung", true));
			this.getAllowedValues().add(new EnumEntry(1, "Vorbereitung", true));
			this.getAllowedValues().add(new EnumEntry(1, "Nachbereitung", true));

			this.getAllowedValues().add(new EnumEntry(0, "Ausbildung", false));
			this.getAllowedValues().add(new EnumEntry(1, "Grundausbildung", false));
			this.getAllowedValues().add(new EnumEntry(2, "Truppmann", true));
			this.getAllowedValues().add(new EnumEntry(2, "Truppführer", true));

			this.getAllowedValues().add(new EnumEntry(1, "Erweiterte Grundausbildung", false));
			this.getAllowedValues().add(new EnumEntry(2, "Funk", true));
			this.getAllowedValues().add(new EnumEntry(2, "Atemschutz", true));
			this.getAllowedValues().add(new EnumEntry(2, "Erste Hilfe", true));

			this.getAllowedValues().add(new EnumEntry(1, "Fach- und Sonderausbildung", false));
			this.getAllowedValues().add(new EnumEntry(2, "Nachrichtendienst", true));
			this.getAllowedValues().add(new EnumEntry(2, "Geräte- und Fahrzeugkunde", true));
			this.getAllowedValues().add(new EnumEntry(2, "Atem- und Körperschutz", true));
			this.getAllowedValues().add(new EnumEntry(2, "Technischer FW-Einsatz", true));
			this.getAllowedValues().add(new EnumEntry(2, "Gefährliche Stoffe", true));
			this.getAllowedValues().add(new EnumEntry(2, "Vorbeugender Brandschutz", true));
			this.getAllowedValues().add(new EnumEntry(2, "Verwaltungsdienst", true));

			this.getAllowedValues().add(new EnumEntry(1, "Führungsausbildung", false));
			this.getAllowedValues().add(new EnumEntry(2, "Gruppen-Kommandant", true));
			this.getAllowedValues().add(new EnumEntry(2, "Zugs-Kommandant", true));
			this.getAllowedValues().add(new EnumEntry(2, "Einsatzleiter", true));
			this.getAllowedValues().add(new EnumEntry(2, "Kommandant", true));

			this.getAllowedValues().add(new EnumEntry(1, "Höhere FW-Ausbildung", false));
			this.getAllowedValues().add(new EnumEntry(2, "Bewerter-Lehrgang", true));

			this.getAllowedValues().add(new EnumEntry(1, "Fachweiterbildung", false));
			this.getAllowedValues().add(new EnumEntry(2, "Chlorgas / Erdgas", true));
			this.getAllowedValues().add(new EnumEntry(2, "Stapler / Kranschein", true));
			this.getAllowedValues().add(new EnumEntry(2, "IT", true));
			this.getAllowedValues().add(new EnumEntry(2, "Sanitäter", true));

			this.getAllowedValues().add(new EnumEntry(1, "Überprüfung Mannschaft", false));
			this.getAllowedValues().add(new EnumEntry(2, "Atemschutz\nUnterweisung", true));
			this.getAllowedValues().add(new EnumEntry(2, "Stapler\nUnterweisung", true));
			this.getAllowedValues().add(new EnumEntry(2, "Kran\nUnterweisung", true));

			this.getAllowedValues().add(new EnumEntry(1, "Vorbereitung", true));
			this.getAllowedValues().add(new EnumEntry(1, "Nachbereitung", true));

			this.getAllowedValues().add(new EnumEntry(0, "Bewerbe", false));
			this.getAllowedValues().add(new EnumEntry(1, "FLA", true));
			this.getAllowedValues().add(new EnumEntry(1, "WLA", true));
			this.getAllowedValues().add(new EnumEntry(1, "FULA", true));
			this.getAllowedValues().add(new EnumEntry(1, "FJLA", true));
			this.getAllowedValues().add(new EnumEntry(1, "STRMLA", true));

			this.getAllowedValues().add(new EnumEntry(0, "Leistungsprüfungen", false));
			this.getAllowedValues().add(new EnumEntry(1, "THL", true));
			this.getAllowedValues().add(new EnumEntry(1, "SPRENGLP", true));

			this.getAllowedValues().add(new EnumEntry(0, "Veranstaltung", false));
			this.getAllowedValues().add(new EnumEntry(1, "Intern", false));
			this.getAllowedValues().add(new EnumEntry(2, "Ausflug", true));
			this.getAllowedValues().add(new EnumEntry(2, "Mitgliederversammlung", true));
			this.getAllowedValues().add(new EnumEntry(2, "Jugendlager", true));

			this.getAllowedValues().add(new EnumEntry(1, "Extern\n(Öffentlichkeitsarbeit)", false));
			this.getAllowedValues().add(new EnumEntry(2, "Fest / Ball", true));
			this.getAllowedValues().add(new EnumEntry(2, "Tag der offenen Tür", true));
			this.getAllowedValues().add(new EnumEntry(2, "Kirchenausrückung", true));
			this.getAllowedValues().add(new EnumEntry(2, "Landesfeuerwertag", true));
			this.getAllowedValues().add(new EnumEntry(2, "Friedenslicht", true));

			this.getAllowedValues().add(new EnumEntry(0, "Verwaltung", false));
			this.getAllowedValues().add(new EnumEntry(1, "Fahrzeug", false));
			this.getAllowedValues().add(new EnumEntry(2, "Wartung/Reinigung", true));
			this.getAllowedValues().add(new EnumEntry(2, "Bewegungsfahrt", true));

			this.getAllowedValues().add(new EnumEntry(1, "Geräte", false));
			this.getAllowedValues().add(new EnumEntry(2, "Wartung/Reinigung", true));
			this.getAllowedValues().add(new EnumEntry(2, "Prüfung/Inspektion", true));

			this.getAllowedValues().add(new EnumEntry(1, "Gebäude", false));
			this.getAllowedValues().add(new EnumEntry(2, "Wartung Haustechnik", true));
			this.getAllowedValues().add(new EnumEntry(2, "Reinigung", true));

			this.getAllowedValues().add(new EnumEntry(1, "IT", false));
			this.getAllowedValues().add(new EnumEntry(2, "EDV", true));
			this.getAllowedValues().add(new EnumEntry(2, "Sybos", true));

			this.getAllowedValues().add(new EnumEntry(1, "Sitzungen", false));
			this.getAllowedValues().add(new EnumEntry(2, "Besprechung", true));
			this.getAllowedValues().add(new EnumEntry(2, "JourFix", true));

			this.getAllowedValues().add(new EnumEntry(1, "Öffentlichkeits-\nArbeit", false));
			this.getAllowedValues().add(new EnumEntry(2, "Homepage / Social Media", true));
			this.getAllowedValues().add(new EnumEntry(2, "Presse", true));
			this.getAllowedValues().add(new EnumEntry(2, "Repräsentation\nbei Veranstaltungen", true));
			this.getAllowedValues().add(new EnumEntry(2, "Mitglieder-Werbung", true));
			this.getAllowedValues().add(new EnumEntry(2, "Haussammlung", true));

			this.getAllowedValues().add(new EnumEntry(0, "Projekt", false));
			this.getAllowedValues().add(new EnumEntry(1, "Fahrzeuganschaffung", true));
			this.getAllowedValues().add(new EnumEntry(1, "FW Hausbau", true));
			this.getAllowedValues().add(new EnumEntry(1, "Landesbewerb", true));

		}
	}

	// -----------------------------------------
	// --------------FlexProd Properties
	// -----------------------------------------

	public static class MaxGluehtemperaturProperty extends LongPropertyDefinition {
		public MaxGluehtemperaturProperty() {
			this.setId("maxgluehtemperatur");
			this.setName("Max. Glühtemperatur");
		}
	}

	public static class VerfuegbaresSchutzgasProperty extends TextPropertyDefinition {
		public VerfuegbaresSchutzgasProperty() {
			this.setId("verfuegbaresschutzgas");
			this.setName("Verfügbares Schutzgas");
		}
	}

	public static class BauartProperty extends TextPropertyDefinition {
		public BauartProperty() {
			this.setId("bauart");
			this.setName("Bauart");
		}
	}

	public static class TemperaturhomogenitaetProperty extends LongPropertyDefinition {
		public TemperaturhomogenitaetProperty() {
			this.setId("temperaturhomogenitaet");
			this.setName("Temperaturhomogenität");
		}
	}

	public static class KaltgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public KaltgewalztesMaterialZulaessigProperty() {
			this.setId("kaltgewalztesmaterialzulaessig");
			this.setName("Kaltgewalztes Material zulässig");
		}
	}

	public static class WarmgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public WarmgewalztesMaterialZulaessigProperty() {
			this.setId("warmgewalztesmaterialzulaessig");
			this.setName("Warmgewalztes Material zulässig");
		}
	}

	public static class BundEntfettenProperty extends BooleanPropertyDefinition {
		public BundEntfettenProperty() {
			this.setId("bundentfetten");
			this.setName("Bund Entfetten");
		}
	}

	public static class InnendurchmesserProperty extends LongPropertyDefinition {
		public InnendurchmesserProperty() {
			this.setId("innendurchmesser");
			this.setName("Innendurchmesser");
		}
	}

	public static class AussendurchmesserProperty extends LongPropertyDefinition {
		public AussendurchmesserProperty() {
			this.setId("aussendurchmesser");
			this.setName("Außendurchmesser");
		}
	}

	public static class HoeheProperty extends LongPropertyDefinition {
		public HoeheProperty() {
			this.setId("hoehe");
			this.setName("Höhe");
		}
	}

	public static class GluehzeitProperty extends LongPropertyDefinition {
		public GluehzeitProperty() {
			this.setId("gluehzeit");
			this.setName("Glühzeit");
		}
	}

	public static class DurchsatzProperty extends LongPropertyDefinition {
		public DurchsatzProperty() {
			this.setId("durchsatz");
			this.setName("Durchsatz");
		}
	}

	public static class MoeglicheInnendurchmesserProperty extends LongPropertyDefinition {
		public MoeglicheInnendurchmesserProperty() {
			this.setId("moeglicheinnendurchmesser");
			this.setName("Mögliche Innendurchmesser");
		}
	}

	public static class MaxAussendurchmesserProperty extends LongPropertyDefinition {
		public MaxAussendurchmesserProperty() {
			this.setId("maxaussendurchmesser");
			this.setName("Max. Außendurchmesser");
		}
	}

	public static class MaxChargierhoeheProperty extends LongPropertyDefinition {
		public MaxChargierhoeheProperty() {
			this.setId("maxchargierhoehe");
			this.setName("Max. Chargierhöhe");
		}
	}

	public static class CQI9Property extends BooleanPropertyDefinition {
		public CQI9Property() {
			this.setId("cqi9");
			this.setName("CQI-9");
		}
	}

	public static class TUSProperty extends BooleanPropertyDefinition {
		public TUSProperty() {
			this.setId("tus");
			this.setName("TUS");
		}
	}

	public static class LetzteWartungProperty extends DatePropertyDefinition {
		public LetzteWartungProperty() {
			this.setId("letztewartung");
			this.setName("Letzte Wartung");
		}
	}

	public static class WartungsintervallProperty extends DatePropertyDefinition {
		public WartungsintervallProperty() {
			this.setId("wartungsintervall");
			this.setName("Wartungsintervall");
		}
	}

	public static class BandbreiteProperty extends LongPropertyDefinition {
		public BandbreiteProperty() {
			this.setId("bandbreite");
			this.setName("Bandbreite");
		}
	}

	public static class BandstaerkeProperty extends BooleanPropertyDefinition {
		public BandstaerkeProperty() {
			this.setId("bandstaerke");
			this.setName("Bandstärke");
		}
	}

	public static class WarmgewalztProperty extends BooleanPropertyDefinition {
		public WarmgewalztProperty() {
			this.setId("warmgewalzt");
			this.setName("Warmgewalzt");
		}
	}

	public static class KaltgewalztProperty extends BooleanPropertyDefinition {
		public KaltgewalztProperty() {
			this.setId("kaltgewalzt");
			this.setName("Kaltgewalzt");
		}
	}

	public static class StreckgrenzeProperty extends LongPropertyDefinition {
		public StreckgrenzeProperty() {
			this.setId("streckgrenze");
			this.setName("Streckgrenze");
		}
	}

	public static class ZugfestigkeitProperty extends LongPropertyDefinition {
		public ZugfestigkeitProperty() {
			this.setId("zugfestigkeit");
			this.setName("Zugfestigkeit");
		}
	}

	public static class DehnungProperty extends LongPropertyDefinition {
		public DehnungProperty() {
			this.setId("dehnung");
			this.setName("Dehnung");
		}
	}

	public static class GefuegeProperty extends TextPropertyDefinition {
		public GefuegeProperty() {
			this.setId("gefuege");
			this.setName("Gefüge");
		}
	}

	public static class MaterialBereitgestelltProperty extends BooleanPropertyDefinition {
		public MaterialBereitgestelltProperty() {
			this.setId("materialbereitgestellt");
			this.setName("Material bereitgestellt?");
		}
	}

	public static class LieferortProperty extends TextPropertyDefinition {
		public LieferortProperty() {
			this.setId("lieferort");
			this.setName("Lieferort");
		}
	}

	public static class VerpackungProperty extends TextPropertyDefinition {
		public VerpackungProperty() {
			this.setId("verpackung");
			this.setName("Verpackung");
		}
	}

	public static class TransportartProperty extends TextPropertyDefinition {
		public TransportartProperty() {
			this.setId("transportart");
			this.setName("Transportart");
		}
	}

	public static class MengeProperty extends LongPropertyDefinition {
		public MengeProperty() {
			this.setId("menge");
			this.setName("Menge");
		}
	}

	public static class LieferdatumProperty extends DatePropertyDefinition {
		public LieferdatumProperty() {
			this.setId("lieferdatum");
			this.setName("Lieferdatum");
		}
	}

	public static class IncotermsProperty extends TextPropertyDefinition {
		public IncotermsProperty() {
			this.setId("incoterms");
			this.setName("Inco-Terms");
		}
	}

	public static class ZahlungsbedingungenProperty extends LongTextPropertyDefinition {
		public ZahlungsbedingungenProperty() {
			this.setId("zahlungsbedingungen");
			this.setName("Zahlungsbedingungen");
		}
	}
	///////////////////////////////

//	public static class TestMultiWithRules extends MultiProperty {
//		public TestMultiWithRules() {
//			this.setId("multi_with_rules");
//			this.setName("Multiple Property With Rules");
//			
//			this.setProperties(new ArrayList<>());
//			this.getProperties().add(new NameProperty());
//			this.getProperties().add(new DescriptionProperty());
//			this.getProperties().add(new LongitudeProperty());
//			this.getProperties().add(new LatitudeProperty());
//			
//			this.setRules(new ArrayList<>());
//			this.getRules().add(new MultiPropertyRule(MultiPropertyRuleKind.REQUIRED_OTHER, this.getProperties().get(0).getId(), this.getProperties().get(1).getId()));
//			this.getRules().add(new MultiPropertyRule(MultiPropertyRuleKind.MAX_OTHER, this.getProperties().get(2).getId(), this.getProperties().get(3).getId()));
//			this.getRules().add(new MultiPropertyRule(MultiPropertyRuleKind.MIN_OTHER, this.getProperties().get(2).getId(), this.getProperties().get(3).getId()));
//		
//		}
//	}

	// -----------------------------------------
	// --------------MAP PROPERTY
	// -----------------------------------------

//	
//	public static class MapProperty extends MultiProperty {
//	
//		public MapProperty() {
//			this.setId("map");
//			this.setType(PropertyType.MAP);
//			this.setName("Map Property");
//			this.setProperties(new ArrayList<>());
//			
//			setTestValues();
//		}
//		
//		public void setViewPort(double latitude, double longitude) {
//			if (this.getProperties().size() <= 0) {
//				this.getProperties().add(new MapEntryProperty(latitude, longitude));
//			} else {
//				this.getProperties().set(0, new MapEntryProperty(latitude, longitude));
//			}
//		}
//		
//		public void setMarker(double latitude, double longitude) {
//			if (this.getProperties().size() <= 0) {
//				this.getProperties().add(new MapEntryProperty(latitude, longitude));
//			}
//			this.getProperties().add(new MapEntryProperty(latitude, longitude));
//
//		}
//		
//		public void setArea(double[] latitude, double[] longitude) {
//			if (this.getProperties().size() <= 0) {
//				this.getProperties().add(new MapEntryProperty(latitude[0], longitude[0]));
//			}
//			
//			this.getProperties().add(new MapEntryProperty(latitude, longitude));
//		}
//		
//		private void setTestValues() {
//			
//			this.setViewPort(0, 0);
//			this.setMarker(10, 10);
//			this.setMarker(0, 0);
//			this.setMarker(40, 33);
//						
//			this.setArea(new double[]{10, 12, 13}, new double[]{11, 13, 15});	
//		}
//	}
//	
//	
//	public static class MapEntryProperty extends MultiProperty {
//		
//		public MapEntryProperty() {
//			this.setId(new ObjectId().toHexString());
//			this.setType(PropertyType.MULTI);
//			this.setName("Map Entry");
//			this.setProperties(new ArrayList<>());
//			
//			this.getProperties().add(new LatitudeProperty());
//			this.getProperties().add(new LongitudeProperty());	
//		}
//		
//		public MapEntryProperty(double latitude, double longitude) {
//			this.setId(new ObjectId().toHexString());
//
//			this.setType(PropertyType.MULTI);
//			this.setName("Map Entry");
//			this.setProperties(new ArrayList<>());
//			
//			LatitudeProperty lat = new LatitudeProperty(latitude);
//			this.getProperties().add(lat);
//			
//			LongitudeProperty lng = new LongitudeProperty(longitude);
//			this.getProperties().add(lng);
//		}
//		
//		public MapEntryProperty (double[] latitude, double[] longitude) {
//			this.setId(new ObjectId().toHexString());
//
//			this.setType(PropertyType.MULTI);
//			this.setName("Map Area");
//			this.setProperties(new ArrayList<>());
//			
//			LatitudeProperty lat = new LatitudeProperty(latitude);
//			this.getProperties().add(lat);
//			
//			LongitudeProperty lng = new LongitudeProperty(longitude);
//			this.getProperties().add(lng);
//		}
//	}
//	
//
//	
//	public static class GraphProperty extends MultiProperty {
//		public GraphProperty() {
//			this.setId("graph");
//			this.setName("Graph");
//			this.setType(PropertyType.GRAPH);
//			
//			this.setProperties(new ArrayList<>());
//			
//		}
//		
//		public void addNode(String name) {
//			this.getProperties().add(new GraphPropertyEntry(name));
//		}
//		
//		public void addNode(GraphPropertyEntry entry) {
//			this.getProperties().add(entry);
//		}
//		
//		public void addEdge(String idFrom, String idTo) {
//			GraphPropertyEntry from = (GraphPropertyEntry) this.getProperties().stream().filter(p -> p.getId().equals(idFrom)).findFirst().get();
//			GraphPropertyEntry to = (GraphPropertyEntry) this.getProperties().stream().filter(p -> p.getId().equals(idTo)).findFirst().get();
//			
//			from.addEdge(idTo, to.getName());
//		}
//		
//		public String print() {
//			
//			StringBuilder sb = new StringBuilder();
//			
//			for (Property p : this.getProperties()) {
//				sb.append(((GraphPropertyEntry)p).print());
//			}
//			
//			return sb.toString();
//		}
//	}
//	
//	public static class GraphPropertyEntry extends SingleProperty<String> {
//		public GraphPropertyEntry(String name) {
//			this.setId(name);
//			this.setName(name);
//			this.setKind(PropertyType.TEXT);
//			
//			this.setValues(new ArrayList<ListEntry<String>>());	
//		}
//		
//		
//		
//		public void addEdge(String nodeId, String nodeLabel) {
//			this.getValues().add(new ListEntry<String>(nodeId, nodeLabel));
//		}
//		
//		public String print() {
//			
//			StringBuilder sb = new StringBuilder();
//			sb.append(this.getName());
//			sb.append(": ");
//			
//			for (ListEntry<String> entry : this.getValues()) {
//				sb.append(entry.value);
//				sb.append(" -> ");
//			}
//			sb.append("/\n");
//			
//			return sb.toString();
//		}
//	}
//	
//	//Testgraph
//	
//	/** Graph:
//	 * 0---1
//	 * |  /| \  
//	 * | / |  2
//	 * |/  | /
//	 * 4---3	
//	 * 
//	 */
//	
//	public static void main(String[] args) {
//		GraphProperty graph = new GraphProperty();
//		
//		GraphPropertyEntry n0 = new GraphPropertyEntry("0");
//		GraphPropertyEntry n1 = new GraphPropertyEntry("1");
//		GraphPropertyEntry n2 = new GraphPropertyEntry("2");
//		GraphPropertyEntry n3 = new GraphPropertyEntry("3");
//		GraphPropertyEntry n4 = new GraphPropertyEntry("4");
//
//		n0.addEdge(n1.getId(), n1.getName());
//		n0.addEdge(n4.getId(), n4.getName());
//		
//		n1.addEdge(n0.getId(), n0.getName());
//		n1.addEdge(n4.getId(), n4.getName());
//		n1.addEdge(n2.getId(), n2.getName());
//		n1.addEdge(n3.getId(), n3.getName());
//		
//		n2.addEdge(n1.getId(), n1.getName());
//		n2.addEdge(n3.getId(), n3.getName());
//		
//		n3.addEdge(n1.getId(), n1.getName());
//		n3.addEdge(n4.getId(), n4.getName());
//		n3.addEdge(n2.getId(), n2.getName());
//		
//		n4.addEdge(n3.getId(), n3.getName());
//		n4.addEdge(n0.getId(), n0.getName());
//		n4.addEdge(n1.getId(), n1.getName());
//		
//		graph.addNode(n0);
//		graph.addNode(n1);
//		graph.addNode(n2);
//		graph.addNode(n3);
//		graph.addNode(n4);
//		
//		System.out.println("Adj. List: ");
//		System.out.println(graph.print());
//		
//	}
//	

}
