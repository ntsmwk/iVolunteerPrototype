package at.jku.cis.iVolunteer.api.standard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.blockchainify.ContractorPublishingRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Service
public class StandardAPIService {

	
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ContractorPublishingRestClient contractorPublishingRestClient;

	public void blockchainify() {
		List<ClassInstance> findAll = classInstanceRepository.findAll();
		findAll.parallelStream().forEach(ci -> contractorPublishingRestClient.publishClassInstance(ci, ""));
	}
}
