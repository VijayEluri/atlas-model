package org.atlasapi.media.entity;

import com.google.common.base.Objects;

public class TopicRef {

	private Boolean supervised;
    private Float weighting;
    private Long topic;
    private Relationship relationship;
    private Publisher publisher;

    public TopicRef(Topic topic, Float weighting, Boolean supervised, Relationship relationship) {
        this.topic = topic.getId();
        this.weighting = weighting;
        this.supervised = supervised;
        this.relationship = relationship;
    }

    public TopicRef(Long topicId, Float weighting, Boolean supervised, Relationship relationship) {
        this.topic = topicId;
        this.weighting = weighting;
        this.supervised = supervised;
        this.relationship = relationship;
    }

    private TopicRef() {
    }

    public void setTopic(Topic topic) {
        this.topic = topic.getId();
    }

    public void setTopicUri(Long topic) {
        this.topic = topic;
    }

    public void setWeighting(Float weighting) {
        this.weighting = weighting;
    }

    public void setSupervised(Boolean supervised) {
        this.supervised = supervised;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public Float getWeighting() {
        return weighting;
    }

    public Boolean isSupervised() {
        return supervised;
    }

    public Long getTopic() {
        return topic;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(supervised, topic, weighting, relationship);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof TopicRef) {
            TopicRef other = (TopicRef) that;
            return Objects.equal(supervised, other.supervised)
                    && Objects.equal(weighting, other.weighting)
                    && Objects.equal(topic, other.topic)
                    && Objects.equal(relationship, other.relationship);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Ref topic %s, %+.2f, %s supervised, with relationship %s", topic, weighting, supervised ? "" : "not", relationship);
    }

    public static enum Relationship {

        ABOUT("about"),
        TWITTER_AUDIENCE("twitter:audience"),
        TWITTER_AUDIENCE_RELATED("twitter:audience-related"),
        TRANSCRIPTION("transcription"),
        TRANSCRIPTION_SUBTITLES("transcription:subtitles");
        private final String name;

        private Relationship(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
