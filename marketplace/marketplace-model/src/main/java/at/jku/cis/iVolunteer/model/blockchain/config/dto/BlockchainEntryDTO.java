package at.jku.cis.iVolunteer.model.blockchain.config.dto;

public class BlockchainEntryDTO {
	
	private String id;
	
	private String name;
	
	private boolean checked;
	
	public BlockchainEntryDTO() {}
	
	public BlockchainEntryDTO(String name, boolean checked) {
		setName(name);
		setChecked(checked);
	}
	
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
