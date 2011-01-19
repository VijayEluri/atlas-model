package org.atlasapi.media.entity.simple;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.atlasapi.media.vocabulary.PLAY_SIMPLE_XML;

import com.google.common.collect.Lists;

@XmlRootElement(namespace=PLAY_SIMPLE_XML.NS, name="content")
@XmlType(name="content", namespace=PLAY_SIMPLE_XML.NS)
public class ContentQueryResult {

	private List<Description> contents = Lists.newArrayList();

	public void add(Description content) {
		contents.add(content);
	}

	@XmlElement(namespace=PLAY_SIMPLE_XML.NS, name="contents")
	public List<Description> getContents() {
		return contents;
	}
	
	public void setContents(List<Description> items) {
		this.contents = items;
	}

	@Override
	public int hashCode() {
		return contents.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this instanceof ContentQueryResult) {
			ContentQueryResult other = (ContentQueryResult) obj;
			return contents.equals(other.contents);
		}
		return false;
	}
}
