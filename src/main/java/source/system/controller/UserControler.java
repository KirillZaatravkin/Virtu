package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.service.UserService;

/**
 * Created by кирюха on 24.02.2018.
 */
@Controller

public class UserControler
{
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/userssee")
    public String userssee(Model model)
    {
        model.addAttribute("usersList",userService.getAllUsers());
        return "userssee";
    }

    @RequestMapping(path = "/useradd", method = {RequestMethod.POST, RequestMethod.GET})
    public String useradd (Model model, @RequestParam(name="fio",required = false) String fio, @RequestParam(name="logins", required = false) String logins, @RequestParam(name="password", required = false) String password, @RequestParam(name="groups", required = false) String groups)
    {
        if (fio!=null && logins !=null && password!=null && groups!=null) {

            userService.addUser(userService.newUser(password, logins, groups, fio, 0));
            return "redirect:/userssee";       }
        else
            {
            return "useradd";
            }
    }

    @RequestMapping(path = "/userupdate", method = {RequestMethod.POST, RequestMethod.GET})
    public String userupdate (Model model, @RequestParam(name="fio",required = false) String fio, @RequestParam(name="login", required = false) String login, @RequestParam(name="password", required = false) String password, @RequestParam(name="groups", required = false) String groups, @RequestParam(name="id", required = false) String id)
    {
        if (fio!=null && login !=null && password!=null && groups!=null && id!=null) {

            userService.updateUser(userService.newUser(password, login, groups, fio, Integer.parseInt(id)));
            return "redirect:/userssee";       }
        else if (id!=null)
        {   model.addAttribute("fio", userService.getUser(Integer.parseInt(id)).getFio());
            model.addAttribute("login", userService.getUser(Integer.parseInt(id)).getLogin());
            model.addAttribute("password", userService.getUser(Integer.parseInt(id)).getPassword());
            model.addAttribute("groups", userService.getUser(Integer.parseInt(id)).getGroups());
            model.addAttribute("id",id);

            return "userupdate";
        }
        else {
            return "redirect:/useradd";
        }
    }
}
