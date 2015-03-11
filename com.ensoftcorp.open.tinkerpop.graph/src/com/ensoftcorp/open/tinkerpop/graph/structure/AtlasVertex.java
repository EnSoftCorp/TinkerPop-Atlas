package com.ensoftcorp.open.tinkerpop.graph.structure;

import java.util.Iterator;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.query.Attr;
import com.tinkerpop.gremlin.structure.Direction;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.structure.VertexProperty;

public class AtlasVertex implements Vertex, Vertex.Iterators{
	private Graph g;
	private GraphElement ge;
	
	public AtlasVertex(Graph g, GraphElement ge){
		this.g = g;
		this.ge = ge;
	}

	@Override
	public Graph graph() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object id() {
		return ge.address();
	}

	@Override
	public String label() {
		return (String) ge.getAttr(Attr.Node.NAME);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Edge addEdge(String arg0, Vertex arg1, Object... arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterators iterators() {
		return this;
	}

	@Override
	public <V> VertexProperty<V> property(String arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Edge> edgeIterator(Direction arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Iterator<VertexProperty<V>> propertyIterator(String... arg0) {
		return null; // TODO
	}

	@Override
	public Iterator<Vertex> vertexIterator(Direction arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
