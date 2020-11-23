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
import at.jku.cis.iVolunteer.configurator.model._httprequests.SaveClassConfigurationRequest;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendClassConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendPropertyConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfigurationBundle;
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

	public void initIVolunteer(InitConfiguratorRequest initRequestBody) {
		this.tenantIds = initRequestBody.getTenantIds();
		this.marketplaceUrl = initRequestBody.getMpUrl();

		if (tenantIds != null) {
			List<FlatPropertyDefinition<Object>> flatDefs = new LinkedList<>();
			List<TreePropertyDefinition> treeDefs = new ArrayList<>();

			flatDefs.addAll(prepareAPIFlatPropertyDefinitions());			
			flatDefs.addAll(prepareGenericPropertyDefintions());
			flatDefs.addAll(prepareHeaderPropertyDefintions());
			flatDefs.addAll(prepareMichaTestProperties());
			treeDefs.addAll(prepareiVolunteerTreePropertyDefinitions());

			flatDefs = flatPropertyDefinitionRepository.save(flatDefs);
			treeDefs = treePropertyDefinitionRepository.save(treeDefs);

			FrontendPropertyConfiguratorRequestBody body = new FrontendPropertyConfiguratorRequestBody();
			
			
			body.setAction("save");
			body.setUrl(marketplaceUrl+"/response/property-configurator");

			
			if (flatDefs != null) {
				body.setFlatPropertyDefinitions(flatDefs);
				sendResponseRestClient.sendPropertyConfiguratorResponse(body);
			}
			if (treeDefs != null) {
				body.setFlatPropertyDefinitions(null);
				body.setTreePropertyDefinitions(treeDefs);
				sendResponseRestClient.sendPropertyConfiguratorResponse(body);

			}

			addClassConfigurations(1);
		}

	}
	
	public void initFlexProd(InitConfiguratorRequest initRequestBody) {
		this.tenantIds = initRequestBody.getTenantIds();
		this.marketplaceUrl = initRequestBody.getMpUrl();

		if (tenantIds != null) {
			List<FlatPropertyDefinition<Object>> flatDefs = new ArrayList<>();
			flatDefs.addAll(prepareFlexProdPropertyDefinitions());
			flatDefs = flatPropertyDefinitionRepository.save(flatDefs);
	
			FrontendPropertyConfiguratorRequestBody body = new FrontendPropertyConfiguratorRequestBody();
			body.setAction("save");
			if (flatDefs != null) {
				body.setFlatPropertyDefinitions(flatDefs);
			}
	
			if (this.marketplaceUrl != null) {
				body.setUrl(marketplaceUrl+"/response/property-configurator");
				sendResponseRestClient.sendPropertyConfiguratorResponse(body);
			}
			
			addFlexProdClassDefinitionsAndConfigurations();
		
		}
	}

	public List<FlatPropertyDefinition<Object>> prepareAPIFlatPropertyDefinitions() {

		List<FlatPropertyDefinition<Object>> flatDefs = new LinkedList<>();

		for (int i = 0; i < tenantIds.size(); i++) {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllAPI()) {
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

		for (int i = 0; i < tenantIds.size(); i++) {
			String key = (tenantIds.get(i).getLabel().equals("MV Schwertberg")) ? "MV" : "FF";

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
				FrontendClassConfiguratorRequestBody body = new FrontendClassConfiguratorRequestBody();
				body.setAction("new");
				SaveClassConfigurationRequest req = new SaveClassConfigurationRequest();
				ClassConfiguration ccStub = new ClassConfiguration();
				ccStub.setTenantId(tenant.getId());
				ccStub.setName("Standardkonfiguration " + i);
				req.setClassConfiguration(ccStub);
				req.setTenantId(tenant.getId());
				body.setSaveRequest(req);
				body.setUrl(marketplaceUrl + "/response/class-configurator");
				body.setActionContext("iVolunteer");
				
				sendResponseRestClient.sendClassConfiguratorResponse(body);
			}
		}
	}

	public void addFlexProdClassDefinitionsAndConfigurations() {
		for (Tuple<String, String> tenant : this.tenantIds) {
			
			FrontendClassConfiguratorRequestBody body = new FrontendClassConfiguratorRequestBody();
			body.setAction("new");
			SaveClassConfigurationRequest req = new SaveClassConfigurationRequest();
			ClassConfiguration ccStub = new ClassConfiguration();
			ccStub.setTenantId(tenant.getId());
			ccStub.setName("Haubenofen");
			req.setClassConfiguration(ccStub);
			req.setTenantId(tenant.getId());
			body.setSaveRequest(req);
			if (marketplaceUrl != null) {
				body.setUrl(marketplaceUrl + "/response/class-configurator");
			}
			body.setActionContext("flexprodHaubenofen");
			
			sendResponseRestClient.sendClassConfiguratorResponse(body);
			
			body = new FrontendClassConfiguratorRequestBody();
			body.setAction("new");
			req = new SaveClassConfigurationRequest();
			ccStub = new ClassConfiguration();
			ccStub.setTenantId(tenant.getId());
			ccStub.setName("RFQ");
			req.setClassConfiguration(ccStub);
			req.setTenantId(tenant.getId());
			body.setSaveRequest(req);
			if (marketplaceUrl != null) {
				body.setUrl(marketplaceUrl + "/response/class-configurator");
			}
			body.setActionContext("flexprodRFQ");
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
