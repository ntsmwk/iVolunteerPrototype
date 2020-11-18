package at.jku.cis.iVolunteer.marketplace.badge;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model._mapper.xnet.XBadgeTemplateToXBadgeSerializedMapper;
import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;
import at.jku.cis.iVolunteer.model.badge.XBadgeCertificateNotification;
import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;
import at.jku.cis.iVolunteer.model.core.tenant.XTenantSerialized;

@Service
public class XBadgeCertificateService {
	@Autowired private XBadgeTemplateRepository badgeTemplateRepository;
	@Autowired private XBadgeTemplateToXBadgeSerializedMapper badgeTemplateToXBadgeSerializedMapper;
	@Autowired private XBadgeCertificateRepository badgeCertificateRepository;
	@Autowired private XBadgeCertificateNotificationRepository badgeCertificateNotificationRepository;

	public void createBadgeCertificate(String userId, String badgeTemplateId, XTenantSerialized tenantSerialized) {
		XBadgeTemplate badgeTemplate = badgeTemplateRepository.findOne(badgeTemplateId);

		XBadgeCertificate badgeCertificate = new XBadgeCertificate();
		badgeCertificate.setIssueDate(new Date());
		badgeCertificate.setUserId(userId);
		badgeCertificate.setBadgeSerialized(badgeTemplateToXBadgeSerializedMapper.toTarget(badgeTemplate));
		badgeCertificate.setTenantSerialized(tenantSerialized);
		badgeCertificate = badgeCertificateRepository.save(badgeCertificate);

		XBadgeCertificateNotification notification = new XBadgeCertificateNotification();
		notification.setUserId(userId);
		notification.setBadgeCertificateId(badgeCertificate.getId());
		badgeCertificateNotificationRepository.save(notification);
	}

}
