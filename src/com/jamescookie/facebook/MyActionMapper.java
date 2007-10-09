package com.jamescookie.facebook;

import com.opensymphony.xwork2.config.ConfigurationManager;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class MyActionMapper implements ActionMapper {
    public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
        ActionMapping mapping = null;
        String uri = RequestUtils.getServletPath(request);

        if (uri.contains("index.html")) {
            mapping = new ActionMapping("Start", "/panoramio", "", null);
        }

        return mapping;
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        return null; // will pass to next mapper instead
    }
}
