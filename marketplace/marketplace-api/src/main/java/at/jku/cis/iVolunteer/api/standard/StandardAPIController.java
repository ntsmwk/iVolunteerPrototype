package at.jku.cis.iVolunteer.api.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/standard")
public class StandardAPIController {

	@Autowired private StandardAPIService apiService;

	@PutMapping("/blockchainify")
	public void blockchainify() {
		apiService.blockchainify();
	}

}
