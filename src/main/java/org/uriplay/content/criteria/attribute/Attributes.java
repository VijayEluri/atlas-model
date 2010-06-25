/* Copyright 2009 Meta Broadcast Ltd

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied. See the License for the specific language governing
permissions and limitations under the License. */

package org.uriplay.content.criteria.attribute;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.uriplay.media.TransportType;
import org.uriplay.media.entity.Brand;
import org.uriplay.media.entity.Broadcast;
import org.uriplay.media.entity.Description;
import org.uriplay.media.entity.Encoding;
import org.uriplay.media.entity.Episode;
import org.uriplay.media.entity.Item;
import org.uriplay.media.entity.Location;
import org.uriplay.media.entity.Playlist;
import org.uriplay.media.entity.Version;

import com.google.common.collect.Maps;

public class Attributes {

	// Simple string-valued attributes
	public static final Attribute<String> ITEM_TITLE = stringAttribute("title", Item.class);
	public static final Attribute<String> PLAYLIST_TITLE = stringAttribute("title", Playlist.class);
	public static final Attribute<String> BRAND_TITLE = stringAttribute("title", Brand.class);
	
	public static final Attribute<String> DESCRIPTION_URI = stringAttribute("uri", "canonicalUri", Description.class);
	public static final Attribute<String> ITEM_URI = stringAttribute("uri", "canonicalUri", Item.class);
	public static final Attribute<String> PLAYLIST_URI = stringAttribute("uri", "canonicalUri", Playlist.class);
	public static final Attribute<String> EPISODE_URI = stringAttribute("uri", "canonicalUri", Episode.class);
	public static final Attribute<String> BRAND_URI = stringAttribute("uri", "canonicalUri", Brand.class);
	public static final Attribute<String> LOCATION_URI = stringAttribute("uri", Location.class);
	
	public static final Attribute<Boolean> ITEM_IS_LONG_FORM = new BooleanValuedAttribute("isLongForm", Item.class).allowShortMatches();
	
	public static final Attribute<String> ITEM_PUBLISHER = stringAttribute("publisher", Item.class);
	public static final Attribute<String> PLAYLIST_PUBLISHER = stringAttribute("publisher", Playlist.class);
	public static final Attribute<String> BRAND_PUBLISHER = stringAttribute("publisher", Brand.class);
	
	public static final Attribute<String> ENCODING_DATA_CONTAINER_FORMAT = stringAttribute("dataContainerFormat",  Encoding.class).allowShortMatches();
	
	// Lists of strings
	public static final Attribute<String> ITEM_GENRE = stringListAttribute("genre",  Item.class);
	public static final Attribute<String> ITEM_TAG = stringListAttribute("tag", Item.class);
	
	public static final Attribute<String> PLAYLIST_GENRE = stringListAttribute("genre",  Playlist.class);
    public static final Attribute<String> PLAYLIST_TAG = stringListAttribute("tag", Playlist.class);
	public static final Attribute<String> BRAND_GENRE = stringListAttribute("genre", Brand.class);
    public static final Attribute<String> BRAND_TAG = stringListAttribute("tag", Brand.class);
	
	// enums
	public static final Attribute<Enum<TransportType>> LOCATION_TRANSPORT_TYPE = new EnumValuedAttribute<TransportType>("transportType", TransportType.class, Location.class).allowShortMatches();

	// Simple integer-valued attributes
	public static final Attribute<Integer> EPISODE_POSITION = integerAttribute("position", "episodeNumber",  Episode.class).allowShortMatches();
	
	public static final Attribute<Integer> EPISODE_SEASON_POSITION = integerAttribute("seasonPosition", "seriesNumber",  Episode.class).allowShortMatches();
	
	public static final Attribute<Integer> VERSION_DURATION = integerAttribute("duration", Version.class).allowShortMatches();

	// Time based attributes
	public static final Attribute<DateTime> BROADCAST_TRANSMISSION_TIME = dateTimeAttribute("transmissionTime", Broadcast.class).allowShortMatches();
	public static final Attribute<DateTime> BROADCAST_TRANSMISSION_END_TIME = dateTimeAttribute("transmissionEndTime", Broadcast.class).allowShortMatches();
	public static final Attribute<String> BROADCAST_ON = stringAttribute("broadcastOn", Broadcast.class).allowShortMatches();
	
	// booleans
	public static final Attribute<Boolean> LOCATION_AVAILABLE = new BooleanValuedAttribute("available", Location.class).allowShortMatches();
	
	private static List<Attribute<?>> ALL_ATTRIBUTES = 
		Arrays.<Attribute<?>>asList(ITEM_TITLE, BRAND_TITLE, PLAYLIST_TITLE, 
								    ITEM_URI, BRAND_URI, PLAYLIST_URI, LOCATION_URI, ITEM_GENRE, 
								    ITEM_TAG, PLAYLIST_TAG, PLAYLIST_GENRE, BRAND_TAG, BRAND_GENRE,
								    ITEM_PUBLISHER, BRAND_PUBLISHER, PLAYLIST_PUBLISHER,
								    VERSION_DURATION,
								    BROADCAST_TRANSMISSION_TIME,
								    BROADCAST_ON,
								    LOCATION_TRANSPORT_TYPE,
								    EPISODE_POSITION,
								    EPISODE_SEASON_POSITION,
								    LOCATION_AVAILABLE,
								    ENCODING_DATA_CONTAINER_FORMAT,
								    ITEM_IS_LONG_FORM);
	
	public static final Map<String, Attribute<?>> lookup = lookupTable();
	
	public static Attribute<?> lookup(String name, Class<? extends Description> queryContext) {
		Attribute<?> attribute = lookup.get(name);
		if (attribute == null && name.indexOf('.') < 0) {
			attribute = lookup.get(queryContext.getSimpleName().toLowerCase() + "." + name);
		}
		return attribute;
	}

	private static Map<String, Attribute<?>> lookupTable() {
		Map<String, Attribute<?>> table = Maps.newHashMap();
		
		for (Attribute<?> attribute : ALL_ATTRIBUTES) {
			addToTable(table, attribute.externalName(), attribute);
			if (attribute.hasAlias()) {
				table.put(attribute.alias(), attribute);
			}
		}
		return table;
	}

	
	private static void addToTable(Map<String, Attribute<?>> table, String key, Attribute<?> attribute) {
		if (table.containsKey(key)) {
			throw new IllegalArgumentException("Duplicate name: " + key);
		}
		table.put(key, attribute);
		
	}

	private static StringValuedAttribute stringAttribute(String name,  String javaAttribute, Class<? extends Description> target) {
		StringValuedAttribute attribute = new StringValuedAttribute(name, target);
		attribute.withJavaAttribute(javaAttribute);
		return attribute;
	}
	
	private static StringValuedAttribute stringAttribute(String name, Class<? extends Description> target) {
		return new StringValuedAttribute(name, target);
	}
	
	private static IntegerValuedAttribute integerAttribute(String name,  String javaAttribute, Class<? extends Description> target) {
		IntegerValuedAttribute attribute = new IntegerValuedAttribute(name, target);
		attribute.withJavaAttribute(javaAttribute);
		return attribute;
	}
	
	private static IntegerValuedAttribute integerAttribute(String name, Class<? extends Description> target) {
		return new IntegerValuedAttribute(name, target);
	}
	
	private static DateTimeValuedAttribute dateTimeAttribute(String name, Class<? extends Description> target) {
		return new DateTimeValuedAttribute(name, target);
	}

	private static StringValuedAttribute stringListAttribute(String name, Class<? extends Description> target) {
		return new StringValuedAttribute(name, target, true);
	}
}
