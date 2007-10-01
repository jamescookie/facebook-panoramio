package com.jamescookie.facebook;

import com.jamescookie.maps.GoogleApiKey;
import org.apache.struts2.ServletActionContext;

public class ProfileAction extends CommonAction {
    public String getUserId() {
        return "344887";
    }

    public String getMapKey() {
        return GoogleApiKey.getKey(ServletActionContext.getRequest().getServerName());
    }

    public String execute() throws Exception {
        log.info("********************foing profile****************************");
        return SUCCESS;
    }
}
