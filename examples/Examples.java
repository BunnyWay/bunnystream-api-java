import com.bunny.net.stream.ListVideoQueryBuilder.ListVideoQuery;
import com.bunny.net.stream.UpdateVideoQueryBuilder.UpdateVideoQuery;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Examples 
{
    public static void main( String[] args )
    {
        BunnyStream bunnyStream = new BunnyStream("your-api-key", 1);
        
        /** List videos **/
        ListVideoQueryBuilder lvqb = new ListVideoQueryBuilder();
        ListVideoQuery lvq = lvqb
            .page(1)
            .perPage(20)
            .sortBy("date")
            .build();
            
        try {
          System.out.println(bunnyStream.listVideos(lvq).getList());
          ArrayList<Video> s = (ArrayList<Video>) bunnyStream.listVideos(lvq).getList();
          System.out.println(s.get(0).getGuid()); // Print out the GUID of the first video in the list
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        /** Get video **/
        try {
          System.out.println(bunnyStream.getVideo("video-guid").getStatus());
        } catch (Exception e) {
          System.out.println(e);
        }
        
        /** Update video (Note: a 403 forbidden error may also mean that you cannot use the collection specified) **/
        String testTitle = "New title " + Integer.toString((new Random()).nextInt(1000));
        UpdateVideoQueryBuilder uvqb = new UpdateVideoQueryBuilder("video-guid");
        UpdateVideoQuery uvq = uvqb
            .title(testTitle)
            .build();
        try {
          bunnyStream.updateVideo(uvq);
          System.out.println("Should have updated the title to: " + testTitle);
        } catch (Exception e) {
          System.out.println(e);
        }
        
        /** Create video **/
        String guid = "";
        try {
          Video p = bunnyStream.createVideo("Best Video", "");
          guid = (String) p.getGuid();
          System.out.println("Created video with GUID " + guid);
        } catch (Exception e) {
          System.out.println(e);
        }
        
        /** Upload video **/
        File f = new File(System.getProperty("user.home") + "/Desktop/sample-mp4-file.mp4");
        try {
          bunnyStream.uploadVideo(guid, f);
          System.out.println("Successfully uploaded video");
        } catch (Exception e) {
          System.out.println(e);
        }

    }
}
