package com.jamescookie.facebook;

import com.opensymphony.xwork2.config.ConfigurationManager;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;

import javax.servlet.http.HttpServletRequest;

public class MyActionMapper extends DefaultActionMapper {
    public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
        ActionMapping mapping = null;
        String uri = RequestUtils.getServletPath(request);

        if (uri.contains("index.html")) {
            mapping = new ActionMapping("Start", "/panoramio", "", null);
        } else {
            mapping = super.getMapping(request, configManager);
        }

        return mapping;
    }
}
