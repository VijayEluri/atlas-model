package org.atlasapi.media.util;

import org.atlasapi.media.entity.simple.Description;
import org.atlasapi.media.entity.simple.Item;
import org.atlasapi.media.entity.simple.Playlist;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class Descriptions {
    private Descriptions() {
    }
    
    public static Iterable<Item> getItems(Iterable<Description> descriptions) {
        return Iterables.transform(Iterables.filter(descriptions, IS_ITEM), TO_ITEM);
    }
    
    public static Iterable<Playlist> getPlaylists(Iterable<Description> descriptions) {
        return Iterables.transform(Iterables.filter(descriptions, IS_PLAYLIST), TO_PLAYLIST);
    }
    
    public static Predicate<Description> IS_PLAYLIST = new Predicate<Description>() {
        @Override
        public boolean apply(Description input) {
            return input instanceof Playlist;
        }
    };
    
    public static Predicate<Description> IS_ITEM = new Predicate<Description>() {
        @Override
        public boolean apply(Description input) {
            return input instanceof Item;
        }
    };
    
    public static Function<Description, Playlist> TO_PLAYLIST = new Function<Description, Playlist>() {
        @Override
        public Playlist apply(Description input) {
            return (Playlist) input;
        }
    };
    
    public static Function<Description, Item> TO_ITEM = new Function<Description, Item>() {
        @Override
        public Item apply(Description input) {
            return (Item) input;
        }
    };
}
