package org.demis.darempredou.controller;

import org.demis.darempredou.domain.UserGroup;
import org.demis.darempredou.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
public class UserGroupController {

    private final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Autowired
    private UserGroupService service;

    @Autowired
    private View jsonView;

    @RequestMapping(value = "/rest/userGroup/{userGroupId}", method = RequestMethod.GET)
    public ModelAndView getUserGroup(@PathVariable("userGroupId") String userGroupId) {
        UserGroup userGroup = null;

        try {
            userGroup = service.get(userGroupId);
//            userGroup = new UserGroup();
        } catch (Exception e) {
            String sMessage = "Error invoking userGroup. [%1$s]";
            logger.error("Error invoking userGroup", e);
            return createErrorResponse(String.format(sMessage, e.toString()));
        }

        logger.debug("Returning userGroup: " + userGroup.toString());
        return new ModelAndView(jsonView, "data", userGroup);
    }

    private ModelAndView createErrorResponse(String message) {
        return new ModelAndView(jsonView, "error", message);
    }


    public void setService(UserGroupService service) {
        this.service = service;
    }

    public void setJsonView(View jsonView) {
        this.jsonView = jsonView;
    }
}
