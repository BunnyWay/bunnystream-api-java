package com.bunny.net.stream;

import org.json.JSONException;
import org.json.JSONObject;

abstract class BunnyStreamResponse {
  protected JSONObject response;
  
  public BunnyStreamResponse(JSONObject r) {
    this.response = r;
  }
  
  /**
   * Get raw response, if desired.
   * 
   * @param key The key of the value you wish to obtain
   * @return Raw object.
   * @throws JSONException If the key is not found
   */
  public Object getRaw(String key) throws JSONException {
    return response.get(key);
  }
  
  /**
   * Get JSON string of response.
   * 
   * @return JSON string
   */
  @Override
  public String toString() {
    return this.response.toString();
  }
}
