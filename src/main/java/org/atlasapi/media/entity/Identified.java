package org.atlasapi.media.entity;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import org.atlasapi.content.rdf.annotations.RdfProperty;
import org.atlasapi.media.vocabulary.OWL;
import org.atlasapi.media.vocabulary.PLAY_USE_IN_RDF_FOR_BACKWARD_COMPATIBILITY;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base type for descriptions of resources.
 *
 * @author Robert Chatley
 * @author Lee Denison
 */
public class Identified {

	private Long id;
	
	private String canonicalUri;

	private String curie;

	private Set<String> aliasUrls = Sets.newHashSet();
	private Set<Alias> aliases = Sets.newHashSet();
	
	private Set<LookupRef> equivalentTo = Sets.newHashSet();
	
	/**
	 * Records the time that the 3rd party reported that the
	 * {@link Identified} was last updated
	 */
	private DateTime lastUpdated;
	
	private DateTime equivalenceUpdate;

	private Map<String, String> customFields = Maps.newHashMap();
	
	public Identified(String uri, String curie) {
		this.canonicalUri = uri;
		this.curie = curie;
	}
	
	public Identified() { 
		/* allow anonymous entities */ 
		this.canonicalUri = null;
		this.curie = null;
	}
	
	public Identified(String uri) { 
		this(uri, null);
	}
	
	@RdfProperty(relation = true, namespace=OWL.NS, uri="sameAs")
	public Set<String> getAliasUrls() {
		return aliasUrls;
	}
	
	@RdfProperty(relation = true, namespace=OWL.NS, uri="sameAs")
    public Set<Alias> getAliases() {
        return aliases;
    }
	
	public void setCanonicalUri(String canonicalUri) {
		this.canonicalUri = canonicalUri;
	}
	
	public void setCurie(String curie) {
		this.curie = curie;
	}
	
	public void setAliasUrls(Iterable<String> urls) {
		this.aliasUrls = ImmutableSortedSet.copyOf(urls);
	}
	
    public void setAliases(Iterable<Alias> aliases) {
        this.aliases = ImmutableSet.copyOf(aliases);
    }
    
    public void addAliasUrl(String alias) {
        addAliasUrls(ImmutableList.of(alias));
    }
    
    public void addAlias(Alias alias) {
        addAliases(ImmutableList.of(alias));
    }
    
    public void addAliasUrls(Iterable<String> urls) {
        setAliasUrls(Iterables.concat(this.aliasUrls, ImmutableList.copyOf(urls)));
    }
	
	public void addAliases(Iterable<Alias> aliases) {
	    setAliases(Iterables.concat(this.aliases, ImmutableList.copyOf(aliases)));
	}
	
	public String getCanonicalUri() {
		return canonicalUri;
	}
	
	@RdfProperty(relation = false, namespace=PLAY_USE_IN_RDF_FOR_BACKWARD_COMPATIBILITY.NS, uri="curie")
	public String getCurie() {
		return curie;
	}

	public Set<String> getAllUris() {
		Set<String> allUris = Sets.newHashSet(getAliasUrls());
		allUris.add(getCanonicalUri());
		return Collections.unmodifiableSet(allUris);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "(uri:"  + canonicalUri + ")";
	}
	
	@Override
	public int hashCode() {
		if (canonicalUri == null) {
			return super.hashCode();
		}
		return canonicalUri.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (canonicalUri != null && obj instanceof Identified) {
			return canonicalUri.equals(((Identified) obj).canonicalUri);
		}
		return false;
	}
	
	public void setLastUpdated(DateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public DateTime getLastUpdated() {
		return lastUpdated;
	}
	
	public void addEquivalentTo(Described content) {
		checkNotNull(content.getCanonicalUri());
		this.equivalentTo.add(LookupRef.from(content));
	}
	
	public Set<LookupRef> getEquivalentTo() {
		return equivalentTo;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public DateTime getEquivalenceUpdate() {
	    return equivalenceUpdate;
	}
	
	public void setEquivalenceUpdate(DateTime equivalenceUpdate) {
	    this.equivalenceUpdate = equivalenceUpdate;
	}

	public void setCustomFields(@NotNull Map<String, String> customFields) {
		this.customFields = checkNotNull(customFields);
	}

	/**
	 * Adds a key-value custom field, if the key already exists it will be overwritten
	 * Since merging logic will combine all custom fields for everything in the equiv set proper key namespacing
	 * may be required to avoid a custom field being ignored in favour of a higher precedence sharing the custom field.
	 * @param key the name of the custom field
	 * @param value the value of the custom field
	 */
	public void addCustomField(@NotNull String key, @Nullable String value) {
		if(value == null) {
			return;
		}
		customFields.put(checkNotNull(key), value);
	}

	/**
	 * Adds each key-value entry as a custom field, overwriting existing customFields which share the same key
	 * @param customFields the map containing the key-value custom fields to add
	 */
	public void addCustomFields(@NotNull Map<String, String> customFields) {
		for(Map.Entry<String, String> entry : customFields.entrySet()) {
			addCustomField(entry.getKey(), entry.getValue());
		}
	}

	@Nullable
	public String getCustomField(@NotNull String key) {
		return customFields.getOrDefault(checkNotNull(key), null);
	}

	public boolean containsCustomFieldKey(@NotNull String key) {
		return customFields.containsKey(key);
	}

	public Map<String, String> getCustomFields() {
		return new HashMap<>(customFields);
	}

	public Set<String> getCustomFieldKeys() {
		return getCustomFieldKeys(null);
	}

	public Set<String> getCustomFieldKeys(@Nullable String regex) {
		if(regex == null) {
			return customFields.keySet();
		}
		Pattern regexPattern = Pattern.compile(regex);
		return customFields.keySet()
				.stream()
				.filter(key -> regexPattern.matcher(key).matches())
				.collect(Collectors.toSet());
	}

	public static final Function<Identified, String> TO_URI = new Function<Identified, String>() {

		@Override
		public String apply(Identified description) {
			return description.getCanonicalUri();
		}
	};
	
	public static final Function<Identified, Long> TO_ID = new Function<Identified, Long>() {
        @Override
        public Long apply(Identified input) {
            return input.getId();
        }
    };

	public void setEquivalentTo(Set<LookupRef> uris) {
		this.equivalentTo = uris;
	}
	
	public static final Comparator<Identified> DESCENDING_LAST_UPDATED = new Comparator<Identified>() {
        @Override
        public int compare(final Identified s1, final Identified s2) {
            if (s1.getLastUpdated() == null && s2.getLastUpdated() == null) {
                return 0;
            }
            if (s2.getLastUpdated() == null) {
                return -1;
            }
            if (s1.getLastUpdated() == null) {
                return 1;
            }
            
            return s2.getLastUpdated().compareTo(s1.getLastUpdated());
        }
    };
	
	 /**
     * This method attempts to preserve symmetry of
     * equivalence (since content is persisted independently
     * there is often a window of inconsistency)
     */
	public boolean isEquivalentTo(Described content) {
		return equivalentTo.contains(LookupRef.from(content))
	        || Iterables.contains(Iterables.transform(content.getEquivalentTo(), LookupRef.TO_URI), canonicalUri);
	}
	
	public static void copyTo(Identified from, Identified to) {
	    to.aliases = Sets.newHashSet(from.aliases);
	    to.aliasUrls = Sets.newHashSet(from.aliasUrls);
	    to.canonicalUri = from.canonicalUri;
	    to.curie = from.curie;
	    to.equivalentTo = Sets.newHashSet(from.equivalentTo);
	    to.lastUpdated = from.lastUpdated;
	    to.id = from.id;
	    to.customFields = from.customFields;
	}
	
	public static <T extends Identified> List<T> sort(List<T> content, final Iterable<String> orderIterable) {
        
        final ImmutableList<String> order = ImmutableList.copyOf(orderIterable);
        
        Comparator<Identified> byPositionInList = new Comparator<Identified>() {

            @Override
            public int compare(Identified c1, Identified c2) {
                return Ints.compare(indexOf(c1), indexOf(c2));
            }

            private int indexOf(Identified content) {
                for (String uri : content.getAllUris()) {
                    int idx = order.indexOf(uri);
                    if (idx != -1) {
                        return idx;
                    }
                }
                if (content.getCurie() != null) {
                    return order.indexOf(content.getCurie());
                }
                return -1;
            }
        };
        
        List<T> toSort = Lists.newArrayList(content);
        Collections.sort(toSort, byPositionInList);
        return toSort;
    }
}