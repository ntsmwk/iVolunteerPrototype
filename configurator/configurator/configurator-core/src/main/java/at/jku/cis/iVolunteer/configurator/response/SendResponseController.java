package at.jku.cis.iVolunteer.configurator.response;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator._mapper.relationship.RelationshipMapper;
import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.configurator.configurations.matching.configuration.MatchingConfigurationService;
import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipController;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassInstanceConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.MatchingConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.PropertyConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendClassInstanceConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendMatchingConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendPropertyConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendClassConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfigurationBundle;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

@RestController
public class SendResponseController {

	@Autowired private ClassConfigurationController classConfigurationController;
	@Autowired private MatchingConfigurationService matchingConfigurationService;
	@Autowired private ResponseRestClient responseRestClient;
	@Autowired private MatchingOperatorRelationshipController matchingOperatorRelationshipController;
	@Autowired private FlatPropertyDefinitionRepository flatPropertyDefinitionRepository;
	@Autowired private TreePropertyDefinitionRepository treePropertyDefinitionRepository;
	@Autowired private RelationshipMapper relationshipMapper;

	@PostMapping("/send-response/class-configurator")
	public ResponseEntity<Object> sendClassConfiguratorResponse(
			@RequestBody FrontendClassConfiguratorRequestBody body) {
		ClassConfiguratorResponseRequestBody responseRequestBody = new ClassConfiguratorResponseRequestBody();

		if (body.getAction().equals("new")) {
			
			ClassConfigurationBundle bundle = classConfigurationController.createNewClassConfiguration(
					body.getSaveRequest().getTenantId(), body.getSaveRequest().getClassConfiguration().getName(),
					body.getSaveRequest().getClassConfiguration().getDescription(), null, body.getActionContext());

			body.getSaveRequest().setClassConfiguration(bundle.getClassConfiguration());
			body.getSaveRequest().setClassDefinitions(bundle.getClassDefinitions());
			body.getSaveRequest().setRelationships(relationshipMapper.toTargets(bundle.getRelationships()));
			body.setAction("save");
		}

		if (body.getSaveRequest() != null) {
			responseRequestBody.setClassConfiguration(body.getSaveRequest().getClassConfiguration());
			responseRequestBody.setClassDefinitions(body.getSaveRequest().getClassDefinitions());
			responseRequestBody.setRelationships(body.getSaveRequest().getRelationships());
		}

		responseRequestBody.setIdsToDelete(body.getIdsToDelete());
		responseRequestBody.setAction(body.getAction());

		ResponseEntity<Object> resp = null;
		if (body.getUrl() != null) {
			resp = responseRestClient.sendClassConfiguratorResponse(body.getUrl(), responseRequestBody);
		} else {
			System.out.println("no response sent");
		}
		
		if (resp != null && resp.getStatusCode().is4xxClientError()) {
			System.out.println("error - dont save / delete");
			return resp;
		} else if ((resp == null) || (resp != null && resp.getStatusCode().is2xxSuccessful())) {
			if (body.getAction().equals("save")) {
				ClassConfiguration ret = classConfigurationController.saveEverything(body.getSaveRequest());
				return ResponseEntity.ok(ret);
			} else if (body.getAction().equals("delete")) {
				List<ClassConfiguration> configs = classConfigurationController.deleteMultipleClassConfigurations(body.getIdsToDelete());
				return ResponseEntity.ok().build();
			}
		}
		
		return ResponseEntity.badRequest().build();


	}

	@PostMapping("/send-response/class-instance-configurator")
	public ResponseEntity<Object> sendClassInstanceConfiguratorResponse(
			@RequestBody FrontendClassInstanceConfiguratorRequestBody body) {
		ClassInstanceConfiguratorResponseRequestBody responseRequestBody = new ClassInstanceConfiguratorResponseRequestBody();
		responseRequestBody.setClassInstance(body.getClassInstance());

		ResponseEntity<Object> resp = responseRestClient.sendClassInstanceConfiguratorResponse(body.getUrl(),
				responseRequestBody);
		return resp;
	}

	@PostMapping("/send-response/matching-configurator")
	public ResponseEntity<Object> sendMatchingConfiguratorResponse(
			@RequestBody FrontendMatchingConfiguratorRequestBody body) {

		MatchingConfiguratorResponseRequestBody responseRequestBody = new MatchingConfiguratorResponseRequestBody();
		if (body.getSaveRequest() != null) {
	
			if (body.getSaveRequest().getMatchingConfiguration().getId() == null) {
				body.getSaveRequest().setMatchingConfiguration(matchingConfigurationService
						.createMatchingConfiguration(body.getSaveRequest().getMatchingConfiguration()));
			}

			responseRequestBody.setMatchingConfiguration(body.getSaveRequest().getMatchingConfiguration());
			responseRequestBody.setMatchingRelationships(body.getSaveRequest().getMatchingOperatorRelationships());
		}

		responseRequestBody.setIdsToDelete(body.getIdsToDelete());
		responseRequestBody.setAction(body.getAction());

		ResponseEntity<Object> resp = null;
		if (body.getUrl() != null) {
		 resp = responseRestClient.sendMatchingConfiguratorResponse(body.getUrl(), responseRequestBody);
		}

		if (resp != null && resp.getStatusCode().is4xxClientError()) {
			System.out.println("error - dont saved / deleted");
			return resp;
		} else if ((resp == null) || (resp != null && resp.getStatusCode().is2xxSuccessful())) {
			if (body.getAction().equals("save")) {
				MatchingConfiguration ret = matchingConfigurationService
						.saveMatchingConfiguration(body.getSaveRequest().getMatchingConfiguration());
				matchingOperatorRelationshipController.saveMatchingOperatorRelationshipByMatchingConfiguration(
						body.getSaveRequest().getMatchingConfiguration().getId(),
						body.getSaveRequest().getMatchingOperatorRelationships());
				return ResponseEntity.ok(ret);
			} else if (body.getAction().equals("delete")) {
				body.getIdsToDelete().forEach(matchingConfigurationService::deleteMatchingConfiguration);
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.badRequest().build();
		}

		return resp;
	}

	@PostMapping("/send-response/property-configurator")
	public ResponseEntity<Object> sendPropertyConfiguratorResponse(@RequestBody FrontendPropertyConfiguratorRequestBody body) {
		PropertyConfiguratorResponseRequestBody responseRequestBody = new PropertyConfiguratorResponseRequestBody();

		responseRequestBody.setAction(body.getAction());
		
		if (body.getFlatPropertyDefinitions() != null) {
			for (FlatPropertyDefinition<Object> pd : body.getFlatPropertyDefinitions()) {
				if (pd.getId() == null) {
					pd.setId(new ObjectId().toHexString());
					List<FlatPropertyDefinition<Object>> existing = flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId());
					if (existing != null && existing.size() > 0) {
						return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
					}
				}
			}
			responseRequestBody.setFlatPropertyDefinitions(body.getFlatPropertyDefinitions());
		}

		if (body.getTreePropertyDefinitions() != null) {
			for(TreePropertyDefinition pd : body.getTreePropertyDefinitions()) {
				if (pd.getId() == null) {
					pd.setId(new ObjectId().toHexString());
					List<TreePropertyDefinition> existing = treePropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId());
					if (existing != null && existing.size() > 0) {
						return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
					}
				}
			}
			responseRequestBody.setTreePropertyDefinitions(body.getTreePropertyDefinitions());
		}
		
		ResponseEntity<Object> resp = null;
		if (body.getUrl() != null) {
			resp = responseRestClient.sendPropertyConfiguratorResponse(body.getUrl(), responseRequestBody);
		}
		if (resp != null && resp.getStatusCode().is4xxClientError()) {
			System.out.println("error - not saved / deleted");
			return resp;
		} else if ((resp == null) || (resp != null && resp.getStatusCode().is2xxSuccessful())) {
			if (body.getAction().equals("save")) {
				if (body.getFlatPropertyDefinitions() != null) {
					flatPropertyDefinitionRepository.save(body.getFlatPropertyDefinitions());
					return ResponseEntity.ok(body.getFlatPropertyDefinitions());
				}
				else if (body.getTreePropertyDefinitions() != null) {
					treePropertyDefinitionRepository.save(body.getTreePropertyDefinitions());
					return ResponseEntity.ok(body.getTreePropertyDefinitions());
				}
			} 
			else if (body.getAction().equals("delete")) {
				if (body.getFlatPropertyDefinitions() != null) {
					flatPropertyDefinitionRepository.delete(body.getFlatPropertyDefinitions());
				}
				else if (body.getTreePropertyDefinitions() != null) {
					treePropertyDefinitionRepository.delete(body.getTreePropertyDefinitions());
				}
				return ResponseEntity.ok().build();
			} 
			return ResponseEntity.badRequest().build();
		}

		return resp;
	}
}
