package at.jku.csi.marketplace.mongo;

import java.net.URI;
import java.util.Arrays;

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
		return new CustomConversions(Arrays.asList(new String2TaskOperationConverter()));
	}

	@ReadingConverter
	static class String2TaskOperationConverter implements Converter<String, TaskOperation> {

		@Override
		public TaskOperation convert(String value) {
			try {
				if (TaskStatus.valueOf(value) != null) {
					return TaskStatus.valueOf(value);
				}
			} catch (IllegalArgumentException e) {
			}
			try {
				if (TaskVolunteerOperation.valueOf(value) != null) {
					return TaskVolunteerOperation.valueOf(value);
				}
			} catch (IllegalArgumentException e) {
			}
			return null;
		}
	}
}