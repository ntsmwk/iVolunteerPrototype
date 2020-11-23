package at.jku.cis.iVolunteer.core.badge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
@RequestMapping("/badgeCertificate")
public class XCoreBadgeCertificateController {

	@Autowired private XBadgeCertificateRestClient badgeCertificateRestClient;
	@Autowired private MarketplaceRepository marketplaceRepository;

	@GetMapping
	public ResponseEntity<List<XBadgeCertificate>> getAllBadgeCertificates(
			@RequestHeader("Authorization") String authorization) {
		List<XBadgeCertificate> xBadgeCertificate = new ArrayList<>();
		List<Marketplace> marketplaces = marketplaceRepository.findAll();
		for (Marketplace marketplace : marketplaces) {
			List<XBadgeCertificate> temp = badgeCertificateRestClient.getXBadgeCertificates(marketplace.getUrl(),
					authorization);
			xBadgeCertificate.addAll(temp);
		}

		return ResponseEntity.ok(xBadgeCertificate);
	}

	@GetMapping("/unnotified")
	public ResponseEntity<Object> getAllUnnotifiedBadgeCertificates(
			@RequestHeader("Authorization") String authorization) {
		List<XBadgeCertificate> xBadgeCertificate = new ArrayList<>();
		List<Marketplace> marketplaces = marketplaceRepository.findAll();
		for (Marketplace marketplace : marketplaces) {
			List<XBadgeCertificate> temp = badgeCertificateRestClient
					.getUnnotifiedXBadgeCertificates(marketplace.getUrl(), authorization);
			xBadgeCertificate.addAll(temp);
		}
		return ResponseEntity.ok(xBadgeCertificate);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Object> getAllBadgeCertificatesOfUser(@PathVariable String userId,
			@RequestHeader("Authorization") String authorization) {
		List<XBadgeCertificate> xBadgeCertificate = new ArrayList<>();
		List<Marketplace> marketplaces = marketplaceRepository.findAll();
		for (Marketplace marketplace : marketplaces) {
			List<XBadgeCertificate> temp = badgeCertificateRestClient
					.getXBadgeCertificatesByUserId(marketplace.getUrl(), userId, authorization);
			xBadgeCertificate.addAll(temp);
		}
		return ResponseEntity.ok(xBadgeCertificate);
	}
}
