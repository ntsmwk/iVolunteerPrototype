package at.jku.cis.iVolunteer.marketplace.meta.configurator;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import at.jku.cis.iVolunteer.model.configuration.clazz.Configurator;

public interface ConfiguratorRepository extends MongoRepository<Configurator, String> {

	public List<Configurator> findByName(String name);

	@Query(value="{}")
	public List<Configurator> findAllWithSort(Sort sort);
}
