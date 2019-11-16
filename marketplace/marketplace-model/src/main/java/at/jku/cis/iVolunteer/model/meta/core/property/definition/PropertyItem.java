package at.jku.cis.iVolunteer.model.meta.core.property.definition;

public class PropertyItem {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropertyItem)) {
			return false;
		}
		return ((PropertyItem) obj).id.equals(id);

	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
