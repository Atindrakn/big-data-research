package by.bsuir.kslisenko.service;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import by.bsuir.kslisenko.model.Cluster;
import by.bsuir.kslisenko.model.RelTypes;

public class ClusterService extends Neo4jUsingService {

	public ClusterService(Neo4jDataService graph) {
		super(graph);
	}
	
	public List<Cluster> getClusters(String experimentId) {
		List<Cluster> result = new ArrayList<Cluster>();
		List<Node> nodes = neo4j.getNodes("experiments", "id", experimentId);
		if (!nodes.isEmpty()) {
			for (Relationship r : nodes.get(0).getRelationships(RelTypes.CONTAINS)) {
				result.add(fromNode(r.getEndNode()));
			}
		}
		return result;		
	}
	
	public List<Cluster> getClusters() {
		List<Cluster> result = new ArrayList<Cluster>();
		List<Node> nodes = neo4j.getNodes("clusters");
		for (Node node : nodes) {
			result.add(fromNode(node));
		}
		return result;
	}
	
	public Cluster fromNode(Node node) {
		Cluster result = new Cluster();
		result.setId(node.getProperty("id").toString());
		result.setName(node.getProperty("name").toString());
		result.setNumPoints(Integer.parseInt(node.getProperty("numPoints").toString()));
		return result;
	}
}