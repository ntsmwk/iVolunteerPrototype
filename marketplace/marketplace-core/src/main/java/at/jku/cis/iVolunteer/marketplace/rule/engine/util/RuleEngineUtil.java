package at.jku.cis.iVolunteer.marketplace.rule.engine.util;

import java.util.List;
import java.util.UUID;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;

/**
 * Adapted from barry.wong
 */

@Component
public class RuleEngineUtil {

	public static byte[] createKJar(ReleaseId releaseId, List<ContainerRuleEntry> rules) {
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.generateAndWritePomXML(releaseId);

		for (ContainerRuleEntry rule : rules) {
			kfs.write("src/main/resources/at/jku/cis/iVolunteer/marketplace/rule/engine/" + rule.getName() + ".drl",
					rule.getContent());
		}
		KieBuilder kb = kieServices.newKieBuilder(kfs).buildAll();

		if (kb.getResults().hasMessages(Message.Level.ERROR)) {
			for (Message result : kb.getResults().getMessages()) {
				System.out.println("*************" + result.getText());
			}
			System.out.println("byte array null!!!!!");
			return null;
		}
		InternalKieModule kieModule = (InternalKieModule) kieServices.getRepository().getKieModule(releaseId);
		byte[] jar = kieModule.getBytes();
		return jar;
	}

	public static ReleaseId generateReleaseId(String tenantId, String containerName) {
		return new ReleaseIdImpl("at.jku.cis.ivolunteer", tenantId + "." + containerName,
				"1.0.0-" + UUID.randomUUID().toString().replaceAll("-", ""));
	}

}
