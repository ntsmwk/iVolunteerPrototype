package at.jku.cis.iVolunteer.marketplace.badge;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.badge.XBadgeCertificateNotification;

@Repository
public interface XBadgeCertificateNotificationRepository
		extends MongoRepository<XBadgeCertificateNotification, String> {
	
	List<XBadgeCertificateNotification> findAllByBadgeCertificateId(List<String> badgeCertificateIds);
	
	List<XBadgeCertificateNotification> findAllByUserId(String userId);

}
