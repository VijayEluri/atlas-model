package org.atlasapi.media.entity.simple;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.atlasapi.media.vocabulary.PLAY;

@XmlRootElement(namespace=PLAY.NS)
@XmlType(name="seriesSummary", namespace=PLAY.NS)
public class SeriesSummary extends Identified {

	private String title;
	private String description;
	private Integer seriesNumber;
	
	public SeriesSummary() { /* required for XML/JSON tools */ }
	
	public SeriesSummary(String uri) {
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
	
	public Integer getSeriesNumber() {
		return seriesNumber;
	}
	
	public void setSeriesNumber(Integer seriesNumber) {
		this.seriesNumber = seriesNumber;
	}
}