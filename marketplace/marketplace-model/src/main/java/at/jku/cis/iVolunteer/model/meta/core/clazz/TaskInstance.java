package at.jku.cis.iVolunteer.model.meta.core.clazz;

import java.util.List;


public class TaskInstance extends ClassInstance {

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

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskInstance))
			return false;
		return ((TaskInstance) obj).id.equals(id);
	}

	public TaskInstance updateTaskInstance(TaskInstance newTaskInstance) {
		this.setClassDefinitionId(newTaskInstance.getClassDefinitionId() != null ? newTaskInstance.getClassDefinitionId() : this.getClassDefinitionId());
		this.setName(newTaskInstance.getName() != null ? newTaskInstance.getClassDefinitionId() : this.getClassDefinitionId());
		this.setProperties(newTaskInstance.getProperties() != null ? newTaskInstance.getProperties() : this.getProperties());
		this.setUserId(newTaskInstance.getUserId() != null ? newTaskInstance.getUserId() : this.getUserId());
		this.setIssuerId(newTaskInstance.getIssuerId() != null ? newTaskInstance.getIssuerId() : this.getIssuerId());
		this.setImagePath(newTaskInstance.getImagePath() != null ? newTaskInstance.getImagePath() : this.getImagePath());
		this.setClassArchetype(newTaskInstance.getClassArchetype() != null ? newTaskInstance.getClassArchetype() : this.getClassArchetype());
		this.setChildClassInstances(newTaskInstance.getChildClassInstances() != null ? newTaskInstance.getChildClassInstances() : this.getChildClassInstances());
		this.setVisible(newTaskInstance.isVisible() != null ? newTaskInstance.isVisible() : this.isVisible());
		this.setTabId(newTaskInstance.getTabId() != null ? newTaskInstance.getTabId() : this.getTabId());
		this.setBlockchainDate(newTaskInstance.getBlockchainDate() != null ? newTaskInstance.getBlockchainDate() : this.getBlockchainDate());
		this.setLevel(newTaskInstance.getLevel() != null ? newTaskInstance.getLevel() : this.getLevel());
		this.setSubscribedVolunteerIds(newTaskInstance.getSubscribedVolunteerIds() != null ? newTaskInstance.getSubscribedVolunteerIds() : this.getSubscribedVolunteerIds());
		this.setStatus(newTaskInstance.getStatus() != null ? newTaskInstance.getStatus() : this.getStatus());
		return this;
	}
	
	public TaskInstance updateTaskInstance(ClassInstance newTaskInstance) {
		this.setClassDefinitionId(newTaskInstance.getClassDefinitionId() != null ? newTaskInstance.getClassDefinitionId() : this.getClassDefinitionId());
		this.setName(newTaskInstance.getName() != null ? newTaskInstance.getName() : this.getName());
		this.setProperties(newTaskInstance.getProperties() != null ? newTaskInstance.getProperties() : this.getProperties());
		this.setUserId(newTaskInstance.getUserId() != null ? newTaskInstance.getUserId() : this.getUserId());
		this.setIssuerId(newTaskInstance.getIssuerId() != null ? newTaskInstance.getIssuerId() : this.getIssuerId());
		this.setImagePath(newTaskInstance.getImagePath() != null ? newTaskInstance.getImagePath() : this.getImagePath());
		this.setClassArchetype(newTaskInstance.getClassArchetype() != null ? newTaskInstance.getClassArchetype() : this.getClassArchetype());
		this.setChildClassInstances(newTaskInstance.getChildClassInstances() != null ? newTaskInstance.getChildClassInstances() : this.getChildClassInstances());
		this.setVisible(newTaskInstance.isVisible() != null ? newTaskInstance.isVisible() : this.isVisible());
		this.setTabId(newTaskInstance.getTabId() != null ? newTaskInstance.getTabId() : this.getTabId());
		this.setBlockchainDate(newTaskInstance.getBlockchainDate() != null ? newTaskInstance.getBlockchainDate() : this.getBlockchainDate());
		this.setLevel(newTaskInstance.getLevel() != null ? newTaskInstance.getLevel() : this.getLevel());
		return this;
	}

}
