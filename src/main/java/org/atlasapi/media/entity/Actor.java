package org.atlasapi.media.entity;

public class Actor extends CrewMember {

    public Actor() {
        super();
        this.withRole(Role.ACTOR);
    }
    
    private String character;
    
    public Actor(String uri, String curie, Publisher publisher) {
         super(uri, curie, publisher);
         this.withRole(Role.ACTOR);
    }
    
    public String character() {
        return character;
    }
    
    public Actor withCharacter(String character) {
        this.character = character;
        return this;
    }
    
    @Override
    public Actor withName(String name) {
        this.name = name;
        return this;
    }
    
    @Override
    public Actor withProfileLink(String profileLink) {
        this.addAlias(profileLink);
        return this;
    }
    
    public static Actor actor(String name, String character, Publisher publisher) {
        String key = Person.formatForUri(name);
        String uri = String.format(Person.BASE_URI, publisher.key(), key);
        String curie = publisher.key()+':'+key;
        return new Actor(uri, curie, publisher)
            .withCharacter(character)
            .withName(name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Actor) {
            Actor actor = (Actor) obj;
            return super.equals(actor) && character.equals(character);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getCanonicalUri().hashCode();
    }
    
    @Override
    public String toString() {
        return "Actor "+name+" plays "+character;
    }
}