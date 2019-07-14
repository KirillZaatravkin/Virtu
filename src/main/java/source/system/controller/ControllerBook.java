package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.service.BookService;
import java.util.ArrayList;
import java.util.HashMap;


@org.springframework.stereotype.Controller
public class ControllerBook {

    @Autowired
    BookService bookService;


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

