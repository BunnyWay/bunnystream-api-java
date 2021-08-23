package com.bunny.net.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class ListVideoResponse extends BunnyStreamResponse {

  /**
   * ListVideoResponse
   * 
   * @param json JSONObject response from API
   */
  public ListVideoResponse(JSONObject json) {
    super(json);
  }

  /**
   * Get total number of videos
   * 
   * @return Total number of videos
   */
  public int getTotalItems() {
    return super.response.getInt("totalItems");
  }
  
  /**
   * Get current page
   * 
   * @return Current page number
   */
  public int getCurrentPage() {
    return super.response.getInt("currentPage");
  }
  
  /**
   * Get items shown per page
   * 
   * @return Items shown per page
   */
  public int getItemsPerPage() {
    return super.response.getInt("itemsPerPage");
  }
  
  /**
   * Get list of videos.
   * 
   * @return List of Video objects containing video data.
   */
  @SuppressWarnings("unchecked")
  public ArrayList<Video> getList() {
    List<Object> l = super.response.getJSONArray("items").toList();
    ArrayList<Video> vidArr = new ArrayList<Video>();
    l.forEach(t -> {
      vidArr.add(new Video(new JSONObject((Map<String, Object>) t)));
    });

    return vidArr;
  }
  
}
