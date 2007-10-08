package com.jamescookie.facebook;

public class ProfileAction extends CommonAction {
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }
}
