package com.bunny.net.stream;

public class AddCaptionQueryBuilder {
  private final String videoId;
  private String label = null;
  private String captionFile = null;
  private String srcLang = null;
  
  /**
   * AddCaptionQuery builder
   * 
   * @param videoId Video ID to add captions to
   * @param srclang Source language code
   */
  public AddCaptionQueryBuilder(String videoId, String srclang) {
    this.videoId = videoId;
    this.srcLang = srclang;
  }

  /**
   * Set label
   * 
   * @param label Video label
   * @return AddCaptionQueryBuilder
   */
  public AddCaptionQueryBuilder label(String label) {
    this.label = label;
    return this;
  }
  
  /**
   * Set caption file
   * 
   * @param captionFile Base64 encoded caption file
   * @return AddCaptionQueryBuilder
   */
  public AddCaptionQueryBuilder captionFile(String captionFile) {
    this.captionFile = captionFile;
    return this;
  }
  
  /**
   * Build AddCaptionQuery from the given parameters
   * 
   * @return AddCaptionQuery
   */
  public AddCaptionQuery build() {
    return new AddCaptionQuery(this.videoId, this.label, this.captionFile, this.srcLang);
  }
  
  public class AddCaptionQuery {
    private final String videoId;
    private String label = null;
    private String captionFile = null;
    private String srcLang = null;
  
    /**
     * AddCaptionQuery
     * 
     * @param videoId Video ID to add captions to
     * @param label Captions label
     * @param captionFile Base64 encoded captions file
     * @param srcLang Source language code
     */
    public AddCaptionQuery(String videoId, String label, String captionFile, String srcLang) {
      this.captionFile = captionFile;
      this.label = label;
      this.videoId = videoId;
      this.srcLang = srcLang;
    }
    
    /**
     * Get video ID
     * 
     * @return Video ID
     */
    public String getVideoId() {
      return videoId;
    }

    /**
     * Get label
     * 
     * @return Captions label
     */
    public String getLabel() {
      return label;
    }

    /**
     * Get captions file
     * 
     * @return Base64 encoded captions file
     */
    public String getCaptionFile() {
      return captionFile;
    }

    /**
     * Get source language
     * 
     * @return Source language code
     */
    public String getSrcLang() {
      return srcLang;
    }
  }

}
