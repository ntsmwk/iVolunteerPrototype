package at.jku.cis.iVolunteer.marketplace.rule.engine;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.drools.compiler.lang.dsl.DSLMapParser.entry_return;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.Fibonacci;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.Message;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.RuleEngineTestData;
import at.jku.cis.iVolunteer.marketplace.rule.engine.util.NoSuchContainerException;
import at.jku.cis.iVolunteer.marketplace.rule.engine.util.RuleEngineUtil;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;
import at.jku.cis.iVolunteer.model.user.Volunteer;

/**
 * Adapted from barry.wong
 */
@Service
public class RuleService {
	
	@Autowired private ContainerRuleEntryRepository containerRuleEntryRepository;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private VolunteerRepository volunteerRepository;
	
	private ConcurrentHashMap<String,  ConcurrentHashMap<String, KieContainer>> tenant2ContainerMap;
	
    @PostConstruct
    private void init() {
    	tenant2ContainerMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, KieContainer>>();
    }
    
    public List<String> getContainerNames(String tenantId){
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
		if (!tenant2ContainerMap.contains(tenantId))
			tenant2ContainerMap.put(tenantId, new ConcurrentHashMap<String, KieContainer>());
		getContainerNames(tenantId).stream().forEach(c -> refreshContainer(tenantId, c));
	}
	
	public void refreshContainer(String tenantId, String container) {
		List<ContainerRuleEntry> containerEntries = containerRuleEntryRepository.getByTenantIdAndContainer(tenantId, container);
				
		ReleaseId releaseId = RuleEngineUtil.generateReleaseId(tenantId, container);
		
		KieServices ks = KieServices.Factory.get();
		
		Resource resource = ks.getResources().newByteArrayResource(RuleEngineUtil.createKJar(releaseId, containerEntries));
    	resource.setResourceType(ResourceType.DRL);
               
		KieModule module = ks.getRepository().addKieModule(resource);
        KieContainer kieContainer = ks.newKieContainer(module.getReleaseId());
        
        tenant2ContainerMap.get(tenantId).put(container, kieContainer);
        
		kieContainer.updateToVersion(releaseId); 

		// remove the repo immediately
        ks.getRepository().removeKieModule(module.getReleaseId()); 
	}
	
	public void printContainers() {
		for (String t: tenant2ContainerMap.keySet()) {
			System.out.println("..... tenant: " + t);
			ConcurrentHashMap<String, KieContainer> containers = tenant2ContainerMap.get(t);
			for (String c: containers.keySet()) {
				System.out.println(".............. container: " + c);
			}
		};
	}
	
	public void executeRules(String tenantId, String container, String volunteerId) {
		KieSession ksession = getKieSession(tenantId, container);
		
		Volunteer volunteer = volunteerRepository.findOne(volunteerId);
		ksession.insert(volunteer);
        
		ksession.fireAllRules();
		
		ksession.dispose();
	}

	public void executeFibonacci(String tenantId, String container) {
		KieSession ksession = getKieSession(tenantId, container);
		// The application can insert facts into the session
		Fibonacci f = new Fibonacci(10);
        ksession.insert( f );

        // and fire the rules
        ksession.fireAllRules();

        // and then dispose the session
        ksession.dispose();
	}
	
    public void executeHelloWorld(String tenantId, String container) {
		
		KieSession ksession = getKieSession(tenantId, container);
		// The application can insert facts into the session
        final Message message = new Message();
        message.setMessage( "Hello World" );
        message.setStatus( Message.HELLO );
        ksession.insert( message );

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

	
	public void addRule2Container(String tenantId, String marketplaceId, String container, String name, String content) {
		ContainerRuleEntry containerRule = new ContainerRuleEntry(tenantId, marketplaceId, container, name, content);
		containerRuleEntryRepository.insert(containerRule);
	}
	
	public void initTestData(String tenantId) {
		String marketplaceId = marketplaceService.getMarketplaceId();
		RuleEngineTestData data = new RuleEngineTestData();
		addRule2Container(tenantId, marketplaceId, "general", "hello-world", data.ruleHelloWorld);
		addRule2Container(tenantId, marketplaceId, "math", "fibonacci", data.ruleFibonacci);
		refreshContainer(tenantId);
	}
}
