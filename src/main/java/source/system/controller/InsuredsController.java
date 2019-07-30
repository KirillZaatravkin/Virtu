package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.model.Insureds;
import source.system.service.InsuredsService;

@Controller
public class InsuredsController {

    @Autowired
    InsuredsService insuredsService;


    @RequestMapping(value = "/insureds", method = {RequestMethod.GET})
    public String co(Model model) {
        model.addAttribute("mass", "Заполни поля для поиска!");
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

    @RequestMapping(value = "/insuredadd", method = {RequestMethod.GET})
    public String add(Model model) {
        return "/insuredadd";
    }

    @RequestMapping(value = "/insuredadd", method = {RequestMethod.POST})
    public String addpost(@RequestParam(value = "firstName", required = false) String firstName,
                          @RequestParam(value = "lastName", required = false) String lastName,
                          @RequestParam(value = "middleName", required = false) String middleName,
                          @RequestParam(value = "pasportn", required = false) String pasportn,
                          @RequestParam(value = "pasports", required = false) String pasports, Model model) {

        if (firstName != "" && lastName != "" && middleName != "" && pasportn != "" && pasports != "") {
            Insureds insured = new Insureds(lastName, firstName, middleName, pasportn, pasports);
            insuredsService.saveInsured(insured);
            int id=insured.getId();
            model.addAttribute("idIns", id);
            model.addAttribute("fio", lastName+ " "+firstName+" "+middleName);
            return "redirect:/contract";
        } else {
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("middleName", middleName);
            model.addAttribute("pasportn", pasportn);
            model.addAttribute("pasports", pasports);
            model.addAttribute("errror", "Заполни все графы");
            return "/insuredadd";
        }


    }
}
