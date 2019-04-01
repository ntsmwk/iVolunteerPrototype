package at.jku.cis.iVolunteer.model.property.listEntry;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ListEntry<T> {

	@Id
	public String id;
	public T value;

	public ListEntry() {
	}
	
	public ListEntry(T value) {
		this.id = new ObjectId().toHexString();
		this.value = value;
	}
	
	public ListEntry(String id, T value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T name) {
		this.value = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ListEntry<?>)) {
			return false;
		}
		return ((ListEntry<?>) obj).id.equals(id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
