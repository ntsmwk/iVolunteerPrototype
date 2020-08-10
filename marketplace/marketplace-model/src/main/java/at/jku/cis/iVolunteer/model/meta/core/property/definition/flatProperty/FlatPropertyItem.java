package at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty;

public class FlatPropertyItem {
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
		if (!(obj instanceof FlatPropertyItem)) {
			return false;
		}
		return ((FlatPropertyItem) obj).id.equals(id);

	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
