package at.jku.cis.iVolunteer.core.marketplace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.marketplace.MarketplaceMapper;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.marketplace.dto.MarketplaceDTO;

@RestController
@RequestMapping("marketplace")
public class MarketplaceController {

	@Autowired
	private MarketplaceRepository marketplaceRepository;

	@Autowired
	private MarketplaceMapper marketplaceMapper;

	@GetMapping
	public List<MarketplaceDTO> findAll() {
		return marketplaceMapper.toDTOs(marketplaceRepository.findAll());
	}

	@GetMapping("{marketplaceId}")
	public MarketplaceDTO findById(@PathVariable("marketplaceId") String marketplaceId) {
		return marketplaceMapper.toDTO(marketplaceRepository.findOne(marketplaceId));
	}

	@PostMapping
	public MarketplaceDTO createMarketplace(@RequestBody MarketplaceDTO marketplaceDto) {
		Marketplace marketplace = marketplaceMapper.toEntity(marketplaceDto);
		return marketplaceMapper.toDTO(marketplaceRepository.insert(marketplace));
	}


	@PutMapping("{marketplaceId}")
	public MarketplaceDTO updateMarketplace(@PathVariable("marketplaceId") String marketplaceId, @RequestBody MarketplaceDTO marketplaceDto) {
		Marketplace orginalMarketplace = marketplaceRepository.findOne(marketplaceId);
		if (orginalMarketplace == null) {
			throw new NotAcceptableException();
		}
		orginalMarketplace.setName(marketplaceDto.getName());
		orginalMarketplace.setShortName(marketplaceDto.getShortName());
		orginalMarketplace.setUrl(marketplaceDto.getUrl());
		return marketplaceMapper.toDTO(marketplaceRepository.save(orginalMarketplace));
	}

	@DeleteMapping("{marketplaceId}")
	public void delete(@PathVariable("marketplaceId") String marketplaceId) {
		marketplaceRepository.delete(marketplaceId);
	}
}
