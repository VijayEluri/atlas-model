package org.atlasapi.media.entity.simple;

import java.util.Set;
import java.util.SortedSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.atlasapi.media.TransportType;
import org.atlasapi.media.vocabulary.PLAY_SIMPLE_XML;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

@XmlType(name="item", namespace=PLAY_SIMPLE_XML.NS)
public class Item extends Description {

	private Integer episodeNumber;
	private Integer seriesNumber;
		
	private Set<Location> locations = Sets.newHashSet();

	private SortedSet<Broadcast> broadcasts = Sets.newTreeSet();
	
	private BrandSummary brandSummary;
	private SeriesSummary seriesSummary;
	private Set<Person> people = Sets.newHashSet();
	
	public Item() { /* required for XML/JSON tools */ }
	
	public Item(String uri) {
		super(uri);
	}
	
	public void addLocation(Location location) {
		locations .add(location);
	}
	
	@XmlElementWrapper(namespace=PLAY_SIMPLE_XML.NS, name="locations")
	@XmlElement(namespace=PLAY_SIMPLE_XML.NS, name="location")
	public Set<Location> getLocations() {
		return locations;
	}
	
	@XmlElementWrapper(namespace=PLAY_SIMPLE_XML.NS, name="people")
    @XmlElement(namespace=PLAY_SIMPLE_XML.NS, name="people")
    public Set<Person> getPeople() {
        return people;
    }
	
	public void setPeople(Iterable<Person> people) {
	    this.people = Sets.newHashSet(people);
	}
	
	public void setLocations(Iterable<Location> locations) {
		this.locations = Sets.newHashSet(locations);
	}
	
	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public Integer getSeriesNumber() {
		return seriesNumber;
	}

	public void setSeriesNumber(Integer seriesNumber) {
		this.seriesNumber = seriesNumber;
	}
	
	public boolean isAvailable() {
		for (Location location : locations) {
			if (location.isAvailable()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEmbeddable() {
		if (locations == null) {
			return false;
		}
		for (Location location : locations) {
			String transportType = location.getTransportType();
			if (transportType != null && TransportType.EMBED.toString().toLowerCase().equals(transportType.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	@XmlElement(namespace=PLAY_SIMPLE_XML.NS, name="brandSummary")
	public BrandSummary getBrandSummary() {
		return brandSummary;
	}
	
	public void setBrandSummary(BrandSummary brand) {
		this.brandSummary = brand;
	}

	public void addBroadcast(Broadcast broadcast) {
		broadcasts.add(broadcast);
	}
	
	@XmlElementWrapper(namespace=PLAY_SIMPLE_XML.NS, name="broadcasts")
	@XmlElement(namespace=PLAY_SIMPLE_XML.NS, name="broadcast")
	public SortedSet<Broadcast> getBroadcasts() {
		return broadcasts;
	}

	public void setBroadcasts(Iterable<Broadcast> broadcasts) {
		this.broadcasts = Sets.newTreeSet(broadcasts);
	}

	@XmlElement(namespace=PLAY_SIMPLE_XML.NS, name="seriesSummary")
	public SeriesSummary getSeriesSummary() {
		return seriesSummary;
	}
	
	public void setSeriesSummary(SeriesSummary seriesSummary) {
		this.seriesSummary = seriesSummary;
	}
	
	public Item copy() {
        Item copy = new Item();
        
        copyTo(copy);
        
        copy.setEpisodeNumber(getEpisodeNumber());
        copy.setSeriesNumber(getSeriesNumber());
        copy.setLocations(Iterables.transform(getLocations(), Location.TO_COPY));
        copy.setBroadcasts(Iterables.transform(getBroadcasts(), Broadcast.TO_COPY));
        
        if (getBrandSummary() != null) {
            copy.setBrandSummary(getBrandSummary().copy());
        }
        if (getSeriesSummary() != null) {
            copy.setSeriesSummary(getSeriesSummary().copy());
        }
        
        Set<Person> people = Sets.newHashSet();
        for (Person person: this.people) {
            people.add(person.copy());
        }
        copy.setPeople(people);
        
        return copy;
    }
	
	public static final Predicate<Item> HAS_AVAILABLE_LOCATION = new Predicate<Item>() {
        @Override
        public boolean apply(Item input) {
            return !input.getLocations().isEmpty() && !Iterables.isEmpty(Iterables.filter(input.getLocations(), Location.IS_AVAILABLE));
        }
    };
    
    public static final Predicate<Item> HAS_CURRENT_OR_UPCOMING_BROADCAST = new Predicate<Item>() {
        @Override
        public boolean apply(Item input) {
            return !input.getBroadcasts().isEmpty() && !Iterables.isEmpty(Iterables.filter(input.getBroadcasts(), Broadcast.IS_CURRENT_OR_UPCOMING));
        }
    };
    
    public static final Function<Item, Item> TO_COPY = new Function<Item, Item>() {
        @Override
        public Item apply(Item input) {
            return input.copy();
        }
    };
}
