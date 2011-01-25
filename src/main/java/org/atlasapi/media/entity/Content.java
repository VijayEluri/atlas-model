/* Copyright 2010 Meta Broadcast Ltd

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Content extends Described {

	private MediaType mediaType = MediaType.VIDEO;
	
	private Specialization specialization;
	
	private ImmutableList<Clip> clips  = ImmutableList.of();
	
	public Content(String uri, String curie, Publisher publisher) {
		super(uri, curie, publisher);
	}

	public Content() { /* some legacy code still requires a default constructor */}
	
	public List<Clip> getClips() {
		return clips;
	}
	
	public void setClips(Iterable<Clip> clips) {
		this.clips = ImmutableList.copyOf(clips);
		for (Clip clip : clips) {
			clip.setClipOf(this.getCanonicalUri());
		}
	}
	
	public void addClip(Clip clip) {
		List<Clip> all = Lists.newArrayList(clips);
		all.add(clip);
		setClips(all);
	}
	
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	
	public MediaType getMediaType() {
		return this.mediaType;
	}
	
	public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
