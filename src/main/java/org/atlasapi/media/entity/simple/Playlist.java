package org.atlasapi.media.entity.simple;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.atlasapi.media.entity.simple.ContentIdentifier.BrandIdentifier;
import org.atlasapi.media.entity.simple.ContentIdentifier.EpisodeIdentifier;
import org.atlasapi.media.entity.simple.ContentIdentifier.FilmIdentifier;
import org.atlasapi.media.entity.simple.ContentIdentifier.ItemIdentifier;
import org.atlasapi.media.entity.simple.ContentIdentifier.PersonIdentifier;
import org.atlasapi.media.entity.simple.ContentIdentifier.SeriesIdentifier;
import org.atlasapi.media.vocabulary.PLAY_SIMPLE_XML;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@XmlRootElement(namespace=PLAY_SIMPLE_XML.NS)
@XmlType(name="list", namespace=PLAY_SIMPLE_XML.NS)
public class Playlist extends Description {

	private List<ContentIdentifier> content = Lists.newArrayList();

	public void add(ContentIdentifier c) {
		content.add(c);
	}
	
	@XmlElementWrapper(namespace=PLAY_SIMPLE_XML.NS, name="content")
	@XmlElements({ 
		@XmlElement(name = "item", type = ItemIdentifier.class, namespace=PLAY_SIMPLE_XML.NS),
		@XmlElement(name = "episode", type = EpisodeIdentifier.class, namespace=PLAY_SIMPLE_XML.NS),
		@XmlElement(name = "film", type = FilmIdentifier.class, namespace=PLAY_SIMPLE_XML.NS),
		@XmlElement(name = "person", type = PersonIdentifier.class, namespace=PLAY_SIMPLE_XML.NS),
		@XmlElement(name = "series", type = SeriesIdentifier.class, namespace=PLAY_SIMPLE_XML.NS),
		@XmlElement(name = "brand", type = BrandIdentifier.class, namespace=PLAY_SIMPLE_XML.NS) 
	})
	public List<ContentIdentifier> getContent() {
		return content;
	}
	
	public void setContent(Iterable<? extends ContentIdentifier> items) {
		this.content = Lists.newArrayList(items);
	}
	
	public Playlist copy() {
	    Playlist copy = new Playlist();
	    copyTo(copy);
	    copy.setContent(Iterables.transform(getContent(), new Function<ContentIdentifier, ContentIdentifier>() {
			@Override
			public ContentIdentifier apply(ContentIdentifier input) {
				return input.copy();
			}
	    }));
	    return copy;
	}
	
	public static final Function<Playlist, List<ContentIdentifier>> TO_CONTENTS = new Function<Playlist, List<ContentIdentifier>>() {

		@Override
		public List<ContentIdentifier> apply(Playlist input) {
			return input.getContent();
		}
	};

}
