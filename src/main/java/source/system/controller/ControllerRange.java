package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.service.RangeService;


@Controller
public class ControllerRange
{

    @Autowired
    RangeService rangeService;


    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String u(@RequestParam( value = "addr1",required=false) String addr1, @RequestParam(value = "addr2",required=false) String addr2, Model model) {

        if(addr1!=null && addr2!=null) {

            if(rangeService.validIp(addr1)==false)
            {
                model.addAttribute("errors","Адрес 1 задан неправильно");
                return "/index";
            }
            if(rangeService.validIp(addr2)==false)
            {
                model.addAttribute("errors","Адрес 2 задан неправильно");
                return "/index";
            }

            if(rangeService.convertIpToInt(rangeService.splitIp(addr1))>rangeService.convertIpToInt(rangeService.splitIp(addr2)))
            {
                model.addAttribute("errors","неправильный диапозон: а1>а2");
                return "/index";
            }
            model.addAttribute("range", rangeService.getRange(addr1, addr2));
            model.addAttribute("addr1",addr1);
            model.addAttribute("addr2", addr2);
        }
        return "/index";

    }


}