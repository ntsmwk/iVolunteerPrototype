package at.jku.cis.iVolunteer.model.meta.core.property;

public class LevelListPropertyEntry {
	int level;
	String value;
	boolean selectable;
	
	public LevelListPropertyEntry() {
	
	}
	
	public LevelListPropertyEntry(String value) {
		this.level = 1;
		this.value = value;
		this.selectable = true;
	}
	
	public LevelListPropertyEntry(int level, String value) {
		this.level = level;
		this.value = value;
		this.selectable = true;
	}
	
	public LevelListPropertyEntry(int level, String value, boolean selectable) {
		this.level = level;
		this.value = value;
		this.selectable = selectable;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
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

	
	
	
	
	
}
