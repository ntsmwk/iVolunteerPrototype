package at.jku.cis.iVolunteer.marketplace.property;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {

	Property findByName(String name);

}



