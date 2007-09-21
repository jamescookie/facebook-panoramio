package com.jamescookie.facebook;

import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.RequestUtils;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.config.ConfigurationManager;

public class MyActionMapper implements ActionMapper {
    public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
        ActionMapping mapping = null;
        String uri = RequestUtils.getServletPath(request);

        if (uri.contains("index.html")) {
            mapping = new ActionMapping("Start", "", "", null);
        }

        return mapping;
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        return null; // will pass to next mapper instead
    }
}
