package at.jku.cis.iVolunteer.marketplace.property;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.configurable.configurables.property.Property;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {

//	@Query(value="{configurableType: 'property', name: ?0}")
//	Property findByName(String name);
//	
//	@Query(value="{configurableType: 'property'}")
//	List<Property> findAll();
//	
//	@Query(value="{configurableType: 'property', }", count=true)
//	long count();

	
	Property findByName(String name);
}



