package com.jamescookie.facebook;

import com.jamescookie.maps.GoogleApiKey;
import org.apache.struts2.ServletActionContext;

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

        createNewProfileImage(userId);

        return SUCCESS;
    }

    public void createNewProfileImage(String userId) throws Exception {
        ProfileThread thread = new ProfileThread(userId, getClient());
        new Thread(thread).start();
    }

}
