package at.jku.csi.mongo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import at.jku.csi.marketplace.task.TaskStatus;
import at.jku.csi.marketplace.task.interaction.TaskOperation;
import at.jku.csi.marketplace.task.interaction.TaskVolunteerOperation;

@Configuration
@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Value("${spring.data.mongodb.uri}")
	private URI uri;

	@Override
	protected String getDatabaseName() {
		return uri.getPath().substring(1);
	}

	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient(uri.getHost(), uri.getPort());
	}

	@Override
	public String getMappingBasePackage() {
		return null;
	}

	@Override
	public CustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(new String2TaskOperationConverter());
		return new CustomConversions(converters);
	}

	@ReadingConverter
	static class String2TaskOperationConverter implements Converter<String, TaskOperation> {

		@Override
		public TaskOperation convert(String value) {
			TaskStatus taskStatus = TaskStatus.valueOf(value);
			if (taskStatus != null) {
				return taskStatus;
			}
			TaskVolunteerOperation taskVolunteerOperation = TaskVolunteerOperation.valueOf(value);
			if (taskVolunteerOperation != null) {
				return taskVolunteerOperation;
			}
			return null;
		}
	}
}