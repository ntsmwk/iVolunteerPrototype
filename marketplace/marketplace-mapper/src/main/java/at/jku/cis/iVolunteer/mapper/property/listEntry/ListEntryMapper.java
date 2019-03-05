package at.jku.cis.iVolunteer.mapper.property.listEntry;

import java.util.LinkedList;
import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;


@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class ListEntryMapper implements AbstractMapper<ListEntry<?>, ListEntryDTO<?>> {

	@Override
	public ListEntryDTO<?> toDTO(ListEntry<?> source) {
		
		if (source == null) {
			return null;
		} else {
			ListEntryDTO dto = new ListEntryDTO<>();
			dto.setId(source.getId());
			dto.setValue(source.getValue());

			return dto;
		}
		

	}

	@Override
	public List<ListEntryDTO<?>> toDTOs(List<ListEntry<?>> sources) {

		if (sources == null) {
			return null;
		} else {
			List<ListEntryDTO<?>> list = new LinkedList<ListEntryDTO<?>>();
			for (ListEntry entry : sources) {
				list.add(this.toDTO(entry));
			}
			
			return list;
			
		}
	}

	@Override
	public ListEntry<?> toEntity(ListEntryDTO<?> target) {
		
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
	public List<ListEntry<?>> toEntities(List<ListEntryDTO<?>> targets) {

		if (targets == null) {
			return null;
		} else {
			List<ListEntry<?>> list = new LinkedList<ListEntry<?>>();
			for (ListEntryDTO entry : targets) {
				list.add(this.toEntity(entry));
			}
			
			return list;
		}
	}
	
}
