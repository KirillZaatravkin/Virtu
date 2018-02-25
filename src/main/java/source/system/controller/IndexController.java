package source.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.dao.LoginAction;
import source.system.service.IndexService;

import javax.servlet.http.HttpSession;

/**
 * Created by кирюха on 23.02.2018.
 */
@Controller
public class IndexController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private IndexService indexService;

    public IndexController() {
        int g = 0;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String index (@RequestParam(name = "nologin", required = false) Boolean nologin, Model model)
    {
        if (nologin == null) {
            model.addAttribute("nologin", false);
        } else {
            model.addAttribute("nologin", nologin);

        }
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/header")
    public String header (Model model)
    {
        return "header";
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, path = "/login")
    public String login (@RequestParam(name = "password") String password, @RequestParam(name = "login") String login, Model model)

    {

       if(indexService.FlagLogin(password, login).equals("user"))
       {
               httpSession.setAttribute("login", login);
               return "redirect:/index";

       }
       else
       {
           return  "redirect:/header";
       }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/index")
     public String indexmenu(Model model)
    {
        model.addAttribute("m",indexService.CurMonth());
        model.addAttribute("y",indexService.CurYear());
        model.addAttribute("l",indexService.LastLoad());
        return "index";
    }

}
