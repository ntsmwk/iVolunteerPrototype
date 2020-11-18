package at.jku.cis.iVolunteer.configurator.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.configurator.model._httprequests.InitConfiguratorRequest;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendClassConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendPropertyConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.Tuple;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.response.ResponseRestClient;
import at.jku.cis.iVolunteer.configurator.response.SendResponseController;

@Service
public class InitializationService {

	@Autowired protected ClassDefinitionRepository classDefinitionRepository;
	@Autowired protected RelationshipRepository relationshipRepository;
	@Autowired protected FlatPropertyDefinitionRepository flatPropertyDefinitionRepository;
	@Autowired protected ClassConfigurationRepository classConfigurationRepository;
	@Autowired protected MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired protected MatchingEntityMappingConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired protected TreePropertyDefinitionRepository treePropertyDefinitionRepository;
	@Autowired protected ClassConfigurationController classConfigurationController;
	@Autowired protected MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired protected SendResponseController sendResponseRestClient;
	@Autowired protected ResponseRestClient resposneRestClient;
	@Autowired protected StandardPropertyDefinitions standardPropertyDefinitions;

	private List<Tuple<String, String>> tenantIds;
	private String marketplaceUrl;

	public void init(InitConfiguratorRequest initRequestBody) {
		this.tenantIds = initRequestBody.getTenantIds();
		this.marketplaceUrl = initRequestBody.getMpUrl();

		if (tenantIds != null) {
			List<FlatPropertyDefinition<Object>> flatDefs = new LinkedList<>();
			List<TreePropertyDefinition> treeDefs = new ArrayList<>();

			flatDefs.addAll(prepareiVolunteerFlatPropertyDefinitions());			
			flatDefs.addAll(prepareGenericPropertyDefintions());
			flatDefs.addAll(prepareHeaderPropertyDefintions());
			flatDefs.addAll(prepareMichaTestProperties());
//			flatDefs.addAll(prepareFlexProdPropertyDefinitions());
			
			treeDefs.addAll(prepareiVolunteerTreePropertyDefinitions());

			flatDefs = flatPropertyDefinitionRepository.save(flatDefs);
			treeDefs = treePropertyDefinitionRepository.save(treeDefs);

			FrontendPropertyConfiguratorRequestBody body = new FrontendPropertyConfiguratorRequestBody();
			body.setAction("save");
			if (flatDefs != null) {
				body.setFlatPropertyDefinitionIds(flatDefs.stream().map(pd -> pd.getId()).collect(Collectors.toList()));
			}
			if (treeDefs != null) {
				body.setTreePropertyDefinitionIds(treeDefs.stream().map(pd -> pd.getId()).collect(Collectors.toList()));
			}
			body.setUrl(marketplaceUrl+"/response/property-configurator");
			sendResponseRestClient.sendPropertyConfiguratorResponse(body);

			addClassConfigurations(1);
//			addFlexProdClassDefinitionsAndConfigurations();
		}

	}

	public List<FlatPropertyDefinition<Object>> prepareiVolunteerFlatPropertyDefinitions() {

		List<FlatPropertyDefinition<Object>> flatDefs = new LinkedList<>();

		for (int i = 0; i < tenantIds.size(); i++) {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAlliVolunteer()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenantIds.get(i).getId())
						.size() == 0) {
					pd.setTenantId(tenantIds.get(i).getId());
					pd.setCustom(false);
					flatDefs.add(pd);
				}
			}
		}

		return flatDefs;
	}

	public List<TreePropertyDefinition> prepareiVolunteerTreePropertyDefinitions() {
		List<TreePropertyDefinition> treeDefs = new LinkedList<>();

//		String key = (tenantIds.get(i).getLabel().equals("FF Eidenberg")) ? "FF" : "MV";
		for (int i = 0; i < tenantIds.size(); i++) {

			String key = "FF";
			for (TreePropertyDefinition tpd : standardPropertyDefinitions.getAllTreeProperties(key)) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(tpd.getName(), tenantIds.get(i).getId())
						.size() == 0) {
					tpd.setTenantId(tenantIds.get(i).getId());
					tpd.setCustom(false);
					treeDefs.add(tpd);
				}
			}
		}

		return treeDefs;
	}

	public List<FlatPropertyDefinition<Object>> prepareFlexProdPropertyDefinitions() {
		List<FlatPropertyDefinition<Object>> defs = new LinkedList<>();

		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllFlexProdProperties()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant.getId()).size() == 0) {
					pd.setTenantId(tenant.getId());
					pd.setCustom(false);
					defs.add(pd);
				}
			}
		});

		return defs;
	}

	public List<FlatPropertyDefinition<Object>> prepareMichaTestProperties() {
		List<FlatPropertyDefinition<Object>> defs = new LinkedList<>();

		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllTest()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant.getId()).size() == 0) {
					pd.setTenantId(tenant.getId());
					pd.setCustom(false);
					defs.add(pd);
				}
			}
		});
		return defs;
	}

	public List<FlatPropertyDefinition<Object>> prepareGenericPropertyDefintions() {
		List<FlatPropertyDefinition<Object>> defs = new LinkedList<>();

		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllGeneric()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant.getId()).size() == 0) {
					pd.setTenantId(tenant.getId());
					pd.setCustom(false);
					defs.add(pd);
				}
			}
		});
		return defs;
	}

	public List<FlatPropertyDefinition<Object>> prepareHeaderPropertyDefintions() {
		List<FlatPropertyDefinition<Object>> defs = new LinkedList<>();

		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllHeader()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant.getId()).size() == 0) {
					pd.setTenantId(tenant.getId());
					pd.setCustom(false);
					defs.add(pd);
				}
			}
		});
		return defs;
	}

	public void addClassConfigurations(int noOfConfigurations) {

		for (Tuple<String, String> tenant : tenantIds) {
			for (int i = 1; i <= noOfConfigurations; i++) {
				ClassConfiguration cc = this.classConfigurationController
						.createNewClassConfiguration(new String[] { tenant.getId(), "Standardkonfiguration" + i, "" });

				// TODO send new stuff to mp
				FrontendClassConfiguratorRequestBody body = new FrontendClassConfiguratorRequestBody();
				body.setAction("save");
				body.setIdToSave(cc.getId());

				body.setUrl(marketplaceUrl + "/response/class-configurator");

				sendResponseRestClient.sendClassConfiguratorResponse(body);

			}
		}
	}

	public void addFlexProdClassDefinitionsAndConfigurations() {
		for (Tuple<String, String> tenant : tenantIds) {
			ClassConfiguration cc1 = this.classConfigurationController.createAndSaveHaubenofen(tenant.getId());
			ClassConfiguration cc2 = this.classConfigurationController.createAndSaveRFQ(tenant.getId());

			FrontendClassConfiguratorRequestBody body = new FrontendClassConfiguratorRequestBody();
			body.setAction("save");
			body.setIdToSave(cc1.getId());
			body.setUrl(marketplaceUrl + "/response/class-configurator");
			sendResponseRestClient.sendClassConfiguratorResponse(body);
			
			body = new FrontendClassConfiguratorRequestBody();
			body.setAction("save");
			body.setIdToSave(cc2.getId());
			body.setUrl(marketplaceUrl + "/response/class-configurator");
			sendResponseRestClient.sendClassConfiguratorResponse(body);
		}
	}

	public void deleteClassDefinitions() {
		classDefinitionRepository.deleteAll();
	}

	public void deleteRelationships() {
		relationshipRepository.deleteAll();
	}

	public void deleteClassConfigurations() {
		classConfigurationRepository.deleteAll();
	}

	public void deleteMatchingConfigurations() {
		matchingConfigurationRepository.deleteAll();
	}

	public void deleteProperties() {
		flatPropertyDefinitionRepository.deleteAll();
		treePropertyDefinitionRepository.deleteAll();
	}

	public void wipeConfigurator() {
		deleteClassDefinitions();
		deleteRelationships();
		deleteClassConfigurations();
		deleteMatchingConfigurations();
		deleteProperties();
		matchingConfigurationRepository.deleteAll();
	}

}
