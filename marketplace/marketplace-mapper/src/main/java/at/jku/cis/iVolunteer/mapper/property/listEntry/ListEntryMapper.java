package at.jku.cis.iVolunteer.mapper.property.listEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyValueConverter;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;


//@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class ListEntryMapper implements AbstractMapper<ListEntry<Object>, ListEntryDTO<Object>> {

	@Autowired PropertyValueConverter propertyValueConverter;
	
	@Override
	public ListEntryDTO<Object> toDTO(ListEntry<Object> source) {
		
		if (source == null) {
			return null;
		} 
		
		ListEntryDTO<Object> dto = new ListEntryDTO<>();
//		System.out.println("Processing " + source.getId() + ": " + source.getValue());
		dto.setId(source.getId());
		dto.setValue(source.getValue());

		return dto;
		
	}
	
//	@Override 
//	public ListEntryDTO<Object> toDTO(ListEntry<Object> source) {
//		throw new UnsupportedOperationException("use Method specifying the PropertyKind to ensure type safety");
//		//return toDTO(source, null);
//	}

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
		
		throw new UnsupportedOperationException("use Method specifying the PropertyKind to ensure type safety");
	}
	
	
	public ListEntry<Object> toEntity(ListEntryDTO<Object> target, PropertyKind kind) {
		
		if (target == null) {
			return null;
		
		} else {
			ListEntry<Object> entry = new ListEntry<>();
			
			if (target.getId() == null) {
				entry.setId(new ObjectId().toHexString());
			} else {
				entry.setId(target.getId());
			}
			
			entry.setValue(propertyValueConverter.convert(target.getValue(), kind));
			
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
