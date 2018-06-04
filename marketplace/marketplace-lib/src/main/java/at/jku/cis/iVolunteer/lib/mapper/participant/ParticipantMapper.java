package at.jku.cis.iVolunteer.lib.mapper.participant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.participant.Participant;
import at.jku.cis.iVolunteer.model.participant.dto.ParticipantDTO;

@Service
public class ParticipantMapper extends AbstractSpringMapper<Participant, ParticipantDTO> {

	private static final String PASSWORD = "password";

	public ParticipantMapper() {
		super(Participant.class, ParticipantDTO.class);
	}

	@Override
	public ParticipantDTO toDTO(Participant entity, String... ignoreProperties) {
		List<String> adaptedIgnoredProperties = asList(ignoreProperties);
		adaptedIgnoredProperties.add(PASSWORD);
		return super.toDTO(entity, asArray(adaptedIgnoredProperties));
	}

	private String[] asArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}

	private List<String> asList(String[] array) {
		return array == null || array.length == 0 ? new ArrayList<>() : Arrays.asList(array);
	}
}
