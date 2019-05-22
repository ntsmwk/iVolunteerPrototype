package at.jku.cis.iVolunteer.model.property.listEntry.dto;

public class ListEntryDTO<T> {

	String id;
	private T value;

	public ListEntryDTO() {
	}
	
	public ListEntryDTO(T value) {
		this.value = value;
	}
	
	public ListEntryDTO(String id, T value) {
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
		if (!(obj instanceof ListEntryDTO<?>)) {
			return false;
		}
		return ((ListEntryDTO<?>) obj).id.equals(id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "\nListEntryDTO [id=" + id + ", value=" + value + "]";
	}
	
	
}
