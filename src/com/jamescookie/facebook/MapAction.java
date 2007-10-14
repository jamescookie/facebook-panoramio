package com.jamescookie.facebook;

import com.jamescookie.facebook.api.MyFacebookRestClient;
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
        if (userId == null || userId.length() == 0) {
            return INPUT;
        }

        createNewProfileImage(userId);

        return SUCCESS;
    }

    public void createNewProfileImage(String otherUserId) {
        MyFacebookRestClient client = getClient();
        if (client != null) {
            String userId = null;
            try {
                userId = UserIdAction.getUserIdFromFaceBook(client);
            } catch (Exception e) {
                log.error("unable to determine userId", e);
            }
            if (otherUserId.equals(userId)) { 
                ProfileThread thread = new ProfileThread(userId, client);
                new Thread(thread).start();
            }
        }
    }

}
