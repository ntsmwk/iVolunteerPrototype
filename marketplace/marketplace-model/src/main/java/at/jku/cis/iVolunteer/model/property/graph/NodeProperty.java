package at.jku.cis.iVolunteer.model.property.graph;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.SingleProperty;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;

public class NodeProperty extends SingleProperty<Object> {
	
	List<String> adjacencies;
	
	
	public NodeProperty() {
		this.initializeNodeProperty();
	}
	
	public List<String> getAdjacencies() {
		return adjacencies;
	}
	
	public void setAdjacencies(List<String> adjacencies) {
		this.adjacencies = adjacencies;
	}
	
	//======================================================================
	//========================Functionalities===============================
	//======================================================================
		
	public void initializeNodeProperty() {
		adjacencies = new ArrayList<>();
		this.setValues(new ArrayList<>());
	}
	
	public void createNode(Object value) {
		String id = new ObjectId().toHexString();
		this.setId(id);
		this.setName("Node");
		this.getValues().add(new ListEntry<Object>(id, value));
	}
	
	public void createNode(String id, Object value) {
		this.setId(id);
		this.setName("Node");
		this.getValues().add(new ListEntry<Object>(id, value));
	}
	
	public void addEdge(String destNodeId) {
		adjacencies.add(destNodeId);
	}
	
	
}
