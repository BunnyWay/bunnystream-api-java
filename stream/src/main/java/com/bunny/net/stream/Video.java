package com.bunny.net.stream;

import org.json.JSONObject;

public class Video {
  private JSONObject res;
  public Video(JSONObject res) {
    this.res = res;
  }
  
  /**
   * Get field from JSON object. If it is not present, return a blank string.
   * 
   * @param key Key in JSON object
   * @return String value
   */
  private String getField(String key) {
    String s = "";
    try {
      s = this.res.getString(key);
    } catch (Exception e) {
    }
    return s;
  }

  /**
   * Get video library ID
   * 
   * @return Video Library ID
   */
  public int getVideoLibraryId() {
    return Integer.parseInt(this.getField("videoLibraryId"));
  }

  /**
   * Get GUID (Video ID)
   * 
   * @return GUID/Video ID
   */
  public String getGuid() {
    return this.getField("guid");
  }

  /**
   * Get video title.
   * 
   * @return Video title
   */
  public String getTitle() {
    return this.getField("title");
  }

  /**
   * Get date uploaded.
   * 
   * @return Date uploaded
   */
  public String getDateUploaded() {
    return this.getField("dateUploaded");
  }

  /**
   * Get view count
   * 
   * @return View count
   */
  public long getViews() {
    return Long.parseLong(this.getField("views"));
  }

  /**
   * Check whether or not 
   * 
   * @return Public status (true if public, false otherwise)
   */
  public boolean isPublic() {
    return Boolean.parseBoolean(this.getField("isPublic"));
  }

  /**
   * Get video length
   * 
   * @return Video length
   */
  public long getLength() {
    return Long.parseLong(this.getField("length"));
  }

  /**
   * Get status
   * 
   * @return the status
   */
  public String getStatus() {
    return this.getField("status");
  }

  /**
   * Get frame rate
   * 
   * @return Framerate
   */
  public double getFrameRate() {
    return Double.parseDouble(this.getField("framerate"));
  }

  /**
   * Get width
   * 
   * @return Width
   */
  public int getWidth() {
    return Integer.parseInt(this.getField("width"));
  }

  /**
   * Get height
   * 
   * @return Height
   */
  public int getHeight() {
    return Integer.parseInt(this.getField("height"));
  }

  /**
   * Get available resolutions. Will be an empty string if it is not available yet.
   * 
   * @return The available resolutions
   */
  public String getAvailableResolutions() {
    return this.getField("availableResolutions");
  }

  /**
   * Get thumbnail count
   * 
   * @return the thumbnailCount
   */
  public Long getThumbnailCount() {
    return Long.parseLong(this.getField("thumbnailCount"));
  }

  /**
   * Get encode progress
   * 
   * @return Encode progress
   */
  public int getEncodeProgress() {
    return Integer.parseInt(this.getField("encodeProgress"));
  }

  /**
   * Get storage size
   * 
   * @return Storage size
   */
  public long getStorageSize() {
    return Long.parseUnsignedLong(this.getField("storageSize"));
  }

  /**
   * Get captions
   * 
   * @return Captions
   */
  public String getCaptions() {
    return this.getField("captions");
  }

  /**
   * Get MP4 fallback status
   * 
   * @return MP4 fallback status
   */
  public boolean isHasMP4Fallback() {
    return Boolean.parseBoolean(this.getField("hasMP4Fallback"));
  }

  /**
   * Get collection ID
   * 
   * @return Collection ID
   */
  public String getCollectionId() {
    return this.getField("collectionId");
  }

  /**
   * Get thumbnail filename
   * 
   * @return Thumbnail filename
   */
  public String getThumbnailFileName() {
    return this.getField("thumbnailFileName");
  }
  
  /**
   * Return string representation of Video
   */
  public String toString() {
    return this.res.toString();
  }
}
