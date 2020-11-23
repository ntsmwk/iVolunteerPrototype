package at.jku.cis.iVolunteer.configurator.commons;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class DateTimeService {

	public Date parseMultipleDateFormats(String dateString) {
		String[] formatStrings = new String[] { "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm.ss'Z'",
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd' 'HH:mm:ss",
				"yyyy-MM-dd'T'HH:mm:ssXXX", "M/y", "M/d/y", "M-d-y" };
		try {
			return DateUtils.parseDate(dateString, formatStrings);
		} catch (ParseException e) {
		}

		return null;
	}
}