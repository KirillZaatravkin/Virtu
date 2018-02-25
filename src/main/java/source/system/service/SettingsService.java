package source.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import source.system.dao.SettingsDao;
import source.system.model.Settings;

import java.util.List;

/**
 * Created by кирюха on 25.02.2018.
 */
@Service
public class SettingsService {
    @Autowired
    private SettingsDao settingsDao;
    public Settings getSetting (int id)
    {
        return settingsDao.getSetting(id);
    }

    public List<Settings> getAllSettings()
    {
        return settingsDao.getAllSettings();
    }

    public void updateSettings (Settings settings)
    {
        settingsDao.updateSetting(settings);
    }
    public Settings newSetting (int id, String settings, String settingname)
    {
        return  settingsDao.newSetting(id, settings, settingname);
    }

}
