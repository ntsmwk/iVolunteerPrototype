package at.jku.cis.iVolunteer;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;

@SpringBootApplication
public class MarketplaceApplication {

	@Autowired private ConfiguratorRepository configuratorRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;

	@Bean
	@Primary
	public RestTemplate produceRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		addConfigurators();
		CompetenceClassDefinition def = new CompetenceClassDefinition();
		def.setClassArchetype(ClassArchetype.COMPETENCE);
		def.setMarketplaceId("asdfasdf");
		def.setName("mycompdf");
		def.setTimestamp(new Date());
		classDefinitionRepository.save(def);

	}

	private void addConfigurators() {
		Configurator c1 = new Configurator();
		c1.setName("test1");
		c1.setId("test1");
		c1.setDate(new Date());

		Configurator c2 = new Configurator();
		c2.setName("test2");
		c2.setId("test2");
		c2.setDate(new Date(1289516400000L));

		Configurator c3 = new Configurator();
		c3.setName("test3");
		c3.setId("test3");

		c3.setDate(new Date());

		Configurator c4 = new Configurator();
		c4.setName("test4");
		c4.setId("test4");

		c4.setDate(new Date());

		if (!configuratorRepository.exists(c1.getId())) {
			configuratorRepository.save(c1);
		}

		if (!configuratorRepository.exists(c2.getId())) {
			configuratorRepository.save(c2);
		}

		if (!configuratorRepository.exists(c3.getId())) {
			configuratorRepository.save(c3);
		}

		if (!configuratorRepository.exists(c3.getId())) {
			configuratorRepository.save(c3);
		}

		if (!configuratorRepository.exists(c4.getId())) {
			configuratorRepository.save(c4);
		}

	}

}
