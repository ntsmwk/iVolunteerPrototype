// TODO Remove

//package at.jku.cis.iVolunteer.model.task.template;
//
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import at.jku.cis.iVolunteer.model.competence.Competence;
//import at.jku.cis.iVolunteer.model.property.NumberProperty;
//import at.jku.cis.iVolunteer.model.property.TextProperty;
//
//@Document
//public class TaskTemplateA {
//
//	@Id
//	private String id;
//	
//	private TextProperty name;
//	private TextProperty description;
//	private TextProperty workflowKey;
//	private NumberProperty number;
//	//private CompetenceProperty acquirableCompetences;
//	//private CompetenceProperty requiredCompetences;
//	
//	public TaskTemplateA() {
//		name = new TextProperty();
//		name.setName("Name");
//		name.setId("Name");
//		
//		description = new TextProperty();
//		description.setName("Description");;
//		description.setId("Description");
//		
//		workflowKey = new TextProperty();
//		workflowKey.setName("Workflow Key");
//		workflowKey.setId("workflowKey");
//		
//		number = new NumberProperty();
//		number.setName("number");
//		number.setValue(23333);
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public TextProperty getName() {
//		return name;
//	}
//
//	public void setName(TextProperty name) {
//		this.name = name;
//	}
//
//	public TextProperty getDescription() {
//		return description;
//	}
//
//	public void setDescription(TextProperty description) {
//		this.description = description;
//	}
//
//	public TextProperty getWorkflowKey() {
//		return workflowKey;
//	}
//
//	public void setWorkflowKey(TextProperty workflowKey) {
//		this.workflowKey = workflowKey;
//	}
//	
//	public void setNumber(NumberProperty number) {
//		this.number = number; 
//	}
//	
//	public NumberProperty getNumber() {
//		return number;
//	}
//
////	public List<Competence> getAcquirableCompetences() {
////		return acquirableCompetences;
////	}
////
////	public void setAcquirableCompetences(List<Competence> acquirableCompetences) {
////		this.acquirableCompetences = acquirableCompetences;
////	}
////
////	public List<Competence> getRequiredCompetences() {
////		return requiredCompetences;
////	}
////
////	public void setRequiredCompetences(List<Competence> requiredCompetences) {
////		this.requiredCompetences = requiredCompetences;
////	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof TaskTemplateA)) {
//			return false;
//		}
//		return ((TaskTemplateA) obj).id.equals(id);
//	}
//
//	@Override
//	public int hashCode() {
//		return id.hashCode();
//	}
//
//}
