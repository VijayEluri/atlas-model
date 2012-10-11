package org.atlasapi.media.entity.simple;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class Channel extends Aliased {

    private PublisherDetails publisher;
    private String title;
    private String mediaType;
    private boolean highDefinition;
    private Set<ChannelGroup> channelGroups;
    private PublisherDetails broadcaster;
    private Set<PublisherDetails> availableFrom;

    public void setPublisherDetails(PublisherDetails publisherDetails) {
        this.publisher = publisherDetails;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    public void setHighDefinition(boolean highDefinition) {
        this.highDefinition = highDefinition;
    }

    public PublisherDetails getPublisherDetails() {
        return this.publisher;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMediaType() {
        return this.mediaType;
    }
    
    public boolean getHighDefinition() {
        return highDefinition;
    }

    public void setChannelGroups(Iterable<ChannelGroup> groups) {
        this.channelGroups = ImmutableSet.copyOf(groups);
    }

    public Set<ChannelGroup> getChannelGroups() {
        return this.channelGroups;
    }

    public PublisherDetails getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(PublisherDetails broadcaster) {
        this.broadcaster = broadcaster;
    }
    
    public void setAvailableFrom(Iterable<PublisherDetails> availableFrom) {
        this.availableFrom = ImmutableSet.copyOf(availableFrom);
    }
    
    public Set<PublisherDetails> getAvailableFrom() {
        return availableFrom;
    }
}
