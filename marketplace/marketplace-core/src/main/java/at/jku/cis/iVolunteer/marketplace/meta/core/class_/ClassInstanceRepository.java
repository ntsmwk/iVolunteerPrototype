package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Repository
public interface ClassInstanceRepository extends MongoRepository<ClassInstance, String> {
		
	List<ClassInstance> getByClassDefinitionId(String classDefinitionId);
	
	List<ClassInstance> getByUserId(String userId);

}
