package at.jku.cis.iVolunteer.marketplace.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import at.jku.cis.iVolunteer.model.task.interaction.String2TaskOperationConverter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new String2TaskOperationConverter());
	}
}