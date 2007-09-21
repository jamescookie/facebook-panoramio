package com.jamescookie.facebook;

import com.jamescookie.facebook.api.MyFacebookRestClient;
import com.facebook.api.FacebookRestClient;
import com.jamescookie.maps.GoogleApiKey;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;

public class UserIdAction extends ActionSupport {
    private MyFacebookRestClient client;

    public void setClient(MyFacebookRestClient client) {
        this.client = client;
    }

    public String getMapKey() {
        return GoogleApiKey.getKey(ServletActionContext.getRequest().getServerName());
    }

    public String execute() throws Exception {
        System.out.println("client = " + client);
        Document document = client.getUserPreference(0);
        System.out.println("document = " + document);
        FacebookRestClient.printDom(document, "  ");
        

        return SUCCESS;
    }

}
