package at.jku.cis.iVolunteer.api.standard.model.certificate;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonCertificateRepository extends MongoRepository<PersonCertificate, String> {

	@Query("{personID:?0}")
	List<PersonCertificate> findByPersonID(String personID);
}
