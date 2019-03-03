package at.jku.cis.iVolunteer.mapper.blockchain.config;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.blockchain.config.BlockchainEntry;
import at.jku.cis.iVolunteer.model.blockchain.config.dto.BlockchainEntryDTO;

@Mapper
public abstract class BlockchainEntryMapper implements AbstractMapper<BlockchainEntry, BlockchainEntryDTO> {

}
