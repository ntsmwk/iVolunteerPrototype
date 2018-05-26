package at.jku.cis.iVolunteer.workflow;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiugration {

	@Value("${spring.data.mysqldb.uri}")
	private String uri;

	@Value("${spring.data.mysqldb.user}")
	private String user;

	@Value("${spring.data.mysqldb.password}")
	private String password;

	@Bean
	public DataSource database() {
		return DataSourceBuilder.create().url(uri).username(user).password(password)
				.driverClassName("com.mysql.jdbc.Driver").build();
	}

}
