package at.jku.cis.iVolunteer;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.property.BooleanProperty;
import at.jku.cis.iVolunteer.model.property.DateProperty;
import at.jku.cis.iVolunteer.model.property.DoubleProperty;
import at.jku.cis.iVolunteer.model.property.NumberProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.TextProperty;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;


@Component
public class StandardProperties {
	
	
	@Autowired public CompetenceRepository competenceRepository;

	
	
	//=========================================
	//========== Text Properties ==============
	//=========================================
	
	public List<Property<?>> getAll() {
		List<Property<?>> props = new LinkedList<>();
		
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

		List<ListEntry<String>> values = new LinkedList<ListEntry<String>>();
		Competence c0 = competenceRepository.findAll().get(0);
		Competence c3 = competenceRepository.findAll().get(3);
		values.add(new ListEntry<String>(c0.getId(), c0.getValue()));
		values.add(new ListEntry<String>(c3.getId(), c3.getValue()));
		cp1.setValues(values);
		props.add(cp1);
		
		cp2.setLegalValues(addCompetenceLegalValues());
		props.add(cp2);
		cp3.setLegalValues(addCompetenceLegalValues());
		props.add(cp3);
		
		
		return props;
		
	}
	
	public List<ListEntry<String>> addCompetenceLegalValues() {
	
		List<ListEntry<String>> legalValues = new LinkedList<ListEntry<String>>();
		
		
		for (Competence c : competenceRepository.findAll()) {
			legalValues.add(new ListEntry<String>(c.getId(), c.getValue()));
		}
		
		return legalValues;
	}
	

	
//	public static void setIds(List<Property<?>> props) {
//		for (Property p : props) {
//			//p.setId(p.getName());
//		}
//	}
	
	public Map<String, Property<?>> getAllMap() {
		Map<String, Property<?>> props = new HashMap<>();
		
		List<Property<?>> list = getAll();
		for (Property p : list) {
			//p.setId(p.getName());
			props.put(p.getId(), p);
		}
		
		return props;
		
	}
	
	
	

	
	public static class NameProperty extends TextProperty {

		NameProperty() {
			inst();
		}
		
		@PostConstruct
		public void inst() {
			this.setId("name");
			this.setKind(PropertyKind.TEXT);
			this.setName("Name");
			this.setDefaultValue("test");
			this.setValue("test");
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
			this.setDefaultValue("lorem ipsum dolor sit amet");
			this.setValue(getDefaultValue());
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
			this.setDefaultValue("");
			this.setValue(getDefaultValue());
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
			this.setDefaultValue("");
			this.setValue(getDefaultValue());
		
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
			this.setDefaultValue("Normal");
			this.setValue(getDefaultValue());
			
			List<ListEntry<String>> legalValues = new ArrayList<>();
			legalValues.add(new ListEntry<String>("low", "Low"));
			legalValues.add(new ListEntry<String>("normal", "Normal"));
			legalValues.add(new ListEntry<String>("high", "High"));
			legalValues.add(new ListEntry<String>("critical", "Critical"));

			
			this.setLegalValues(legalValues);
			
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
			this.setDefaultValue("Somewhat Important");
			this.setValue(getDefaultValue());
			
			List<ListEntry<String>> legalValues = new ArrayList<>();
			legalValues.add(new ListEntry<String>("not_important", "Not Important"));
			legalValues.add(new ListEntry<String>("somewhat_important", "Somewhat Important"));
			legalValues.add(new ListEntry<String>("important", "Important"));
			legalValues.add(new ListEntry<String>("very_important", "Very Important"));
			legalValues.add(new ListEntry<String>("critically_important", "Critically Important"));
			
			this.setLegalValues(legalValues);
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
			this.setValue("");
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
			this.setDefaultValue("");
			this.setValue(getDefaultValue());
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
			this.setDefaultValue("None");
			this.setValue(getDefaultValue());
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
			this.setDefaultValue("Evening");
			this.setValue(getDefaultValue());
			
			List<ListEntry<String>> legalValues = new ArrayList<>();
			legalValues.add(new ListEntry<String>("morning", "Morning"));
			legalValues.add(new ListEntry<String>("day", "Day"));
			legalValues.add(new ListEntry<String>("evening", "Evening"));
			legalValues.add(new ListEntry<String>("evening-night", "Evening-Night"));
			legalValues.add(new ListEntry<String>("night", "Night"));
			legalValues.add(new ListEntry<String>("night-morning", "Night-Morning"));

			this.setLegalValues(legalValues);
		}
	}
	
	public static class TaskPeriodTypeProperty extends TextProperty {
		
		public TaskPeriodTypeProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("period_type");
			this.setName("Period Type");
			this.setDefaultValue("Weeks");
			this.setValue(getDefaultValue());
			
			List<ListEntry<String>> legalValues = new ArrayList<>();
			legalValues.add(new ListEntry<String>("days", "Days"));
			legalValues.add(new ListEntry<String>("weeks", "Weeks"));
			legalValues.add(new ListEntry<String>("months", "Months"));
			legalValues.add(new ListEntry<String>("weekly", "Weekly"));
			legalValues.add(new ListEntry<String>("daily", "Daily"));
			legalValues.add(new ListEntry<String>("monthly", "Monthly"));

			this.setLegalValues(legalValues);
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
			this.setDefaultValue("");
			this.setValue(getDefaultValue());
		}
	}
	
	////Rewards ??
	public static class RewardsProperty extends TextProperty {
		public RewardsProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("offered_rewards");
			this.setKind(PropertyKind.TEXT);
			this.setName("Offered Reward(s)");
			this.setDefaultValue("10 Coins, Badge??");
			this.setValue(getDefaultValue());
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
			this.setDefaultValue(1234);
			this.setValue(1234);
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
			this.setDefaultValue(1);
			this.setValue(getDefaultValue());
			List<ListEntry<Integer>> legalValues = new LinkedList<ListEntry<Integer>>();
			for(int i = 1; i <=10; i++) {
				legalValues.add(new ListEntry<Integer>(String.valueOf(i), i));
			}
			this.setLegalValues(legalValues);
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
			this.setDefaultValue(1);
			this.setValue(getDefaultValue());
		}
	}
	
	
	//=========================================
	//========== Date Properties ==============
	//=========================================
	
	public static class StartDateProperty extends DateProperty {
		public StartDateProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("starting_date");
			this.setKind(PropertyKind.DATE);
			this.setName("Starting Date");
			this.setDefaultValue(new Date()); //Default current Date
			this.setValue(getDefaultValue());
		}
	}
	
	public static class EndDateProperty extends DateProperty {
		public EndDateProperty() {
			inst();
		}
		public void inst() {
			this.setId("end_date");
			this.setKind(PropertyKind.DATE);
			this.setName("End Date");
			this.setDefaultValue(new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime());
			this.setValue(getDefaultValue());
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
			this.setDefaultValue(false);
			this.setValue(getDefaultValue());
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
			this.setDefaultValue(false);
			this.setValue(getDefaultValue());
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
			this.setDefaultValue(false);
			this.setValue(getDefaultValue());
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
			this.setDefaultValue(true);
			this.setValue(getDefaultValue());
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
			this.setDefaultValue(true);
			this.setValue(getDefaultValue());
		}
	}
	
	//=========================================
	//==== Floating Point Number Properties ===
	//=========================================
	
	public static class LatitudeProperty extends DoubleProperty {
		public LatitudeProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("latitude");
			this.setKind(PropertyKind.FLOAT_NUMBER);
			this.setName("Latitude");
			this.setDefaultValue(0.0);
			this.setValue(getDefaultValue());
		}
	}
	
	public static class LongitudeProperty extends DoubleProperty {
		public LongitudeProperty() {
			inst();
		}
		
		public void inst() {
			this.setId("longitude");
			this.setKind(PropertyKind.FLOAT_NUMBER);
			this.setName("Longitude");
			this.setDefaultValue(0.0);
			this.setValue(getDefaultValue());
		}
	}
	
	//=========================================
	//========= Competence Properties =========
	//=========================================
	//TODO
	public static class RequiredCompetencesProperty extends TextProperty {
				
		
		public RequiredCompetencesProperty() {
			inst();
		}
		
		
		public void inst() {
			this.setName("Required Competences");
			this.setId("required_competences");
			this.setKind(PropertyKind.LIST);
			//postInst();
		}
		
//		public void postInst() {			
//			List<ListEntry<String>> legalValues = new LinkedList<ListEntry<String>>();
//			
//			for (Competence c : competences) {
//				legalValues.add(new ListEntry<String>(c.getId(), c.getValue()));
//			}
//			
//			this.setLegalValues(legalValues);
//			
//			
//			List<ListEntry<String>> values = new LinkedList<ListEntry<String>>();
//			Competence c0 = competences.get(0);
//			Competence c3 = competences.get(3);
//			values.add(new ListEntry<String>(c0.getId(), c0.getValue()));
//			values.add(new ListEntry<String>(c3.getId(), c3.getValue()));
//			this.setValues(values);
//		}
	}
	
	public static class OptionalCompetencesProperty extends TextProperty {
		
		@Autowired StandardProperties sp;
		
		public OptionalCompetencesProperty() {
			inst();
		}
		
		
		public void inst() {
			this.setName("Optional Competences");
			this.setId("optional_competences");
			this.setKind(PropertyKind.LIST);
			//postInst();
		}
		
//		@PostConstruct
//		public void postInst() {
//			List<ListEntry<String>> legalValues = new LinkedList<ListEntry<String>>();
//			
//			
//			
//			List<Competence> competences = sp.getAllCompetences();
//			
//			for (Competence c : competences) {
//				legalValues.add(new ListEntry<String>(c.getId(), c.getValue()));
//			}
//			
//			this.setLegalValues(legalValues);
//		}
		
		
	}
	
	public static class AquireableCompetencesProperty extends TextProperty {
				
		@Autowired StandardProperties sp ;
		
		public AquireableCompetencesProperty() {
			inst();
			
		}
		
		
		public void inst() {
			this.setName("Aquirable Competences");
			this.setId("aquirable_competences");

			this.setKind(PropertyKind.LIST);
			//postInst();
		}
		
//		@PostConstruct
//		public void postInst() {
//			List<ListEntry<String>> legalValues = new LinkedList<ListEntry<String>>();
//			
//			
//			
//			List<Competence> competences = sp.getAllCompetences();
//			
//			for (Competence c : competences) {
//				legalValues.add(new ListEntry<String>(c.getId(), c.getValue()));
//			}
//			
//			this.setLegalValues(legalValues);
//		}
	}
	
	
	
	
}
