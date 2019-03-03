package at.jku.cis.iVolunteer.core.blockchain;

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

import at.jku.cis.iVolunteer.mapper.blockchain.config.BlockchainEntryMapper;
import at.jku.cis.iVolunteer.model.blockchain.config.BlockchainEntry;
import at.jku.cis.iVolunteer.model.blockchain.config.dto.BlockchainEntryDTO;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;

@RestController
@RequestMapping("bcEntry")
public class BlockchainController {
	
	@Autowired
	private BlockchainEntryRepository repository;
	
	@Autowired
	private BlockchainEntryMapper mapper;
	
	@GetMapping
	public List<BlockchainEntryDTO> findAll() {
		return mapper.toDTOs(repository.findAll());
	}
	
	@GetMapping("{id}")
	public BlockchainEntryDTO findBlockchainEntryById(@PathVariable("id") String id) {
		return mapper.toDTO(repository.findOne(id));
	}
	
	@PostMapping
	public BlockchainEntryDTO addBlockchainEntry(@RequestBody BlockchainEntryDTO dto) {
		return mapper.toDTO(repository.insert(mapper.toEntity(dto)));
	}
	
	@PutMapping("{id}")
	public BlockchainEntryDTO updateBlockchainEntry(@PathVariable("id") String id, @RequestBody BlockchainEntryDTO dto) {
		BlockchainEntry originalEntry = repository.findOne(id);
		if (originalEntry == null) {
			throw new NotAcceptableException();
		}
		originalEntry.setChecked(dto.isChecked());
		return mapper.toDTO(repository.save(originalEntry));
	}
	
	@DeleteMapping("{id}")
	public void deleteBlockchainEntry(@PathVariable("id") String id) {
		repository.delete(id);
	}
}
