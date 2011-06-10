package org.atlasapi.media.entity;

import org.atlasapi.content.rdf.annotations.RdfClass;
import org.atlasapi.content.rdf.annotations.RdfProperty;
import org.atlasapi.media.vocabulary.DCTERMS;
import org.atlasapi.media.vocabulary.PLAY_USE_IN_RDF_FOR_BACKWARD_COMPATIBILITY;

import com.google.common.collect.ImmutableList;

@RdfClass(namespace = PLAY_USE_IN_RDF_FOR_BACKWARD_COMPATIBILITY.NS, uri = "List")
public abstract class ContentGroup extends Described implements MutableContentList {
	
	private ImmutableList<ChildRef> contents = ImmutableList.of();

	public ContentGroup(String uri, String curie) {
		super(uri, curie);
	}
	
	public ContentGroup(String uri, String curie, Publisher publisher) {
		super(uri, curie, publisher);
	}
    
    public ContentGroup() {}

    @RdfProperty(relation = true, namespace = DCTERMS.NS, uri = "hasPart")
    public ImmutableList<ChildRef> getContents() {
		return contents;
	}
}
