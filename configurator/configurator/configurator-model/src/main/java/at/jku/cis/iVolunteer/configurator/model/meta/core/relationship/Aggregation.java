package at.jku.cis.iVolunteer.configurator.model.meta.core.relationship;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relationship")
public class Aggregation extends Association {

	public Aggregation() {
		this.sourceCardinality = AssociationCardinality.ONE;
		this.targetCardinality = AssociationCardinality.ONE;
	}

}
