package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.service.BookService;
import source.system.service.RangeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by кирюха on 11.07.2019.
 */
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    RangeService rangeService;
    @Autowired
    BookService bookService;

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

    @RequestMapping(value = "/book", method = {RequestMethod.POST, RequestMethod.GET})
    public String t(@RequestParam(value = "person", required = false) String person, Model model) {

        HashMap<String,ArrayList<String>> book= new HashMap<>();
        int flag=bookService.addTestBook(book);
        if(flag==1) {

            if (person != null) {
                ArrayList<String> telArray = bookService.findTel(book, person);
                model.addAttribute("person", person);
                model.addAttribute("telArray", telArray);
                if(telArray.size()==0)
                {
                    model.addAttribute("error", "Такого человека нет!");
                }
            }
        }
        return "/book";

    }

}

