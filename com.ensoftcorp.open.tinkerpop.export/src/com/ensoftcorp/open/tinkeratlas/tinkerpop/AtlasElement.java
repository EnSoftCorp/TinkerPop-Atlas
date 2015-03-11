package com.ensoftcorp.open.tinkeratlas.tinkerpop;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.query.Attr.Edge;
import com.ensoftcorp.atlas.core.query.Attr.Node;
import com.tinkerpop.gremlin.structure.Element;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Property;

public class AtlasElement implements Element{
	GraphElement ge;
	
	public AtlasElement(GraphElement ge){
		this.ge = ge;
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
	public Iterators iterators() {
		return new AtlasIterators();
	}

	@Override
	public String label() {
		String name = (String) (com.ensoftcorp.atlas.core.db.graph.Graph.U.nodes().contains(ge) ? ge.getAttr(Node.NAME):ge.getAttr(Edge.NAME));
		return name == null ? "":name;
	}

	@Override
	public <V> Property<V> property(String arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	class AtlasIterators implements Element.Iterators{
		@Override
		public <V> Iterator<? extends Property<V>> propertyIterator(
				String... arg0) {
			return (Iterator<? extends Property<V>>) new AtlasValueIterator<V>();
		}
	}
	
	class AtlasValueIterator<V> implements Iterator<Property<V>>{
		Iterator<String> keyIter = ge.attr().keyIterator();
		
		@Override
		public boolean hasNext() {
			return keyIter.hasNext();
		}

		@Override
		public Property<V> next() {
			return new AtlasProperty<V>(keyIter.next());
		}
	}
	
	class AtlasProperty<V> implements Property<V>{
		String key;
		public AtlasProperty(String key){
			this.key = key;
		}

		@Override
		public Element element() {
			return AtlasElement.this;
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
	}
}
