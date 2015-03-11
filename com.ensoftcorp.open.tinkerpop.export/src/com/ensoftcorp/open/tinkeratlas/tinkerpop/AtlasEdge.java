package com.ensoftcorp.open.tinkeratlas.tinkerpop;

import java.util.Iterator;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.db.graph.GraphElement.EdgeDirection;
import com.ensoftcorp.atlas.core.db.set.SingletonAtlasSet;
import com.ensoftcorp.atlas.core.db.set.UnionSet;
import com.tinkerpop.gremlin.structure.Direction;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Property;
import com.tinkerpop.gremlin.structure.Vertex;

public class AtlasEdge extends AtlasElement implements Edge{
	public AtlasEdge(GraphElement ge){
		super(ge);
	}
	
	@Override
	public Edge.Iterators iterators(){
		return new AtlasEdgeIterators();
	}
	
	class AtlasEdgeIterators implements Edge.Iterators{
		@Override
		public <V> Iterator<Property<V>> propertyIterator(String... arg0) {
			return new AtlasPropertyIterator<V>(arg0);
		}

		@Override
		public Iterator<Vertex> vertexIterator(Direction arg0) {
			return new AtlasEdgeVertexIterator(arg0);
		}
	}
	
	class AtlasEdgeVertexIterator implements Iterator<Vertex> {
		Iterator<GraphElement> iter;
		AtlasEdgeVertexIterator(Direction direction){
			switch(direction){
			case IN:
				iter = new SingletonAtlasSet<GraphElement>(ge.getNode(EdgeDirection.FROM)).iterator();
				break;
			case OUT:
				iter = new SingletonAtlasSet<GraphElement>(ge.getNode(EdgeDirection.TO)).iterator();
				break;
			case BOTH:
				iter = new UnionSet<GraphElement>(
						new SingletonAtlasSet<GraphElement>(ge.getNode(EdgeDirection.TO)),
						new SingletonAtlasSet<GraphElement>(ge.getNode(EdgeDirection.FROM))).iterator();
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
}
