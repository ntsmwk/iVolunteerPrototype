package at.jku.csi.marketplace.configuration;

import java.net.URI;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import at.jku.csi.marketplace.task.interaction.String2TaskOperationConverter;

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

	
}