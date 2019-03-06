package at.jku.cis.iVolunteer.mapper.property.listEntry;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;


//@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class ListEntryMapper implements AbstractMapper<ListEntry<Object>, ListEntryDTO<Object>> {

	@Override
	public ListEntryDTO<Object> toDTO(ListEntry<Object> source) {
		
		if (source == null) {
			return null;
		} else {
			ListEntryDTO<Object> dto = new ListEntryDTO<>();
			dto.setId(source.getId());
			dto.setValue(source.getValue());

			return dto;
		}
		

	}

	@Override
	public List<ListEntryDTO<Object>> toDTOs(List<ListEntry<Object>> sources) {

		if (sources == null) {
			return null;
		} else {
			List<ListEntryDTO<Object>> list = new LinkedList<>();
			for (ListEntry<Object> entry : sources) {
				list.add(this.toDTO(entry));
			}
			
			return list;
			
		}
	}

	@Override
	public ListEntry<Object> toEntity(ListEntryDTO<Object> target) {
		
		if (target == null) {
			return null;
		
		} else {
			ListEntry<Object> entry = new ListEntry<>();
			entry.setId(target.getId());
			entry.setValue(target.getValue());
			
			return entry;
		}
	}

	@Override
	public List<ListEntry<Object>> toEntities(List<ListEntryDTO<Object>> targets) {

		if (targets == null) {
			return null;
		} else {
			List<ListEntry<Object>> list = new LinkedList<>();
			for (ListEntryDTO<Object> entry : targets) {
				list.add(this.toEntity(entry));
			}
			
			return list;
		}
	}
	
}
