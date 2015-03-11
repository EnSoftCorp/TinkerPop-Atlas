package com.ensoftcorp.open.tinkeratlas.tinkerpop;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.db.graph.GraphElement.NodeDirection;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.db.set.UnionSet;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.tinkerpop.gremlin.structure.Direction;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Property;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.structure.VertexProperty;

public class AtlasVertex extends AtlasElement implements Vertex {
	public AtlasVertex(GraphElement ge){
		super(ge);
	}
	
	@Override
	public Edge addEdge(String arg0, Vertex arg1, Object... arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <V> VertexProperty<V> property(String key, V value){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Vertex.Iterators iterators(){
		return new AtlasVertexIterators();
	}
	
	class AtlasVertexIterators implements Vertex.Iterators{
		@Override
		public Iterator<Edge> edgeIterator(Direction direction, String... edgeLabels){
			return new VertexEdgeIterator(direction, edgeLabels);
		}

		@Override
		public <V> Iterator<VertexProperty<V>> propertyIterator(String... arg0) {
			return new AtlasVertexPropertyIterator<V>(arg0);
		}

		@Override
		public Iterator<Vertex> vertexIterator(Direction arg0, String... arg1) {
			return new VertexNeighborIterator(arg0, arg1);
		}
	}

	class VertexNeighborIterator implements Iterator<Vertex>{
		Iterator<GraphElement> iter;
		
		public VertexNeighborIterator(Direction direction, String... labels){
			Q geQ = Common.toQ(Common.toGraph(ge));
			Q c = Common.universe().edgesTaggedWithAny(labels);
			switch(direction){
			case OUT:
				iter = c.successors(geQ).eval().nodes().iterator();
				break;
			case IN:
				iter = c.predecessors(geQ).eval().nodes().iterator();
				break;
			case BOTH:
				iter = c.predecessors(geQ).union(c.successors(geQ)).eval().nodes().iterator();
			}
		}
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}
		@Override
		public Vertex next() {
			return new AtlasVertex(iter.next());
		}
	}
	
	class VertexEdgeIterator implements Iterator<Edge>{
		Iterator<GraphElement> edgeIterator;
		public VertexEdgeIterator(Direction direction, String... labels){
			AtlasSet<GraphElement> edges = com.ensoftcorp.atlas.core.db.graph.Graph.U.edges(ge, direction == Direction.OUT ? NodeDirection.OUT:NodeDirection.IN);
			if(direction == Direction.BOTH) edges = new UnionSet<GraphElement>(edges, com.ensoftcorp.atlas.core.db.graph.Graph.U.edges(ge, direction == Direction.OUT ? NodeDirection.IN:NodeDirection.OUT));
			edgeIterator = edges.taggedWithAny(labels).iterator();
		}
		@Override
		public boolean hasNext() {
			return edgeIterator.hasNext();
		}
		@Override
		public Edge next() {
			return new AtlasEdge(edgeIterator.next());
		}
	}
	
	class VertexPropertyIterator<V> implements Iterator<VertexProperty<V>>{
		Iterator<String> keyIter = ge.attr().keyIterator();
		
		@Override
		public boolean hasNext() {
			return keyIter.hasNext();
		}

		@Override
		public VertexProperty<V> next() {
			return new AtlasVertexProperty<V>(keyIter.next());
		}
	}
	
	class AtlasVertexProperty<V> implements VertexProperty<V>{
		String key;
		public AtlasVertexProperty(String key){
			this.key = key;
		}
		@Override
		public boolean isPresent() {
			return true;
		}
		@Override
		public String key() {
			return key;
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		@SuppressWarnings("unchecked")
		@Override
		public V value() throws NoSuchElementException {
			return (V) ge.getAttr(key);
		}
		@Override
		public Graph graph() {
			throw new UnsupportedOperationException();
		}
		@Override
		public Object id() {
			return ge.address().toAddressString();
		}
		@Override
		public String label() {
			return AtlasVertex.this.label();
		}
		@Override
		public <U> Property<U> property(String arg0, U arg1) {
			return new AtlasVertexProperty<U>(arg0);
		}
		@Override
		public Vertex element() {
			return AtlasVertex.this;
		}
		@Override
		public com.tinkerpop.gremlin.structure.VertexProperty.Iterators iterators() {
			return new AtlasVertexPropertyIterators();
		}
	}
	
	class AtlasVertexPropertyIterators implements VertexProperty.Iterators{
		@Override
		public <U> Iterator<Property<U>> propertyIterator(String... arg0) {
			return new AtlasPropertyIterator<U>(arg0);
		}
	}
	
	class AtlasPropertyIterator<V> implements Iterator<Property<V>>{
		String[] keys;
		int idx = 0;
		public AtlasPropertyIterator(String... keys){
			this.keys = keys;
		}
		
		@Override
		public boolean hasNext() {
			return idx < keys.length;
		}

		@Override
		public Property<V> next() {
			return new AtlasProperty<V>(keys[idx++]);
		}
	}
	
	class AtlasVertexPropertyIterator<V> implements Iterator<VertexProperty<V>>{
		String[] keys;
		int idx = 0;
		public AtlasVertexPropertyIterator(String... keys){
			this.keys = keys;
		}
		
		@Override
		public boolean hasNext() {
			return idx < keys.length;
		}

		@Override
		public VertexProperty<V> next() {
			return new AtlasVertexProperty<V>(keys[idx++]);
		}
	}
}
