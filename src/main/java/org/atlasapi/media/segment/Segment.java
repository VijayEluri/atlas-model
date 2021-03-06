package org.atlasapi.media.segment;

import org.atlasapi.media.SegmentType;
import org.atlasapi.media.entity.Described;
import org.atlasapi.media.entity.Publisher;
import org.joda.time.Duration;

import com.google.common.base.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public class Segment extends Described {

    private SegmentType type;
    private Duration duration;
    private Publisher publisher;

    public SegmentRef toRef() {
        return new SegmentRef(checkNotNull(this.getId(),
                "Can't create reference for segment without ID"));
    }

    public SegmentType getType() {
        return this.type;
    }

    public void setType(SegmentType type) {
        this.type = type;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public Described copy() {
        Segment copy = new Segment();
        copy.setType(this.type);
        copy.setDuration(this.duration);
        copy.setPublisher(this.publisher);
        return copy;
    }

    public static final Function<Segment, SegmentRef> TO_REF = Segment::toRef;

}
