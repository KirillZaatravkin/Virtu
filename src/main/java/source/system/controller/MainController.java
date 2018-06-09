package source.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by кирюха on 26.02.2018.
 */
@Controller
public class MainController {


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST},path = "/apovd")
    public String apovd (Model model)
    {

        return "apovd";
    }
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST},path = "/gibdd")
    public String gibdd (Model model)
    {

        return "gibdd";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST},path = "/prist")
    public String prist (Model model)
    {

        return "prist";
    }

    @RequestMapping(path = "/createword")
    public String createword (Model model)
    {

        return "createword";
    }

    @RequestMapping(path = "/echofilter")
    public String echofilter (Model model)
    {

        return "echofilter";
    }

    @RequestMapping(path = "/findface")
    public String findface (Model model)
    {

        return "findface";
    }

    @RequestMapping(path = "/preloader")
    public String preloader (Model model)
    {

        return "preloader";
    }

    @RequestMapping(path = "/statresediv")
    public String statresediv (Model model)
    {

        return "statresediv";
    }


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST},path = "/analiz")
    public String analiz (Model model)
    {

        return "analiz";
    }
}
