package at.jku.cis.iVolunteer.model.meta.core.clazz;

import java.util.List;

import org.springframework.data.annotation.Id;

public class TaskInstance extends ClassInstance {

	@Id private String id; 
	private List<String> subscribedVolunteerIds;
	private String status;
	
	public List<String> getSubscribedVolunteerIds() {
		return subscribedVolunteerIds;
	}
	public void setSubscribedVolunteerIds(List<String> subscribedVolunteerIds) {
		this.subscribedVolunteerIds = subscribedVolunteerIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
