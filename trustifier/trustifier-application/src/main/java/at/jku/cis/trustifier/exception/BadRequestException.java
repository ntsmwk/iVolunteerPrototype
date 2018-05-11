package at.jku.cis.trustifier.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	public BadRequestException() {

	}

	public BadRequestException(Throwable throwable) {
		super(throwable);
	}

}
