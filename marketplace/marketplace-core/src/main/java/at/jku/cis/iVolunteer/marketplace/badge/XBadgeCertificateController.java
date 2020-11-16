package at.jku.cis.iVolunteer.marketplace.badge;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;
import at.jku.cis.iVolunteer.model.badge.XBadgeCertificateNotification;
import at.jku.cis.iVolunteer.model.user.User;

@RestController
@RequestMapping("badgeCertificate")
public class XBadgeCertificateController {

	@Autowired private XBadgeCertificateRepository badgeCertificateRepository;
	@Autowired private XBadgeCertificateNotificationRepository badgeCertificateNotificationRepository;
	@Autowired private LoginService loginService;

	@GetMapping
	public List<XBadgeCertificate> getAll() {
		return badgeCertificateRepository.findAll();
	}

	@GetMapping("/unnotified")
	public ResponseEntity<List<XBadgeCertificate>> getAllUnnotified() {
		User user = loginService.getLoggedInUser();

		List<XBadgeCertificateNotification> notifications = badgeCertificateNotificationRepository
				.findAllByUserId(user.getId());
		List<String> badgeCertificateIds = notifications.stream().map(n -> n.getBadgeCertificateId())
				.collect(Collectors.toList());

		List<XBadgeCertificate> collectedList = StreamSupport
				.stream(badgeCertificateRepository.findAll(badgeCertificateIds).spliterator(), false)
				.collect(Collectors.toList());
		return ResponseEntity.ok(collectedList);
	}

	@PostMapping("/notify")
	public ResponseEntity<Object> notify(@RequestBody List<String> badgeCertificateIds) {
		Iterable<XBadgeCertificateNotification> badgeCertificateNotifications = badgeCertificateNotificationRepository
				.findAllByBadgeCertificateId(badgeCertificateIds);
		badgeCertificateNotificationRepository.delete(badgeCertificateNotifications);
		return ResponseEntity.ok().build();
	}

	@GetMapping("user/{userId}")
	public ResponseEntity<Object> getAllByUserId(@PathVariable String userId) {
		User user = loginService.getLoggedInUser();
		return ResponseEntity.ok(badgeCertificateRepository.findAllByUserId(user.getId()));
	}
}
