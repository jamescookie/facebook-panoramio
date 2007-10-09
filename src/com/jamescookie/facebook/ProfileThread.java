package com.jamescookie.facebook;

import com.jamescookie.facebook.api.MyFacebookRestClient;
import com.jamescookie.web.photos.Utils;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProfileThread implements Runnable {
    private String userId;
    private MyFacebookRestClient client;
    private Logger log = Logger.getLogger(this.getClass());
    private static final String THUMBNAIL_DIR = "panoramio" + File.separator + "thumbs";

    public ProfileThread(String userId, MyFacebookRestClient client) {
        this.userId = userId;
        this.client = client;
    }

    public void run() {
        try {
            writeStreamToFile(getImage());
            refreshProfile();
        } catch (Exception e) {
            log.error("Problem creating image!", e);
        }
    }

    private void writeStreamToFile(InputStream is) throws IOException {
        File file = new File(getOutputFileName());
        FileOutputStream os = new FileOutputStream(file);
        byte[] buf = new byte[4096];
        for (int len = -1; (len = is.read(buf)) != -1;)
            os.write(buf, 0, len);
        os.flush();
        os.close();
    }

    private void refreshProfile() {
        String URL = CommonAction.FULL_URL + "/Profile.action?userId="+userId;

        try {
            client.fbml_refreshRefUrl(URL);
            client.profile_setFBML("<fb:ref url=\"" + URL + "\"/>", client.users_getLoggedInUser());
        } catch (Exception e) {
            log.error("error refreshing cache", e);
        }
    }

    protected String getOutputFileName() {
        return getThumbnailDirectory() + "world" + userId + ".jpg";
    }

    protected String getThumbnailDirectory() {
        return Utils.getPathToWebAppByDodgyMethod() + File.separator + THUMBNAIL_DIR + File.separator;
    }

    private InputStream getImage() throws Exception {
        log.debug("Creating image");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageWriter imageWriter = new ImageWriter(getResource(), outputStream);
        imageWriter.overlayImages(PanoramioService.getPhotos(userId), new File(getThumbnailDirectory()));
        outputStream.flush();
        outputStream.close();
        log.debug("Finished creating image");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    protected InputStream getResource() throws Exception {
        return getClass().getClassLoader().getResourceAsStream("world.jpg");
    }

    public static void main(String[] args) throws Exception {
        final ProfileThread mapAction = new ProfileThread("344887", null) {
            protected InputStream getResource() throws Exception {
                return new FileInputStream("world.jpg");
            }
            protected String getThumbnailDirectory() {
                return "";
            }
        };
        mapAction.writeStreamToFile(mapAction.getImage());
    }
}
