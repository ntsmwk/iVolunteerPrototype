package at.jku.cis.iVolunteer.configurator.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class PreConditionFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PreConditionFailedException() {
		super();
	}

	public PreConditionFailedException(String message) {
		super(message);
	}

	public PreConditionFailedException(Throwable throwable) {
		super(throwable);
	}

}
