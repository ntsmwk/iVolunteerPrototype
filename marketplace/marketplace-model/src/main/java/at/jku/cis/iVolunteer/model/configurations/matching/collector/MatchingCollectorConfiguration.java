package at.jku.cis.iVolunteer.model.configurations.matching.collector;

import java.util.List;

import org.springframework.data.annotation.Id;

import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.matching.MatchingCollector;

public class MatchingCollectorConfiguration {
	@Id String id;
	
	String classConfigurationId;
	
	List<MatchingCollector> collectors;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassConfigurationId() {
		return classConfigurationId;
	}

	public void setClassConfigurationId(String classConfigurationId) {
		this.classConfigurationId = classConfigurationId;
	}

	public List<MatchingCollector> getCollectors() {
		return collectors;
	}

	public void setCollectors(List<MatchingCollector> collectors) {
		this.collectors = collectors;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MatchingCollectorConfiguration)) {
			return false;
		}
		return ((MatchingCollectorConfiguration) obj).id.equals(id);
	}
	
	

}
