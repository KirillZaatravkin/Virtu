package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.service.RangeService;

/**
 * Created by кирюха on 11.07.2019.
 */
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    RangeService rangeService;

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String u(@RequestParam( value = "addr1",required=false) String addr1, @RequestParam(value = "addr2",required=false) String addr2, Model model) {

        if(addr1!=null && addr2!=null) {
            model.addAttribute("range", rangeService.getRange(addr1, addr2));
            model.addAttribute("addr1",addr1);
            model.addAttribute("addr2", addr2);
        }
        return "/index";

    }




}

