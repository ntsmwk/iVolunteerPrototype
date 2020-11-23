package at.jku.cis.iVolunteer.configurator.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
