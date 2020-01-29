package at.jku.cis.iVolunteer.marketplace.fake;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class IsSunburstFakeDocument {
	@Id String id;
}
