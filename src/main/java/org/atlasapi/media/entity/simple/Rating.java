package org.atlasapi.media.entity.simple;


public class Rating {

    private PublisherDetails publisherDetails;
    private Float value;
    private String type;
    
    public PublisherDetails getPublisherDetails() {
        return publisherDetails;
    }
    
    public void setPublisherDetails(PublisherDetails publisherDetails) {
        this.publisherDetails = publisherDetails;
    }
    
    public Float getValue() {
        return value;
    }
    
    public void setValue(Float value) {
        this.value = value;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
}
