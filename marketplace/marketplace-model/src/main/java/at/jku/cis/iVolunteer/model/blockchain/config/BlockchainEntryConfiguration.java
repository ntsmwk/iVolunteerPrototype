package at.jku.cis.iVolunteer.model.blockchain.config;

import java.util.HashSet;

public class BlockchainEntryConfiguration {
	
	private HashSet<BlockchainEntry> entries = new HashSet<BlockchainEntry>();
	
	public BlockchainEntryConfiguration() {
		
	}
	
	public BlockchainEntryConfiguration(HashSet<BlockchainEntry> entries) {
		setEntries(entries);
	}

	public HashSet<BlockchainEntry> getEntries() {
		return entries;
	}

	public void setEntries(HashSet<BlockchainEntry> entries) {
		this.entries = entries;
	}
}
