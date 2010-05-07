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

package org.uriplay.media.entity;

import java.util.HashSet;
import java.util.Set;

import org.jherd.core.MimeType;
import org.jherd.rdf.annotations.RdfClass;
import org.jherd.rdf.annotations.RdfProperty;
import org.uriplay.media.reference.entity.AudioFormat;
import org.uriplay.media.reference.entity.VideoFormat;
import org.uriplay.media.vocabulary.PLAY;

/**
 * @author Robert Chatley (robert@metabroadcast.com)
 * @author Lee Denison (lee@metabroadcast.com)
 */
@RdfClass(namespace = PLAY.NS)
public class Encoding extends Description {

	public static final String[] sizeUnits = {"bytes", "kB", "MB", "GB", "TB"};

    private Integer advertisingDuration;
    
    private Integer audioBitRate;

    private Integer audioChannels;

    // private AudioFormat audioCoding;
    private String audioCoding;

    private Integer bitRate;

    private Boolean containsAdvertising;

    private MimeType dataContainerFormat;

    private Long dataSize;

    private String distributor;

    private Boolean hasDOG;

    private Set<Location> availableAt = new HashSet<Location>();

    private String source;

    private String videoAspectRatio;

    private Integer videoBitRate;

    private String videoCoding;
    
    private Float videoFrameRate;

    private Integer videoHorizontalSize;

    private Boolean videoProgressiveScan;
    
    private Integer videoVerticalSize;

    @RdfProperty(relation = true)
    public Set<Location> getAvailableAt() { 
        return this.availableAt; 
    }

    public void setAvailableAt(Set<Location> availableAt) { 
        this.availableAt = availableAt;
    }

    public void addAvailableAt(Location location) {
    	this.availableAt.add(location);    
    }

    public boolean removeAvailableAt(Location location) {
    	return this.availableAt.remove(location);
    }
   
    public String formatDataSize() {
        if (dataSize != null) {
            StringBuffer result = new StringBuffer();
            long unitOrder = 1;

            for (String unit : sizeUnits) {
                if (dataSize / unitOrder > 0) {
                    result.append((dataSize / unitOrder)).append(" ").append(unit);
                    unitOrder = unitOrder * 1000;
                }
                else {
                    break;
                }
            }

            return result.toString();
        }
        else {
            return null;
        }
    }
  
    @RdfProperty
    public Integer getAdvertisingDuration() { 
        return this.advertisingDuration;
    }

    @RdfProperty
    public Integer getAudioBitRate() { 
        return this.audioBitRate;
    }

    @RdfProperty
    public Integer getAudioChannels() { 
        return this.audioChannels;
    }
    
//    @RdfProperty
//    @ManyToOne
//    public AudioFormat getAudioCoding() { 
//        return this.audioCoding; 
//    }
    
    @RdfProperty
    public String getAudioCoding() { 
        return this.audioCoding; 
    }

    @RdfProperty
    public Integer getBitRate() {
        return this.bitRate;
    }
    
    @RdfProperty
    public Boolean getContainsAdvertising() { 
        return this.containsAdvertising;
    }
    
    @RdfProperty
    public MimeType getDataContainerFormat() { 
        return this.dataContainerFormat; 
    }
    
    @RdfProperty
    public Long getDataSize() { 
        return this.dataSize;
    }

    @RdfProperty
    public String getDistributor() {
        return this.distributor;
    }

    @RdfProperty
    public Boolean getHasDOG() { 
        return this.hasDOG;
    }

    @RdfProperty
    public String getSource() {
        return this.source;
    }

    @RdfProperty
    public String getVideoAspectRatio() { 
        return this.videoAspectRatio;
    }

    @RdfProperty
    public Integer getVideoBitRate() { 
        return this.videoBitRate;
    }

    @RdfProperty
    public String getVideoCoding() { 
        return this.videoCoding; 
    }

    @RdfProperty
    public Float getVideoFrameRate() { 
        return this.videoFrameRate;
    }

    @RdfProperty
    public Integer getVideoHorizontalSize() { 
        return this.videoHorizontalSize;
    }

    @RdfProperty
    public Boolean getVideoProgressiveScan() { 
        return this.videoProgressiveScan;
    }

    @RdfProperty
    public Integer getVideoVerticalSize() { 
        return this.videoVerticalSize;
    }

    public void setAdvertisingDuration(Integer advertisingDuration) {
        this.advertisingDuration = advertisingDuration;
    }

    public void setAudioBitRate(Integer audioBitRate) {
        this.audioBitRate = audioBitRate;
    }

    public void setAudioChannels(Integer audioChannels) {
        this.audioChannels = audioChannels;
    }

//    public void setAudioCoding(AudioFormat audioCoding) { 
//        this.audioCoding = audioCoding; 
//    }

    public void setAudioCoding(String audioCoding) { 
    	MimeType type = AudioFormat.fromAltName(audioCoding);
		if (type != null) {
			this.audioCoding = type.toString();
		} else {
			this.audioCoding = null;
		}
    }
    
    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public void setContainsAdvertising(Boolean containsAdvertising) {
        this.containsAdvertising = containsAdvertising;
    }

    public void setDataContainerFormat(MimeType dataContainerFormat) { 
    	this.dataContainerFormat = dataContainerFormat;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public void setHasDOG(Boolean hasDOG) {
        this.hasDOG = hasDOG;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setVideoAspectRatio(String videoAspectRatio) {
        this.videoAspectRatio = videoAspectRatio;
    }

    public void setVideoBitRate(Integer videoBitRate) {
        this.videoBitRate = videoBitRate;
    }

    public void setVideoCoding(String videoCoding) { 
    	MimeType type = VideoFormat.fromAltName(videoCoding);
		if (type != null) {
			this.videoCoding = type.toString();
		} else {
			this.videoCoding = null;
		}
    }

    public void setVideoFrameRate(Float videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public void setVideoHorizontalSize(Integer videoHorizontalSize) {
        this.videoHorizontalSize = videoHorizontalSize;
    }

    public void setVideoProgressiveScan(Boolean videoProgressiveScan) {
        this.videoProgressiveScan = videoProgressiveScan;
    }

    public void setVideoVerticalSize(Integer videoVerticalSize) {
        this.videoVerticalSize = videoVerticalSize;
    }

	public boolean hasVideoCoding(MimeType... mimeTypes) {
		for (MimeType mimeType : mimeTypes) {
			if (mimeType.toString().equalsIgnoreCase(getVideoCoding())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasDataContainerFormat(MimeType... mimeTypes) {
		for (MimeType mimeType : mimeTypes) {
			if (mimeType.equals(getDataContainerFormat())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAudioCoding(MimeType... mimeTypes) {
		for (MimeType mimeType : mimeTypes) {
			if (getAudioCoding().equalsIgnoreCase(mimeType.toString())) {
				return true;
			}
		}
		return false;
	}

	public Encoding withVideoBitRate(Integer bitrate) {
		setVideoBitRate(bitrate);
		return this;
	}

	public Encoding withAudioBitRate(Integer bitrate) {
		setAudioBitRate(bitrate);
		return this;
	}

	public Encoding withVideoFrameRate(float framerate) {
		setVideoFrameRate(framerate);
		return this;
	}

	public Encoding withVideoHorizontalSize(int size) {
		setVideoHorizontalSize(size);
		return this;
	}
	
	public Encoding withVideoVerticalSize(int size) {
		setVideoVerticalSize(size);
		return this;
	}

	public Encoding withAudioCoding(String audioCoding) {
		setAudioCoding(audioCoding);
		return this;
	}
	
	public Encoding withVideoCoding(String videoCoding) {
		setVideoCoding(videoCoding);
		return this;
	}
}
