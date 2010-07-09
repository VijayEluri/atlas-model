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

import org.atlasapi.content.rdf.annotations.RdfClass;
import org.atlasapi.content.rdf.annotations.RdfProperty;
import org.atlasapi.media.vocabulary.PO;

/**
 * 
 * @author Robert Chatley (robert@metabroadcast.com)
 * @author Chris Jackson
 */
@RdfClass(namespace = PO.NS)
public class Brand extends Playlist {

    public Brand(String uri, String curie) {
		super(uri, curie);
	}
    
    public Brand() { /* some legacy code still requires a default constructor */ }

	@Override
    @RdfProperty(relation = true, namespace = PO.NS, uri = "episode")
    public List<Item> getItems() {
        return super.getItems();
    }

    @Override
    public void addItem(Item item) {
        super.addItem(item);
        
        if (item instanceof Episode) {
            ((Episode) item).setBrand(this);
        }
    }

    public Brand toSummary() {
        Brand summary = new Brand(this.getCanonicalUri(), this.getCurie());
        summary.setTitle(this.getTitle());
        summary.setDescription(this.getDescription());
        return summary;
    }
}