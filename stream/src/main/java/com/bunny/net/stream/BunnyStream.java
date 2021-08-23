package com.bunny.net.stream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import com.bunny.net.stream.AddCaptionQueryBuilder.AddCaptionQuery;
import com.bunny.net.stream.ListVideoQueryBuilder.ListVideoQuery;
import com.bunny.net.stream.UpdateVideoQueryBuilder.UpdateVideoQuery;

public class BunnyStream {
  private static String baseUrl = "https://video.bunnycdn.com/library/";
  private String apiKey;
  private int streamLibraryId;

  /**
   * BunnyStream Library
   * 
   * @param apiKey API key from Bunny.net's Stream API
   * @param streamLibraryId Bunny Stream library ID (is a number)
   */
  public BunnyStream(String apiKey, int streamLibraryId) {
    this.apiKey = apiKey;
    this.streamLibraryId = streamLibraryId;
  }

  /**
   * Generate base URL with the Stream library ID, including an endpoint.
   * 
   * @param endpoint The desired URL endpoint
   * @return Base URL with endpoint
   */
  public String generateBaseUrl(String endpoint) {
    return BunnyStream.baseUrl + this.streamLibraryId + endpoint;
  }

  /**
   * Lists videos.
   * 
   * @param lvq ListVideoQuery (Obtain by building a query with ListVideoQueryBuilder)
   * @return ListVideoResponse Response object
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public ListVideoResponse listVideos(ListVideoQuery lvq) throws BunnyStreamAPIException, Exception {
    int totalParameters = 3;
    if (lvq.getSearch() != null)
      totalParameters++;
    if (lvq.getCollection() != null)
      totalParameters++;

    List<NameValuePair> parameters = new ArrayList<NameValuePair>(totalParameters);

    parameters.add(new BasicNameValuePair("page", Integer.toString(lvq.getPage())));
    parameters.add(new BasicNameValuePair("perPage", Integer.toString(lvq.getPerPage())));
    parameters.add(new BasicNameValuePair("sortBy", lvq.getSortBy()));

    if (lvq.getSearch() != null)
      parameters.add(new BasicNameValuePair("search", lvq.getSearch()));
    if (lvq.getCollection() != null)
      parameters.add(new BasicNameValuePair("collection", lvq.getCollection()));

    return new ListVideoResponse(Requests.get(this.apiKey, this.generateBaseUrl("/videos"), parameters));
  }

  /**
   * Get video.
   * 
   * @param videoId The video ID of the video you wish to retrieve.
   * @return Video Response
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public Video getVideo(String videoId) throws BunnyStreamAPIException, Exception {
    return new Video(Requests.get(this.apiKey, this.generateBaseUrl("/videos/" + videoId), null));
  }

  /**
   * Update video.
   * 
   * @param uvq UpdateVideoQuery (Obtain by building a query with UpdateVideoQueryBuilder)
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public void updateVideo(UpdateVideoQuery uvq) throws BunnyStreamAPIException, Exception {
    int totalParameters = 0;
    if (uvq.getCollection() != null)
      totalParameters++;
    if (uvq.getTitle() != null)
      totalParameters++;

    if (totalParameters == 0)
      throw new Exception("You must update at least one parameter.");

    ArrayList<NameValuePair> body = new ArrayList<NameValuePair>(totalParameters);

    if (uvq.getCollection() != null)
      body.add(new BasicNameValuePair("collectionId", uvq.getCollection()));
    if (uvq.getTitle() != null)
      body.add(new BasicNameValuePair("title", uvq.getTitle()));

    Requests.post(this.apiKey, this.generateBaseUrl("/videos/" + uvq.getVideoId()), body);
  }

  /**
   * Set video thumbnail.
   * 
   * @param videoId Video ID of the video you wish to set the thumbnail on.
   * @param thumbnailUrl URL to the video thumbnail.
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public void setVideoThumbnail(String videoId, String thumbnailUrl)
      throws BunnyStreamAPIException, Exception {
    ArrayList<NameValuePair> body = new ArrayList<NameValuePair>(1);
    body.add(new BasicNameValuePair("thumbnailUrl", thumbnailUrl));

    Requests.post(this.apiKey, this.generateBaseUrl("/videos/" + videoId), body);
  }

  /**
   * Create new video.
   * 
   * @param title Title of the new video.
   * @param collectionId Collection ID of the new video (optional, set as empty string to not use)
   * @return Video object
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public Video createVideo(String title, String collectionId)
      throws BunnyStreamAPIException, Exception {
    int totalParameters = 1;
    if (!collectionId.equals(""))
      totalParameters++;

    ArrayList<NameValuePair> body = new ArrayList<NameValuePair>(totalParameters);

    if (!collectionId.equals(""))
      body.add(new BasicNameValuePair("collectionId", collectionId));
    body.add(new BasicNameValuePair("title", title));

    return new Video(Requests.post(this.apiKey, this.generateBaseUrl("/videos"), body));
  }

  /**
   * Upload new video. (async?)
   * 
   * @param videoId Video ID of the video you wish to attach to the upload.
   * @param file File object of the video.
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   * @throws IOException File could not be read
   */
  public void uploadVideo(String videoId, File file) throws IOException, BunnyStreamAPIException, Exception {
    if (file == null || !file.canRead()) {
      throw new IOException("File could not be read.");
    }
    Requests.put(this.apiKey, this.generateBaseUrl("/videos/" + videoId), file,
        "application/json");
  }

  /**
   * Create and upload new video.
   * 
   * @param title Title of the new video.
   * @param file File object of the video.
   * @param collectionId Collection ID of the new video (optional, set as empty string to not use)
   * @return Video Response
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public Video createAndUploadVideo(String title, File file, String collectionId) throws BunnyStreamAPIException, Exception {
    String guid = (String) this.createVideo(title, collectionId).getGuid();
    this.uploadVideo(guid, file);
    return this.getVideo(guid);
  }

  /**
   * Delete video.
   * 
   * @param videoId Video ID of the video you wish to delete.
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public void deleteVideo(String videoId) throws BunnyStreamAPIException, Exception {
    Requests.delete(this.apiKey, this.generateBaseUrl("/videos/" + videoId),
        "application/json");
  }

  /**
   * Delete caption.
   * 
   * @param videoId Video ID of the video you wish to delete the caption from.
   * @param srclang Language code of the caption you wish to delete.
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public void deleteCaption(String videoId, String srclang) throws BunnyStreamAPIException, Exception {
    Requests.delete(this.apiKey,
        this.generateBaseUrl("/videos/" + videoId + "/captions/" + srclang), "application/json");
  }

  /**
   * Fetch video from URL.
   * 
   * @param videoId Video ID of the video.
   * @param url URL of the video you wish to fetch.
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public void fetchVideo(String videoId, String url) throws BunnyStreamAPIException, Exception {
    this.fetchVideo(videoId, url, null);
  }

  /**
   * Fetch video from URL.
   * 
   * @param videoId Video ID of the video.
   * @param url URL of the video you wish to fetch.
   * @param headers Array list of headers to be sent with the request.
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public void fetchVideo(String videoId, String url, ArrayList<NameValuePair> headers) throws BunnyStreamAPIException, Exception {
    ArrayList<NameValuePair> body = new ArrayList<NameValuePair>(headers == null ? 1 : 2);
    body.add(new BasicNameValuePair("url", url));
    if (headers != null) {
      body.add((new BasicNameValuePair("headers", headers.toString())));
    }
    Requests.post(this.apiKey, this.generateBaseUrl("/videos/" + videoId + "/fetch"), body);
  }

  /**
   * Add caption file.
   * 
   * @param acq AddCaptionQuery (Obtain by building a query with AddCaptionQueryBuilder).
   * @throws Exception Unhandled exception
   * @throws BunnyStreamAPIException Error returned from the API
   */
  public void addCaptionFile(AddCaptionQuery acq) throws BunnyStreamAPIException, Exception {
    int parameters = 1;
    if (acq.getCaptionFile() != null)
      parameters++;
    if (acq.getLabel() != null)
      parameters++;

    ArrayList<NameValuePair> body = new ArrayList<NameValuePair>(parameters);
    if (acq.getLabel() != null)
      body.add(new BasicNameValuePair("label", acq.getLabel()));
    if (acq.getCaptionFile() != null)
      body.add(new BasicNameValuePair("captionFile", acq.getCaptionFile()));
    body.add(new BasicNameValuePair("srclang", acq.getSrcLang()));
    Requests.post(this.apiKey,
        this.generateBaseUrl("/videos/" + acq.getVideoId() + "/captions"), body);
  }

}
