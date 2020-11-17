package at.jku.cis.iVolunteer.marketplace.badge;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;

@Repository
public interface XBadgeCertificateRepository extends MongoRepository<XBadgeCertificate, String> {

	List<XBadgeCertificate> findAllByUserId(String userId);

}
