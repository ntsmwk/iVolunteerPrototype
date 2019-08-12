package at.jku.cis.iVolunteer.marketplace.configurable.asset;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import at.jku.cis.iVolunteer.model.configurable.asset.ConfigurableAsset;


@Controller
public class ConfigurableAssetController {

	@Autowired ConfigurableAssetRepository configurableAssetRepository;
	
	
	@GetMapping("/configasset/all")
	List<ConfigurableAsset> getAllConfigurableAssets() {
		return configurableAssetRepository.findAll();
	}
	
	@PostMapping("/configasset/{configClassId}/new")
	ConfigurableAsset createConfigurabeAssetFromClass() {
		return null;
	}
	

}
