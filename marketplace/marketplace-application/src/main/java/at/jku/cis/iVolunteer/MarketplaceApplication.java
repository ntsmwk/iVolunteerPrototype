package at.jku.cis.iVolunteer;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;

@SpringBootApplication
public class MarketplaceApplication {

	@Bean
	@Primary
	public RestTemplate produceRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	// does not work - find better solution @Alex -- fixed --- kinda; maybe use
	// array or List of repositories instead

	@Autowired private ConfiguratorRepository configuratorRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private DerivationRuleRepository derivationRuleRepository;

	@PreDestroy
	public void onExit() {
		FinalizationService finalizationService = new FinalizationService();
		finalizationService.destroy(configuratorRepository, classDefinitionRepository, classInstanceRepository,
				relationshipRepository, propertyDefinitionRepository, derivationRuleRepository);
	}

}
