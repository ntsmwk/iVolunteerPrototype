//// TODO remove
//
//
//package at.jku.cis.iVolunteer.model.task.template.dto;
//
//import java.util.List;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import at.jku.cis.iVolunteer.model.competence.Competence;
//import at.jku.cis.iVolunteer.model.property.NumberProperty;
//import at.jku.cis.iVolunteer.model.property.TextProperty;
//import at.jku.cis.iVolunteer.model.property.TransferProperty;
//import at.jku.cis.iVolunteer.model.property.dto.TransferPropertyDTO;
//
//
//public class TaskTemplateADTO {
//
//	
//	private String id;
//	
//	private TransferPropertyDTO name;
//	private TransferPropertyDTO description;
//	private TransferPropertyDTO workflowKey;
//	private TransferPropertyDTO number;
//	//private CompetenceProperty acquirableCompetences;
//	//private CompetenceProperty requiredCompetences;
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public TransferPropertyDTO getName() {
//		return name;
//	}
//
//	public void setName(TransferPropertyDTO name) {
//		this.name = name;
//	}
//
//	public TransferPropertyDTO getDescription() {
//		return description;
//	}
//
//	public void setDescription(TransferPropertyDTO description) {
//		this.description = description;
//	}
//
//	public TransferPropertyDTO getWorkflowKey() {
//		return workflowKey;
//	}
//
//	public void setWorkflowKey(TransferPropertyDTO workflowKey) {
//		this.workflowKey = workflowKey;
//	}
//	
//	public void setNumber(TransferPropertyDTO number) {
//		this.number = number; 
//	}
//	
//	public TransferPropertyDTO getNumber() {
//		return number;
//	}
//	
//	
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
//		if (!(obj instanceof TaskTemplateADTO)) {
//			return false;
//		}
//		return ((TaskTemplateADTO) obj).id.equals(id);
//	}
//
//	@Override
//	public int hashCode() {
//		return id.hashCode();
//	}
//
//}
