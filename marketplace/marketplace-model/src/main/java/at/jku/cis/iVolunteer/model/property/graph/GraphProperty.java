package at.jku.cis.iVolunteer.model.property.graph;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.PropertyKind;

public class GraphProperty extends Property{

	List<NodeProperty> properties;
	
	public GraphProperty() {
		super();
		this.setKind(PropertyKind.GRAPH);
		this.initializeGraphProperty();
	}
	
	public List<NodeProperty> getProperties() {
		return properties;
	}
	
	public void setProperties(List<NodeProperty> properties) {
		this.properties = properties;
	}
	
	//======================================================================
	//========================Functionalities===============================
	//======================================================================
	
	public void initializeGraphProperty() {
		this.properties = new ArrayList<>();
	}
	
	public void addVertex(NodeProperty node) {
		properties.add(node);
	}
	
	public void addEdge(String sourceNodePropertyId, String destNodePropertyId) {
		
		for (NodeProperty node : properties) {
			if (node.getId().equals(sourceNodePropertyId)) {
				node.addEdge(destNodePropertyId);
			}
		}
		
	}
	
	public static void main(String[] args) {
		GraphProperty graph = new GraphProperty(); 
		
		NodeProperty n0 = new NodeProperty();
		n0.initializeNodeProperty();
		n0.createNode("0", "Test 0");
		graph.properties.add(n0);
		
		NodeProperty n1 = new NodeProperty();
		n1.initializeNodeProperty();
		n1.createNode("1", "Test 1");
		graph.properties.add(n1);
		
		NodeProperty n2 = new NodeProperty();
		n2.initializeNodeProperty();
		n2.createNode("2", "Test 2");
		graph.properties.add(n2);
		
		NodeProperty n3 = new NodeProperty();
		n3.initializeNodeProperty();
		n3.createNode("3", "Test 3");
		graph.properties.add(n3);
		
		NodeProperty n4 = new NodeProperty();
		n4.initializeNodeProperty();
		n4.createNode("4", "Test 4");
		graph.properties.add(n4);
		
		n0.addEdge(n1.getValues().get(0).id);
		n0.addEdge(n4.getValues().get(0).id);
		
		n1.addEdge(n0.getValues().get(0).id);
		n1.addEdge(n4.getValues().get(0).id);
		n1.addEdge(n2.getValues().get(0).id);
		n1.addEdge(n3.getValues().get(0).id);
		
		n2.addEdge(n1.getValues().get(0).id);
		n2.addEdge(n3.getValues().get(0).id);
		
		graph.addEdge(n3.getId(), n1.getId());
		graph.addEdge(n3.getId(), n4.getId());
		graph.addEdge(n3.getId(), n2.getId());
		
		n4.addEdge(n3.getId());
		n4.addEdge(n0.getId());
		n4.addEdge(n1.getId());
		
		
		
		
		for (NodeProperty node : graph.properties) {
			System.out.print(node.getValues().get(0).id);
			
			for (String adj : node.adjacencies) {
				System.out.print( " -> " + adj);
			}
			
			System.out.println();
		}
		
		
	}
	
	
	
}


