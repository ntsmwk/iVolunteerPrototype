package at.jku.cis.iVolunteer.model.meta.form;

public class EnumEntry {
	int level;
	int[] position;
	String value;
	boolean selectable;
	
	public EnumEntry() {
	
	}
	
	public EnumEntry(String value) {
		this.level = 1;
		this.value = value;
		this.selectable = true;
	}
	
	public EnumEntry(int level, String value) {
		this.level = level;
		this.value = value;
		this.selectable = true;
	}
	
	public EnumEntry(int level, String value, boolean selectable) {
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
	
	public int[] getPosition() {
		return position;
	}
	
	public void setPosition(int[] position) {
		this.position = position;
	}
	
	public boolean isSelectable() {
		return selectable;
	}
	
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	
	
	
	
	
}
