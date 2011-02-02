package org.atlasapi.media.entity.simple;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.atlasapi.media.vocabulary.PLAY_SIMPLE_XML;

@XmlRootElement(namespace=PLAY_SIMPLE_XML.NS)
@XmlType(name="brandSummary", namespace=PLAY_SIMPLE_XML.NS)
public class BrandSummary extends Identified {

	private String title;
	private String description;
	
	public BrandSummary() { /* required for XML/JSON tools */ }
	
	public BrandSummary(String uri) {
		super(uri);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BrandSummary copy() {

	    BrandSummary copy = new BrandSummary();
        
        copyTo(copy);
        
        copy.setTitle(getTitle());
        copy.setDescription(getDescription());
        
        return copy;
    }
}
