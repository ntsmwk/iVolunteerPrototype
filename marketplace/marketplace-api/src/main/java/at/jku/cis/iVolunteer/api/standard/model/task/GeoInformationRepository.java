package at.jku.cis.iVolunteer.api.standard.model.task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.task.GeoInformation;

@Repository
public interface GeoInformationRepository extends MongoRepository<GeoInformation, String> {

}
