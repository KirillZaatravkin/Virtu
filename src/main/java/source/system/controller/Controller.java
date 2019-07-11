package source.system.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by кирюха on 11.07.2019.
 */
@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String u(Model model) {
        return "/index";
    }




}

