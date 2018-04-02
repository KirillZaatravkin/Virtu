package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.service.MaskService;

/**
 * Created by кирюха on 29.03.2018.
 */

@Controller
public class MaskController
{
@Autowired
private MaskService maskService;


@RequestMapping(path="/masksee")
    public String masksee(Model model)
{
    model.addAttribute("maskList", maskService.getAllMask());
    return "masksee";
}

@RequestMapping(path = "/maskadd", method = {RequestMethod.POST, RequestMethod.GET})
    public String maskadd(Model model, @RequestParam(name="mask",required = false) String mask, @RequestParam(name="title", required = false) String title)
    {
        if (mask!=null && title !=null ) {

            maskService.addMask(maskService.newMask(mask, title,0));
            return "redirect:/masksee";       }
        else
        {
            return "maskadd";
        }
}

@RequestMapping(path = "/maskupdate", method = {RequestMethod.POST, RequestMethod.GET})
    public String maskUpdate (Model model, @RequestParam(name="mask",required = false) String mask, @RequestParam(name="title", required = false) String title, @RequestParam(name="id", required = false) String id) {
    if (mask != null && title != null && id != null) {

        maskService.updateMask(maskService.newMask(mask, title, Integer.parseInt(id)));
        return "redirect:/masksee";
    } else if (id != null) {
        model.addAttribute("mask", maskService.getMask(Integer.parseInt(id)).getMask());
        model.addAttribute("title", maskService.getMask(Integer.parseInt(id)).getTitle());
        model.addAttribute("id", id);

        return "maskupdate";
    } else {
        return "redirect:/maskadd";
    }
}
}
