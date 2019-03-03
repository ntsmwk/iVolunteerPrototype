package at.jku.cis.iVolunteer.core.blockchain;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.blockchain.config.BlockchainEntry;

public interface BlockchainEntryRepository extends MongoRepository<BlockchainEntry, String> {

}
