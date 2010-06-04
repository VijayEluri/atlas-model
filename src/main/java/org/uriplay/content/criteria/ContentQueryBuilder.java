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

package org.uriplay.content.criteria;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.uriplay.content.criteria.attribute.QueryFactory;
import org.uriplay.content.criteria.operator.Operators;

import com.google.common.collect.Lists;
import com.metabroadcast.common.query.Selection;

public class ContentQueryBuilder {

	private final List<AtomicQuery> queries;
	private Selection selection;
	
	public ContentQueryBuilder(Iterable<AtomicQuery> queries) {
		this.queries = Lists.newArrayList(queries);
	}

	public static ContentQueryBuilder query() {
		return new ContentQueryBuilder(Collections.<AtomicQuery>emptyList());
	}
	
	public ContentQuery build() {
		return new ContentQuery(queries, selection);
	}
	
	public ContentQueryBuilder withSelection(Selection selection) {
		this.selection = selection;
		return this;
	}
	
	public ContentQueryBuilder equalTo(QueryFactory<String> attribute, String... values) {
		queries.add(attribute.createQuery(Operators.EQUALS, Arrays.asList(values)));
		return this;
	}
	
	public ContentQueryBuilder equalTo(QueryFactory<String> attribute, Iterable<String> values) {
		queries.add(attribute.createQuery(Operators.EQUALS, values));
		return this;
	}
	
	public ContentQueryBuilder searchFor(QueryFactory<String> attribute, String... values) {
		queries.add(attribute.createQuery(Operators.SEARCH,  Arrays.asList(values)));
		return this;
	}
	
	public ContentQueryBuilder equalTo(QueryFactory<Boolean> attribute, Boolean... values) {
		queries.add(attribute.createQuery(Operators.EQUALS, Arrays.asList(values)));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> ContentQueryBuilder equalTo(QueryFactory<Enum<T>> attribute, Enum<T> value) {
		queries.add(attribute.createQuery(Operators.EQUALS, Arrays.asList(value)));
		return this;
	}
	
	public ContentQueryBuilder equalTo(QueryFactory<Integer> attribute, Integer... values) {
		queries.add(attribute.createQuery(Operators.EQUALS, Arrays.asList(values)));
		return this;
	}
	
	public ContentQueryBuilder beginning(QueryFactory<String> attribute, String... values) {
		queries.add(attribute.createQuery(Operators.BEGINNING, Arrays.asList(values)));
		return this;
	}
	
	public ContentQueryBuilder greaterThan(QueryFactory<Integer> attribute, Integer... values) {
		queries.add(attribute.createQuery(Operators.GREATER_THAN, Arrays.asList(values)));
		return this;
	}
	public ContentQueryBuilder lessThan(QueryFactory<Integer> attribute, Integer... values) {
		queries.add(attribute.createQuery(Operators.LESS_THAN, Arrays.asList(values)));
		return this;
	}
		
	public ContentQueryBuilder after(QueryFactory<DateTime> attribute, DateTime... values) {
		queries.add(attribute.createQuery(Operators.AFTER, Arrays.asList(values)));
		return this;
	}
	
	public ContentQueryBuilder before(QueryFactory<DateTime> attribute, DateTime... values) {
		queries.add(attribute.createQuery(Operators.BEFORE, Arrays.asList(values)));
		return this;
	}
}
