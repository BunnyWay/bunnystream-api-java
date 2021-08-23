package com.bunny.net.stream;

import java.util.Arrays;

public class BunnyStreamAPIException extends Exception {

  /**
   * Generated serial version UID
   */
  private static final long serialVersionUID = 6211015429695402886L;

  private enum HTTPStatusCode {
    HTTP_200 ("(200) Success.", 200),
    HTTP_404 ("(404) Not found. Check your video, collection, and library ID.", 404),
    HTTP_401 ("(401) Unauthorized. Check your API key.", 401),
    HTTP_403 ("(403) Access was denied.", 403),
    HTTP_500 ("(500) Internal server error.", 500),
    DEFAULT ("Unexpected error.", -1)
    ;
    
    private final String msg;
    private final Integer code;

    HTTPStatusCode(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
    
    public String getMsg() {
      return this.msg;
    }
    
    public static String resolveCode(int code) {
      return Arrays.stream(HTTPStatusCode.values()).filter(aEnum -> aEnum.code.equals(code)).findFirst().orElse(HTTPStatusCode.DEFAULT).getMsg();
    }
  }
  
  /**
   * BunnyStreamAPIException
   * 
   * @param apiError The error message to return
   * @param httpCode The HTTP code returned from the API
   */
  public BunnyStreamAPIException(String apiError, int httpCode) {
    super(apiError + " (" + HTTPStatusCode.resolveCode(httpCode) + ")");
  }
}
