package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.service.InsuredsService;

@Controller
public class InsuredsController {

    @Autowired
    InsuredsService insuredsService;


    @RequestMapping(value = "/insureds", method = {RequestMethod.GET})
    public String co(Model model) {
        model.addAttribute("insuredsList", insuredsService.findAll());
        return "/insureds";
    }

    @RequestMapping(value = "/insureds", method = {RequestMethod.POST})
    public String c(@RequestParam(value = "firstName", required = false) String firstName,
                    @RequestParam(value = "lastName", required = false) String lastName,
                    @RequestParam(value = "middleName", required = false) String middleName, Model model) {

        if (firstName != null || middleName != null || lastName != null) {
            if (firstName == null) {
                firstName = "";
            }
            if (lastName == null) {
                lastName = "";
            }
            if (middleName == null) {
                middleName = "";
            }

            model.addAttribute("insuredsList", insuredsService.findInsureds(firstName, lastName, middleName));
        } else {
            model.addAttribute("insuredsList", insuredsService.findAll());
        }
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("middleName", middleName);
        return "/insureds";
    }
}
