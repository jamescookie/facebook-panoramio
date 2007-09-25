package com.jamescookie.facebook;

import com.jamescookie.facebook.api.MyFacebookRestClient;
import com.opensymphony.xwork2.Action;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public abstract class CommonAction implements Action, SessionAware {
    protected Logger log = Logger.getLogger(this.getClass());
    private Map session;
    private static final String FACEBOOK_CLIENT = "FACEBOOK_CLIENT";
    protected MyFacebookRestClient client;

    public MyFacebookRestClient getClient() {
        if (session.containsKey(FACEBOOK_CLIENT)) {
            client = (MyFacebookRestClient) session.get(FACEBOOK_CLIENT);
        }
        return client;
    }

    public void setClient(MyFacebookRestClient client) {
        //noinspection unchecked
        session.put(FACEBOOK_CLIENT, client);
        this.client = client;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public abstract String execute() throws Exception;
}
