package com.bunny.net.stream;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Requests {

  /**
   * Adds query parameters to a given URL.
   * 
   * @param baseUrl The base URL to append the query to.
   * @param parameters Array list of NameValuePair parameters.
   * @return URI including query parameters
   * @throws UnsupportedEncodingException Should not occur.
   */
  private static String addQueryParameters(String baseUrl, List<NameValuePair> parameters)
      throws UnsupportedEncodingException {
    String url = baseUrl;
    boolean isFirstParameter = true;
    for (NameValuePair parameter : parameters) {
      if (isFirstParameter) {
        url += "?"; // The first query parameter must be prepended by ?
        isFirstParameter = false;
      } else {
        url += "&"; // Subsequent parameters are prepended by &
      }
      url += parameter.getName() + "=" + URLEncoder.encode(parameter.getValue(), "UTF-8");
    }

    return url;
  }

  /**
   * Post request helper for application/json requests.
   * 
   * @param apiKey The API key.
   * @param uri Target of the POST request.
   * @param form Parameters to send along with the POST request.
   * @return JSONObject Response.
   * @throws BunnyStreamAPIException Error returned from the API
   * @throws Exception Unhandled exception
   */
  public static JSONObject post(String apiKey, String uri, List<NameValuePair> form)
      throws BunnyStreamAPIException, Exception {
    return post(apiKey, uri, form, "application/json");
  }

  /**
   * Get request helper for application/json requests.
   * 
   * @param apiKey The API key.
   * @param uri Target of the GET request.
   * @param parameters Query parameters to send along with the GET request.
   * @return JSONObject Response.
   * @throws BunnyStreamAPIException Error returned from the API
   * @throws Exception Unhandled exception.
   */
  public static JSONObject get(String apiKey, String uri, List<NameValuePair> parameters)
      throws BunnyStreamAPIException, Exception {
    return get(apiKey, uri, parameters, "application/json");
  }

  /**
   * Post request to the API.
   * 
   * @param apiKey The API key.
   * @param uri Target of the POST request.
   * @param form Parameters to send along with the POST request.
   * @param contentType The content type. Ex: application/json
   * @return JSONObject Response.
   * @throws BunnyStreamAPIException Error returned from the API
   * @throws Exception Unhandled exception
   */
  public static JSONObject post(String apiKey, String uri, List<NameValuePair> form,
      String contentType) throws BunnyStreamAPIException, Exception {
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpPost httpPost = new HttpPost(uri);

      // Set required HTTP headers
      httpPost.addHeader("AccessKey", apiKey);
      httpPost.addHeader("Content-Type", contentType);

      // Add form parameters
      JSONObject obj = new JSONObject();
      for (NameValuePair nvp : form) {
        obj.put(nvp.getName(), nvp.getValue());
      }

      httpPost.setEntity(new StringEntity(obj.toString(), "UTF-8"));

      ResponseHandler<String> responseHandler = response -> {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
          HttpEntity responseEntity = response.getEntity();
          return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
        } else {
          throw new ClientProtocolException(Integer.toString(status));
        }
      };
      String responseBody = httpclient.execute(httpPost, responseHandler);
      return new JSONObject(responseBody);
    } catch (ClientProtocolException e) {
      throw new BunnyStreamAPIException("API returned error code", Integer.parseInt(e.getMessage()));
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Put request to the API.
   * 
   * @param apiKey The API key.
   * @param uri Target of the PUT request.
   * @param file File to send with the put request.
   * @param contentType The content type. Ex: application/json
   * @return JSONObject Response.
   * @throws BunnyStreamAPIException Error returned from the API
   * @throws Exception Unhandled exception
   * @throws IOException File could not be read.
   */
  public static JSONObject put(String apiKey, String uri, File file, String contentType)
      throws IOException, BunnyStreamAPIException, Exception {
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpPut httpPut = new HttpPut(uri);

      // Set required HTTP headers
      httpPut.addHeader("AccessKey", apiKey);
      httpPut.addHeader("Content-Type", contentType);

      httpPut.setEntity(new ByteArrayEntity(Files.readAllBytes(file.toPath())));

      ResponseHandler<String> responseHandler = response -> {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
          HttpEntity responseEntity = response.getEntity();
          return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
        } else {
          throw new ClientProtocolException(Integer.toString(status));
        }
      };
      String responseBody = httpclient.execute(httpPut, responseHandler);
      return new JSONObject(responseBody);
    } catch (ClientProtocolException e) {
      throw new BunnyStreamAPIException("API returned error code", Integer.parseInt(e.getMessage()));
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Delete request to the API.
   * 
   * @param apiKey The API key.
   * @param uri Target of the DELETE request.
   * @param contentType The content type. Ex: application/json
   * @return JSONObject Response
   * @throws BunnyStreamAPIException Error returned from the API
   * @throws Exception Unhandled exception
   */
  public static JSONObject delete(String apiKey, String uri, String contentType) throws BunnyStreamAPIException, Exception {
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpDelete httpDelete = new HttpDelete(uri);

      // Set required HTTP headers
      httpDelete.addHeader("AccessKey", apiKey);
      httpDelete.addHeader("Content-Type", contentType);

      ResponseHandler<String> responseHandler = response -> {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
          HttpEntity responseEntity = response.getEntity();
          return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
        } else {
          throw new ClientProtocolException(Integer.toString(status));
        }
      };
      String responseBody = httpclient.execute(httpDelete, responseHandler);
      return new JSONObject(responseBody);
    } catch (ClientProtocolException e) {
      throw new BunnyStreamAPIException("API returned error code", Integer.parseInt(e.getMessage()));
    } catch (Exception e) {
      throw e;
    }
  }


  /**
   * Get request to the API.
   * 
   * @param apiKey The API key.
   * @param uri Target of the GET request.
   * @param parameters Query parameters to send along with the GET request.
   * @param contentType The content type. Ex: application/json
   * @return JSONObject Response
   * @throws BunnyStreamAPIException Error returned from the API
   * @throws Exception Unhandled exception
   */
  public static JSONObject get(String apiKey, String uri, List<NameValuePair> parameters,
      String contentType) throws BunnyStreamAPIException, Exception {
    try {
      CloseableHttpClient httpclient = HttpClients.createDefault();
      String target = uri;
      if (parameters != null) {
        target = addQueryParameters(target, parameters);
      }
      HttpGet httpGet = new HttpGet(target);

      // Set required HTTP headers
      httpGet.addHeader("AccessKey", apiKey);
      httpGet.addHeader("Content-Type", contentType);

      ResponseHandler<String> responseHandler = response -> {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
          HttpEntity responseEntity = response.getEntity();
          return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
        } else {
          throw new ClientProtocolException(Integer.toString(status));
        }
      };
      String responseBody = httpclient.execute(httpGet, responseHandler);
      return new JSONObject(responseBody);
    } catch (ClientProtocolException e) {
      throw new BunnyStreamAPIException("API returned error code", Integer.parseInt(e.getMessage()));
    } catch (UnsupportedEncodingException e) {
      throw new Exception("Should not occur. Please contact support.");
    } catch (Exception e) {
      throw e;
    }
  }
}
