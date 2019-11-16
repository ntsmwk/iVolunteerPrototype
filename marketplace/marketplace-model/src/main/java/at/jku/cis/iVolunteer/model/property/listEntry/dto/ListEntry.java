package at.jku.cis.iVolunteer.model.property.listEntry.dto;

public class ListEntry<T> {

	String id;
	private T value;

	public ListEntry() {
	}
	
	public ListEntry(T value) {
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

	@Override
	public String toString() {
		return "\nListEntry [id=" + id + ", value=" + value + "]";
	}
	
	
}
