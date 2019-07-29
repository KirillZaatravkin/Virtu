package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.model.Contract;
import source.system.model.Property;
import source.system.service.ContractService;
import source.system.service.PropertyService;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Controller
public class ContractController {

    @Autowired
    ContractService contractService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping(value = "/contract", method = {RequestMethod.GET})
    public String co(Model model) {
        model.addAttribute("propertyList", propertyService.findAllPropertys());
        return "/contract";

    }

    @RequestMapping(value = "/contract", method = {RequestMethod.POST})
    public String c(@RequestParam(value = "sum", required = false) String sumSTR, @RequestParam(value = "year", required = false) String yearSTR,
                    @RequestParam(value = "property", required = false) String propertySTR, @RequestParam(value = "sq", required = false) String sqSTR,
                    @RequestParam(value = "date1", required = false) String date1, @RequestParam(value = "date2", required = false) String date2,
                    @RequestParam(value = "idIns", required = false) String idInsSTR, @RequestParam(value = "fio", required = false) String fio,
                    @RequestParam(value = "land", required = false) String land, @RequestParam(value = "oblast", required = false) String oblast,
                    @RequestParam(value = "punkt", required = false) String punkt, @RequestParam(value = "raion", required = false) String raion,
                    @RequestParam(value = "room", required = false) String room, @RequestParam(value = "street", required = false) String street,
                    @RequestParam(value = "stroenie", required = false) String stroenie, @RequestParam(value = "house", required = false) String house,
                    @RequestParam(value = "korpus", required = false) String korpus, @RequestParam(value = "number", required = false) String number,
                    @RequestParam(value = "save", required = false) String save, Model model) {
        int sum = 0;
        int year = 0;
        int property = 0;
        int sq = 0;
        int idIns = 0;
        int interval = 0;
        int error = 0;
        Date SQLdate1 = new java.sql.Date(0);
        Date SQLdate2 = new java.sql.Date(0);

        try {
            sum = Integer.parseInt(sumSTR);
        } catch (NumberFormatException e) {
            model.addAttribute("errorSum", "Данное поле имеет числовой формат");
            error = 1;
        }

        try {
            year = Integer.parseInt(yearSTR);
        } catch (NumberFormatException e) {
            model.addAttribute("errorYear", "Данное поле имеет числовой формат");
            error = 1;
        }
        try {
            property = Integer.parseInt(propertySTR);

        } catch (NumberFormatException e) {
            error = 1;
        }

        try {
            sq = Integer.parseInt(sqSTR);
        } catch (NumberFormatException e) {
            model.addAttribute("errorSq", "Данное поле имеет числовой формат");
            error = 1;
        }


        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            SQLdate1 = new java.sql.Date(format.parse(date1).getTime());
            SQLdate2 = new java.sql.Date(format.parse(date2).getTime());

            long milliseconds = SQLdate2.getTime() - SQLdate1.getTime();
            interval = (int) (milliseconds / (86400000));
        } catch (ParseException e) {
            e.printStackTrace();
            model.addAttribute("errorDat", "Данное поле имеет формат даты");
            error = 1;
        }

        if (error == 0) {
            if (save != null) {


                try {
                    idIns = Integer.parseInt(idInsSTR);
                    if (idIns == 0) {
                        model.addAttribute("errorIns", "Выбери страхователя");
                    }
                } catch (NumberFormatException e) {
                    model.addAttribute("errorIns", "Выбери страхователя");
                }
                Contract contract = new Contract(year, sum, sq, number, SQLdate1, SQLdate2, new Date(System.currentTimeMillis()), contractService.calcPrize(interval, sum, year, property, sq), new Date(System.currentTimeMillis()), land, oblast, raion, punkt, street, house, korpus, stroenie, room);
                contractService.saveContract(contract);
            }
        } else {
            model.addAttribute("prize", "Для рассчета введи праильные данные");
        }

        model.addAttribute("prize", contractService.calcPrize(interval, sum, year, property, sq));
        model.addAttribute("date_calc", new Date(System.currentTimeMillis()));
        model.addAttribute("sum", sum);
        model.addAttribute("year", year);
        model.addAttribute("property", property);
        model.addAttribute("sq", sq);
        model.addAttribute("date1", date1);
        model.addAttribute("date2", date2);
        model.addAttribute("number", number);
        model.addAttribute("idIns", idIns);
        model.addAttribute("fio", fio);
        model.addAttribute("house", house);
        model.addAttribute("korpus", korpus);
        model.addAttribute("land", land);
        model.addAttribute("oblast", oblast);
        model.addAttribute("punkt", punkt);
        model.addAttribute("korpus", korpus);
        model.addAttribute("room", room);
        model.addAttribute("street", street);
        model.addAttribute("raion", raion);
        model.addAttribute("stroenie", stroenie);
        model.addAttribute("propertyList", propertyService.findAllPropertys());

        return "/contract";

    }

    @RequestMapping(value = "/contract", method = {RequestMethod.GET})
    public String ui(Model model) {
        model.addAttribute("propertyList", propertyService.findAllPropertys());
        return "/contract";

    }

}

