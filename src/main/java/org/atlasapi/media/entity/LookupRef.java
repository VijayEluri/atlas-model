package org.atlasapi.media.entity;

import static org.atlasapi.persistence.content.ContentCategory.categoryFor;

import java.util.List;

import org.atlasapi.media.entity.ChildRef;
import org.atlasapi.media.entity.Described;
import org.atlasapi.media.entity.Publisher;
import org.atlasapi.persistence.content.ContentCategory;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class LookupRef {

    public static LookupRef from(Described subject) {
        return new LookupRef(subject.getCanonicalUri(), subject.getPublisher(), categoryFor(subject));
    }
    
    public static Function<Described,LookupRef> FROM_DESCRIBED = new Function<Described, LookupRef>() {
        @Override
        public LookupRef apply(Described input) {
            return LookupRef.from(input);
        }
    };
    
    public static Function<LookupRef,String> TO_ID = new Function<LookupRef, String>() {
        @Override
        public String apply(LookupRef input) {
            return input.id();
        }
    };
    
    public static Function<LookupRef,Publisher> TO_SOURCE = new Function<LookupRef, Publisher>() {
        @Override
        public Publisher apply(LookupRef input) {
            return input.publisher();
        }
    };
    
    private final String id;
    private final Publisher publisher;
    private final ContentCategory category;

    public LookupRef(String id, Publisher publisher, ContentCategory category) {
        this.id = id;
        this.publisher = publisher;
        this.category = category;
    }

    public String id() {
        return id;
    }

    public Publisher publisher() {
        return publisher;
    }

    public ContentCategory category() {
        return category;
    }
    
    @Override
    public boolean equals(Object that) {
        if(this == that) {
            return true;
        }
        if(that instanceof LookupRef) {
            LookupRef other = (LookupRef) that;
            return id.equals(other.id) && publisher.equals(other.publisher);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id, publisher);
    }
    
    @Override
    public String toString() {
        return String.format("Equiv:%s(%s,%s)", id, publisher.title(), category);
    }
}