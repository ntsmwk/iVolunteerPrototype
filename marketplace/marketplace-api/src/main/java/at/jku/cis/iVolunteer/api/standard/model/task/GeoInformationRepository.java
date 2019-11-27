package at.jku.cis.iVolunteer.api.standard.model.task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoInformationRepository extends MongoRepository<GeoInformation, String> {

}
