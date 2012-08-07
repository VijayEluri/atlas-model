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

package org.atlasapi.content.criteria.attribute;

import org.atlasapi.media.common.Identified;

public abstract class Attribute<T> implements QueryFactory<T> {

	protected final String name;
	private String javaAttributeName;
	private final Class<? extends Identified> target;
	private final boolean isCollectionOfValues;
	private String alias;
	
	Attribute(String name, Class<? extends Identified> target) {
		this(name, target, false);
	}
	
	Attribute(String name, Class<? extends Identified> target, boolean isCollectionOfValues) {
		this.name = name;
		this.target = target;
		this.isCollectionOfValues = isCollectionOfValues;
		this.javaAttributeName = name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Attribute<?>) {
			Attribute<?> attribute = (Attribute<?>) obj;
			return name.equals(attribute.name) && target.equals(attribute.target);
		}
		return false;
	}
	
	public String externalName() {
		return name;
	}
	
	public String javaAttributeName() {
		return javaAttributeName;
	}


	public Class<? extends Identified> target() {
		return target;
	}

	public boolean isCollectionOfValues() {
		return isCollectionOfValues;
	}
	
	public Attribute<T> withJavaAttribute(String javaAttribute) {
		this.javaAttributeName = javaAttribute;
		return this;
	}
	
	public Attribute<T> allowShortMatches() {
		this.alias = name;
		return this;
	}
	
	public Attribute<T> withAlias(String alias) {
		this.alias = alias;
		return this;
	}
	
	public String alias() {
		return alias;
	}

	public boolean hasAlias() {
		return alias != null;
	}
}
