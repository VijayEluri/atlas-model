package org.atlasapi.media.channel;

import java.util.Set;

import com.google.common.collect.Sets;

public class Platform extends ChannelGroup {
    private Set<Long> regions = Sets.newHashSet(); 
    
    public void addRegion(Long regionId) {
        this.regions.add(regionId);
    }
    
    public void addRegion(Region region) {
        this.regions.add(region.getId());
    }
    
    public void setRegions(Iterable<Region> regions) {
        this.regions.clear();
        for (Region region : regions) {
            addRegion(region);
        }
    }
    
    public void setRegionIds(Iterable<Long> regionIds) {
        Sets.newHashSet(regionIds);        
    }
    
    public Set<Long> getRegions() {
        return regions;
    }
}
