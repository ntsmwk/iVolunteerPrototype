package at.jku.cis.iVolunteer.core.configuration;

import static java.util.Arrays.asList;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
public class MongoConfiguration extends AbstractMongoConfiguration {

	private static final String ADMIN = "admin";

	@Value("${spring.data.mongodb.uri}")
	private URI uri;

	@Value("${spring.data.mongodb.user}")
	private String user;

	@Value("${spring.data.mongodb.password}")
	private String password;

	@Override
	protected String getDatabaseName() {
		return uri.getPath().substring(1);
	}

	@Bean
	public Mongo mongo() throws Exception {
		MongoCredential credential = createMongoCredential();
		return new MongoClient(createServerAddress(), asList(credential));
	}

	@Override
	public String getMappingBasePackage() {
		return null;
	}

	private ServerAddress createServerAddress() {
		return new ServerAddress(uri.getHost(), uri.getPort());
	}

	private MongoCredential createMongoCredential() {
		return MongoCredential.createCredential(user, ADMIN, password.toCharArray());
	}
}