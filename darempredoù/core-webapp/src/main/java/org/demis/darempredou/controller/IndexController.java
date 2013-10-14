package org.demis.darempredou.controller;

import org.demis.darempredou.domain.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
public class IndexController {

    @Autowired
    private View jsonView;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView test() {
        return new ModelAndView(jsonView, "data", new ApplicationUser());
    }
}

