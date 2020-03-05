package at.jku.cis.iVolunteer.marketplace.configurations.clazz;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfigurator;

public interface ConfiguratorRepository extends MongoRepository<ClassConfigurator, String> {

	public List<ClassConfigurator> findByName(String name);

	@Query(value="{}")
	public List<ClassConfigurator> findAllWithSort(Sort sort);
}
