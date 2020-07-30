package at.jku.cis.iVolunteer.marketplace.rule.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.Fibonacci;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.Message;
import at.jku.cis.iVolunteer.marketplace.rule.engine.util.NoSuchContainerException;
import at.jku.cis.iVolunteer.marketplace.rule.engine.util.RuleEngineUtil;
import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserService;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;
import at.jku.cis.iVolunteer.model.rule.engine.RuleExecution;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

/**
 * Adapted from barry.wong
 */
@Service
public class RuleService {

	@Autowired private ContainerRuleEntryRepository containerRuleEntryRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private RuleEngineMapper ruleEngineMapper;
	@Autowired private UserService userService;

	private ConcurrentHashMap<String, ConcurrentHashMap<String, KieContainer>> tenant2ContainerMap;

	@PostConstruct
	private void init() {
		tenant2ContainerMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, KieContainer>>();
		refreshContainer();
	}

	public List<String> getContainerNames(String tenantId) {
		List<ContainerRuleEntry> rules = containerRuleEntryRepository.findByTenantId(tenantId);
		// obtain containers with rules
		List<String> containerNames = rules.stream().map(x -> x.getContainer()).distinct().collect(Collectors.toList());
		return containerNames;
	}

	public void refreshContainer() {
		// obtain all tenants with rules
		List<ContainerRuleEntry> allRules = containerRuleEntryRepository.findAll();
		allRules.stream().map(t -> t.getTenantId()).distinct().forEach(t -> refreshContainer(t));
	}

	public void refreshContainer(String tenantId) {
		// create map for tenant
		if (!tenant2ContainerMap.containsKey(tenantId))
			tenant2ContainerMap.put(tenantId, new ConcurrentHashMap<String, KieContainer>());
		getContainerNames(tenantId).stream().forEach(c -> refreshContainer(tenantId, c));
	}

	public void refreshContainer(String tenantId, String container) {
		List<ContainerRuleEntry> containerEntries = containerRuleEntryRepository
				.getByTenantIdAndContainer(tenantId, container).stream().filter(rule -> rule.isActivated())
				.collect(Collectors.toList());

		ReleaseId releaseId = RuleEngineUtil.generateReleaseId(tenantId, container);

		KieServices ks = KieServices.Factory.get();

		Resource resource = ks.getResources()
				.newByteArrayResource(RuleEngineUtil.createKJar(releaseId, containerEntries));
		resource.setResourceType(ResourceType.DRL);

		KieModule module = ks.getRepository().addKieModule(resource);
		KieContainer kieContainer = ks.newKieContainer(module.getReleaseId());

		tenant2ContainerMap.get(tenantId).put(container, kieContainer);

		kieContainer.updateToVersion(releaseId);

		// remove the repo immediately
		ks.getRepository().removeKieModule(module.getReleaseId());
	}

	public void printContainers() {
		System.out.println(" print containers: ");
		for (String t : tenant2ContainerMap.keySet()) {
			System.out.println("..... tenant: " + t);
			ConcurrentHashMap<String, KieContainer> containers = tenant2ContainerMap.get(t);
			for (String c : containers.keySet()) {
				System.out.println(".............. container: " + c);
			}
		}
		;
	}

	public RuleExecution executeRules(String tenantId, String container, String volunteerId) {
		KieSession ksession = getKieSession(tenantId, container);
		User volunteer = userRepository.findOne(volunteerId);
		Tenant tenant = coreTenantRestClient.getTenantById(tenantId);

		RuleExecution ruleExecution = new RuleExecution(volunteer);
		// insert objects into session
		ksession.insert(ruleExecution);
		ksession.insert(tenant);
		ksession.insert(volunteer);
		ksession.insert(userService);
		ksession.insert(classInstanceService);

		ksession.fireAllRules();

		ksession.dispose();

		userRepository.save(volunteer);
		return ruleExecution;
	}

	public void executeFibonacci(String tenantId, String container) {
		KieSession ksession = getKieSession(tenantId, container);
		// The application can insert facts into the session
		Fibonacci f = new Fibonacci(10);
		ksession.insert(f);

		// and fire the rules
		ksession.fireAllRules();

		// and then dispose the session
		ksession.dispose();
	}

	public void executeHelloWorld(String tenantId, String container) {

		KieSession ksession = getKieSession(tenantId, container);
		// The application can insert facts into the session
		final Message message = new Message();
		message.setMessage("Hello World");
		message.setStatus(Message.HELLO);
		ksession.insert(message);

		// and fire the rules
		ksession.fireAllRules();

		// and then dispose the session
		ksession.dispose();
	}

	public KieSession getKieSession(String tenantId, String container) {
		ConcurrentHashMap<String, KieContainer> containerName2Container = tenant2ContainerMap.get(tenantId);
		KieContainer kieContainer = containerName2Container.get(container);
		if (container == null) {
			throw new NoSuchContainerException(container);
		}
		return kieContainer.newKieSession();
	}

	public void deleteRule(String tenantId, String containerName, String ruleName) {
		ContainerRuleEntry rule = containerRuleEntryRepository.getByTenantIdAndContainerAndName(tenantId, containerName,
				ruleName);
		if (rule != null)
			containerRuleEntryRepository.delete(rule);
	}

	public void addRule(DerivationRule derivationRule, boolean isTest) {
		String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(derivationRule);
		ContainerRuleEntry containerRule;
		if (derivationRule.getContainerRuleEntryId() == null) {
			containerRule = new ContainerRuleEntry();
			containerRule.setTenantId(derivationRule.getTenantId());
			containerRule.setMarketplaceId(derivationRule.getMarketplaceId());
		} else {
			containerRule = containerRuleEntryRepository.findOne(derivationRule.getContainerRuleEntryId());
		}
		containerRule.setContainer(derivationRule.getContainer());
		containerRule.setName(derivationRule.getName());
		containerRule.setContent(ruleContent);
		containerRule.setActive(derivationRule.getActive());
		containerRuleEntryRepository.save(containerRule);
		if (derivationRule.getContainerRuleEntryId() == null) {
			derivationRule.setContainerRuleEntryId(containerRule.getId());
		}
		refreshContainer(derivationRule.getTenantId());
	}

	public List<RuleExecution> executeRulesForAllVolunteers(String tenantId, String container) {
		List<RuleExecution> ruleExecutionList = new ArrayList<RuleExecution>();

		userService.getUsersByRole(UserRole.VOLUNTEER).forEach(vol -> {
			ruleExecutionList.add(executeRules(tenantId, container, vol.getId()));
		});

		return ruleExecutionList;
	}
}
