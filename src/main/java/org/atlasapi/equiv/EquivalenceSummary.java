package org.atlasapi.equiv;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.annotation.Nullable;

import org.atlasapi.media.entity.Publisher;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Records computed candidates and resultant equivalents of the equivalence
 * process. This intended for use in other equivalence settings, i.e. the
 * results of a Brand's children can influence the result of the Brand itself,
 * or vice-versa.
 * 
 * Any other use, such as resolving content, is probably not a good idea.
 * 
 */
public class EquivalenceSummary {

    private final String subject;
    private final String parent;
    private final ImmutableList<String> candidates;
    private final ImmutableMap<Publisher, ContentRef> equivalents;

    public EquivalenceSummary(String subject, Iterable<String> candidates, Map<Publisher, ContentRef> equivalents) {
        this(checkNotNull(subject), null, candidates, equivalents);
    }
    
    public EquivalenceSummary(String subject, @Nullable String parent, Iterable<String> candidates, Map<Publisher, ContentRef> equivalents) {
        this.subject = checkNotNull(subject);
        this.parent = parent;
        //handle jackson deserialization passing null values, ugh.
        this.candidates = candidates != null ? ImmutableList.copyOf(candidates)
                                             : ImmutableList.<String>of();
        this.equivalents = equivalents != null ? ImmutableMap.copyOf(equivalents)
                                               : ImmutableMap.<Publisher,ContentRef>of();
    }


    public String getSubject() {
        return this.subject;
    }

    public String getParent() {
        return this.parent;
    }

    public ImmutableList<String> getCandidates() {
        return this.candidates;
    }

    public ImmutableMap<Publisher, ContentRef> getEquivalents() {
        return this.equivalents;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof EquivalenceSummary) {
            EquivalenceSummary other = (EquivalenceSummary) that;
            return subject.equals(other.subject) 
                && Objects.equal(parent, other.parent)
                && candidates.equals(other.candidates)
                && equivalents.equals(other.equivalents);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(subject, parent, candidates, equivalents);
    }

    @Override
    public String toString() {
        return Objects
                .toStringHelper(getClass())
                .add("subject", subject)
                .add("parent", parent)
                .add("candidates", candidates)
                .add("equivalents", equivalents)
                .toString();
    }
}
