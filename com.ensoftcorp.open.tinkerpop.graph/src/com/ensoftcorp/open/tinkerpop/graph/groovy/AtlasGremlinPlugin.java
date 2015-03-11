package com.ensoftcorp.open.tinkerpop.graph.groovy;
import java.util.HashSet;
import java.util.Set;

import com.ensoftcorp.open.tinkerpop.graph.structure.AtlasGraph;
import com.tinkerpop.gremlin.groovy.plugin.AbstractGremlinPlugin;
import com.tinkerpop.gremlin.groovy.plugin.IllegalEnvironmentException;
import com.tinkerpop.gremlin.groovy.plugin.PluginAcceptor;
import com.tinkerpop.gremlin.groovy.plugin.PluginInitializationException;

/**
 * Groovy plugin for Atlas
 * @author tdeering
 *
 */
public class AtlasGremlinPlugin extends AbstractGremlinPlugin{

    private static final Set<String> IMPORTS = new HashSet<String>() {
    	private static final long serialVersionUID = -1410804070389205131L;
    	{
    		add(IMPORT_SPACE + AtlasGraph.class.getPackage().getName() + DOT_STAR);
    	}
    };
	
	@Override
	public String getName() {
		return "tinkerpop.atlasgraph";
	}

	@Override
	public void pluginTo(PluginAcceptor arg0)
			throws IllegalEnvironmentException, PluginInitializationException {
		arg0.addImports(IMPORTS);
	}

	@Override
	public void afterPluginTo(PluginAcceptor arg0)
			throws IllegalEnvironmentException, PluginInitializationException {
	}
}
