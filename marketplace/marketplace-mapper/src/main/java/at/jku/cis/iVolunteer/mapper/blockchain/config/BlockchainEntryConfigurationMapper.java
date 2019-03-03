package at.jku.cis.iVolunteer.mapper.blockchain.config;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.blockchain.config.BlockchainEntryConfiguration;
import at.jku.cis.iVolunteer.model.blockchain.config.dto.BlockchainEntryConfigurationDTO;

@Mapper(uses = { BlockchainEntryMapper.class })
public abstract class BlockchainEntryConfigurationMapper
		implements AbstractMapper<BlockchainEntryConfiguration, BlockchainEntryConfigurationDTO> {

}
