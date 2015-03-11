package com.ensoftcorp.open.tinkerpop.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.db.graph.SimpleAddress;
import com.ensoftcorp.atlas.core.db.notification.NotificationMap;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Property;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.structure.VertexProperty;

public class AtlasPropertyIterator<V> implements Iterator<VertexProperty<V>>  {
	private GraphElement ge;
	private NotificationMap<String, Object> attr;
	private Iterator<String> keyIter;
	public AtlasPropertyIterator(GraphElement ge){
		this.ge = ge;
		attr = ge.attr();
		keyIter = attr.keyIterator();
	}

	@Override
	public boolean hasNext() {
		return keyIter.hasNext();
	}

	@Override
	public VertexProperty<V> next() {
		// TODO Auto-generated method stub
		final String key = keyIter.next();
		final V val = (V) attr.get(key);
		
		return new VertexProperty<V>(){
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

			@Override
			public V value() throws NoSuchElementException {
				return val;
			}

			@Override
			public Graph graph() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Object id() {
				return new SimpleAddress();
			}

			@Override
			public String label() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <V> Property<V> property(String arg0, V arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Vertex element() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public com.tinkerpop.gremlin.structure.VertexProperty.Iterators iterators() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}

}
