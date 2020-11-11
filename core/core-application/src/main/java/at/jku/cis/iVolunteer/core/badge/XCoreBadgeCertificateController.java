package at.jku.cis.iVolunteer.core.badge;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/badgeCertificate")
public class XCoreBadgeCertificateController {

	
	@GetMapping
	public ResponseEntity<Object> getAllBadgeCertificates(){
		
		return null;
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<Object> getAllBadgeCertificatesOfUser(@PathVariable String userId){
		
		return null;
	}
}
