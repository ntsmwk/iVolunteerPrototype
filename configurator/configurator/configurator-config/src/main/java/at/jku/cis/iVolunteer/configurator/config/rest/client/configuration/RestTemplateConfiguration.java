package at.jku.cis.iVolunteer.configurator.config.rest.client.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.configurator.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.configurator.model.exception.ForbiddenException;
import at.jku.cis.iVolunteer.configurator.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.configurator.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.configurator.model.exception.PreConditionFailedException;

@Configuration
public class RestTemplateConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateConfiguration.class);

	@Bean(name = WorkflowRestTemplate.NAME)
	public RestTemplate produceRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				LOGGER.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());

				switch (response.getStatusCode()) {
				case BAD_REQUEST:
					throw new BadRequestException();
				case FORBIDDEN:
					throw new ForbiddenException();
				case NOT_FOUND:
					throw new NotFoundException();
				case NOT_ACCEPTABLE:
					throw new NotAcceptableException();
				case PRECONDITION_FAILED:
					throw new PreConditionFailedException();
				default:
					super.handleError(response);
				}
			}
		});
		return restTemplate;
	}
}
