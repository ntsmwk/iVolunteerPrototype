package at.jku.cis.iVolunteer.model.configurable.configurables;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;

@Document
public class MatchingRule extends ConfigurableObject {
	
	//subset von Properties angeben
	//gewichtung per Property 
	//Aggregationsfunktion enum
	
	
	public MatchingRule() {
		super.setConfigurableType("matchingRule");
	}



}
