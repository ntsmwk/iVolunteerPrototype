package at.jku.cis.iVolunteer.configurator.model.meta.core.clazz;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.IVolunteerObject;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.ClassProperty;

public class ClassDefinitionDTO extends IVolunteerObject{

	
	private String parentId;
	private String configurationId;
	private String configurationName;
	private String name;
	private List<ClassProperty<Object>> properties = new ArrayList<>();
	private ClassArchetype classArchetype;
	
	
	private boolean collector;
	private boolean writeProtected;

	private String imagePath;
	
	boolean root;
	
	
	private boolean visible;
	private int tabId;

	public ClassDefinitionDTO() {
		
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(String configurationId) {
		this.configurationId = configurationId;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClassProperty<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<ClassProperty<Object>> properties) {
		this.properties = properties;
	}

	public ClassArchetype getClassArchetype() {
		return classArchetype;
	}

	public void setClassArchetype(ClassArchetype classArchetype) {
		this.classArchetype = classArchetype;
	}

	public boolean isCollector() {
		return collector;
	}

	public void setCollector(boolean collector) {
		this.collector = collector;
	}

	public boolean isWriteProtected() {
		return writeProtected;
	}

	public void setWriteProtected(boolean writeProtected) {
		this.writeProtected = writeProtected;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getTabId() {
		return tabId;
	}

	public void setTabId(int tabId) {
		this.tabId = tabId;
	}
	
	
	
}
