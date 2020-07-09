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
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.BooleanPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.DatePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.DoublePropertyDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.EnumPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.LongPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.LongTextPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinitionTypes.TextPropertyDefinition;
//import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class StandardPropertyDefinitions {

	@Autowired public PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired public CoreTenantRestClient coreTenantRestClient;

	public StandardPropertyDefinitions() {

	}
	
	public List<PropertyDefinition<Object>> getAlliVolunteer(String tenantId) {
		List<PropertyDefinition<Object>> properties = getAllHeader(tenantId);
		properties.addAll(getAllGeneric(tenantId));
		
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

		//...
		
		props.add(new OrtProperty(tenantId));
		return new ArrayList(props);

	}

	public List<PropertyDefinition<Object>> getAllGeneric(String tenantId) {
		List<PropertyDefinition<?>> props = new LinkedList<>();

		props.add(new NameProperty(tenantId));
		props.add(new DescriptionProperty(tenantId));
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
		props.add(new VolunteerAgeProperty(tenantId));

		return new ArrayList(props);

	}

	public List<PropertyDefinition<Object>> getAllFlexProdProperties(String tenantId) {
		List<PropertyDefinition<?>> list = new LinkedList<>();

		list.add(new MaxGluehtemperaturProperty(tenantId));
		list.add(new VerfuegbaresSchutzgasProperty(tenantId));
		list.add(new BauartProperty(tenantId));
		list.add(new TemperaturhomogenitaetProperty(tenantId));
		list.add(new KaltgewalztesMaterialZulaessigProperty(tenantId));
		list.add(new WarmgewalztesMaterialZulaessigProperty(tenantId));

		list.add(new BundEntfettenProperty(tenantId));

		list.add(new InnendurchmesserProperty(tenantId));
		list.add(new AussendurchmesserProperty(tenantId));
		list.add(new HoeheProperty(tenantId));

		list.add(new GluehzeitProperty(tenantId));
		list.add(new DurchmesserProperty(tenantId));
		list.add(new DurchsatzProperty(tenantId));
		
		list.add(new ChargierhilfeProperty(tenantId));
		list.add(new WalzartProperty(tenantId));

		list.add(new MoeglicheInnendurchmesserProperty(tenantId));
		list.add(new MaxAussendurchmesserProperty(tenantId));
		list.add(new MaxChargierhoeheProperty(tenantId));

		list.add(new CQI9Property(tenantId));
		list.add(new TUSProperty(tenantId));

		list.add(new LetzteWartungProperty(tenantId));
		list.add(new WartungsintervallProperty(tenantId));

		list.add(new BandBreiteProperty(tenantId));
		list.add(new BandstaerkeProperty(tenantId));

		list.add(new WarmgewalztProperty(tenantId));
		list.add(new KaltgewalztProperty(tenantId));

		list.add(new StreckgrenzeProperty(tenantId));
		list.add(new ZugfestigkeitProperty(tenantId));
		list.add(new DehnungProperty(tenantId));

		list.add(new GefuegeProperty(tenantId));

		list.add(new MaterialBereitgestelltProperty(tenantId));
		list.add(new LieferortProperty(tenantId));
		list.add(new VerpackungProperty(tenantId));
		list.add(new TransportartProperty(tenantId));
		list.add(new MengeProperty(tenantId));
		list.add(new LieferdatumProperty(tenantId));
		list.add(new IncotermsProperty(tenantId));

		list.add(new ZahlungsbedingungenProperty(tenantId));

		list.add(new TitelProperty(tenantId));
		list.add(new ProdukttypProperty(tenantId));
		list.add(new MengeProperty(tenantId));
		list.add(new MinimaleMengeProperty(tenantId));
		list.add(new LieferdatumProperty(tenantId));
		list.add(new WerkstoffBereitgestelltProperty(tenantId));
		list.add(new BeschreibungZusatzinfoProperty(tenantId));
		
		list.add(new DurchmesserInnenProperty(tenantId));
		list.add(new DurchmesserAussenProperty(tenantId));
		list.add(new HoeheProperty(tenantId));
		
		list.add(new WerkstoffProperty(tenantId));
		list.add(new WerkstoffFreitextProperty(tenantId));
		list.add(new ZugfestigkeitProperty(tenantId));
		
		list.add(new SchutzgasProperty(tenantId));
		list.add(new GluehreiseProperty(tenantId));
		list.add(new TemperaturhomogenitaetProperty(tenantId));
		
		list.add(new OberflaechenqualitaetProperty(tenantId));
		list.add(new ZusaetzlicheProduktinformationenProperty(tenantId));
		
		list.add(new IncotermsProperty(tenantId));
		list.add(new LieferortProperty(tenantId));
		list.add(new AbholortProperty(tenantId));
		list.add(new VerpackungsvorgabenProperty(tenantId));
		
		list.add(new BandDickeProperty(tenantId));
		
		list.add(new DurchmesserKronenstockProperty(tenantId));
		list.add(new MaximaldurchmesserBundProperty(tenantId));
		
		list.add(new DurchmesserDornProperty(tenantId));
		list.add(new InnendurchmesserOfenProperty(tenantId));
		
		list.add(new OfenHoeheProperty(tenantId));
		list.add(new MaxGluehtemperaturProperty(tenantId));
		list.add(new TemperaturhomogenitaetProperty(tenantId));
		list.add(new ErforderlicheTemperaturhomogenitaetProperty(tenantId));
		list.add(new AufheizrateProperty(tenantId));
		list.add(new AbkuehlrateProperty(tenantId));
		list.add(new MaxAnteilH2Property(tenantId));
		list.add(new KapazitaetProperty(tenantId));
		list.add(new GluehprogrammVerfuegbarProperty(tenantId));
		
		
		return new ArrayList(list);
	}

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

	public static class DateFromProperty extends DatePropertyDefinition {

		DateFromProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Starting Date");
			this.setTenantId(tenantId);
		}
	}

	public static class DateToProperty extends DatePropertyDefinition {

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
	
	public static class OrtProperty extends TextPropertyDefinition {

		OrtProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("ort");
			this.setTenantId(tenantId);
		}
	}

	/**
	 * 
	 * Standard Properties
	 *
	 */
	
	// =========================================
	// ========== Text Properties ==============
	// =========================================
	public static class NameProperty extends TextPropertyDefinition {

		NameProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setType(PropertyType.TEXT);
			this.setName("name");
			this.setRequired(false);
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

	public static class VolunteerAgeProperty extends LongPropertyDefinition {
		public VolunteerAgeProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Alter");
			this.setTenantId(tenantId);
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

	// -----------------------------------------
	// --------------FlexProd Properties
	// -----------------------------------------

	public static class VerfuegbaresSchutzgasProperty extends TextPropertyDefinition {
		public VerfuegbaresSchutzgasProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("verfuegbaresschutzgas");
			this.setName("Verfügbares Schutzgas");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("H2");
			this.getAllowedValues().add("N2");
			this.getAllowedValues().add("75% N2");
		}
	}

	public static class BauartProperty extends TextPropertyDefinition {
		public BauartProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("bauart");
			this.setName("Bauart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Band");
			this.getAllowedValues().add("Draht");
		}
	}

	public static class KaltgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public KaltgewalztesMaterialZulaessigProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("kaltgewalztesmaterialzulaessig");
			this.setName("Kaltgewalztes Material zulässig");
		}
	}

	public static class WarmgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public WarmgewalztesMaterialZulaessigProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("warmgewalztesmaterialzulaessig");
			this.setName("Warmgewalztes Material zulässig");
		}
	}

	public static class BundEntfettenProperty extends BooleanPropertyDefinition {
		public BundEntfettenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("bundentfetten");
			this.setName("Bund Entfetten");
		}
	}

	public static class ChargierhilfeProperty extends TextPropertyDefinition {
		public ChargierhilfeProperty(String tenantId) {
			this.setTenantId(tenantId);
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
		public InnendurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("innendurchmesser");
			this.setName("Innendurchmesser");
		}
	}

	public static class AussendurchmesserProperty extends LongPropertyDefinition {
		public AussendurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("aussendurchmesser");
			this.setName("Außendurchmesser");
		}
	}

	public static class GluehzeitProperty extends LongPropertyDefinition {
		public GluehzeitProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("gluehzeit");
			this.setName("Glühzeit");
		}
	}

	public static class DurchsatzProperty extends LongPropertyDefinition {
		public DurchsatzProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchsatz");
			this.setName("Durchsatz");
		}
	}

	public static class MoeglicheInnendurchmesserProperty extends LongPropertyDefinition {
		public MoeglicheInnendurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("moeglicheinnendurchmesser");
			this.setName("Mögliche Innendurchmesser");
			this.setMultiple(true);
		}
	}

	public static class MaxAussendurchmesserProperty extends LongPropertyDefinition {
		public MaxAussendurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("maxaussendurchmesser");
			this.setName("Max. Außendurchmesser");
		}
	}

	public static class MaxChargierhoeheProperty extends LongPropertyDefinition {
		public MaxChargierhoeheProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("maxchargierhoehe");
			this.setName("Max. Chargierhöhe");
		}
	}

	public static class CQI9Property extends BooleanPropertyDefinition {
		public CQI9Property(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("cqi9");
			this.setName("CQI-9");
		}
	}

	public static class TUSProperty extends BooleanPropertyDefinition {
		public TUSProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("tus");
			this.setName("TUS");
		}
	}

	public static class LetzteWartungProperty extends DatePropertyDefinition {
		public LetzteWartungProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("letztewartung");
			this.setName("Letzte Wartung");
		}
	}

	public static class WartungsintervallProperty extends DatePropertyDefinition {
		public WartungsintervallProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("wartungsintervall");
			this.setName("Wartungsintervall");
		}
	}

	public static class BandstaerkeProperty extends BooleanPropertyDefinition {
		public BandstaerkeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("bandstaerke");
			this.setName("Bandstärke");
		}
	}

	public static class WarmgewalztProperty extends BooleanPropertyDefinition {
		public WarmgewalztProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("warmgewalzt");
			this.setName("Warmgewalzt");
		}
	}

	public static class KaltgewalztProperty extends BooleanPropertyDefinition {
		public KaltgewalztProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("kaltgewalzt");
			this.setName("Kaltgewalzt");
		}
	}

	public static class WalzartProperty extends TextPropertyDefinition {
		public WalzartProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("walzart");
			this.setName("Walzart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Warmgewalzt");
			this.getAllowedValues().add("Kaltgewalzt");
		}
	}

	public static class StreckgrenzeProperty extends LongPropertyDefinition {
		public StreckgrenzeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("streckgrenze");
			this.setName("Streckgrenze");
		}
	}

	public static class DehnungProperty extends LongPropertyDefinition {
		public DehnungProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("dehnung");
			this.setName("Dehnung");
		}
	}

	public static class GefuegeProperty extends TextPropertyDefinition {
		public GefuegeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("gefuege");
			this.setName("Gefüge");
		}
	}

	public static class MaterialBereitgestelltProperty extends BooleanPropertyDefinition {
		public MaterialBereitgestelltProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("materialbereitgestellt");
			this.setName("Material bereitgestellt?");
		}
	}

	public static class VerpackungProperty extends TextPropertyDefinition {
		public VerpackungProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("verpackung");
			this.setName("Verpackung");
		}
	}

	public static class TransportartProperty extends TextPropertyDefinition {
		public TransportartProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("transportart");
			this.setName("Transportart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("LKW");
			this.getAllowedValues().add("Zug");
			this.getAllowedValues().add("Schiff");
			this.getAllowedValues().add("Sonstiges");
		}
	}

	public static class ZahlungsbedingungenProperty extends LongTextPropertyDefinition {
		public ZahlungsbedingungenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("zahlungsbedingungen");
			this.setName("Zahlungsbedingungen");
		}
	}

	public static class TitelProperty extends TextPropertyDefinition {
		TitelProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("titel");
			this.setName("Titel");
		}
	}

	public static class ProdukttypProperty extends TextPropertyDefinition {
		ProdukttypProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("produkttyp");
			this.setName("Produkttyp");

			this.setAllowedValues(new ArrayList<String>());
			this.getAllowedValues().add("Band");
			this.getAllowedValues().add("Draht");
			this.getAllowedValues().add("Band & Draht");
		}
	}

	public static class MengeProperty extends DoublePropertyDefinition {
		MengeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("menge");
			this.setName("Menge");
			this.setUnit("t");
		}
	}

	public static class MinimaleMengeProperty extends DoublePropertyDefinition {
		MinimaleMengeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("minimale_menge");
			this.setName("minimale Menge");
			this.setUnit("t");
		}
	}

	public static class LieferdatumProperty extends DatePropertyDefinition {
		LieferdatumProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("lieferdatum");
			this.setName("Lieferdatum (spätestens)");
		}
	}

	public static class WerkstoffBereitgestelltProperty extends TextPropertyDefinition {
		WerkstoffBereitgestelltProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("werkstoff_bereitgestellt");
			this.setName("Werkstoff bereitgestellt");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("Ja");
			this.getAllowedValues().add("Nein");
		}
	}

	public static class BeschreibungZusatzinfoProperty extends LongTextPropertyDefinition {
		BeschreibungZusatzinfoProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("allgemeine_beschreibung");
			this.setName("allgemeine Beschreibung / Zusatzinformationen");
		}
	}

	public static class DurchmesserInnenProperty extends LongPropertyDefinition {
		public DurchmesserInnenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser_innen");
			this.setName("Durchmesser (innen)");
			this.setUnit("mm");
		}
	}

	public static class DurchmesserAussenProperty extends LongPropertyDefinition {
		public DurchmesserAussenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser_aussen");
			this.setName("Durchmesser (außen)");
			this.setUnit("mm");
		}
	}

	public static class HoeheProperty extends LongPropertyDefinition {
		public HoeheProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("hoehe");
			this.setName("Höhe");
			this.setUnit("mm");
		}
	}

	public static class WerkstoffProperty extends TextPropertyDefinition {
		public WerkstoffProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("werkstoff");
			this.setName("Werkstoff");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("Eintrag 1");
			this.getAllowedValues().add("Eintrag 2");
			this.getAllowedValues().add("Eintrag 3");
			this.getAllowedValues().add("...");
			this.getAllowedValues().add("Freitext");
		}
	}

	public static class DurchmesserProperty extends LongPropertyDefinition {
		public DurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser");
			this.setName("Durchmesser");
			this.setUnit("mm");
		}
	}

	public static class WerkstoffFreitextProperty extends LongTextPropertyDefinition {
		public WerkstoffFreitextProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("werkstoff_freitext");
			this.setName("Werkstoff (Freitext)");
		}
	}

	public static class ZugfestigkeitProperty extends LongPropertyDefinition {
		public ZugfestigkeitProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("zugfestigkeit");
			this.setName("Zugfestigkeit");
			this.setUnit("N/mm²");
		}
	}

	public static class SchutzgasProperty extends TextPropertyDefinition {
		public SchutzgasProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("schutzgas");
			this.setName("Schutzgas");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("H2 0%, N2 100%");
			this.getAllowedValues().add("H2 10%, N2 90%");
			this.getAllowedValues().add("H2 20%, N2 80%");
			this.getAllowedValues().add("H2 30%, N2 70%");
			this.getAllowedValues().add("H2 40%, N2 60%");
			this.getAllowedValues().add("H2 50%, N2 50%");
			this.getAllowedValues().add("H2 60%, N2 40%");
			this.getAllowedValues().add("H2 70%, N2 30%");
			this.getAllowedValues().add("H2 80%, N2 20%");
			this.getAllowedValues().add("H2 90%, N2 10%");
			this.getAllowedValues().add("H2 100%, N2 0%");
		}
	}

	public static class GluehreiseProperty extends TextPropertyDefinition {
		public GluehreiseProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("gluehreise");
			this.setName("Glühprogramm / -reise");
		}
	}

	public static class ErforderlicheTemperaturhomogenitaetProperty extends LongPropertyDefinition {
		public ErforderlicheTemperaturhomogenitaetProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("erforderliche_temperaturhomogenitaet");
			this.setName("erforderliche Temperaturhomogenität");
			this.setUnit("°C (+/-)");
		}
	}

	public static class OberflaechenqualitaetProperty extends TextPropertyDefinition {
		public OberflaechenqualitaetProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("oberflaechenqualitaet");
			this.setName("Oberflächenqualität");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("blank");
			this.getAllowedValues().add("schwarz");
		}
	}

	public static class ZusaetzlicheProduktinformationenProperty extends LongTextPropertyDefinition {
		public ZusaetzlicheProduktinformationenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("zusaetzliche_produktinformationen");
			this.setName("Zusätzliche Produkt- und Bearbeitungsinformationen");
		}
	}

	public static class IncotermsProperty extends TextPropertyDefinition {
		public IncotermsProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("incoterms");
			this.setName("Incoterms");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("EXW");
			this.getAllowedValues().add("DAP");
		}
	}

	public static class LieferortProperty extends TextPropertyDefinition {
		public LieferortProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("lieferort");
			this.setName("Lieferort");
		}
	}

	public static class AbholortProperty extends TextPropertyDefinition {
		public AbholortProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("abholort");
			this.setName("Abholort");
		}
	}

	public static class VerpackungsvorgabenProperty extends LongTextPropertyDefinition {
		public VerpackungsvorgabenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("verpackungsvorgaben");
			this.setName("Verpackungsvorgaben");
		}
	}

	public static class BandDickeProperty extends LongPropertyDefinition {
		public BandDickeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("banddicke");
			this.setName("Banddicke");
			this.setUnit("mm");
		}
	}

	public static class BandBreiteProperty extends LongPropertyDefinition {
		public BandBreiteProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("bandbreite");
			this.setName("Bandbreite");
			this.setUnit("mm");
			;
		}
	}

	public static class DurchmesserKronenstockProperty extends LongPropertyDefinition {
		public DurchmesserKronenstockProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser_kronenstock");
			this.setName("Durchmesser Kronenstock");
			this.setUnit("mm");
			;
		}
	}

	public static class MaximaldurchmesserBundProperty extends LongPropertyDefinition {
		public MaximaldurchmesserBundProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("maximaldurchmesser_bund");
			this.setName("Maximaldurchmesser Bund");
			this.setUnit("mm");
			;
		}
	}

	public static class DurchmesserDornProperty extends LongPropertyDefinition {
		public DurchmesserDornProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser_dorn");
			this.setName("Durchmesser Dorn");
			this.setUnit("mm");
			;
		}
	}

	public static class InnendurchmesserOfenProperty extends LongPropertyDefinition {
		public InnendurchmesserOfenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("innendurchmesser_ofen");
			this.setName("Innendurchmesser Ofen");
			this.setUnit("mm");
			;
		}
	}

	public static class OfenHoeheProperty extends LongPropertyDefinition {
		public OfenHoeheProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("ofenhoehe");
			this.setName("Ofenhöhe");
			this.setUnit("mm");
			;
		}
	}

	public static class MaxGluehtemperaturProperty extends LongPropertyDefinition {
		public MaxGluehtemperaturProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("max_gluehtemperatur");
			this.setName("Max. Glühtemperatur");
			this.setUnit("°C");
			;
		}
	}

	public static class TemperaturhomogenitaetProperty extends LongPropertyDefinition {
		public TemperaturhomogenitaetProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("temperaturhomogenitaet");
			this.setName("Temperaturhomogenität");
			this.setUnit("°C");
			;
		}
	}

	public static class AufheizrateProperty extends DoublePropertyDefinition {
		public AufheizrateProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("aufheizrate");
			this.setName("Aufheizrate");
		}
	}

	public static class AbkuehlrateProperty extends DoublePropertyDefinition {
		public AbkuehlrateProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("abkuehlrate");
			this.setName("Abkühlrate");
		}
	}

	public static class GluehprogrammVerfuegbarProperty extends BooleanPropertyDefinition {
		public GluehprogrammVerfuegbarProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("gluehprogramm_verfuegbar");
			this.setName("Glühprogramm /-reise verfügbar?");
		}
	}

	public static class MaxAnteilH2Property extends LongPropertyDefinition {
		public MaxAnteilH2Property(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("max_anteil_h2");
			this.setName("Maximaler Anteil H2");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add(10L);
			this.getAllowedValues().add(20L);
			this.getAllowedValues().add(30L);
			this.getAllowedValues().add(40L);
			this.getAllowedValues().add(50L);
			this.getAllowedValues().add(60L);
			this.getAllowedValues().add(70L);
			this.getAllowedValues().add(80L);
			this.getAllowedValues().add(90L);
			this.getAllowedValues().add(100L);
			this.setUnit("%");
		}
	}

	public static class KapazitaetProperty extends TextPropertyDefinition {
		public KapazitaetProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("kapazitaet");
			this.setName("Kapazität");
		}
	}

}
