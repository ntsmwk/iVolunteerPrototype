package at.jku.cis.iVolunteer.model.blockchain.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BlockchainEntry {
	
	@Id
	private String id;
	
	private String name;
	
	private boolean checked;
	
	public BlockchainEntry() {}
	
	public BlockchainEntry(String name) {
		setName(name);
		setChecked(true);
	}
	
	public BlockchainEntry(String name, boolean checked) {
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
