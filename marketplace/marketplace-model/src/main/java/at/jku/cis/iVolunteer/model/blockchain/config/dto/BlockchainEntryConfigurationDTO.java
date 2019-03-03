package at.jku.cis.iVolunteer.model.blockchain.config.dto;

import java.util.HashSet;

public class BlockchainEntryConfigurationDTO {
	
	private HashSet<BlockchainEntryDTO> entries = new HashSet<BlockchainEntryDTO>();
	
	public BlockchainEntryConfigurationDTO() {
		
	}
	
	public BlockchainEntryConfigurationDTO(HashSet<BlockchainEntryDTO> entries) {
		setEntries(entries);
	}

	public HashSet<BlockchainEntryDTO> getEntries() {
		return entries;
	}

	public void setEntries(HashSet<BlockchainEntryDTO> entries) {
		this.entries = entries;
	}
}
