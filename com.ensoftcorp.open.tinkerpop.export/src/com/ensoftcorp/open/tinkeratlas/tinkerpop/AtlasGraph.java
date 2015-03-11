package com.ensoftcorp.open.tinkeratlas.tinkerpop;

import java.util.Iterator;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.tinkerpop.gremlin.process.computer.GraphComputer;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Transaction;
import com.tinkerpop.gremlin.structure.Vertex;
import org.apache.commons.configuration.Configuration;

public class AtlasGraph implements Graph{

	@Override
	public void close() throws Exception {
	}

	@Override
	public Vertex addVertex(Object... arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GraphComputer compute(Class... arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Configuration configuration() {
		return null;
	}

	@Override
	public Iterators iterators() {
		return new AtlasGraphIterators();
	}

	@Override
	public Transaction tx() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Variables variables() {
		return null;
	}

	class AtlasGraphIterators implements Iterators{
		@Override
		public Iterator<Edge> edgeIterator(Object... arg0) {
			return new AtlasEdgeIterator();
		}

		@Override
		public Iterator<Vertex> vertexIterator(Object... arg0) {
			return new AtlasVertexIterator();
		}
	}
	
	class AtlasVertexIterator implements Iterator<Vertex>{
		Iterator<GraphElement> iter = com.ensoftcorp.atlas.core.db.graph.Graph.U.nodes().iterator();

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public Vertex next() {
			return new AtlasVertex(iter.next());
		}
	}
	
	class AtlasEdgeIterator implements Iterator<Edge>{
		Iterator<GraphElement> iter = com.ensoftcorp.atlas.core.db.graph.Graph.U.edges().iterator();

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public Edge next() {
			return new AtlasEdge(iter.next());
		}
	}
}