package at.jku.cis.iVolunteer.marketplace.source;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.source.dto.SourceDTO;

@RestController
public class SourceController {

	@Value("${server.port}")
	private String serverPort;

	@Value("${marketplace.identifier}")
	private String identifier;

	@GetMapping("/source")
	public SourceDTO getMarketplaceSource(HttpServletRequest httpServletRequest) {
		SourceDTO source = new SourceDTO();
		source.setIdentifier(identifier);
		source.setAddress(determineRemoteAddress(httpServletRequest));
		return source;
	}

	private String determineRemoteAddress(HttpServletRequest httpServletRequest) {
		return "http://" + httpServletRequest.getRemoteAddr() + ":" + serverPort;
	}
}