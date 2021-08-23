package com.bunny.net.stream;

public class UpdateVideoQueryBuilder {
  private final String videoId;
  private String title = null;
  private String collection = null;

  /**
   * UpdateVideoQuery builder
   * 
   * @param videoId Video ID
   */
  public UpdateVideoQueryBuilder(String videoId) {
    this.videoId = videoId;
  }

  /**
   * Set collection
   * 
   * @param collection Collection ID
   * @return UpdateVideoQueryBuilder
   */
  public UpdateVideoQueryBuilder collection(String collection) {
    this.collection = collection;
    return this;
  }
  
  /**
   * Set title
   * 
   * @param title Video title
   * @return UpdateVideoQueryBuilder
   */
  public UpdateVideoQueryBuilder title(String title) {
    this.title = title;
    return this;
  }
  
  /**
   * Build UpdateVideoQuery from the given parameters
   * 
   * @return UpdateVideoQuery
   */
  public UpdateVideoQuery build() {
    return new UpdateVideoQuery(this.videoId, this.collection, this.title);
  }
  
  public class UpdateVideoQuery {
    private String videoId = "";
    private String collection = null;
    private String title = null;
    
    /**
     * UpdateVideoQuery
     * 
     * @param videoId Video ID to update
     * @param collection Collection to use
     * @param title Title to use
     */
    public UpdateVideoQuery(String videoId, String collection, String title) {
      this.videoId = videoId;
      this.collection = collection;
      this.title = title;
    }
    
    /**
     * Get title
     * 
     * @return Title
     */
    public String getTitle() {
      return this.title;
    }
    
    /**
     * Get collection
     * 
     * @return Collection ID
     */
    public String getCollection() {
      return this.collection;
    }
    
    /**
     * Get video ID
     * 
     * @return Video ID
     */
    public String getVideoId() {
      return this.videoId;
    }
  }

}
