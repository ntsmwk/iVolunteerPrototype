package at.jku.cis.iVolunteer;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.property.BooleanProperty;
import at.jku.cis.iVolunteer.model.property.DateProperty;
import at.jku.cis.iVolunteer.model.property.DoubleProperty;
import at.jku.cis.iVolunteer.model.property.MultipleProperty;
import at.jku.cis.iVolunteer.model.property.NumberProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.TextProperty;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.rule.Rule;
import at.jku.cis.iVolunteer.model.property.rule.RuleKind;


@Component
public class StandardProperties {
	
	
	@Autowired public CompetenceRepository competenceRepository;
	@Autowired public PropertyRepository propertyRepository;

	//=========================================
	//========== Text Properties ==============
	//=========================================
	
	public StandardProperties() {
		
	}
	
	public StandardProperties(CompetenceRepository cp, PropertyRepository pp) {
		this.competenceRepository = cp;
		this.propertyRepository = pp;
	}
	
	
	public List<Property> getAllSingle() {
		List<Property> props = new LinkedList<>();
		
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
		
		cp1.setLegalValues(addCompetenceLegalValues());
		props.add(cp1);
		cp2.setLegalValues(addCompetenceLegalValues());
		props.add(cp2);
		cp3.setLegalValues(addCompetenceLegalValues());
		props.add(cp3);
		
		
		
		
		List<Property> ret = new ArrayList<>(props);
		return ret;
		
	}
	
	public List<Property> getAllMulti() {
		List<Property> props = new LinkedList<>();
		
		MultipleProperty mp = new TestMultiProperty();
		List<Property> multiProps = new LinkedList<>();

		multiProps.add(new PostcodeProperty());
		multiProps.add(new LatitudeProperty());
		multiProps.add(new LongitudeProperty());
		MultipleProperty mp11 = new TestMultiProperty();
		mp11.setId("nested1");
		
		MultipleProperty mp111 = new TestMultiProperty();
		mp111.setProperties(new ArrayList<>(multiProps));
		mp111.setId("nested2");
		
		
		mp11.setProperties(new ArrayList<>(multiProps));
		mp11.getProperties().add(mp111);
		
		
		multiProps.add(mp11);
		
		
		
		mp.setProperties(new ArrayList<>(multiProps));
		
		props.add(mp);
		
		props.add(new MapProperty());
		
		List<Property> ret = new ArrayList<>(props);
		return ret;
	}
	
	public List<Property> getAllSybos() {
		List<Property> list = new LinkedList<>();
		
		list.add(new DurationTimeProperty());
		list.add(new AreaOfExpertiseProperty());
		list.add(new PlanningTypeProperty());
		list.add(new DurationHoursProperty());
		list.add(new PointsProperty());
		list.add(new DepartmentProperty());
		list.add(new StreetProperty());
		list.add(new HouseNumberProperty());
		list.add(new DoorNumberProperty());
		list.add(new CityProperty());
		list.add(new AddressProperty());
		list.add(new CommentsProperty());
		list.add(new VehicleSelectionProperty());
		list.add(new EventTypeProperty());
		list.add(new PublicEventProperty());
		list.add(new PaidProperty());
		list.add(new PrerequisitesProperty());
		list.add(new ActivityGroupProperty());
		list.add(new RegisterDateProperty());
		list.add(new EducationClassProperty());
		list.add(new GradeProperty());
		
		return new ArrayList<>(list);
	}
	
	public List<Property> getAll() {
		List<Property> sps = this.getAllSingle();
		List<Property> mps = this.getAllMulti();
		List<Property> sbs = this.getAllSybos();
	
		sps.addAll(mps);
		sps.addAll(sbs);
		
		return sps;
	
	}
	
	
	
	public List<ListEntry<String>> addCompetenceLegalValues() {
	
		List<ListEntry<String>> legalValues = new LinkedList<ListEntry<String>>();
		
		
		
		for (Competence c : competenceRepository.findAll()) {
			legalValues.add(new ListEntry<String>(c.getId(), c.getName()));
		}
	
		
		
		return legalValues;
	}
	
	public Map<String, Property> getAllSingleMap() {
		Map<String, Property> props = new HashMap<>();
		
		List<Property> list = getAllSingle();
		for (Property p : list) {
			props.put(p.getId(), p);
		}
			
		return new HashMap<>(props);
		
	}
	
	/**
	 * 
	 * Standard Properties
	 *
	 */
	
	
	
	public static class NameProperty extends TextProperty {

		NameProperty() {
			inst();
		}
		
		@PostConstruct
		public void inst() {
			this.setId("name");
			this.setKind(PropertyKind.TEXT);
			this.setName("Name");

			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			rules.get(rules.size()-1).setMessage("Custom Testmessage Required");
			rules.add(new Rule(RuleKind.MAX_LENGTH, 100));
			rules.get(rules.size()-1).setMessage("Custom Testmessage max length");
			rules.add(new Rule(RuleKind.MIN_LENGTH, 5));
			rules.get(rules.size()-1).setMessage("Custom Testmessage min length");
			rules.add(new Rule(RuleKind.REGEX_PATTERN, "^[A-Za-z][A-Za-zöäüÖÄÜß\\s]*")); //Only Letters and Spaces, Start with Letter
			rules.get(rules.size()-1).setMessage("Custom Testmessage regex");
			this.setRules(rules);
			
		}
	}
	
	public static class DescriptionProperty extends TextProperty {
		
		public DescriptionProperty() {
			inst();
		}
		
		@PostConstruct
		public void inst() {
			this.setId("description");
			this.setName("Description");
			this.setKind(PropertyKind.LONG_TEXT);
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.MIN_LENGTH, 30));
			this.setRules(rules);
		}
	}
	
	public static class WorkflowKeyProperty extends TextProperty {
		
		public WorkflowKeyProperty() {
			inst();
		}
		
		@PostConstruct
		public void inst() {
			this.setId("workflow_key");
			this.setKind(PropertyKind.TEXT);
			this.setName("Workflow Key");
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REGEX_PATTERN, "^WF_K[0-9]+")); //Matches "WF_K<number>" i.e. "WF_K1" or "WF_K39099" NOT "WF_K" or "wf_k<number>"
			this.setRules(rules);
		}
	}
	
	public static class ContentProperty extends TextProperty {
		public ContentProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("content");
			this.setKind(PropertyKind.TEXT);
			this.setName("Content");
		}
	}
	
	public static class PriorityProperty extends TextProperty {
		public PriorityProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("priority");
			this.setKind(PropertyKind.TEXT);
			this.setName("Priority");
			
			List<ListEntry<String>> legalValues = new ArrayList<>();
			legalValues.add(new ListEntry<String>("low", "Low"));
			legalValues.add(new ListEntry<String>("normal", "Normal"));
			legalValues.add(new ListEntry<String>("high", "High"));
			legalValues.add(new ListEntry<String>("critical", "Critical"));
			this.setLegalValues(legalValues);
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			this.setRules(rules);
			
		}
	}
	
	public static class ImportancyProperty extends TextProperty {
		public ImportancyProperty() {
			inst();
		}
		public void inst() {
			this.setId("importancy");
			this.setKind(PropertyKind.TEXT);
			this.setName("Importancy");
			
			List<ListEntry<String>> legalValues = new ArrayList<>();
			legalValues.add(new ListEntry<String>("not_important", "Not Important"));
			legalValues.add(new ListEntry<String>("somewhat_important", "Somewhat Important"));
			legalValues.add(new ListEntry<String>("important", "Important"));
			legalValues.add(new ListEntry<String>("very_important", "Very Important"));
			legalValues.add(new ListEntry<String>("critically_important", "Critically Important"));
			
			this.setLegalValues(legalValues);
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			this.setRules(rules);
		}
	}
	
	public static class RoleProperty extends TextProperty {
		public RoleProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("role");
			this.setKind(PropertyKind.TEXT);
			this.setName("Role");
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.MIN_LENGTH, 3));
			this.setRules(rules);
			
		}
	}
	
	public static class LocationProperty extends TextProperty {
		public LocationProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("location");
			this.setKind(PropertyKind.TEXT);
			this.setName("Location");	
		}
	}
	
	public static class RequiredEquipmentProperty extends TextProperty {
		public RequiredEquipmentProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("required_equipment");
			this.setKind(PropertyKind.TEXT);
			this.setName("Required Equipment");
			
			List<ListEntry<String>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<String>("None"));
			this.setDefaultValues(defaultValues);
			
			
			List<ListEntry<String>> values = new ArrayList<>();
			values.add(defaultValues.get(0));
			this.setValues(values);	
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			this.setRules(rules);
		}
	}
	
	public static class WorkshiftProperty extends TextProperty {
		public WorkshiftProperty() {
			inst();
		}
		public void inst() {
			this.setId("workshift");
			this.setKind(PropertyKind.TEXT);
			this.setName("Allocated Shift");
			
			List<ListEntry<String>> legalValues = new ArrayList<>();
			legalValues.add(new ListEntry<String>("morning", "Morning"));
			legalValues.add(new ListEntry<String>("day", "Day"));
			legalValues.add(new ListEntry<String>("evening", "Evening"));
			legalValues.add(new ListEntry<String>("evening-night", "Evening-Night"));
			legalValues.add(new ListEntry<String>("night", "Night"));
			legalValues.add(new ListEntry<String>("night-morning", "Night-Morning"));

			this.setLegalValues(legalValues);
			
			List<ListEntry<String>> defaultValues = new ArrayList<>();
			defaultValues.add(legalValues.get(3));
			this.setDefaultValues(defaultValues);
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			this.setRules(rules);
		}
	}
	
	public static class TaskPeriodTypeProperty extends TextProperty {
		
		public TaskPeriodTypeProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("period_type");
			this.setKind(PropertyKind.TEXT);
			this.setName("Period Type");
			
			List<ListEntry<String>> legalValues = new ArrayList<>();
			legalValues.add(new ListEntry<String>("days", "Days"));
			legalValues.add(new ListEntry<String>("weeks", "Weeks"));
			legalValues.add(new ListEntry<String>("months", "Months"));
			legalValues.add(new ListEntry<String>("weekly", "Weekly"));
			legalValues.add(new ListEntry<String>("daily", "Daily"));
			legalValues.add(new ListEntry<String>("monthly", "Monthly"));

			this.setLegalValues(legalValues);
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			this.setRules(rules);
			
		}
	}
	
	public static class KeywordsProperty extends TextProperty {
		public KeywordsProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("keywords");
			this.setKind(PropertyKind.TEXT);
			this.setName("Keywords");
		}		
		
	}
	
	////Rewards 
	public static class RewardsProperty extends TextProperty {
		public RewardsProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("offered_rewards");
			this.setKind(PropertyKind.TEXT);
			this.setName("Offered Reward(s)");
		}
	}
	
	//=========================================
	//========== Number Properties ============
	//=========================================
	
	public static class PostcodeProperty extends NumberProperty {
		public PostcodeProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("postcode");
			this.setKind(PropertyKind.WHOLE_NUMBER);
			this.setName("Postcode");
			
			List<ListEntry<Integer>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<Integer>(1234));
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Integer>> values = new ArrayList<>();
			values.add(this.getDefaultValues().get(0));
			this.setValues(values);	
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.MAX_LENGTH, 4));
			rules.add(new Rule(RuleKind.MIN_LENGTH, 4));
			rules.add(new Rule(RuleKind.REGEX_PATTERN));
			this.setRules(rules);
			
			
		}
	}
	
	public static class NumberOfVolunteersProperty extends NumberProperty {
		public NumberOfVolunteersProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("number_of_volunteers");
			this.setKind(PropertyKind.WHOLE_NUMBER);
			this.setName("Number of Volunteers");
			
			List<ListEntry<Integer>> legalValues = new LinkedList<ListEntry<Integer>>();
			for(int i = 1; i <=10; i++) {
				legalValues.add(new ListEntry<Integer>(String.valueOf(i), i));
			}
			this.setLegalValues(legalValues);
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			this.setRules(rules);
			
		}
	}
	
	
	public static class TaskPeriodValueProperty extends NumberProperty {
		public TaskPeriodValueProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("period_length");
			this.setKind(PropertyKind.WHOLE_NUMBER);
			this.setName("Period length");
			
			List<ListEntry<Integer>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<Integer>(1));
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Integer>> values = new ArrayList<>();
			values.add(this.getDefaultValues().get(0));
			this.setValues(values);	
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			this.setRules(rules);
		}
	}
	
	
	//=========================================
	//========== Date Properties ==============
	//=========================================
	
	public static class StartDateProperty extends DateProperty {
		public StartDateProperty() {
			
			this.setId("starting_date");
			this.setKind(PropertyKind.DATE);
			this.setName("Starting Date");
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.REQUIRED));
			this.setRules(rules);
			
			setTestValues();
		}
		
		public void setTestValues() {

			List<ListEntry<Date>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<Date>(new Date()));
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Date>> values = new ArrayList<>();
			values.add(this.getDefaultValues().get(0));
			this.setValues(values);	
			
		}
	}
	
	public static class EndDateProperty extends DateProperty {
		public EndDateProperty() {
			this.setId("end_date");
			this.setKind(PropertyKind.DATE);
			this.setName("End Date");
						
			List<ListEntry<Date>> defaultValues = new ArrayList<>();
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Date>> values = new ArrayList<>();
			this.setValues(values);
			
		}

	}
	
	//=========================================
	//========== Bool Properties ==============
	//=========================================
	
	public static class UrgentProperty extends BooleanProperty {
		public UrgentProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("urgent");
			this.setKind(PropertyKind.BOOL);
			this.setName("Urgent");
			
			List<ListEntry<Boolean>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<Boolean>(false));
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Boolean>> values = new ArrayList<>();
			values.add(this.getDefaultValues().get(0));
			this.setValues(values);	
		}
	}
	
	public static class HighlightedProperty extends BooleanProperty {
		public HighlightedProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("highlighted");
			this.setKind(PropertyKind.BOOL);
			this.setName("Highlighted");
			
			List<ListEntry<Boolean>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<Boolean>(false));
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Boolean>> values = new ArrayList<>();
			values.add(this.getDefaultValues().get(0));
			this.setValues(values);	
		}
	}
	
	public static class PromotedProperty extends BooleanProperty {
		public PromotedProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("promotion");
			this.setKind(PropertyKind.BOOL);
			this.setName("Promotion");
			
			List<ListEntry<Boolean>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<Boolean>(false));
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Boolean>> values = new ArrayList<>();
			values.add(this.getDefaultValues().get(0));
			this.setValues(values);	
		}
	}
	
	public static class FeedbackRequestedProperty extends BooleanProperty {
		public FeedbackRequestedProperty() {
			inst(); 
		}
		
		public void inst() {
			this.setId("feedback_requested");
			this.setKind(PropertyKind.BOOL);
			this.setName("Feedback Requested");
			
			List<ListEntry<Boolean>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<Boolean>(false));
			this.setDefaultValues(defaultValues);

			List<ListEntry<Boolean>> values = new ArrayList<>();
			values.add(this.getDefaultValues().get(0));
			this.setValues(values);	
		}
	}
	
	public static class RemindParticipantsProperty extends BooleanProperty {
		public RemindParticipantsProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("remind_participants");
			this.setKind(PropertyKind.BOOL);
			this.setName("Remind Participants");

			List<ListEntry<Boolean>> defaultValues = new ArrayList<>();
			defaultValues.add(new ListEntry<Boolean>(false));
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Boolean>> values = new ArrayList<>();
			values.add(this.getDefaultValues().get(0));
			this.setValues(values);	
		}
	}
	
	//=========================================
	//==== Floating Point Number Properties ===
	//=========================================
	
	public static class LatitudeProperty extends DoubleProperty {
		public LatitudeProperty() {
			inst();
			setTestValues();
		}
		
		public LatitudeProperty(double latitude) {
			inst();
			this.getDefaultValues().add(new ListEntry<Double>(latitude));
			this.getValues().add(this.getDefaultValues().get(0));
		}
		
		public LatitudeProperty(double[] latitude) {
			inst();
			
			for (int i = 0; i < latitude.length; i++) {
				this.getDefaultValues().add(new ListEntry<>(latitude[i]));
				this.getValues().add(this.getDefaultValues().get(i));
			}
				
			
		}
		
		public void inst() {
			this.setId("latitude");
			this.setKind(PropertyKind.FLOAT_NUMBER);
			this.setName("Latitude");
			
			List<ListEntry<Double>> defaultValues = new ArrayList<>();
			this.setDefaultValues(defaultValues);
			

			List<ListEntry<Double>> values = new ArrayList<>();	
			this.setValues(values);	
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.MIN, -90));
			rules.add(new Rule(RuleKind.MAX, 90));
			this.setRules(rules);	
		}
		
		private void setTestValues() {
			this.getDefaultValues().add(new ListEntry<Double>(0.01));	
			this.getValues().add(this.getDefaultValues().get(0));
		}
	}
	
	public static class LongitudeProperty extends DoubleProperty {
		public LongitudeProperty() {
			inst();
			setTestValues();
		}
		
		public LongitudeProperty(double longitude) {
			inst();
			this.getDefaultValues().add(new ListEntry<Double>(longitude));
			this.getValues().add(this.getDefaultValues().get(0));
		}
		
		public LongitudeProperty(double[] longitude) {
			inst();
			
			for (int i = 0; i < longitude.length; i++) {
				this.getDefaultValues().add(new ListEntry<>(longitude[i]));
				this.getValues().add(this.getDefaultValues().get(i));
			}
		}
		
		public void inst() {
			this.setId("longitude");
			this.setKind(PropertyKind.FLOAT_NUMBER);
			this.setName("Longitude");
			
			List<ListEntry<Double>> defaultValues = new ArrayList<>();
			this.setDefaultValues(defaultValues);
			
			List<ListEntry<Double>> values = new ArrayList<>();
			this.setValues(values);	
			
			List<Rule> rules = new LinkedList<Rule>();
			rules.add(new Rule(RuleKind.MIN, -180));
			rules.add(new Rule(RuleKind.MAX, 180));
			this.setRules(rules);
		}
		
		private void setTestValues() {
			this.getDefaultValues().add(new ListEntry<Double>(Double.NaN));	
			this.getValues().add(this.getDefaultValues().get(0));
		}
	}
	
	//=========================================
	//========= Competence Properties =========
	//=========================================
	
	public static class RequiredCompetencesProperty extends TextProperty {
				
		public RequiredCompetencesProperty() {
			inst();
		}
		
		public void inst() {
			this.setName("Required Competences");
			this.setId("required_competences");
			this.setKind(PropertyKind.LIST);
		}

	}
	
	public static class OptionalCompetencesProperty extends TextProperty {
				
		public OptionalCompetencesProperty() {
			inst();
		}
		
		public void inst() {
			this.setName("Optional Competences");
			this.setId("optional_competences");
			this.setKind(PropertyKind.LIST);
		}	
	}
	
	public static class AquireableCompetencesProperty extends TextProperty {
						
		public AquireableCompetencesProperty() {
			inst();	
		}
		
		public void inst() {
			this.setName("Aquirable Competences");
			this.setId("aquireable_competences");
			this.setKind(PropertyKind.LIST);
		}
	}
	
	public static class TestMultiProperty extends MultipleProperty {
		public TestMultiProperty() {
			this.setId("test_multi");
			this.setKind(PropertyKind.MULTIPLE);
			this.setName("Test Multi");
		}
	}
	
	
	//-----------------------------------------
	//--------------SyBOS Properties
	//-----------------------------------------
	
	public static class DurationTimeProperty extends MultipleProperty {
		public DurationTimeProperty() {
			this.setId("duration_time");
			this.setName("duration");	
			this.setProperties(new ArrayList<>(2));
			
			StartDateProperty start = new StartDateProperty();
			start.setName("From");
			
			EndDateProperty end = new EndDateProperty();
			end.setName("To");
			
			this.getProperties().add(start);
			this.getProperties().add(end);
		}	
	}
	
	public static class PlanningTypeProperty extends TextProperty {
		public PlanningTypeProperty() {
			this.setId("planning_type");
			this.setName("Planning Type");
			this.setLegalValues(new ArrayList<>(2));
			this.getLegalValues().add(new ListEntry<String>("repeating"));
			this.getLegalValues().add(new ListEntry<String>("once"));
		
		}
	}
	
	public static class AreaOfExpertiseProperty extends TextProperty {
		public AreaOfExpertiseProperty() {
			this.setId("area_of_expertise");
			this.setName("Area of Expertise");
			this.setLegalValues(new ArrayList<>(5));
			this.getLegalValues().add(new ListEntry<String>("Brandbereitschaftsdienst"));
			this.getLegalValues().add(new ListEntry<String>("Ordnungs- und Absperrdienst"));
			this.getLegalValues().add(new ListEntry<String>("Wasserbeförderung"));
			this.getLegalValues().add(new ListEntry<String>("Hilfs- und Assistenzdienst"));
			this.getLegalValues().add(new ListEntry<String>("Schädlingsbekämpfung"));
		}
	}
	
	
	public static class DurationHoursProperty extends DoubleProperty {
		public DurationHoursProperty() {
			this.setId("duration_hours");
			this.setName("Duration (in hrs)");
		}
	}
	
	public static class PointsProperty extends DoubleProperty {
		public PointsProperty() {
			this.setId("points");
			this.setName("Points");
		}
	}
	
	public static class DepartmentProperty extends TextProperty {
		public DepartmentProperty() {
			this.setId("department");
			this.setName("Department");
			
			this.setLegalValues(new ArrayList<>());	
			this.getLegalValues().add(new ListEntry<>("FF Grieskirchen"));
			this.getLegalValues().add(new ListEntry<>("FF Krems"));
			this.getLegalValues().add(new ListEntry<>("FF Wels"));
			this.getLegalValues().add(new ListEntry<>("FF Ried"));
			this.getLegalValues().add(new ListEntry<>("BF Linz"));
			this.getLegalValues().add(new ListEntry<>("FW Ravensburg"));	
		}
	}
	
	public static class StreetProperty extends TextProperty {
		public StreetProperty() {
			this.setId("street");
			this.setName("Street");
		}
	}
	
	public static class HouseNumberProperty extends TextProperty {
		public HouseNumberProperty() {
			this.setId("house_number");
			this.setName("House Number");
		}
	}
	
	public static class DoorNumberProperty extends TextProperty {
		public DoorNumberProperty() {
			this.setId("door_number");
			this.setName("Door Number");
		}
	}
	
	public static class CityProperty extends TextProperty {
		public CityProperty() {
			this.setId("city");
			this.setName("city");
		}
	}
	
	public static class AddressProperty extends MultipleProperty {
		public AddressProperty() {
			this.setId("address");
			this.setName("Address");
			
			this.setProperties(new ArrayList<>());
			this.getProperties().add(new StreetProperty());
			this.getProperties().add(new HouseNumberProperty());
			this.getProperties().add(new DoorNumberProperty());
			this.getProperties().add(new PostcodeProperty());
			this.getProperties().add(new CityProperty());		
			this.getProperties().add(new DescriptionProperty());
		}
	}
	
	public static class CommentsProperty extends TextProperty {
		public CommentsProperty() {
			this.setId("comments");
			this.setName("Comments");
			this.setKind(PropertyKind.LONG_TEXT);
		}
	}
	
	public static class VehicleSelectionProperty extends TextProperty {
		public VehicleSelectionProperty() {
			this.setId("vehicle_selection");
			this.setName("Vehicle");
			
			this.setLegalValues(new ArrayList<>());
			
			this.getLegalValues().add(new ListEntry<>("Arbeitsboot (1)"));
			this.getLegalValues().add(new ListEntry<>("Arbeitsboot (2)"));
			this.getLegalValues().add(new ListEntry<>("TLF 2000"));
			this.getLegalValues().add(new ListEntry<>("LF-B 1"));
			this.getLegalValues().add(new ListEntry<>("LF-B 2"));
			this.getLegalValues().add(new ListEntry<>("KLF"));
			this.getLegalValues().add(new ListEntry<>("TLF A"));
			this.getLegalValues().add(new ListEntry<>("ULF"));
			this.getLegalValues().add(new ListEntry<>("SLF"));
			this.getLegalValues().add(new ListEntry<>("RLF-T"));
		}
	}
	
	public static class EventTypeProperty extends TextProperty {
		public EventTypeProperty() {
			this.setId("event_type");
			this.setName("Event Type");
			
			this.setLegalValues(new ArrayList<>());
			this.getLegalValues().add(new ListEntry<>("Besprechung"));
			this.getLegalValues().add(new ListEntry<>("Kurs"));
			this.getLegalValues().add(new ListEntry<>("Schulung"));
			this.getLegalValues().add(new ListEntry<>("sonstiges"));
		}
	}
	
	public static class PublicEventProperty extends BooleanProperty {
		public PublicEventProperty() {
			this.setId("public_event");
			this.setName("Event is public");
		}
	}
	
	public static class PaidProperty extends BooleanProperty {
		public PaidProperty() {
			this.setId("paid");
			this.setName("paid");
		}
	}
	
	public static class PrerequisitesProperty extends TextProperty {
		public PrerequisitesProperty() {
			this.setId("prerequisites");
			this.setName("Prerequisites");
			this.setKind(PropertyKind.LONG_TEXT);
		}
	}
	
	public static class ActivityGroupProperty extends TextProperty {
		public ActivityGroupProperty() {
			this.setId("activity_group");
			this.setName("Activity Group");
			
			this.setLegalValues(new ArrayList<>());
			
			this.getLegalValues().add(new ListEntry<>("Education"));
			this.getLegalValues().add(new ListEntry<>("Meeting"));
			this.getLegalValues().add(new ListEntry<>("Recruitment"));
			this.getLegalValues().add(new ListEntry<>("Maintainence"));
		}
	}
	
	public static class RegisterDateProperty extends DateProperty {
		public RegisterDateProperty() {
			this.setId("register_date");
			this.setName("Register Date");
		
		}
	}
	
	public static class EducationClassProperty extends TextProperty {
		public EducationClassProperty() {
			this.setId("education_class");
			this.setName("Education Class");
		}
	}
	
	public static class GradeProperty extends TextProperty {
		public GradeProperty() {
			this.setId("grade");
			this.setName("Grade");
			
			this.setLegalValues(new ArrayList<>());
			
			this.getLegalValues().add(new ListEntry<>("Mit Erfolg Teilgenommen"));
			this.getLegalValues().add(new ListEntry<>("Teilgenommen"));
			this.getLegalValues().add(new ListEntry<>("Ohne Erfolg Teilgenommen"));
			this.getLegalValues().add(new ListEntry<>("sehr gut (1)"));
			this.getLegalValues().add(new ListEntry<>("gut (2)"));
			this.getLegalValues().add(new ListEntry<>("befriedigend (3)"));
			this.getLegalValues().add(new ListEntry<>("genügend (4)"));
			this.getLegalValues().add(new ListEntry<>("nicht genügend (5)"));
		}
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-----------------------------------------
	//--------------MAP PROPERTY
	//-----------------------------------------
	
	
	public static class MapProperty extends MultipleProperty {
	
		public MapProperty() {
			this.setId("map");
			this.setKind(PropertyKind.MAP);
			this.setName("Map Property");
			this.setProperties(new ArrayList<>());
			
			setTestValues();
		}
		
		public void setViewPort(double latitude, double longitude) {
			if (this.getProperties().size() <= 0) {
				this.getProperties().add(new MapEntryProperty(latitude, longitude));
			} else {
				this.getProperties().set(0, new MapEntryProperty(latitude, longitude));
			}
		}
		
		public void setMarker(double latitude, double longitude) {
			if (this.getProperties().size() <= 0) {
				this.getProperties().add(new MapEntryProperty(latitude, longitude));
			}
			this.getProperties().add(new MapEntryProperty(latitude, longitude));

		}
		
		public void setArea(double[] latitude, double[] longitude) {
			if (this.getProperties().size() <= 0) {
				this.getProperties().add(new MapEntryProperty(latitude[0], longitude[0]));
			}
			
			this.getProperties().add(new MapEntryProperty(latitude, longitude));
		}
		
		private void setTestValues() {
			
			this.setViewPort(0, 0);
			this.setMarker(10, 10);
			this.setMarker(0, 0);
			this.setMarker(40, 33);
						
			this.setArea(new double[]{10, 12, 13}, new double[]{11, 13, 15});	
		}
	}
	
	public static class MapEntryProperty extends MultipleProperty {
		
		public MapEntryProperty() {
			this.setId(new ObjectId().toHexString());
			this.setKind(PropertyKind.MULTIPLE);
			this.setName("Map Entry");
			this.setProperties(new ArrayList<>());
			
			this.getProperties().add(new LatitudeProperty());
			this.getProperties().add(new LongitudeProperty());	
		}
		
		public MapEntryProperty(double latitude, double longitude) {
			this.setId(new ObjectId().toHexString());

			this.setKind(PropertyKind.MULTIPLE);
			this.setName("Map Entry");
			this.setProperties(new ArrayList<>());
			
			LatitudeProperty lat = new LatitudeProperty(latitude);
			this.getProperties().add(lat);
			
			LongitudeProperty lng = new LongitudeProperty(longitude);
			this.getProperties().add(lng);
		}
		
		public MapEntryProperty (double[] latitude, double[] longitude) {
			this.setId(new ObjectId().toHexString());

			this.setKind(PropertyKind.MULTIPLE);
			this.setName("Map Area");
			this.setProperties(new ArrayList<>());
			
			LatitudeProperty lat = new LatitudeProperty(latitude);
			this.getProperties().add(lat);
			
			LongitudeProperty lng = new LongitudeProperty(longitude);
			this.getProperties().add(lng);
		}
	}
	

	
	public static class GraphProperty extends MultipleProperty {
		public GraphProperty() {
			this.setId("graph");
			this.setName("Graph");
			this.setKind(PropertyKind.GRAPH);
			
			this.setProperties(new ArrayList<>());
			
		}
		
		public void addNode(String name) {
			this.getProperties().add(new GraphPropertyEntry(name));
		}
		
		public void addNode(GraphPropertyEntry entry) {
			this.getProperties().add(entry);
		}
		
		public void addEdge(String idFrom, String idTo) {
			GraphPropertyEntry from = (GraphPropertyEntry) this.getProperties().stream().filter(p -> p.getId().equals(idFrom)).findFirst().get();
			GraphPropertyEntry to = (GraphPropertyEntry) this.getProperties().stream().filter(p -> p.getId().equals(idTo)).findFirst().get();
			
			from.addEdge(idTo, to.getName());
		}
		
		public String print() {
			
			StringBuilder sb = new StringBuilder();
			
			for (Property p : this.getProperties()) {
				sb.append(((GraphPropertyEntry)p).print());
			}
			
			return sb.toString();
		}
	}
	
	public static class GraphPropertyEntry extends SingleProperty<String> {
		public GraphPropertyEntry(String name) {
			this.setId(name);
			this.setName(name);
			this.setKind(PropertyKind.TEXT);
			
			this.setValues(new ArrayList<ListEntry<String>>());	
		}
		
		
		
		public void addEdge(String nodeId, String nodeLabel) {
			this.getValues().add(new ListEntry<String>(nodeId, nodeLabel));
		}
		
		public String print() {
			
			StringBuilder sb = new StringBuilder();
			sb.append(this.getName());
			sb.append(": ");
			
			for (ListEntry<String> entry : this.getValues()) {
				sb.append(entry.value);
				sb.append(" -> ");
			}
			sb.append("/\n");
			
			return sb.toString();
		}
	}
	
	//Testgraph
	
	/** Graph:
	 * 0---1
	 * |  /| \  
	 * | / |  2
	 * |/  | /
	 * 4---3	
	 * 
	 */
	
	public static void main(String[] args) {
		GraphProperty graph = new GraphProperty();
		
		GraphPropertyEntry n0 = new GraphPropertyEntry("0");
		GraphPropertyEntry n1 = new GraphPropertyEntry("1");
		GraphPropertyEntry n2 = new GraphPropertyEntry("2");
		GraphPropertyEntry n3 = new GraphPropertyEntry("3");
		GraphPropertyEntry n4 = new GraphPropertyEntry("4");

		n0.addEdge(n1.getId(), n1.getName());
		n0.addEdge(n4.getId(), n4.getName());
		
		n1.addEdge(n0.getId(), n0.getName());
		n1.addEdge(n4.getId(), n4.getName());
		n1.addEdge(n2.getId(), n2.getName());
		n1.addEdge(n3.getId(), n3.getName());
		
		n2.addEdge(n1.getId(), n1.getName());
		n2.addEdge(n3.getId(), n3.getName());
		
		n3.addEdge(n1.getId(), n1.getName());
		n3.addEdge(n4.getId(), n4.getName());
		n3.addEdge(n2.getId(), n2.getName());
		
		n4.addEdge(n3.getId(), n3.getName());
		n4.addEdge(n0.getId(), n0.getName());
		n4.addEdge(n1.getId(), n1.getName());
		
		graph.addNode(n0);
		graph.addNode(n1);
		graph.addNode(n2);
		graph.addNode(n3);
		graph.addNode(n4);
		
		System.out.println("Adj. List: ");
		System.out.println(graph.print());
		
	}
	
	
	
	
	
	
	
	
}
