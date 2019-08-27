package at.jku.cis.iVolunteer.marketplace.configurable.asset;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import at.jku.cis.iVolunteer.model.meta.core.class_.ClassInstance;


@Controller
public class ConfigurableAssetController {

	@Autowired ConfigurableAssetRepository configurableAssetRepository;
	
	
	@GetMapping("/configasset/all")
	List<ClassInstance> getAllConfigurableAssets() {
		return configurableAssetRepository.findAll();
	}
	
	@PostMapping("/configasset/{configClassId}/new")
	ClassInstance createConfigurabeAssetFromClass() {
		return null;
	}
	

}
