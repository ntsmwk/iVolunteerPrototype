package at.jku.cis.iVolunteer.configurator.model.meta.core.clazz;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.instance.PropertyInstance;

public class TaskInstance extends ClassInstance {

	private List<String> subscribedVolunteerIds = new ArrayList<>();
	private TaskInstanceStatus status;

	public List<String> getSubscribedVolunteerIds() {
		return subscribedVolunteerIds;
	}
	public void setSubscribedVolunteerIds(List<String> subscribedVolunteerIds) {
		this.subscribedVolunteerIds = subscribedVolunteerIds;
	}
	public TaskInstanceStatus getStatus() {
		return status;
	}
	public void setStatus(TaskInstanceStatus status) {
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
		this.setName(newTaskInstance.getName() != null ? newTaskInstance.getName() : this.getName());
		this.setDescription(newTaskInstance.getDescription() != null ? newTaskInstance.getDescription() : this.getDescription());
		this.setUserId(newTaskInstance.getUserId() != null ? newTaskInstance.getUserId() : this.getUserId());
		this.setIssuerId(newTaskInstance.getIssuerId() != null ? newTaskInstance.getIssuerId() : this.getIssuerId());
		this.setImagePath(newTaskInstance.getImagePath() != null ? newTaskInstance.getImagePath() : this.getImagePath());
		this.setClassArchetype(newTaskInstance.getClassArchetype() != null ? newTaskInstance.getClassArchetype() : this.getClassArchetype());
		this.setChildClassInstances(newTaskInstance.getChildClassInstances() != null ? newTaskInstance.getChildClassInstances() : this.getChildClassInstances());
		this.setVisible(newTaskInstance.isVisible() != null ? newTaskInstance.isVisible() : this.isVisible());
		this.setTabId(newTaskInstance.getTabId() != null ? newTaskInstance.getTabId() : this.getTabId());
		this.setBlockchainDate(newTaskInstance.getBlockchainDate() != null ? newTaskInstance.getBlockchainDate() : this.getBlockchainDate());
		this.setLevel(newTaskInstance.getLevel() != null ? newTaskInstance.getLevel() : this.getLevel());

		if (newTaskInstance.getProperties() != null) {
			for (PropertyInstance<Object> pi : newTaskInstance.getProperties()) {
				if (this.containsProperty(pi.getId())) {
					int i = this.getProperties().indexOf(pi);
					this.getProperties().set(i, pi);
				} else {
					this.getProperties().add(pi);
				}
			}
		}		
		
		this.setSubscribedVolunteerIds(newTaskInstance.getSubscribedVolunteerIds() != null ? newTaskInstance.getSubscribedVolunteerIds() : this.getSubscribedVolunteerIds());
		this.setStatus(newTaskInstance.getStatus() != null ? newTaskInstance.getStatus() : this.getStatus());
		return this;
	}
	
	public TaskInstance updateTaskInstance(ClassInstance newTaskInstance) {
		this.setClassDefinitionId(newTaskInstance.getClassDefinitionId() != null ? newTaskInstance.getClassDefinitionId() : this.getClassDefinitionId());
		this.setName(newTaskInstance.getName() != null ? newTaskInstance.getName() : this.getName());
		this.setDescription(newTaskInstance.getDescription() != null ? newTaskInstance.getDescription() : this.getDescription());
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
		this.setProperties(newTaskInstance.getProperties());
		this.setTenantId(newTaskInstance.getTenantId() != null ? newTaskInstance.getTenantId() : this.getTenantId());

		return this;
	}

}
