package com.ensoftcorp.open.tinkerpop.graph.process;

import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.graph.step.sideEffect.GraphStep;
import com.tinkerpop.gremlin.structure.Element;
import com.tinkerpop.gremlin.structure.Graph;

public class AtlasStep<E extends Element> extends GraphStep<E>{

	public AtlasStep(Traversal traversal, Graph graph, Class<E> returnClass,
			Object[] ids) {
		super(traversal, graph, returnClass, ids);
		// TODO Auto-generated constructor stub
	}

}
