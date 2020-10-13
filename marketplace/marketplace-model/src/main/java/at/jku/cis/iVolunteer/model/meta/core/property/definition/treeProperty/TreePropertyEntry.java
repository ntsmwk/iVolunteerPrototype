package at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty;

import java.util.ArrayList;
import java.util.List;

public class TreePropertyEntry {
	String id;
	String value;
	boolean selectable;
	int level;
	int[] position;
	
	List<TreePropertyEntry> parents;

	public TreePropertyEntry() {
	}

	public TreePropertyEntry(String value) {
		this.value = value;
		this.selectable = true;
		this.parents = new ArrayList<>();
	}

	public TreePropertyEntry(String value, boolean selectable) {
		this.value = value;
		this.selectable = selectable;
		this.parents = new ArrayList<>();
	}

	public TreePropertyEntry(String value, boolean selectable, int level) {
		this.value = value;
		this.selectable = selectable;
		this.level = level;
		this.parents = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public List<TreePropertyEntry> getParents() {
		return parents;
	}

	public void setParents(List<TreePropertyEntry> parents) {
		this.parents = parents;
	}
	
	

}
