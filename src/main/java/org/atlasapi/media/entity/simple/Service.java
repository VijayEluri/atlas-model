package org.atlasapi.media.entity.simple;


public class Service extends Description {

    @Override
    public Description copy() {
        Service service = new Service();
        super.copyTo(service);
        return service;
    }

}
