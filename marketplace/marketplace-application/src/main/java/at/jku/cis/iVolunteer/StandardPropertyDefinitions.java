package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
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

	@Autowired
	public PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired
	public CoreTenantRestClient coreTenantRestClient;
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
//		list.add(new DurchmesserProperty());
//		list.add(new DurchsatzProperty());
//		
//		list.add(new ChargierhilfeProperty());
//		list.add(new WalzartProperty());
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
//	}
	

	
	

	public List<PropertyDefinition<Object>> getAll(String tenantId) {
		List<PropertyDefinition<Object>> properties = this.getAllHeader(tenantId);
		List<PropertyDefinition<Object>> sps = this.getAllSingle(tenantId);
//		List<PropertyDefinition<Object>> mps = this.getAllMulti();
//		List<PropertyDefinition<Object>> sbs = this.getAllSybos();
//		List<PropertyDefinition<Object>> tmwr = this.getTestMultiWithRules();
//		List<PropertyDefinition<Object>> flexProd = this.getAllFlexProdProperties();
		
//		sps.addAll(mps);
//		sps.addAll(sbs);
//		sps.addAll(tmwr);
//		sps.addAll(flexProd);
//		sps.addAll(drahtofen);

		properties.addAll(sps);
		return properties;

	}
	
	public List<PropertyDefinition<Object>> getAllHeader(String tenantId) {
		List<PropertyDefinition<?>> props = new LinkedList<>();
		
		props.add(new IDProperty(tenantId));
		props.add(new EvidenzProperty(tenantId));
		props.add(new DateFromProperty(tenantId));
		props.add(new DateToProperty(tenantId));
		props.add(new TaskType1Property(tenantId));
		props.add(new TaskType2Property(tenantId));
		props.add(new TaskType3Property(tenantId));
		props.add(new TaskType4Property(tenantId));
		props.add(new RankProperty(tenantId));
		props.add(new DurationProperty(tenantId));
		
		return new ArrayList(props);
		
	}

	public List<PropertyDefinition<Object>> getAllSingle(String tenantId) {
		List<PropertyDefinition<?>> props = new LinkedList<>();

		NameProperty np = new NameProperty(tenantId);
		np.inst(tenantId);
		props.add(np);

		DescriptionProperty dp = new DescriptionProperty(tenantId);
		np.inst(tenantId);
		props.add(dp);

		props.add(new WorkflowKeyProperty(tenantId));
		props.add(new ContentProperty(tenantId));
		props.add(new PriorityProperty(tenantId));
		props.add(new ImportancyProperty(tenantId));
		props.add(new RoleProperty(tenantId));
		props.add(new LocationProperty(tenantId));
		props.add(new RequiredEquipmentProperty(tenantId));
		props.add(new WorkshiftProperty(tenantId));
		props.add(new TaskPeriodTypeProperty(tenantId));
		props.add(new KeywordsProperty(tenantId));
		props.add(new RewardsProperty(tenantId));
		props.add(new PostcodeProperty(tenantId));
		props.add(new NumberOfVolunteersProperty(tenantId));
		props.add(new TaskPeriodValueProperty(tenantId));
		props.add(new StartDateProperty(tenantId));
		props.add(new EndDateProperty(tenantId));
		props.add(new UrgentProperty(tenantId));
		props.add(new HighlightedProperty(tenantId));
		props.add(new PromotedProperty(tenantId));
		props.add(new FeedbackRequestedProperty(tenantId));
		props.add(new RemindParticipantsProperty(tenantId));
		props.add(new LatitudeProperty(tenantId));
		props.add(new LongitudeProperty(tenantId));

		RequiredCompetencesProperty cp1 = new RequiredCompetencesProperty(tenantId);
		OptionalCompetencesProperty cp2 = new OptionalCompetencesProperty(tenantId);
		AquireableCompetencesProperty cp3 = new AquireableCompetencesProperty(tenantId);

		cp1.setAllowedValues(addCompetenceLegalValues());
		props.add(cp1);
		cp2.setAllowedValues(addCompetenceLegalValues());
		props.add(cp2);
		cp3.setAllowedValues(addCompetenceLegalValues());
		props.add(cp3);

		props.add(new TaetigkeitsArtProperty(tenantId));

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
	 * Header Properties
	 *
	 */
	public static class IDProperty extends TextPropertyDefinition {

		IDProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("id");
			this.setTenantId(tenantId);
		}
	}
	
	public static class EvidenzProperty extends TextPropertyDefinition {

		EvidenzProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("evidenz");
			this.setTenantId(tenantId);
		}
	}
	
	public static class DateFromProperty extends TextPropertyDefinition {

		DateFromProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Starting Date");
			this.setTenantId(tenantId);
		}
	}
	
	public static class DateToProperty extends TextPropertyDefinition {

		DateToProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("End Date");
			this.setTenantId(tenantId);
		}
	}
	
	public static class TaskType1Property extends TextPropertyDefinition {

		TaskType1Property(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("taskType1");
			this.setTenantId(tenantId);
		}
	}
	public static class TaskType2Property extends TextPropertyDefinition {

		TaskType2Property(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("taskType2");
			this.setTenantId(tenantId);
		}
	}
	
	public static class TaskType3Property extends TextPropertyDefinition {

		TaskType3Property(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("taskType3");
			this.setTenantId(tenantId);
		}
	}
	
	public static class TaskType4Property extends TextPropertyDefinition {

		TaskType4Property(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("taskType4");
			this.setTenantId(tenantId);
		}
	}
	
	public static class RankProperty extends TextPropertyDefinition {

		RankProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("rank");
			this.setTenantId(tenantId);
		}
	}
	
	public static class DurationProperty extends TextPropertyDefinition {

		DurationProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("duration");
			this.setTenantId(tenantId);
		}
	}
	
	

	/**
	 * 
	 * Standard Properties
	 *
	 */
	public static class NameProperty extends TextPropertyDefinition {

		NameProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setType(PropertyType.TEXT);
			this.setName("name");
			this.setRequired(true);
			this.setTenantId(tenantId);

//			List<PropertyConstraint<?>> constraints = new ArrayList<>();
//			constraints.add(new MinimumTextLength(3));
//			constraints.add(new MaximumTextLength(10));
//			constraints.add(new TextPattern("^[A-Za-z][A-Za-zöäüÖÄÜß\\s]*"));
//			this.setPropertyConstraints(new ArrayList(constraints));

		}
	}

	public static class DescriptionProperty extends LongTextPropertyDefinition {

		public DescriptionProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Description");
			this.setRequired(true);
			this.setTenantId(tenantId);
		}
	}

	public static class WorkflowKeyProperty extends TextPropertyDefinition {
		public WorkflowKeyProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Workflow Key");
			this.setTenantId(tenantId);
		}
	}

	public static class ContentProperty extends TextPropertyDefinition {
		public ContentProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Content");
			this.setTenantId(tenantId);
		}
	}

	public static class PriorityProperty extends TextPropertyDefinition {
		public PriorityProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Priority");
			this.setTenantId(tenantId);

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Low");
			legalValues.add("Normal");
			legalValues.add("High");
			legalValues.add("Critical");
			this.setAllowedValues(legalValues);
		}
	}

	public static class ImportancyProperty extends TextPropertyDefinition {
		public ImportancyProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Importancy");
			this.setTenantId(tenantId);

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
		public RoleProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Role");
			this.setTenantId(tenantId);
		}
	}

	public static class LocationProperty extends TextPropertyDefinition {
		public LocationProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Location");
			this.setTenantId(tenantId);
		}
	}

	public static class RequiredEquipmentProperty extends TextPropertyDefinition {
		public RequiredEquipmentProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Required Equipment");
			this.setTenantId(tenantId);
		}
	}

	public static class WorkshiftProperty extends TextPropertyDefinition {
		public WorkshiftProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Allocated Shift");
			this.setTenantId(tenantId);

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
		public TaskPeriodTypeProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Period Type");
			this.setTenantId(tenantId);

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
		public KeywordsProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Keywords");
			this.setTenantId(tenantId);
		}

	}

	//// Rewards ??
	public static class RewardsProperty extends TextPropertyDefinition {
		public RewardsProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Offered Reward(s)");
			this.setTenantId(tenantId);
		}
	}

	// =========================================
	// ========== Number Properties ============
	// =========================================

	public static class PostcodeProperty extends LongPropertyDefinition {
		public PostcodeProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Postcode");
			this.setTenantId(tenantId);

		}
	}

	public static class NumberOfVolunteersProperty extends LongPropertyDefinition {
		public NumberOfVolunteersProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Number of Volunteers");
			this.setTenantId(tenantId);

			List<Long> legalValues = new LinkedList<>();
			for (long i = 1; i <= 10; i++) {
				legalValues.add(i);
			}
			this.setAllowedValues(legalValues);
		}
	}

	public static class TaskPeriodValueProperty extends LongPropertyDefinition {
		public TaskPeriodValueProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Period length");
			this.setTenantId(tenantId);

			List<Long> defaultValues = new ArrayList<>();
			defaultValues.add(1L);
			this.setAllowedValues(defaultValues);
		}
	}

	// =========================================
	// ========== Date Properties ==============
	// =========================================

	public static class StartDateProperty extends DatePropertyDefinition {
		public StartDateProperty(String tenantId) {
			super();
			this.setName("Starting Date");
			this.setTenantId(tenantId);
			setTestValues();
		}

		public void setTestValues() {

			List<Date> defaultValues = new ArrayList<>();
			defaultValues.add(new Date());
			this.setAllowedValues(defaultValues);

		}
	}

	public static class EndDateProperty extends DatePropertyDefinition {
		public EndDateProperty(String tenantId) {
			this.setName("End Date");
			this.setTenantId(tenantId);
			setTestValues();
		}

		public void setTestValues() {

		}
	}

	// =========================================
	// ========== Bool Properties ==============
	// =========================================

	public static class UrgentProperty extends BooleanPropertyDefinition {
		public UrgentProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Urgent");
			this.setTenantId(tenantId);
		}
	}

	public static class HighlightedProperty extends BooleanPropertyDefinition {
		public HighlightedProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Highlighted");
			this.setTenantId(tenantId);
		}
	}

	public static class PromotedProperty extends BooleanPropertyDefinition {
		public PromotedProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Promotion");
			this.setTenantId(tenantId);
		}
	}

	public static class FeedbackRequestedProperty extends BooleanPropertyDefinition {
		public FeedbackRequestedProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Feedback Requested");
			this.setTenantId(tenantId);
		}
	}

	public static class RemindParticipantsProperty extends BooleanPropertyDefinition {
		public RemindParticipantsProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Remind Participants");
			this.setTenantId(tenantId);
		}
	}

	// =========================================
	// ==== Floating Point Number Properties ===
	// =========================================

	public static class LatitudeProperty extends DoublePropertyDefinition {
		public LatitudeProperty(String tenantId) {
			inst(tenantId);
			setTestValues();
		}

		public void inst(String tenantId) {
			this.setName("Latitude");
			this.setTenantId(tenantId);
		}

		private void setTestValues() {

		}
	}

	public static class LongitudeProperty extends DoublePropertyDefinition {
		public LongitudeProperty(String tenantId) {
			inst(tenantId);
			setTestValues();
		}

		public void inst(String tenantId) {
			this.setName("Longitude");
			this.setTenantId(tenantId);
		}

		private void setTestValues() {

		}
	}

	// =========================================
	// ========= Competence Properties =========
	// =========================================
	public static class RequiredCompetencesProperty extends TextPropertyDefinition {
		public RequiredCompetencesProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Required Competences");
			this.setMultiple(true);
			this.setTenantId(tenantId);
		}

	}

	public static class OptionalCompetencesProperty extends TextPropertyDefinition {
		public OptionalCompetencesProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Optional Competences");
			this.setMultiple(true);
			this.setTenantId(tenantId);
		}
	}

	public static class AquireableCompetencesProperty extends TextPropertyDefinition {
		public AquireableCompetencesProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Aquirable Competences");
			this.setMultiple(true);
			this.setTenantId(tenantId);
		}
	}

	public static class TaetigkeitsArtProperty extends EnumPropertyDefinition {
		public TaetigkeitsArtProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Tätigkeitsart");
			this.setTenantId(tenantId);
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
		public MaxGluehtemperaturProperty(String tenantId) {
			this.setName("Max. Glühtemperatur");
			this.setTenantId(tenantId);
		}
	}

	public static class VerfuegbaresSchutzgasProperty extends TextPropertyDefinition {
		public VerfuegbaresSchutzgasProperty(String tenantId) {
			this.setName("Verfügbares Schutzgas");
			this.setTenantId(tenantId);
		}
	}

	public static class BauartProperty extends TextPropertyDefinition {
		public BauartProperty() {
			this.setName("Bauart");
			this.setTenantId(tenantId);
		}
	}

	public static class TemperaturhomogenitaetProperty extends LongPropertyDefinition {
		public TemperaturhomogenitaetProperty() {
			this.setName("Temperaturhomogenität");
			this.setTenantId(tenantId);
		}
	}

	public static class KaltgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public KaltgewalztesMaterialZulaessigProperty() {
			this.setName("Kaltgewalztes Material zulässig");
			this.setTenantId(tenantId);
		}
	}

	public static class WarmgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public WarmgewalztesMaterialZulaessigProperty() {
			this.setName("Warmgewalztes Material zulässig");
			this.setTenantId(tenantId);
		}
	}

	public static class BundEntfettenProperty extends BooleanPropertyDefinition {
		public BundEntfettenProperty() {
			this.setName("Bund Entfetten");
			this.setTenantId(tenantId);
		}
	}
	
	public static class ChargierhilfeProperty extends TextPropertyDefinition {
		public ChargierhilfeProperty() {
			this.setId("chargierhilfe");
			this.setName("Chargierhilfe");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Konvektoren");
			this.getAllowedValues().add("Tragerahmen");
			this.getAllowedValues().add("Zwischenrahmen");
			this.getAllowedValues().add("Kronenstöcke");
			this.getAllowedValues().add("Chargierkörbe");
		}
	}

	public static class InnendurchmesserProperty extends LongPropertyDefinition {
		public InnendurchmesserProperty() {
			this.setName("Innendurchmesser");
			this.setTenantId(tenantId);
		}

		public static class AussendurchmesserProperty extends LongPropertyDefinition {
			public AussendurchmesserProperty() {
				this.setName("Außendurchmesser");
				this.setTenantId(tenantId);
			}
		}

		public static class HoeheProperty extends LongPropertyDefinition {
			public HoeheProperty() {
				this.setName("Höhe");
				this.setTenantId(tenantId);
			}
		}

		public static class GluehzeitProperty extends LongPropertyDefinition {
			public GluehzeitProperty() {
				this.setName("Glühzeit");
				this.setTenantId(tenantId);
			}
		}

		public static class DurchsatzProperty extends LongPropertyDefinition {
			public DurchsatzProperty() {
				this.setName("Durchsatz");
				this.setTenantId(tenantId);
			}
		}

		public static class MoeglicheInnendurchmesserProperty extends LongPropertyDefinition {
			public MoeglicheInnendurchmesserProperty() {
				this.setName("Mögliche Innendurchmesser");
				this.setTenantId(tenantId);
			}
		}

		public static class MaxAussendurchmesserProperty extends LongPropertyDefinition {
			public MaxAussendurchmesserProperty() {
				this.setName("Max. Außendurchmesser");
				this.setTenantId(tenantId);
			}
		}

		public static class MaxChargierhoeheProperty extends LongPropertyDefinition {
			public MaxChargierhoeheProperty() {
				this.setName("Max. Chargierhöhe");
				this.setTenantId(tenantId);
			}
		}

		public static class CQI9Property extends BooleanPropertyDefinition {
			public CQI9Property() {
				this.setName("CQI-9");
				this.setTenantId(tenantId);
			}
		}

		public static class TUSProperty extends BooleanPropertyDefinition {
			public TUSProperty() {
				this.setId("tus");
				this.setName("TUS");
				this.setTenantId(tenantId);
			}
		}

		public static class LetzteWartungProperty extends DatePropertyDefinition {
			public LetzteWartungProperty() {
				this.setName("Letzte Wartung");
				this.setTenantId(tenantId);
			}
		}

		public static class WartungsintervallProperty extends DatePropertyDefinition {
			public WartungsintervallProperty() {
				this.setName("Wartungsintervall");
				this.setTenantId(tenantId);
			}
		}

		public static class BandbreiteProperty extends LongPropertyDefinition {
			public BandbreiteProperty() {
				this.setName("Bandbreite");
				this.setTenantId(tenantId);
			}
		}

		public static class BandstaerkeProperty extends BooleanPropertyDefinition {
			public BandstaerkeProperty() {
				this.setName("Bandstärke");
				this.setTenantId(tenantId);
			}
		}

		public static class WarmgewalztProperty extends BooleanPropertyDefinition {
			public WarmgewalztProperty() {
				this.setName("Warmgewalzt");
				this.setTenantId(tenantId);
			}
		}

		public static class KaltgewalztProperty extends BooleanPropertyDefinition {
			public KaltgewalztProperty() {
				this.setName("Kaltgewalzt");
				this.setTenantId(tenantId);
			}
		}

		public static class StreckgrenzeProperty extends LongPropertyDefinition {
			public StreckgrenzeProperty() {
				this.setName("Streckgrenze");
				this.setTenantId(tenantId);
			}
		}

		public static class ZugfestigkeitProperty extends LongPropertyDefinition {
			public ZugfestigkeitProperty() {
				this.setName("Zugfestigkeit");
				this.setTenantId(tenantId);
			}
		}

		public static class DehnungProperty extends LongPropertyDefinition {
			public DehnungProperty() {
				this.setName("Dehnung");
				this.setTenantId(tenantId);
			}
		}

		public static class GefuegeProperty extends TextPropertyDefinition {
			public GefuegeProperty() {
				this.setName("Gefüge");
				this.setTenantId(tenantId);
			}
		}

		public static class MaterialBereitgestelltProperty extends BooleanPropertyDefinition {
			public MaterialBereitgestelltProperty() {
				this.setName("Material bereitgestellt?");
				this.setTenantId(tenantId);
			}
		}

		public static class LieferortProperty extends TextPropertyDefinition {
			public LieferortProperty() {
				this.setName("Lieferort");
				this.setTenantId(tenantId);
			}
		}

		public static class VerpackungProperty extends TextPropertyDefinition {
			public VerpackungProperty() {
				this.setName("Verpackung");
				this.setTenantId(tenantId);
			}
		}

		public static class TransportartProperty extends TextPropertyDefinition {
			public TransportartProperty() {
				this.setName("Transportart");
				this.setTenantId(tenantId);
			}
		}

		public static class MengeProperty extends LongPropertyDefinition {
			public MengeProperty() {
				this.setName("Menge");
				this.setTenantId(tenantId);
			}
		}

		public static class LieferdatumProperty extends DatePropertyDefinition {
			public LieferdatumProperty() {
				this.setName("Lieferdatum");
				this.setTenantId(tenantId);
			}
		}

		public static class IncotermsProperty extends TextPropertyDefinition {
			public IncotermsProperty() {
				this.setName("Inco-Terms");
				this.setTenantId(tenantId);
			}
		}

		public static class ZahlungsbedingungenProperty extends LongTextPropertyDefinition {
			public ZahlungsbedingungenProperty() {
				this.setName("Zahlungsbedingungen");
				this.setTenantId(tenantId);
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
}
