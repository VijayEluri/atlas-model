package org.atlasapi.media.channel;

import java.util.Set;

import javax.annotation.Nullable;

import org.atlasapi.equiv.ChannelRef;
import org.atlasapi.media.entity.Alias;
import org.atlasapi.media.entity.Identified;
import org.atlasapi.media.entity.Image;
import org.atlasapi.media.entity.ImageTheme;
import org.atlasapi.media.entity.MediaType;
import org.atlasapi.media.entity.Publisher;
import org.atlasapi.media.entity.RelatedLink;

import com.metabroadcast.common.base.MorePredicates;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import static com.google.common.base.Preconditions.checkNotNull;

public class Channel extends Identified {

    private Publisher source;
    private Set<TemporalField<String>> titles = Sets.newHashSet();
    private Set<TemporalField<Image>> images = Sets.newHashSet();
    private Set<RelatedLink> relatedLinks = Sets.newHashSet();
    private MediaType mediaType;
    private String key;
    private Boolean highDefinition;
    private Boolean isTimeshifted;
    private Boolean regional;
    private Boolean adult;
    private Duration timeshift;
    private Publisher broadcaster;
    private DateTime advertiseFrom;
    private DateTime advertiseTo;
    private Set<Publisher> availableFrom;
    private Set<Long> variations = Sets.newHashSet();
    private Long parent;
    private Set<ChannelNumbering> channelNumbers = Sets.newHashSet();
    private LocalDate startDate;
    private LocalDate endDate;
    private ImmutableSet<String> genres = ImmutableSet.of();
    private String shortDescription;
    private String mediumDescription;
    private String longDescription;
    private String region;
    private ChannelType channelType;
    private Set<String> targetRegions = Sets.newHashSet();
    private Boolean interactive;
    private Set<ChannelRef> sameAs;
    
    public static final Predicate<Image> IS_PRIMARY_IMAGE = input -> input != null &&
            input.getTheme() != null &&
            input.getTheme().equals(ImageTheme.LIGHT_OPAQUE);

    public static final Function<Channel, String> TO_KEY = Channel::getKey;

    @Deprecated
    public Channel(
            Publisher publisher,
            String title,
            String key,
            Boolean highDefinition,
            MediaType mediaType,
            String uri
    ) {
        this(
                publisher,
                ImmutableSet.of(new TemporalField<>(title, null, null)),
                ImmutableSet.of(),
                ImmutableSet.of(),
                key,
                highDefinition,
                null,
                null,
                null,
                null,
                mediaType,
                uri,
                null,
                null,
                null,
                ImmutableSet.of(),
                ImmutableSet.of(),
                null,
                ImmutableSet.of(),
                null,
                null,
                ImmutableSet.of(),
                null,
                null,
                null,
                null,
                ChannelType.CHANNEL,
                ImmutableSet.of(),
                false,
                ImmutableSet.of()
        );
    }

    @Deprecated //Required for OldChannel
    protected Channel() { }

    private Channel(
            Publisher publisher,
            Set<TemporalField<String>> titles,
            Set<TemporalField<Image>> images,
            Set<RelatedLink> relatedLinks,
            String key,
            Boolean highDefinition,
            Boolean isTimeshifted,
            Boolean regional,
            Boolean adult,
            Duration timeshift,
            MediaType mediaType,
            String uri,
            Publisher broadcaster,
            DateTime advertiseFrom,
            DateTime advertiseTo,
            Iterable<Publisher> availableFrom,
            Iterable<Long> variations,
            Long parent,
            Iterable<ChannelNumbering> channelNumbers,
            LocalDate startDate,
            LocalDate endDate,
            Iterable<String> genres,
            String shortDescription,
            String mediumDescription,
            String longDescription,
            String region,
            ChannelType channelType,
            Set<String> targetRegions,
            Boolean interactive,
            Set<ChannelRef> sameAs
    ) {
        super(uri);
        this.source = publisher;
        this.regional = regional;
        this.timeshift = timeshift;
        this.titles = Sets.newHashSet(titles);
        this.images = Sets.newCopyOnWriteArraySet(images);
        this.relatedLinks = Sets.newHashSet(relatedLinks);
        this.parent = parent;
        this.key = key;
        this.highDefinition = highDefinition;
        this.isTimeshifted = isTimeshifted;
        this.adult = adult;
        this.mediaType = mediaType;
        this.broadcaster = broadcaster;
        this.startDate = startDate;
        this.endDate = endDate;
        this.advertiseFrom = advertiseFrom;
        this.advertiseTo = advertiseTo;
        this.availableFrom = ImmutableSet.copyOf(availableFrom);
        this.variations = Sets.newHashSet(variations);
        this.channelNumbers = Sets.newHashSet(channelNumbers);
        this.genres = ImmutableSet.copyOf(genres);
        this.shortDescription = shortDescription;
        this.mediumDescription = mediumDescription;
        this.longDescription = longDescription;
        this.region = region;
        this.channelType = channelType;
        this.targetRegions = targetRegions;
        this.interactive = checkNotNull(interactive);
        this.sameAs = sameAs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Channel copy) {
        Builder builder = new Builder();

        builder.source = copy.source;
        builder.uri = copy.getCanonicalUri();
        builder.key = copy.key;
        builder.broadcaster = copy.broadcaster;
        builder.images = Sets.newHashSet(copy.images);
        builder.titles = Sets.newHashSet(copy.titles);
        builder.relatedLinks = Sets.newHashSet(copy.relatedLinks);
        builder.mediaType = copy.mediaType;
        builder.regional = copy.regional;
        builder.highDefinition = copy.highDefinition;
        builder.isTimeshifted = copy.isTimeshifted;
        builder.adult = copy.adult;
        builder.timeshift = copy.timeshift;
        builder.advertiseFrom = copy.advertiseFrom;
        builder.advertiseTo = copy.advertiseTo;
        builder.availableFrom = Sets.newHashSet(copy.availableFrom);
        builder.variations = Sets.newHashSet(copy.variations);
        builder.parent = copy.parent;
        builder.channelNumbers = Sets.newHashSet(copy.channelNumbers);
        builder.startDate = copy.startDate;
        builder.endDate = copy.endDate;
        builder.genres = Sets.newHashSet(copy.genres);
        builder.shortDescription = copy.shortDescription;
        builder.mediumDescription = copy.mediumDescription;
        builder.longDescription = copy.longDescription;
        builder.region = copy.region;
        builder.targetRegions = Sets.newHashSet(copy.targetRegions);
        builder.interactive = copy.interactive;
        builder.aliases = Sets.newHashSet(copy.getAliases());
        builder.channelType = copy.channelType;
        builder.sameAs = Sets.newHashSet(copy.getSameAs());

        return builder;
    }
    
    public String getUri() {
        return getCanonicalUri();
    }
    
    /**
     * Gets the current or next title
     * @return the current title, if one exists, otherwise the 
     * first future title
     */
    @Nullable
    public String getTitle() {
        return TemporalField.currentOrFutureValue(titles);
    }

    @Nullable
    public String getTitleForDate(LocalDate date) {
        return Iterables.getOnlyElement(TemporalField.valuesForDate(titles, date), null);
    }

    public Iterable<TemporalField<String>> getAllTitles() {
        return ImmutableSet.copyOf(titles);
    }

    @Nullable
    public Boolean getHighDefinition() {
        return highDefinition;
    }

    @Nullable
    public Boolean getRegional() {
        return regional;
    }

    @Nullable
    public Boolean getAdult() {
        return adult;
    }

    @Nullable
    public Duration getTimeshift() {
        return timeshift;
    }

    @Nullable
    public Boolean isTimeshifted() {
        return isTimeshifted;
    }

    @Nullable
    public MediaType getMediaType() {
        return mediaType;
    }
    
    public Publisher getSource() {
        return source;
    }

    @Nullable
    public Publisher getBroadcaster() {
        return broadcaster;
    }

    @Nullable
    public DateTime getAdvertiseFrom() { return advertiseFrom; }

    @Nullable
    public DateTime getAdvertiseTo() { return advertiseTo; }
    
    public Set<Publisher> getAvailableFrom() {
        return availableFrom;
    }
    
    public Set<Long> getVariations() {
        return variations;
    }

    @Nullable
    public Long getParent() {
        return parent;
    }

    @Nullable
    public Set<ChannelNumbering> getChannelNumbers() {
        return ImmutableSet.copyOf(channelNumbers);
    }
    
    @Deprecated
    @Nullable
    public String getKey() {
        return key;
    }
    
    /**
     * @return primary channel image, or first future primary image if 
     * no current image
     */
    @Nullable
    public Image getImage() {
        Iterable<TemporalField<Image>> primaryImages = Iterables.filter(
            images,
            MorePredicates.transformingPredicate(TemporalField.toValueFunction(), IS_PRIMARY_IMAGE)
        );
        return TemporalField.currentOrFutureValue(primaryImages);
    }
    
    public Set<Image> getImages() {
        return TemporalField.currentValues(images);
    }
    
    public Set<Image> getImagesForDate(LocalDate date) {
        return TemporalField.valuesForDate(images, date);
    }
    
    public Iterable<TemporalField<Image>> getAllImages() {
        return ImmutableSet.copyOf(images);
    }

    @Nullable
    public LocalDate getStartDate() {
        return startDate;
    }

    public Set<RelatedLink> getRelatedLinks() {
        return relatedLinks;
    }

    @Nullable
    public LocalDate getEndDate() {
        return endDate;
    }

    @Nullable
    public Set<String> getGenres() {
        return this.genres;
    }
    
    public void setSource(Publisher source) {
        this.source = source;
    }
    
    public void addTitle(@Nullable String title) {
        addTitle(title, null);
    }
    
    public void addTitle(@Nullable String title, @Nullable LocalDate startDate) {
        addTitle(title, startDate, null);
    }
    
    public void addTitle(
            @Nullable String title,
            @Nullable LocalDate startDate,
            @Nullable LocalDate endDate
    ) {
        this.titles.add(new TemporalField<>(title, startDate, endDate));
    }
    
    public void addTitle(@Nullable TemporalField<String> title) {
        this.titles.add(title);
    }
    
    public void setTitles(Iterable<TemporalField<String>> titles) {
        this.titles = Sets.newHashSet(titles);
    }
    
    public void setMediaType(@Nullable MediaType mediaType) {
        this.mediaType = mediaType;
    }
    
    public void setKey(@Nullable String key) {
        this.key = key;
    }
    
    public void setHighDefinition(@Nullable Boolean highDefinition) {
        this.highDefinition = highDefinition;
    }

    public void setRegional(@Nullable Boolean regional) {
        this.regional = regional;
    }

    public void setAdult(@Nullable Boolean adult) {
        this.adult = adult;
    }

    public void setTimeshift(@Nullable Duration timeshift) {
        this.timeshift = timeshift;
    }

    public void setIsTimeshifted(@Nullable Boolean isTimeshifted) {
        this.isTimeshifted = isTimeshifted;
    }
    
    public void setBroadcaster(@Nullable Publisher broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void setAdvertiseFrom(@Nullable DateTime dateTime) {
        this.advertiseFrom = dateTime;
    }

    public void setAdvertiseTo(@Nullable DateTime dateTime) {
        this.advertiseTo = dateTime;
    }
    
    public void setAvailableFrom(Iterable<Publisher> availableOn) {
        this.availableFrom = ImmutableSet.copyOf(availableOn);
    }
    
    public void setVariations(Iterable<Channel> variations) {
        this.variations.clear();
        for (Channel variation : variations) {
            addVariation(variation);
        }
    }
    
    public void setVariationIds(Iterable<Long> variationIds) {
        this.variations = Sets.newHashSet(variationIds);
    }
    
    public void addVariation(Channel variation) {
        this.variations.add(variation.getId());
    }
    
    public void addVariation(@Nullable Long variationId) {
        this.variations.add(variationId);
    }
    
    public void setParent(@Nullable Channel parent) {
        if (parent != null) {
            this.parent = parent.getId();
        }
    }
    
    public void setParent(@Nullable Long parentId) {
        this.parent = parentId;
    }
    
    public void setChannelNumbers(Iterable<ChannelNumbering> channelNumbers) {
        this.channelNumbers = Sets.newHashSet(channelNumbers);
    }
    
    public void addChannelNumber(ChannelNumbering channelNumber) {
        this.channelNumbers.add(channelNumber);
    }

    public void setStartDate(@Nullable LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(@Nullable LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public void setGenres(Iterable<String> genres) {
        this.genres = ImmutableSet.copyOf(genres);
    }

    public void addChannelNumber(
            ChannelGroup channelGroup,
            @Nullable String channelNumber,
            @Nullable LocalDate startDate,
            @Nullable LocalDate endDate
    ) {
        ChannelNumbering channelNumbering = ChannelNumbering.builder()
                .withChannelGroup(channelGroup)
                .withChannelNumber(channelNumber)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
        this.channelNumbers.add(channelNumbering);
    }

    public void addImage(@Nullable Image image) {
        addImage(image, null, null);
    }
    
    public void addImage(@Nullable Image image, @Nullable LocalDate startDate) {
        addImage(image, startDate, null);
    }

    public void addImage(
            @Nullable Image image,
            @Nullable LocalDate startDate,
            @Nullable LocalDate endDate
    ) {
        this.images.add(new TemporalField<>(image, startDate, endDate));
    }
    
    public void addImage(@Nullable TemporalField<Image> image) {
        this.images.add(image);
    }
    
    public void setImages(Iterable<TemporalField<Image>> images) {
        this.images = Sets.newHashSet(images);
    }
    
    public void addRelatedLink(@Nullable RelatedLink relatedLink) {
        this.relatedLinks.add(relatedLink);
    }
    
    public void setRelatedLinks(Iterable<RelatedLink> relatedLinks) {
        this.relatedLinks = Sets.newHashSet(relatedLinks);
    }

    @Nullable
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(@Nullable String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Nullable
    public String getMediumDescription() {
        return mediumDescription;
    }

    public void setMediumDescription(@Nullable String mediumDescription) {
        this.mediumDescription = mediumDescription;
    }

    @Nullable
    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(@Nullable String longDescription) {
        this.longDescription = longDescription;
    }

    @Nullable
    public String getRegion() {
        return region;
    }

    public void setRegion(@Nullable String region) {
        this.region = region;
    }

    public Set<String> getTargetRegions() {
        return targetRegions;
    }

    public void setTargetRegions(Set<String> targetRegions) {
        this.targetRegions = targetRegions;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = checkNotNull(channelType);
    }

    public Boolean getInteractive() {
        return interactive;
    }

    public void setInteractive(@Nullable Boolean interactive) {
        this.interactive = interactive != null ? interactive : false;
    }

    public Set<ChannelRef> getSameAs() {
        return ImmutableSet.copyOf(sameAs);
    }

    public void addSameAs(ChannelRef sameAs) {
        this.sameAs.add(sameAs);
    }

    public void setSameAs(Set<ChannelRef> sameAs) {
        this.sameAs = Sets.newHashSet(sameAs);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Channel) {
            Channel target = (Channel) obj;
            return getId() != null ? Objects.equal(getId(), target.getId()) 
                                   : Objects.equal(getUri(), target.getUri());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : getUri().hashCode();
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(getId())
                .addValue(getCanonicalUri())
                .toString();
    }

    public ChannelRef toChannelRef() {
        return ChannelRef.fromChannel(this);
    }

    public static class Builder {

        private Publisher source;
        private String uri;
        private String key;
        private Publisher broadcaster;
        private Set<TemporalField<Image>> images = Sets.newHashSet();
        private Set<TemporalField<String>> titles = Sets.newHashSet();
        private Set<RelatedLink> relatedLinks = Sets.newHashSet();
        private MediaType mediaType;
        private Boolean regional;
        private Boolean highDefinition;
        private Boolean isTimeshifted;
        private Boolean adult;
        private Duration timeshift;
        private DateTime advertiseFrom;
        private DateTime advertiseTo;
        private Set<Publisher> availableFrom = ImmutableSet.of();
        private Set<Long> variations = Sets.newHashSet();
        private Long parent;
        private Set<ChannelNumbering> channelNumbers = Sets.newHashSet();
        private LocalDate startDate;
        private LocalDate endDate;
        private Set<String> genres = Sets.newHashSet();
        private String shortDescription;
        private String mediumDescription;
        private String longDescription;
        private String region;
        private Set<String> targetRegions = Sets.newHashSet();
        private Boolean interactive = false;
        private Set<Alias> aliases = ImmutableSet.of();
        private ChannelType channelType = ChannelType.CHANNEL;
        private Set<ChannelRef> sameAs = Sets.newHashSet();

        public Builder withSource(Publisher source) {
            this.source = source;
            return this;
        }

        public Builder withUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder withTitle(String title) {
            return withTitle(title, null, null);
        }

        public Builder withTitle(String title, LocalDate startDate) {
            return withTitle(title, startDate, null);
        }

        public Builder withTitle(String title, LocalDate startDate, LocalDate endDate) {
            this.titles.add(new TemporalField<>(title, startDate, endDate));
            return this;
        }

        public Builder withImage(Image image) {
            return withImage(image, null, null);
        }

        public Builder withImage(Image image, LocalDate startDate) {
            return withImage(image, startDate, endDate);
        }

        public Builder withImage(Image image, LocalDate startDate, LocalDate endDate) {
            this.images.add(new TemporalField<>(image, startDate, endDate));
            return this;
        };

        public Builder withImages(Iterable<Image> images) {
            this.images.clear();
            for (Image image : images) {
                this.images.add(new TemporalField<>(image, null, null));
            }
            return this;
        }

        public Builder withRelatedLink(RelatedLink relatedLink) {
            this.relatedLinks.add(relatedLink);
            return this;
        }

        public Builder withMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        @Deprecated
        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder withHighDefinition(Boolean highDefinition) {
            this.highDefinition = highDefinition;
            return this;
        }

        public Builder withTimeshifted(Boolean isTimeshifted) {
            this.isTimeshifted = isTimeshifted;
            return this;
        }

        public Builder withRegional(Boolean regional) {
            this.regional = regional;
            return this;
        }

        public Builder withAdult(Boolean isAdult) {
            this.adult = isAdult;
            return this;
        }

        public Builder withTimeshift(Duration timeshift) {
            this.timeshift = timeshift;
            return this;
        }

        public Builder withBroadcaster(Publisher broadcaster) {
            this.broadcaster = broadcaster;
            return this;
        }

        public Builder withAdvertiseFrom(@Nullable DateTime dateTime) {
            this.advertiseFrom = dateTime;
            return this;
        }

        public Builder withAdvertiseTo(@Nullable DateTime dateTime) {
            this.advertiseTo = dateTime;
            return this;
        }

        public Builder withAvailableFrom(Iterable<Publisher> availableFrom) {
            this.availableFrom = ImmutableSet.copyOf(availableFrom);
            return this;
        }

        public Builder withVariationIds(Iterable<Long> variationIds) {
            this.variations = Sets.newHashSet(variationIds);
            return this;
        }

        public Builder withVariations(Iterable<Channel> variations) {
            this.variations.clear();
            for (Channel variation : variations) {
                withVariation(variation);
            }
            return this;
        }

        public Builder withVariation(Long variationId) {
            this.variations.add(variationId);
            return this;
        }

        public Builder withVariation(Channel variation) {
            this.variations.add(variation.getId());
            return this;
        }

        public Builder withParent(Channel parent) {
            this.parent = parent.getId();
            return this;
        }

        public Builder withParent(Long parentId) {
            this.parent = parentId;
            return this;
        }

        public Builder withChannelNumbers(Iterable<ChannelNumbering> channelNumbers) {
            this.channelNumbers = Sets.newHashSet(channelNumbers);
            return this;
        }

        public Builder withChannelNumber(ChannelNumbering channelNumber) {
            this.channelNumbers.add(channelNumber);
            return this;
        }

        public Builder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder addGenre(String genre) {
            this.genres.add(genre);
            return this;
        }

        public Builder withGenres(Iterable<String> genres) {
            this.genres = Sets.newHashSet(genres);
            return this;
        }

        public Builder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder withMediumDescription(String mediumDescription) {
            this.mediumDescription = mediumDescription;
            return this;
        }

        public Builder withLongDescription(String longDescription) {
            this.longDescription = longDescription;
            return this;
        }

        public Builder withRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder withChannelType(ChannelType channelType) {
            this.channelType = checkNotNull(channelType);
            return this;
        }

        public Builder withTargetRegions(Set<String> targetRegions) {
            this.targetRegions = targetRegions;
            return this;
        }

        public Builder withInteractive(Boolean interactive) {
            this.interactive = checkNotNull(interactive);
            return this;
        }

        public Builder withAliases(Set<Alias> aliases) {
            this.aliases = aliases;
            return this;
        }

        public Builder withSameAs(Set<ChannelRef> sameAs) {
            this.sameAs = sameAs;
            return this;
        }

        public Channel build() {
            Channel channel = new Channel(
                    source,
                    titles,
                    images,
                    relatedLinks,
                    key,
                    highDefinition,
                    isTimeshifted,
                    regional,
                    adult,
                    timeshift,
                    mediaType,
                    uri,
                    broadcaster,
                    advertiseFrom,
                    advertiseTo,
                    availableFrom,
                    variations,
                    parent,
                    channelNumbers,
                    startDate,
                    endDate,
                    genres,
                    shortDescription,
                    mediumDescription,
                    longDescription,
                    region,
                    channelType,
                    targetRegions,
                    interactive,
                    sameAs
            );
            channel.setAliases(aliases);
            return channel;
        }
    }

}
