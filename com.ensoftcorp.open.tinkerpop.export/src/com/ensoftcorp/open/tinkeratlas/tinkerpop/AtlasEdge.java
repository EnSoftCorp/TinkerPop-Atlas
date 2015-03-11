package com.ensoftcorp.open.tinkeratlas.tinkerpop;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.tinkerpop.gremlin.structure.Edge;

public class AtlasEdge extends AtlasElement implements Edge{
	public AtlasEdge(GraphElement ge){
		super(ge);
	}
	@Override
	public Edge.Iterators iterators(){
		return null; // TODO
	}
}
