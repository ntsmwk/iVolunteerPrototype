package at.jku.cis.iVolunteer.marketplace.property;



import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.Property;


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



