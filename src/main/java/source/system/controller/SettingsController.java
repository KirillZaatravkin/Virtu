package source.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import source.system.service.SettingsService;

/**
 * Created by кирюха on 25.02.2018.
 */
@Controller
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @RequestMapping( path = "/settingssee")
    public String settingssee(Model model)
    {
        model.addAttribute( "settingsList",settingsService.getAllSettings());
        return "settingssee";
    }

    @RequestMapping(path = "/settingsupdate", method = {RequestMethod.GET,RequestMethod.POST})
    public String settingsupdate (Model model, @RequestParam(name = "id") int id, @RequestParam(name = "settings", required = false) String settings, @RequestParam(name = "settingname", required = false) String settingname)

    {
        if(settings==null && settingname==null)
        {
            model.addAttribute("setting1",settingsService.getSetting(id));
            return "/settingsupdate";
        }
        else if(settings!=null && settingname!=null)
        {
            settingsService.updateSettings(settingsService.newSetting(id, settings, settingname));
            return "redirect:/settingssee";
        }
        return "redirect:/settingssee";

    }
}
