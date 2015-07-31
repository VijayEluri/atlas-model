package org.atlasapi.application.v3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.atlasapi.media.entity.Publisher;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

public class ApplicationConfiguration {
	
    @Deprecated
    /**
     * Use defaultConfiguration
     */
	public static final ApplicationConfiguration DEFAULT_CONFIGURATION = new ApplicationConfiguration(ImmutableMap.<Publisher, SourceStatus>of(), null);
	
    public static final ApplicationConfiguration defaultConfiguration() {
        return DEFAULT_CONFIGURATION;
    }
    
    private static final ApplicationConfiguration NO_API_KEY_CONFIG
        = ApplicationConfiguration.defaultConfiguration()
            .copyWithSourceStatuses(noAPIKeySourceStatusMap());
    
    public static final ApplicationConfiguration forNoApiKey() {
        return NO_API_KEY_CONFIG;
    }

    private static Map<Publisher, SourceStatus> noAPIKeySourceStatusMap() {
        return Maps.toMap(Publisher.all(), new Function<Publisher, SourceStatus>() {
            @Override
            public SourceStatus apply(Publisher input) {
                return input.enabledWithNoApiKey() ? SourceStatus.AVAILABLE_ENABLED : SourceStatus.UNAVAILABLE;
            }
        });
    }
	
	private final Map<Publisher, SourceStatus> sourceStatuses;
	private final Set<Publisher> enabledSources;
	private final List<Publisher> precedence;
	private final ImmutableSet<Publisher> writableSources;
	private final Boolean imagePrecedenceEnabled;
	
	private ApplicationConfiguration(Map<Publisher, SourceStatus> sourceStatuses, Set<Publisher> enabledSources, List<Publisher> precedence, Iterable<Publisher> writable,
	        Boolean imagePrecedenceEnabled) {
	    this.sourceStatuses = ImmutableMap.copyOf(sourceStatuses);
        this.enabledSources = ImmutableSet.copyOf(enabledSources);
        if(precedence == null) {
            this.precedence = null;
        } else {
            this.precedence = appendMissingPublishersTo(precedence);
        }
        this.writableSources = ImmutableSet.copyOf(writable);
        this.imagePrecedenceEnabled = imagePrecedenceEnabled;
	}

    ApplicationConfiguration(Map<Publisher, SourceStatus> sourceStatuses, List<Publisher> precedence) {
	    this(sourceStatuses, precedence, ImmutableSet.<Publisher>of(), null);
	}
    
    ApplicationConfiguration(Map<Publisher, SourceStatus> sourceStatuses, List<Publisher> precedence, Iterable<Publisher> writableSources) {
        this(sourceStatuses, enabledPublishers(sourceStatuses), precedence, writableSources, null);
    }

	ApplicationConfiguration(Map<Publisher, SourceStatus> sourceStatuses, List<Publisher> precedence, Iterable<Publisher> writableSources,
	        Boolean imagePrecedenceEnabled) {
	    this(sourceStatuses, enabledPublishers(sourceStatuses), precedence, writableSources, imagePrecedenceEnabled);
	}

    private static Set<Publisher> enabledPublishers(Map<Publisher, SourceStatus> sourceStatuses) {
	    return ImmutableSet.copyOf(Maps.filterValues(allSourcesStatuses(sourceStatuses), SourceStatus.IS_ENABLED).keySet());
    }

	private static Map<Publisher, SourceStatus> allSourcesStatuses(Map<Publisher, SourceStatus> configSpecificStatuses) {
	    Builder<Publisher, SourceStatus> statuses = ImmutableMap.builder();
	    for (Publisher source : ImmutableSet.copyOf(Publisher.values())) {
	         statuses.put(source, Optional.fromNullable(configSpecificStatuses.get(source)).or(source.getDefaultSourceStatus()));
        }
	    return statuses.build();
	}
	
	public boolean isEnabled(Publisher source) {
	    return enabledSources.contains(source);
	}
	
    public Set<Publisher> getEnabledSources() {
        return enabledSources;
    }
    
    public SourceStatus statusOf(Publisher source) {
        return Optional.fromNullable(sourceStatuses.get(source)).or(source.getDefaultSourceStatus());
    }
    
    public Map<Publisher, SourceStatus> sourceStatuses() {
        return allSourcesStatuses(sourceStatuses);
    }
    
    public ApplicationConfiguration enable(Publisher source) {
        return withSource(source, statusOf(source).enable());
    }
    
    public ApplicationConfiguration disable(Publisher source) {
        return withSource(source, statusOf(source).disable());
    }
    
    public ApplicationConfiguration request(Publisher source) {
        return withSource(source, statusOf(source).request());
    }
    
    public ApplicationConfiguration deny(Publisher source) {
        return withSource(source, statusOf(source).deny());
    }
    
    public ApplicationConfiguration revoke(Publisher source) {
        return withSource(source, statusOf(source).revoke());
    }
    
    public ApplicationConfiguration approve(Publisher source) {
        return withSource(source, statusOf(source).approve());
    }
    
    public ApplicationConfiguration agreeLicense(Publisher source) {
        return withSource(source, statusOf(source).agreeLicense());
    }
    
    public ApplicationConfiguration reset(Publisher source) {
        return withSource(source, statusOf(source).reset(source));
    }
    
    public ApplicationConfiguration withSource(Publisher source, SourceStatus status) {
        HashMap<Publisher,SourceStatus> mutableSources = Maps.newHashMap(sourceStatuses);
        mutableSources.put(source, status);
        
        Set<Publisher> mutablePublishers = Sets.newHashSet(enabledSources);
        if (status.isEnabled()) {
            mutablePublishers.add(source);
        } else {
            mutablePublishers.remove(source);
        }
        
        return new ApplicationConfiguration(mutableSources, mutablePublishers, precedence, writableSources, imagePrecedenceEnabled);
    }
    
    public ApplicationConfiguration withSources(Map<Publisher, SourceStatus> statuses) {
        HashMap<Publisher,SourceStatus> mutableSources = Maps.newHashMap(sourceStatuses);
        mutableSources.putAll(statuses);
        
        return new ApplicationConfiguration(mutableSources, precedence);
    }
	
    public ApplicationConfiguration copyWithPrecedence(List<Publisher> publishers) {
        return new ApplicationConfiguration(sourceStatuses, ImmutableList.copyOf(publishers));
    }
    
    public ApplicationConfiguration copyWithNullPrecedence() {
        return new ApplicationConfiguration(sourceStatuses, null);
    }
    
    public ApplicationConfiguration copyWithWritableSources(Iterable<Publisher> writable) {
        return new ApplicationConfiguration(sourceStatuses, enabledSources, precedence, writable, imagePrecedenceEnabled);
    }
    
    public ApplicationConfiguration copyWithSourceStatuses(Map<Publisher, SourceStatus> statuses) {
        return new ApplicationConfiguration(statuses, precedence, writableSources, imagePrecedenceEnabled);
    }
    
    public boolean canWrite(Publisher source) {
        return writableSources.contains(source);
    }
    
    public ImmutableSet<Publisher> writableSources() {
        return writableSources;
    }
	
	public Ordering<Publisher> publisherPrecedenceOrdering() {
		return Ordering.explicit(precedence);
	}
	
	public List<Publisher> precedence() {
		return precedence;
	}
	
	public boolean precedenceEnabled() {
		return precedence != null;
	}

	public List<Publisher> orderdPublishers() {
		if (!precedenceEnabled()) {
			return Publisher.all().asList();
		}
		return precedence;
	}
	
    /**
     * Temporary: these should be persisted and not hardcoded
     */
    private ImmutableList<Publisher> imagePrecedence() {
        return ImmutableList.of(Publisher.PA, Publisher.BBC, Publisher.C4);
    }
    
    public boolean imagePrecedenceEnabled() {
        // Inverted check to be null-safe and default to true if
        // imagePrecedenceEnabled is null
        return !Boolean.FALSE.equals(imagePrecedenceEnabled);
    }
    
    protected Boolean imagePrecedenceEnabledRawValue() {
        return imagePrecedenceEnabled;
    }
    
    public Ordering<Publisher> imagePrecedenceOrdering() {
//        return Ordering.explicit(appendMissingPublishersTo(imagePrecedence()));
        return publisherPrecedenceOrdering();
    }

    private List<Publisher> appendMissingPublishersTo(Iterable<Publisher> selected) {
        List<Publisher> publishers = Lists.newArrayList(selected);
        for (Publisher publisher : Publisher.values()) {
            if (!publishers.contains(publisher)) {
                publishers.add(publisher);
            }
        }
        return publishers;
    }
    
    private ImmutableList<Publisher> peoplePrecedence() {
        return ImmutableList.of(Publisher.RADIO_TIMES, Publisher.PA, Publisher.BBC, Publisher.C4, Publisher.ITV);
    }
    
    public boolean peoplePrecedenceEnabled() {
        return peoplePrecedence() != null;
    }
    
    public Ordering<Publisher> peoplePrecedenceOrdering() {
        return Ordering.explicit(appendMissingPublishersTo(peoplePrecedence()));
    }
}
