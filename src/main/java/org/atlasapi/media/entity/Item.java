/* Copyright 2009 British Broadcasting Corporation
   Copyright 2009 Meta Broadcast Ltd

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied. See the License for the specific language governing
permissions and limitations under the License. */

package org.atlasapi.media.entity;

import java.util.List;
import java.util.Set;

import org.atlasapi.content.rdf.annotations.RdfClass;
import org.atlasapi.content.rdf.annotations.RdfProperty;
import org.atlasapi.media.TransportType;
import org.atlasapi.media.vocabulary.DC;
import org.atlasapi.media.vocabulary.PO;
import org.joda.time.DateTime;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.metabroadcast.common.intl.Country;
import com.metabroadcast.common.time.DateTimeZones;

/**
 * @author Robert Chatley (robert@metabroadcast.com)
 * @author Lee Denison (lee@metabroadcast.com)
 * @author John Ayres (john@metabroadcast.com)
 */
@RdfClass(namespace = PO.NS)
public class Item extends Content {
	
	private Container<?> container;
	
	private Set<Version> versions = Sets.newHashSet();
	private List<CrewMember> people = Lists.newArrayList();
	
	private boolean isLongForm = false;
	private Boolean blackAndWhite;
	private Set<Country> countriesOfOrigin = Sets.newHashSet();
	private String sortKey;
	
	public Item(String uri, String curie, Publisher publisher) {
		super(uri, curie, publisher);
	}
	
	public Item() { }
	
	public void setContainer(Container<?> container) {
        this.container = container;
    }
    
    public Container<?> getContainer() {
		if (container == null) {
			return null;
		}
		return this.container.toSummary();
    }
    
    public Container<?> getFullContainer() {
		return container;
    }
	
	@RdfProperty(namespace = DC.NS)
	public boolean getIsLongForm() {
		return isLongForm;
	}

	public void setIsLongForm(boolean isLongForm) {
		this.isLongForm = isLongForm;
	}

	public void addVersion(Version version) {
		if (version.getProvider() == null) {
			version.setProvider(publisher);
		}
		versions.add(version);
	}

	@RdfProperty(relation = true, uri="version")
	public Set<Version> getVersions() {
		return versions;
	}
	
	public Set<Version> nativeVersions() {
		return Sets.filter(versions, new Predicate<Version>() {
			@Override
			public boolean apply(Version v) {
				return publisher.equals(v.getProvider());
			}
		});
	}
	
	public void setVersions(Set<Version> versions) {
		this.versions = Sets.newHashSet();
		addVersions(versions);
	}

	public void addVersions(Set<Version> versions) {
		for (Version version : versions) {
			addVersion(version);
		}
	}

	public boolean removeVersion(Version version) {
		return versions.remove(version);
	}
	
	public Set<Country> getCountriesOfOrigin() {
	    return countriesOfOrigin;
	}
	
	public void setCountriesOfOrigin(Set<Country> countries) {
	    this.countriesOfOrigin = Sets.newHashSet();
	    for (Country country : countries) {
	        countriesOfOrigin.add(country);
	    }
	}
	
	@RdfProperty(relation = true, uri="person")
	public List<CrewMember> getPeople() {
	    return people();
	}
	
	public List<CrewMember> people() {
	    return people;
	}
	
	public List<Actor> actors() {
	    return Lists.<Actor>newArrayList(Iterables.filter(people, Actor.class));
	}
	
	public void addPerson(CrewMember person) {
	    people.add(person);
	}
	
	public void setPeople(List<CrewMember> people) {
	    this.people = people;
	}
	
	public void setBlackAndWhite(Boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public Boolean isBlackAndWhite() {
        return blackAndWhite;
    }

	public boolean isAvailable() {
		for (Location location : locations()) {
			if (location.getAvailable()) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmbeddable() {
		for (Location location : locations()) {
			if (location.getTransportType() != null && TransportType.EMBED.equals(location.getTransportType())) {
				return true;
			}
		}
		return false;
	}
	
	private List<Location> locations() {
		List<Location> locations = Lists.newArrayList();
		for (Version version : getVersions()) {
			for (Encoding encoding : version.getManifestedAs()) {
				for (Location location : encoding.getAvailableAt()) {
					locations.add(location);
				}
			}
		}
		
		return locations;
	}
	
	@Override
	public Content copy() {
	    Item copy = new Item();
	    
	    Item.copyTo(this, copy);
	    
	    return copy;
	}
	
	public Item copyWithVersions(Set<Version> versions) {
	    Item copy = new Item();
        
        Item.copyToWithVersions(this, copy, versions);
        
        return copy;
	}
	
	public static void copyTo(Item from, Item to) {
	    copyToWithVersions(from, to, Sets.newHashSet(Iterables.transform(from.versions, Version.COPY)));
	}
	
	public static void copyToWithVersions(Item from, Item to, Set<Version> versions) {
        Content.copyTo(from, to);
        if (from.container != null) {
            to.container = from.container.toSummary();
        }
        to.isLongForm = from.isLongForm;
        to.people = Lists.newArrayList(Iterables.transform(from.people, CrewMember.COPY));
        to.versions = versions;
        to.blackAndWhite = from.blackAndWhite;
        to.countriesOfOrigin = Sets.newHashSet(from.countriesOfOrigin);
    }
	
    public Item withSortKey(String sortKey) {
        this.sortKey = sortKey;
        return this;
    }

    public Comparable<?> sortKey() {
        return sortKey;
    }
    
    public boolean isChild() {
        return this.container == null;
    }
    
    public ChildRef childRef() {
        return new ChildRef(this.getCanonicalUri(), sortKey, new DateTime(DateTimeZones.UTC));
    }

    public static final Function<Item, Item> COPY = new Function<Item, Item>() {
        @Override
        public Item apply(Item input) {
            return (Item) input.copy();
        }
    };
}
