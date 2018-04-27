package at.jku.cis.marketplace.source;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceController {

	@Value("${marketplace.identifier}")
	private String identifier;

	@GetMapping("/source")
	public Source getMarketplaceSource(HttpServletRequest httpServletRequest) {
		Source source = new Source();
		source.setIdentifier(identifier);
		source.setAddress(determineRemoteAddress(httpServletRequest));
		return source;
	}

	private String determineRemoteAddress(HttpServletRequest httpServletRequest) {
		String requestURL = httpServletRequest.getRequestURL().toString();
		String requestPath = httpServletRequest.getRequestURI().toString();
		return requestURL.substring(0, requestURL.length() - requestPath.length());
	}

}
