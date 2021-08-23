package com.bunny.net.stream;

public class ListVideoQueryBuilder {
  private int page = 1;
  private int perPage = 10;
  private String sortBy = "date";
  private String collection = null;
  private String search = null;
  
  /**
   * Set page number
   * 
   * @param page Page number
   * @return ListVideoQueryBuilder
   */
  public ListVideoQueryBuilder page(int page) {
    this.page = page;
    return this;
  }
  
  /**
   * Set perPage value
   * 
   * @param perPage Results per page
   * @return ListVideoQueryBuilder
   */
  public ListVideoQueryBuilder perPage(int perPage) {
    this.perPage = perPage;
    return this;
  }
  
  /**
   * Set sortBy parameter
   * 
   * @param sortBy Sort by
   * @return ListVideoQueryBuilder
   */
  public ListVideoQueryBuilder sortBy(String sortBy) {
    this.sortBy = sortBy;
    return this;
  }
  
  /**
   * Set collection
   * 
   * @param collection Collection
   * @return ListVideoQueryBuilder
   */
  public ListVideoQueryBuilder collection(String collection) {
    this.collection = collection;
    return this;
  }
  
  /**
   * Set search query
   * 
   * @param search Search query
   * @return ListVideoQueryBuilder
   */
  public ListVideoQueryBuilder search(String search) {
    this.search = search;
    return this;
  }
  
  /**
   * Build ListVideoQuery from the given parameters
   * 
   * @return ListVideoQuery
   */
  public ListVideoQuery build() {
    return new ListVideoQuery(this.page, this.perPage, this.sortBy, this.search, this.collection);
  }
  
  public class ListVideoQuery {
    private int page;
    private int perPage;
    private String sortBy;
    private String search;
    private String collection;
    
    /**
     * ListVideoQuery
     * 
     * @param page Page number
     * @param perPage Results per page
     * @param sortBy Sort by value
     * @param search Search query
     * @param collection Collection ID
     */
    public ListVideoQuery(int page, int perPage, String sortBy, String search, String collection) {
      this.page = page;
      this.perPage = perPage;
      this.sortBy = sortBy;
      this.search = search;
      this.collection = collection;
    }
    
    /**
     * Get page
     * 
     * @return Page number
     */
    public int getPage() {
      return this.page;
    }
    
    /**
     * Get per page count
     * 
     * @return Results per page
     */
    public int getPerPage() {
      return this.perPage;
    }
    
    /**
     * Get sort by value
     * 
     * @return sortBy value
     */
    public String getSortBy() {
      return this.sortBy;
    }
    
    /**
     * Get search query
     * 
     * @return Search query
     */
    public String getSearch() {
      return this.search;
    }
    
    /**
     * Get collection ID
     * 
     * @return Collection ID
     */
    public String getCollection() {
      return this.collection;
    }
  }

}
