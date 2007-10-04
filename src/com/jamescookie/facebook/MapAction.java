package com.jamescookie.facebook;

import com.jamescookie.maps.GoogleApiKey;
import org.apache.struts2.ServletActionContext;

import java.io.FileOutputStream;
import java.net.URL;

public class MapAction extends CommonAction {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMapKey() {
        return GoogleApiKey.getKey(ServletActionContext.getRequest().getServerName());
    }

    public String execute() throws Exception {
        if (userId == null) {
            return INPUT;
        }

//        createNewProfileImage(userId);

        return SUCCESS;
    }

    public void createNewProfileImage(String userId) throws Exception {
        URL resource = getClass().getClassLoader().getResource("world.jpg");
        System.out.println(resource);
        FileOutputStream fileOutputStream = new FileOutputStream("newWorld.jpg");
        ImageWriter imageWriter = new ImageWriter(resource, fileOutputStream);
        imageWriter.overlayImages(PanoramioService.getPhotos(userId));
        fileOutputStream.flush();
        fileOutputStream.close();

    }

    public static void main(String[] args) throws Exception{
        final MapAction mapAction = new MapAction();
        mapAction.createNewProfileImage("344887");
    }
}
