package com.jamescookie.facebook;

public class ErrorAction extends CommonAction {

    public String execute() throws Exception {
        setClient(null);
        return SUCCESS;
    }
}
